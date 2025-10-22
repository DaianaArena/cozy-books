package main.java.com.app.repository;

import main.java.com.app.model.Libro;
import main.java.com.app.model.Autor;
import main.java.com.app.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroRepository {

    public LibroRepository() {
        // Constructor vacío - no necesitamos inicializar listas en memoria
    }

    public void registrar(Libro libro) {
        String sql = "INSERT INTO LIBRO (titulo, isbn, editorial, año, precio, genero, tipo_libro, stock, fecha_registro, id_autor, encuadernado, num_edicion, extension, permisos_impresion, duracion, plataforma, narrador) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getIsbn());
            stmt.setString(3, libro.getEditorial());
            stmt.setInt(4, libro.getAño());
            stmt.setDouble(5, libro.getPrecio());
            stmt.setString(6, libro.getGenero());
            stmt.setString(7, libro.getTipoLibro());
            stmt.setInt(8, libro.getStock());
            stmt.setTimestamp(9, libro.getFechaRegistro());
            stmt.setInt(10, libro.getAutor().getIdAutor());

            // Campos específicos por tipo
            stmt.setString(11, libro.getEncuadernado());
            stmt.setObject(12, libro.getNumEdicion() > 0 ? libro.getNumEdicion() : null);
            stmt.setString(13, libro.getExtension());
            stmt.setObject(14, libro.getPermisosImpresion());
            stmt.setObject(15, libro.getDuracion() > 0 ? libro.getDuracion() : null);
            stmt.setString(16, libro.getPlataforma());
            stmt.setString(17, libro.getNarrador());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        libro.setIdLibro(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar libro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void actualizar(Libro libro) {
        String sql = "UPDATE LIBRO SET titulo = ?, isbn = ?, editorial = ?, año = ?, precio = ?, genero = ?, tipo_libro = ?, stock = ?, id_autor = ?, encuadernado = ?, num_edicion = ?, extension = ?, permisos_impresion = ?, duracion = ?, plataforma = ?, narrador = ? WHERE id_libro = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getIsbn());
            stmt.setString(3, libro.getEditorial());
            stmt.setInt(4, libro.getAño());
            stmt.setDouble(5, libro.getPrecio());
            stmt.setString(6, libro.getGenero());
            stmt.setString(7, libro.getTipoLibro());
            stmt.setInt(8, libro.getStock());
            stmt.setInt(9, libro.getAutor().getIdAutor());
            stmt.setString(10, libro.getEncuadernado());
            stmt.setObject(11, libro.getNumEdicion() > 0 ? libro.getNumEdicion() : null);
            stmt.setString(12, libro.getExtension());
            stmt.setObject(13, libro.getPermisosImpresion());
            stmt.setObject(14, libro.getDuracion() > 0 ? libro.getDuracion() : null);
            stmt.setString(15, libro.getPlataforma());
            stmt.setString(16, libro.getNarrador());
            stmt.setInt(17, libro.getIdLibro());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM LIBRO WHERE id_libro = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Libro> listar() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.*, a.id_autor, a.nombre as autor_nombre, a.fecha_nacimiento, a.nacionalidad, a.biografia "
                +
                "FROM LIBRO l " +
                "INNER JOIN AUTOR a ON l.id_autor = a.id_autor " +
                "ORDER BY l.id_libro";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = crearLibroDesdeResultSet(rs);
                libros.add(libro);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
            e.printStackTrace();
        }

        return libros;
    }

    public Libro obtenerPorId(int id) {
        String sql = "SELECT l.*, a.id_autor, a.nombre as autor_nombre, a.fecha_nacimiento, a.nacionalidad, a.biografia "
                +
                "FROM LIBRO l " +
                "INNER JOIN AUTOR a ON l.id_autor = a.id_autor " +
                "WHERE l.id_libro = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return crearLibroDesdeResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener libro por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Libro> buscar(String criterio) {
        List<Libro> resultados = new ArrayList<>();
        String sql = "SELECT l.*, a.id_autor, a.nombre as autor_nombre, a.fecha_nacimiento, a.nacionalidad, a.biografia "
                +
                "FROM LIBRO l " +
                "INNER JOIN AUTOR a ON l.id_autor = a.id_autor " +
                "WHERE l.titulo LIKE ? OR l.editorial LIKE ? OR l.genero LIKE ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            String criterioLike = "%" + criterio + "%";
            stmt.setString(1, criterioLike);
            stmt.setString(2, criterioLike);
            stmt.setString(3, criterioLike);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Libro libro = crearLibroDesdeResultSet(rs);
                    resultados.add(libro);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar libros: " + e.getMessage());
            e.printStackTrace();
        }

        return resultados;
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>();
        String sql = "SELECT l.*, a.id_autor, a.nombre as autor_nombre, a.fecha_nacimiento, a.nacionalidad, a.biografia "
                +
                "FROM LIBRO l " +
                "INNER JOIN AUTOR a ON l.id_autor = a.id_autor " +
                "WHERE l.titulo LIKE ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + titulo + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Libro libro = crearLibroDesdeResultSet(rs);
                    resultados.add(libro);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar libros por título: " + e.getMessage());
            e.printStackTrace();
        }

        return resultados;
    }

    public List<Libro> buscarPorAutor(int idAutor) {
        List<Libro> resultados = new ArrayList<>();
        String sql = "SELECT l.*, a.id_autor, a.nombre as autor_nombre, a.fecha_nacimiento, a.nacionalidad, a.biografia "
                +
                "FROM LIBRO l " +
                "INNER JOIN AUTOR a ON l.id_autor = a.id_autor " +
                "WHERE l.id_autor = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAutor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Libro libro = crearLibroDesdeResultSet(rs);
                    resultados.add(libro);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar libros por autor: " + e.getMessage());
            e.printStackTrace();
        }

        return resultados;
    }

    public void actualizarStock(int id, int nuevoStock) {
        String sql = "UPDATE LIBRO SET stock = ? WHERE id_libro = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrarTodos() {
        List<Libro> libros = listar();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {
            System.out.println("-----------------------------------------------------");
            System.out.println("LISTA DE LIBROS REGISTRADOS");
            System.out.println("-----------------------------------------------------");

            for (int i = 0; i < libros.size(); i++) {
                System.out.println("ID: " + (i + 1));
                libros.get(i).mostrarLibro();
                System.out.println("-----------------------------------------------------");
            }
        }
    }

    public void mostrarPorAutor(int idAutor) {
        List<Libro> librosAutor = buscarPorAutor(idAutor);

        if (librosAutor.isEmpty()) {
            System.out.println("No se encontraron libros para este autor.");
        } else {
            System.out.println("-----------------------------------------------------");
            System.out.println("LIBROS DEL AUTOR ID: " + idAutor);
            System.out.println("-----------------------------------------------------");

            for (Libro libro : librosAutor) {
                libro.mostrarLibro();
                System.out.println("-----------------------------------------------------");
            }
        }
    }

    public boolean estaVacio() {
        String sql = "SELECT COUNT(*) FROM LIBRO";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar si hay libros: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    private Libro crearLibroDesdeResultSet(ResultSet rs) throws SQLException {
        // Crear autor
        Autor autor = new Autor(
                rs.getInt("id_autor"),
                rs.getString("autor_nombre"),
                rs.getDate("fecha_nacimiento"),
                rs.getString("nacionalidad"),
                rs.getString("biografia"));

        // Crear libro
        Libro libro = new Libro(
                rs.getInt("id_libro"),
                rs.getString("titulo"),
                rs.getString("isbn"),
                rs.getString("editorial"),
                rs.getInt("año"),
                rs.getDouble("precio"),
                rs.getString("genero"),
                rs.getString("tipo_libro"),
                rs.getInt("stock"),
                rs.getTimestamp("fecha_registro"),
                autor);

        // Establecer campos específicos por tipo
        libro.setEncuadernado(rs.getString("encuadernado"));
        libro.setNumEdicion(rs.getInt("num_edicion"));
        libro.setExtension(rs.getString("extension"));
        libro.setPermisosImpresion(rs.getBoolean("permisos_impresion"));
        libro.setDuracion(rs.getInt("duracion"));
        libro.setPlataforma(rs.getString("plataforma"));
        libro.setNarrador(rs.getString("narrador"));

        return libro;
    }
}
