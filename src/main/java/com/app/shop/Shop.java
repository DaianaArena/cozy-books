package main.java.com.app.shop;

import java.util.ArrayList;
import java.util.Scanner;

import main.java.com.app.autor.Autor;
import main.java.com.app.cliente.Cliente;
import main.java.com.app.interfaceArchivo.Archivo;
import main.java.com.app.libro.Libro;
import main.java.com.app.venta.Venta;
import main.java.com.app.venta.DetalleVenta;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Shop {

    private String nombre;
    private String descripcion;
    private ArrayList<Cliente> listaDeClientes;
    private ArrayList<Libro> listaDeLibros;
    private ArrayList<Autor> ListaDeAutores;
    private ArrayList<Venta> listaDeVentas;
    private Scanner lector;

    // Constructor de la clase Shop
    public Shop() {
        this.nombre = "Cozy Books";
        this.descripcion = "Cozy Books es una tienda en línea de libros fundada en 2013 por una familia apasionada por la lectura. La filosofía de la marca se centra en la promoción de la lectura y la literatura como una forma de enriquecer la mente y el alma de las personas. Desde su creación, Cozy Books ha estado comprometida en ofrecer una amplia variedad de títulos y géneros a precios asequibles, y en proporcionar una experiencia de compra en línea fácil y conveniente para sus clientes. La familia detrás de Cozy Books está formada por lectores ávidos y apasionados por la literatura, y esto se refleja en la selección cuidadosa de los títulos que ofrecen en su tienda en línea. Desde los clásicos hasta las últimas novedades, Cozy Books tiene algo para todos los gustos y edades.";

        this.listaDeClientes = new ArrayList<Cliente>();
        this.listaDeLibros = new ArrayList<Libro>();
        this.listaDeVentas = new ArrayList<Venta>();
        this.ListaDeAutores = new ArrayList<Autor>();

        this.lector = new Scanner(System.in);

    }

    public void menu() {
        String op;
        System.out.println("-----------------------------------------------------");
        System.out.println("  Bienvenido al Sistema de gestión de ventas de ");

        System.out.println("  _____                 ____              _        ");
        System.out.println(" / ____|               |  _ \\            | |       ");
        System.out.println("| |     ___ _____   _  | |_) | ___   ___ | | _____ ");
        System.out.println("| |    / _ \\_  / | | | |  _ < / _ \\ / _ \\| |/ / __|");
        System.out.println("| |___| (_) / /| |_| | | |_) | (_) | (_) |   <\\__ \\");
        System.out.println(" \\_____\\___/___|\\__, | |____/ \\___/ \\___/|_|\\_\\___/");
        System.out.println("                __/  |                             ");
        System.out.println("               |____/                              ");

        System.out.println("-----------------------------------------------------");

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
            switch (op) {
                case "1":
                    this.registrarAutor();
                    break;
                case "2":
                    this.registrarCliente();
                    break;
                case "3":
                    this.registrarLibro();
                    break;
                case "4":
                    this.registrarVenta();
                    break;
                case "5":
                    this.mostrarAutores();
                    break;
                case "6":
                    this.mostrarClientes();
                    break;
                case "7":
                    this.mostrarLibros();
                    break;
                case "8":
                    this.mostrarLibrosPorAutor();
                    break;
                case "9":
                    this.mostrarVentas();
                    break;
                case "10":
                    this.mostrarInfo();
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

        } while (!op.equals("0"));

    }

    private void mostrarInfo() {
        System.out.println("-----------------------");
        System.out.println("| :) | Nosotros:");
        System.out.println("-----------------------");
        System.out.println("Nombre de la tienda: " + this.nombre);
        System.out.println("Sobre nosotros: " + this.descripcion);
    }

    private void mostrarVentas() {
        // double montoTotal = 0;
        if (this.listaDeVentas.isEmpty()) {
            System.out.println("No hay ventas registradas");
        } else {
            System.out.println("------------------------");
            System.out.println("| $ | Ventas registradas");
            System.out.println("------------------------");
            for (Venta venta : listaDeVentas) {
                venta.mostrarVenta();
                // montoTotal += venta.getMonto();
            }

            System.out.println("------------------------------------------------");
            System.out.println("| $ | Total de ventas: " + Venta.gananciaTotal);
            System.out.println("------------------------------------------------");
        }
    }

    private void registrarVenta() {
        System.out.println("-----------------------------------------------------");
        System.out.println("REGISTRO DE NUEVA VENTA");
        System.out.println("-----------------------------------------------------");

        // Generar ID automático
        int idVenta = listaDeVentas.size() + 1;

        // Fecha automática
        Timestamp fecha = new Timestamp(System.currentTimeMillis());

        // Seleccionar cliente
        if (listaDeClientes.isEmpty()) {
            System.out.println("No hay clientes registrados para realizar ventas");
            return;
        }

        System.out.println("Selecciona el cliente:");
        for (int i = 1; i - 1 < listaDeClientes.size(); i++) {
            System.out.println("ID: " + i);
            listaDeClientes.get(i - 1).mostrarCliente();
        }
        System.out.println("Ingresa el ID del cliente elegido: ");
        int idCliente = lector.nextInt();
        Cliente cliente = listaDeClientes.get(idCliente - 1);

        // Seleccionar método de pago
        String metodoPago = "";
        int opcionPago;
        do {
            System.out.println("Selecciona el método de pago:");
            System.out.println("1. EFECTIVO");
            System.out.println("2. TARJETA");
            System.out.println("3. TRANSFERENCIA");
            opcionPago = lector.nextInt();

            switch (opcionPago) {
                case 1:
                    metodoPago = "EFECTIVO";
                    break;
                case 2:
                    metodoPago = "TARJETA";
                    break;
                case 3:
                    metodoPago = "TRANSFERENCIA";
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcionPago < 1 || opcionPago > 3);

        // Estado por defecto
        String estado = "COMPLETADA";

        // Crear venta
        Venta venta = new Venta(idVenta, fecha, 0.0, metodoPago, estado, cliente);

        // Agregar libros a la venta
        String opcion;
        do {
            if (listaDeLibros.isEmpty()) {
                System.out.println("No hay libros disponibles para vender");
                break;
            }

            System.out.println("Selecciona el libro vendido:");
            for (int i = 1; i - 1 < listaDeLibros.size(); i++) {
                System.out.println("ID: " + i);
                listaDeLibros.get(i - 1).mostrarLibro();
            }
            System.out.println("Ingresa el ID del libro elegido: ");
            int idLibro = lector.nextInt();
            Libro libro = listaDeLibros.get(idLibro - 1);

            System.out.println("Ingresa la cantidad vendida:");
            int cantidad = lector.nextInt();

            // Crear detalle de venta
            int idDetalle = venta.getDetallesVenta().size() + 1;
            DetalleVenta detalle = new DetalleVenta(idDetalle, cantidad, libro.getPrecio(), venta, libro);
            venta.agregarDetalle(detalle);

            // Actualizar stock si es libro físico
            if ("FISICO".equals(libro.getTipoLibro())) {
                libro.setStock(libro.getStock() - cantidad);
            }

            System.out.println("El libro fue agregado con éxito.");
            lector.nextLine();

            System.out.println("¿Desea agregar más libros a esta venta? S/N");
            opcion = lector.nextLine();

        } while (opcion.equalsIgnoreCase("S"));

        // Calcular monto total
        double montoTotal = 0;
        for (DetalleVenta detalle : venta.getDetallesVenta()) {
            montoTotal += detalle.getSubtotal();
        }
        venta.setMonto(montoTotal);

        // Agregar venta a la lista
        listaDeVentas.add(venta);

        // Imprimir factura a un txt
        Archivo factura = new Archivo();
        factura.escribirArchivo(venta);

        System.out.println("Venta agregada con éxito. ID: " + idVenta);
        System.out.println("Monto total: $" + montoTotal);
    }

    private void mostrarLibrosPorAutor() {
        if (this.ListaDeAutores.isEmpty()) {
            System.out.println("No hay autores registrados");
        } else {
            System.out.println("Selecciona el autor del cual deseas conocer sus libros:");
            // Mostrar la lista de autores para elegir uno
            for (int i = 1; i - 1 < ListaDeAutores.size(); i++) {
                System.out.println("ID: " + i);
                ListaDeAutores.get(i - 1).mostrarAutor();
            }
            System.out.println("Ingresa el ID: ");
            int idBuscado = lector.nextInt();
            Autor autor = ListaDeAutores.get(idBuscado - 1);

            // Buscar libros del autor
            System.out.println("-----------------------------------------------------");
            System.out.println("LIBROS DEL AUTOR: " + autor.getNombre());
            System.out.println("-----------------------------------------------------");

            boolean encontroLibros = false;
            for (Libro libro : listaDeLibros) {
                if (libro.getAutor().getIdAutor() == autor.getIdAutor()) {
                    libro.mostrarLibro();
                    System.out.println("-----------------------------------------------------");
                    encontroLibros = true;
                }
            }

            if (!encontroLibros) {
                System.out.println("No se encontraron libros para este autor.");
            }

            lector.nextLine();
        }
    }

    public void registrarCliente() {
        System.out.println("-----------------------------------------------------");
        System.out.println("REGISTRO DE NUEVO CLIENTE");
        System.out.println("-----------------------------------------------------");

        // Generar ID automático
        int idCliente = listaDeClientes.size() + 1;

        System.out.println("Ingrese el nombre completo del nuevo cliente:");
        String nombre = lector.nextLine();

        String documento = "";
        // Valida que el DNI sea un numero entero de 8 cifras
        boolean documentoValido = false;
        while (!documentoValido) {
            System.out.println("Ingrese el documento del nuevo cliente (8 dígitos):");
            documento = lector.nextLine();
            try {
                // Chequea que los dni tengan 8 digitos alfanumericos
                if (documento.length() == 8 && documento.matches("\\d{8}")) {
                    documentoValido = true;
                } else {
                    System.out.println("El documento debe tener exactamente 8 dígitos numéricos.");
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("El documento es inválido.");
            }
        }

        System.out.println("Ingrese el email del cliente (opcional, presione Enter para omitir):");
        String email = lector.nextLine();
        if (email.trim().isEmpty()) {
            email = null;
        }

        System.out.println("Ingrese el teléfono del cliente (opcional, presione Enter para omitir):");
        String telefono = lector.nextLine();
        if (telefono.trim().isEmpty()) {
            telefono = null;
        }

        // Fecha de registro automática
        Timestamp fechaRegistro = new Timestamp(System.currentTimeMillis());

        Cliente nuevoCliente = new Cliente(idCliente, nombre, documento, email, telefono, fechaRegistro);
        this.listaDeClientes.add(nuevoCliente);
        System.out.println("Cliente registrado con éxito con ID: " + idCliente);
    }

    public void mostrarClientes() {
        if (this.listaDeClientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            System.out.println("Lista de clientes registrados:");
            this.listaDeClientes.forEach((cliente) -> {
                cliente.mostrarCliente();
            });
        }
    }

    public void registrarLibro() {
        if (this.ListaDeAutores.isEmpty()) {
            System.out.println("No hay autores registrados para cargar libros");
        } else {
            System.out.println("-----------------------------------------------------");
            System.out.println("REGISTRO DE NUEVO LIBRO");
            System.out.println("-----------------------------------------------------");

            // Generar ID automático
            int idLibro = listaDeLibros.size() + 1;

            System.out.println("Ingrese el título del nuevo libro:");
            String titulo = lector.nextLine();

            System.out.println("Ingrese el ISBN del libro (opcional, presione Enter para omitir):");
            String isbn = lector.nextLine();
            if (isbn.trim().isEmpty()) {
                isbn = null;
            }

            // Mostrar la lista de autores para elegir uno
            System.out.println("-----------------------------------------------------");
            System.out.println("Lista de autores disponibles");
            System.out.println("-----------------------------------------------------");
            for (int i = 1; i - 1 < ListaDeAutores.size(); i++) {
                System.out.println("ID: " + i);
                ListaDeAutores.get(i - 1).mostrarAutor();
            }
            System.out.println("Ingresa el ID del autor del libro: ");
            int idBuscado = lector.nextInt();
            Autor autor = ListaDeAutores.get(idBuscado - 1);

            System.out.println("Ingrese la editorial del libro:");
            lector.nextLine();
            String editorial = lector.nextLine();

            System.out.println("Ingrese el año del libro:");
            int año = lector.nextInt();

            System.out.println("Ingrese el precio del libro:");
            double precio = lector.nextDouble();

            System.out.println("Ingrese el género del libro (opcional, presione Enter para omitir):");
            lector.nextLine();
            String genero = lector.nextLine();
            if (genero.trim().isEmpty()) {
                genero = null;
            }

            // Fecha de registro automática
            Timestamp fechaRegistro = new Timestamp(System.currentTimeMillis());

            int tipoDeLibro;
            do {
                System.out.println("Ingrese la opción para elegir el tipo de libro:");
                System.out.println("1. Libro Físico");
                System.out.println("2. Libro Digital");
                System.out.println("3. Audiolibro");

                tipoDeLibro = lector.nextInt();

                String tipoLibro = "";
                switch (tipoDeLibro) {
                    case 1:
                        tipoLibro = "FISICO";
                        System.out.println("Ingrese el stock inicial del libro físico:");
                        int stock = lector.nextInt();
                        System.out.println("Ingrese el encuadernado del libro:");
                        lector.nextLine();
                        String encuadernado = lector.nextLine();
                        System.out.println("Ingrese el número de edición del libro:");
                        int numEdicion = lector.nextInt();

                        Libro nuevoFisico = new Libro(idLibro, titulo, isbn, editorial, año, precio, genero, tipoLibro,
                                stock, fechaRegistro, autor);
                        nuevoFisico.setEncuadernado(encuadernado);
                        nuevoFisico.setNumEdicion(numEdicion);

                        this.listaDeLibros.add(nuevoFisico);
                        System.out.println("Libro físico agregado exitosamente");
                        break;

                    case 2:
                        tipoLibro = "DIGITAL";
                        System.out.println("Ingrese la extensión del archivo (ej: PDF, EPUB):");
                        lector.nextLine();
                        String extension = lector.nextLine();
                        System.out.println("¿Tiene permisos de impresión? (true/false):");
                        boolean permisosImpresion = lector.nextBoolean();

                        Libro nuevoDigital = new Libro(idLibro, titulo, isbn, editorial, año, precio, genero, tipoLibro,
                                0, fechaRegistro, autor);
                        nuevoDigital.setExtension(extension);
                        nuevoDigital.setPermisosImpresion(permisosImpresion);

                        this.listaDeLibros.add(nuevoDigital);
                        System.out.println("Libro digital agregado exitosamente");
                        break;

                    case 3:
                        tipoLibro = "AUDIOLIBRO";
                        System.out.println("Ingrese la duración del audio en minutos:");
                        lector.nextLine();
                        int duracion = lector.nextInt();
                        System.out.println("Ingrese la plataforma del audiolibro:");
                        lector.nextLine();
                        String plataforma = lector.nextLine();
                        System.out.println("Ingrese el narrador del audiolibro:");
                        String narrador = lector.nextLine();

                        Libro nuevoAudiolibro = new Libro(idLibro, titulo, isbn, editorial, año, precio, genero,
                                tipoLibro, 0, fechaRegistro, autor);
                        nuevoAudiolibro.setDuracion(duracion);
                        nuevoAudiolibro.setPlataforma(plataforma);
                        nuevoAudiolibro.setNarrador(narrador);

                        this.listaDeLibros.add(nuevoAudiolibro);
                        System.out.println("Audiolibro agregado exitosamente");
                        break;

                    default:
                        System.out.println("Opción no válida");
                        break;
                }

            } while (tipoDeLibro != 1 && tipoDeLibro != 2 && tipoDeLibro != 3);

            System.out.println("Libro agregado exitosamente con ID: " + idLibro);
            System.out.println(">>TOCAR ENTER PARA VOLVER AL MENU PRINCIPAL<<");
            lector.nextLine();
        }
    }

    public void registrarAutor() {
        System.out.println("-----------------------------------------------------");
        System.out.println("REGISTRO DE NUEVO AUTOR");
        System.out.println("-----------------------------------------------------");

        // Generar ID automático
        int idAutor = ListaDeAutores.size() + 1;

        System.out.println("Ingrese el nombre completo del nuevo autor:");
        String nombre = lector.nextLine();

        // Validar fecha de nacimiento
        Date fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.println("Ingrese fecha de nacimiento del autor (formato: dd/MM/yyyy):");
            String fechaStr = lector.nextLine();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                fechaNacimiento = sdf.parse(fechaStr);
                fechaValida = true;
            } catch (ParseException e) {
                System.out.println("Formato de fecha inválido. Use dd/MM/yyyy (ej: 15/03/1985)");
            }
        }

        System.out.println("Ingrese la nacionalidad del autor (opcional, presione Enter para omitir):");
        String nacionalidad = lector.nextLine();
        if (nacionalidad.trim().isEmpty()) {
            nacionalidad = null;
        }

        System.out.println("Ingrese la biografía del autor (opcional, presione Enter para omitir):");
        String biografia = lector.nextLine();
        if (biografia.trim().isEmpty()) {
            biografia = null;
        }

        Autor nuevoAutor = new Autor(idAutor, nombre, fechaNacimiento, nacionalidad, biografia);

        // agregar autor a la lista
        this.ListaDeAutores.add(nuevoAutor);
        System.out.println("El autor se agregó correctamente con ID: " + idAutor);
    }

    public void mostrarAutores() {
        if (this.ListaDeAutores.isEmpty()) {
            System.out.println("No hay autores registrados");
        } else {
            System.out.println("Lista de autores registrados:");
            this.ListaDeAutores.forEach((autor) -> {
                autor.mostrarAutor();
            });
        }
    }

    public void mostrarLibros() {
        if (this.listaDeLibros.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {
            System.out.println("-----------------------------------------------------");
            System.out.println("LISTA DE LIBROS REGISTRADOS");
            System.out.println("-----------------------------------------------------");

            for (Libro libro : listaDeLibros) {
                libro.mostrarLibro();
                System.out.println("-----------------------------------------------------");
            }
        }
    }

}
