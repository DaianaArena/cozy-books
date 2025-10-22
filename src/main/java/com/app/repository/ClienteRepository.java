package main.java.com.app.repository;

import main.java.com.app.model.Cliente;
import main.java.com.app.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    public ClienteRepository() {
        // Constructor vacío - no necesitamos inicializar listas en memoria
    }

    public void registrar(Cliente cliente) {
        String sql = "INSERT INTO CLIENTE (nombre, documento, email, telefono, fecha_registro) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDocumento());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setTimestamp(5, cliente.getFechaRegistro());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setIdCliente(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void actualizar(Cliente cliente) {
        String sql = "UPDATE CLIENTE SET nombre = ?, documento = ?, email = ?, telefono = ? WHERE id_cliente = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDocumento());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setInt(5, cliente.getIdCliente());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM CLIENTE WHERE id_cliente = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE ORDER BY id_cliente";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("documento"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getTimestamp("fecha_registro"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
            e.printStackTrace();
        }

        return clientes;
    }

    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM CLIENTE WHERE id_cliente = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nombre"),
                            rs.getString("documento"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getTimestamp("fecha_registro"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public Cliente buscar(String criterio) {
        String sql = "SELECT * FROM CLIENTE WHERE nombre LIKE ? OR documento LIKE ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + criterio + "%");
            stmt.setString(2, "%" + criterio + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nombre"),
                            rs.getString("documento"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getTimestamp("fecha_registro"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void mostrarTodos() {
        List<Cliente> clientes = listar();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println("ID: " + (i + 1));
                clientes.get(i).mostrarCliente();
                System.out.println("-------------------");
            }
        }
    }

    public boolean estaVacio() {
        String sql = "SELECT COUNT(*) FROM CLIENTE";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar si hay clientes: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }
}
