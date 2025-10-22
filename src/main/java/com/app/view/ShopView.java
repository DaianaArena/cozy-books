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
            System.out.println("1. Autores");
            System.out.println("2. Clientes");
            System.out.println("3. Libros");
            System.out.println("4. Ventas");
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
                menuAutores();
                break;
            case "2":
                menuClientes();
                break;
            case "3":
                menuLibros();
                break;
            case "4":
                menuVentas();
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

    public void menuAutores() {
        String op;

        do {
            System.out.println("-----------------------------------------------------");
            System.out.println("MENU AUTORES");
            System.out.println("-----------------------------------------------------");
            System.out.println("1. Registrar Autor");
            System.out.println("2. Mostrar Autores");
            System.out.println("0. Volver al menú principal");
            System.out.println("");
            System.out.println("Ingrese su opción:");
            op = lector.nextLine();

            switch (op) {
                case "1":
                    autorController.registrarAutor();
                    break;
                case "2":
                    autorController.mostrarAutores();
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        } while (!op.equals("0"));
    }

    public void menuClientes() {
        String op;

        do {
            System.out.println("-----------------------------------------------------");
            System.out.println("MENU CLIENTES");
            System.out.println("-----------------------------------------------------");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("0. Volver al menú principal");
            System.out.println("");
            System.out.println("Ingrese su opción:");
            op = lector.nextLine();

            switch (op) {
                case "1":
                    clienteController.registrarCliente();
                    break;
                case "2":
                    clienteController.mostrarClientes();
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        } while (!op.equals("0"));
    }

    public void menuLibros() {
        String op;

        do {
            System.out.println("-----------------------------------------------------");
            System.out.println("MENU LIBROS");
            System.out.println("-----------------------------------------------------");
            System.out.println("1. Registrar Libro");
            System.out.println("2. Mostrar Libros");
            System.out.println("3. Mostrar libros por autor");
            System.out.println("0. Volver al menú principal");
            System.out.println("");
            System.out.println("Ingrese su opción:");
            op = lector.nextLine();

            switch (op) {
                case "1":
                    libroController.registrarLibro();
                    break;
                case "2":
                    libroController.mostrarLibros();
                    break;
                case "3":
                    libroController.mostrarLibrosPorAutor();
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        } while (!op.equals("0"));
    }

    public void menuVentas() {
        String op;

        do {
            System.out.println("-----------------------------------------------------");
            System.out.println("MENU VENTAS");
            System.out.println("-----------------------------------------------------");
            System.out.println("1. Registrar Venta");
            System.out.println("2. Mostrar Ventas");
            System.out.println("0. Volver al menú principal");
            System.out.println("");
            System.out.println("Ingrese su opción:");
            op = lector.nextLine();

            switch (op) {
                case "1":
                    ventaController.registrarVenta();
                    break;
                case "2":
                    ventaController.mostrarVentas();
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        } while (!op.equals("0"));
    }
}
