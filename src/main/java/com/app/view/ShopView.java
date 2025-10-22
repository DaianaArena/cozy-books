package main.java.com.app.view;

import main.java.com.app.controller.AutorController;
import main.java.com.app.controller.ClienteController;
import main.java.com.app.controller.LibroController;
import main.java.com.app.controller.VentaController;
import java.util.Scanner;

public class ShopView {

    private AutorController autorController;
    private ClienteController clienteController;
    private LibroController libroController;
    private VentaController ventaController;
    private Scanner lector;

    public ShopView() {
        this.autorController = new AutorController();
        this.clienteController = new ClienteController();
        this.libroController = new LibroController();
        this.ventaController = new VentaController();
        this.lector = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=====================================================");
        System.out.println("  Bienvenido al Sistema de gestión de ventas de ");
        System.out.println("  _____                 ____              _        ");
        System.out.println(" / ____|               |  _ \\            | |       ");
        System.out.println("| |     ___ _____   _  | |_) | ___   ___ | | _____ ");
        System.out.println("| |    / _ \\_  / | | | |  _ < / _ \\ / _ \\| |/ / __|");
        System.out.println("| |___| (_) / /| |_| | | |_) | (_) | (_) |   <\\__ \\");
        System.out.println(" \\_____\\___/___|\\__, | |____/ \\___/ \\___/|_|\\_\\___/");
        System.out.println("                __/  |                             ");
        System.out.println("               |____/                              ");
        System.out.println("=====================================================");
        System.out.println("Nombre de la tienda: Cozy Books");
        System.out.println("Sobre nosotros: Cozy Books es una tienda en línea de libros");
        System.out.println("fundada en 2013 por una familia apasionada por la lectura.");
        System.out.println("=====================================================");

        // Mostrar menú principal
        menu();
    }

    public void menu() {
        String op;

        do {
            System.out.println("-----------------------------------------------------");
            System.out.println("Menu principal");
            System.out.println("-----------------------------------------------------");
            System.out.println("Elija una de las siguientes opciones: ");
            System.out.println("");
            System.out.println("1. Registrar Autor");
            System.out.println("2. Registrar Cliente");
            System.out.println("3. Registrar Libro");
            System.out.println("4. Registrar Venta");
            System.out.println("5. Mostrar Autores");
            System.out.println("6. Mostrar Clientes");
            System.out.println("7. Mostrar Libros");
            System.out.println("8. Mostrar libros por autor");
            System.out.println("9. Mostrar Ventas");
            System.out.println("0. Salir");
            System.out.println("");
            System.out.println("Ingrese su opción:");
            op = lector.nextLine();

            ejecutarOpcion(op);

        } while (!op.equals("0"));
    }

    public void ejecutarOpcion(String opcion) {
        switch (opcion) {
            case "1":
                autorController.registrarAutor();
                break;
            case "2":
                clienteController.registrarCliente();
                break;
            case "3":
                libroController.registrarLibro();
                break;
            case "4":
                ventaController.registrarVenta();
                break;
            case "5":
                autorController.mostrarAutores();
                break;
            case "6":
                clienteController.mostrarClientes();
                break;
            case "7":
                libroController.mostrarLibros();
                break;
            case "8":
                libroController.mostrarLibrosPorAutor();
                break;
            case "9":
                ventaController.mostrarVentas();
                break;
            case "0":
                System.out.println("Hasta luego!");
                break;
            default:
                System.out.println("-----------------------");
                System.out.println("| ! |  Opción inválida");
                System.out.println("-----------------------");
                break;
        }
    }
}
