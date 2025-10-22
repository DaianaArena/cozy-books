package main.java.com.app.controller;

import main.java.com.app.model.Libro;
import main.java.com.app.model.Autor;
import main.java.com.app.repository.LibroRepository;
import main.java.com.app.repository.AutorRepository;
import java.sql.Timestamp;
import java.util.Scanner;

public class LibroController {

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private Scanner lector;

    public LibroController() {
        this.libroRepository = new LibroRepository();
        this.autorRepository = new AutorRepository();
        this.lector = new Scanner(System.in);
    }

    public void registrarLibro() {
        if (autorRepository.estaVacio()) {
            System.out.println("No hay autores registrados para cargar libros");
            return;
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("REGISTRO DE NUEVO LIBRO");
        System.out.println("-----------------------------------------------------");

        System.out.println("Ingrese el título del nuevo libro:");
        String titulo = lector.nextLine();

        System.out.println("Ingrese el ISBN del libro (opcional, presione Enter para omitir):");
        String isbn = lector.nextLine();
        if (isbn.trim().isEmpty()) {
            isbn = null;
        }

        // Mostrar autores disponibles
        System.out.println("-----------------------------------------------------");
        System.out.println("Lista de autores disponibles");
        System.out.println("-----------------------------------------------------");
        autorRepository.mostrarTodos();
        System.out.println("Ingresa el ID del autor del libro: ");
        int idAutor = lector.nextInt();
        Autor autor = autorRepository.obtenerPorId(idAutor);

        if (autor == null) {
            System.out.println("Autor no encontrado.");
            return;
        }

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

        Timestamp fechaRegistro = new Timestamp(System.currentTimeMillis());

        int tipoDeLibro;
        do {
            System.out.println("Ingrese la opción para elegir el tipo de libro:");
            System.out.println("1. Libro Físico");
            System.out.println("2. Libro Digital");
            System.out.println("3. Audiolibro");

            tipoDeLibro = lector.nextInt();

            String tipoLibro = "";
            int stock = 0;

            switch (tipoDeLibro) {
                case 1:
                    tipoLibro = "FISICO";
                    System.out.println("Ingrese el stock inicial del libro físico:");
                    stock = lector.nextInt();
                    System.out.println("Ingrese el encuadernado del libro:");
                    lector.nextLine();
                    String encuadernado = lector.nextLine();
                    System.out.println("Ingrese el número de edición del libro:");
                    int numEdicion = lector.nextInt();

                    Libro nuevoFisico = new Libro(0, titulo, isbn, editorial, año, precio, genero, tipoLibro, stock,
                            fechaRegistro, autor);
                    nuevoFisico.setEncuadernado(encuadernado);
                    nuevoFisico.setNumEdicion(numEdicion);

                    libroRepository.registrar(nuevoFisico);
                    System.out.println("Libro físico agregado exitosamente");
                    break;

                case 2:
                    tipoLibro = "DIGITAL";
                    System.out.println("Ingrese la extensión del archivo (ej: PDF, EPUB):");
                    lector.nextLine();
                    String extension = lector.nextLine();
                    System.out.println("¿Tiene permisos de impresión? (true/false):");
                    boolean permisosImpresion = lector.nextBoolean();

                    Libro nuevoDigital = new Libro(0, titulo, isbn, editorial, año, precio, genero, tipoLibro, 0,
                            fechaRegistro, autor);
                    nuevoDigital.setExtension(extension);
                    nuevoDigital.setPermisosImpresion(permisosImpresion);

                    libroRepository.registrar(nuevoDigital);
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

                    Libro nuevoAudiolibro = new Libro(0, titulo, isbn, editorial, año, precio, genero, tipoLibro, 0,
                            fechaRegistro, autor);
                    nuevoAudiolibro.setDuracion(duracion);
                    nuevoAudiolibro.setPlataforma(plataforma);
                    nuevoAudiolibro.setNarrador(narrador);

                    libroRepository.registrar(nuevoAudiolibro);
                    System.out.println("Audiolibro agregado exitosamente");
                    break;

                default:
                    System.out.println("Opción no válida");
                    break;
            }

        } while (tipoDeLibro != 1 && tipoDeLibro != 2 && tipoDeLibro != 3);

        System.out.println("Libro agregado exitosamente");
        System.out.println(">>TOCAR ENTER PARA VOLVER AL MENU PRINCIPAL<<");
        lector.nextLine();
    }

    public void mostrarLibros() {
        libroRepository.mostrarTodos();
    }

    public void mostrarLibrosPorAutor() {
        if (autorRepository.estaVacio()) {
            System.out.println("No hay autores registrados");
            return;
        }

        System.out.println("Selecciona el autor del cual deseas conocer sus libros:");
        autorRepository.mostrarTodos();
        System.out.println("Ingresa el ID: ");
        int idAutor = lector.nextInt();

        libroRepository.mostrarPorAutor(idAutor);
        lector.nextLine();
    }

    public Libro obtenerLibro(int id) {
        return libroRepository.obtenerPorId(id);
    }

    public void actualizarStock(int id, int cantidad) {
        libroRepository.actualizarStock(id, cantidad);
    }
}
