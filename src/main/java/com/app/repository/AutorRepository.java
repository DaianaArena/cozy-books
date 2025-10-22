package main.java.com.app.repository;

import main.java.com.app.model.Autor;
import main.java.com.app.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorRepository {

    public AutorRepository() {
        // Constructor vacío - no necesitamos inicializar listas en memoria
    }

    public void registrar(Autor autor) {
        String sql = "INSERT INTO AUTOR (nombre, fecha_nacimiento, nacionalidad, biografia) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, autor.getNombre());
            stmt.setDate(2, new java.sql.Date(autor.getFechaNacimiento().getTime()));
            stmt.setString(3, autor.getNacionalidad());
            stmt.setString(4, autor.getBiografia());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        autor.setIdAutor(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar autor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void actualizar(Autor autor) {
        String sql = "UPDATE AUTOR SET nombre = ?, fecha_nacimiento = ?, nacionalidad = ?, biografia = ? WHERE id_autor = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, autor.getNombre());
            stmt.setDate(2, new java.sql.Date(autor.getFechaNacimiento().getTime()));
            stmt.setString(3, autor.getNacionalidad());
            stmt.setString(4, autor.getBiografia());
            stmt.setInt(5, autor.getIdAutor());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar autor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM AUTOR WHERE id_autor = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar autor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Autor> listar() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM AUTOR ORDER BY id_autor";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Autor autor = new Autor(
                        rs.getInt("id_autor"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_nacimiento"),
                        rs.getString("nacionalidad"),
                        rs.getString("biografia"));
                autores.add(autor);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar autores: " + e.getMessage());
            e.printStackTrace();
        }

        return autores;
    }

    public Autor obtenerPorId(int id) {
        String sql = "SELECT * FROM AUTOR WHERE id_autor = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Autor(
                            rs.getInt("id_autor"),
                            rs.getString("nombre"),
                            rs.getDate("fecha_nacimiento"),
                            rs.getString("nacionalidad"),
                            rs.getString("biografia"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener autor por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public Autor buscar(String criterio) {
        String sql = "SELECT * FROM AUTOR WHERE nombre LIKE ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + criterio + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Autor(
                            rs.getInt("id_autor"),
                            rs.getString("nombre"),
                            rs.getDate("fecha_nacimiento"),
                            rs.getString("nacionalidad"),
                            rs.getString("biografia"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar autor: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void mostrarTodos() {
        List<Autor> autores = listar();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
        } else {
            for (int i = 0; i < autores.size(); i++) {
                System.out.println("ID: " + (i + 1));
                autores.get(i).mostrarAutor();
                System.out.println("-------------------");
            }
        }
    }

    public boolean estaVacio() {
        String sql = "SELECT COUNT(*) FROM AUTOR";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar si hay autores: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }
}
