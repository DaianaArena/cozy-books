package main.java.com.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    private static final String URL_WITHOUT_DB = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static final String DATABASE_NAME = "cozy_books";

    public static void setupDatabase() {
        try {
            // Conectar sin especificar base de datos
            Connection conn = DriverManager.getConnection(URL_WITHOUT_DB, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            // Crear base de datos si no existe
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
            System.out.println("Base de datos '" + DATABASE_NAME + "' verificada/creada correctamente.");

            // Usar la base de datos
            stmt.executeUpdate("USE " + DATABASE_NAME);

            // Verificar conexión
            if (DBConnection.testConnection()) {
                System.out.println("Conexión a la base de datos establecida correctamente.");
            } else {
                System.out.println("Error: No se pudo establecer la conexión a la base de datos.");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error al configurar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
