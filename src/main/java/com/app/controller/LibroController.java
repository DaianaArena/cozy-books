package main.java.com.app.controller;

import main.java.com.app.model.Libro;
import main.java.com.app.model.Autor;
import main.java.com.app.repository.LibroRepository;
import main.java.com.app.repository.AutorRepository;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.List;

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

    public void actualizarLibro() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ACTUALIZAR LIBRO");
        System.out.println("-----------------------------------------------------");

        if (libroRepository.estaVacio()) {
            System.out.println("No hay libros registrados para actualizar");
            return;
        }

        libroRepository.mostrarTodos();
        System.out.println("Ingrese el ID del libro a actualizar:");
        int id = lector.nextInt();
        lector.nextLine(); // Limpiar buffer

        Libro libro = libroRepository.obtenerPorId(id);
        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        System.out.println("Libro encontrado:");
        libro.mostrarLibro();
        System.out.println("\nIngrese los nuevos datos (presione Enter para mantener el valor actual):");

        // Actualizar datos básicos
        System.out.println("Título actual: " + libro.getTitulo());
        System.out.println("Nuevo título: ");
        String nuevoTitulo = lector.nextLine().trim();
        if (!nuevoTitulo.isEmpty() && nuevoTitulo.length() <= 200) {
            libro.setTitulo(nuevoTitulo);
        } else if (!nuevoTitulo.isEmpty()) {
            System.out.println("Error: El título no puede exceder 200 caracteres");
            return;
        }

        System.out.println("Precio actual: " + libro.getPrecio());
        System.out.println("Nuevo precio: ");
        String precioStr = lector.nextLine().trim();
        if (!precioStr.isEmpty()) {
            try {
                double nuevoPrecio = Double.parseDouble(precioStr);
                if (nuevoPrecio > 0) {
                    libro.setPrecio(nuevoPrecio);
                } else {
                    System.out.println("Error: El precio debe ser mayor a 0");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato de precio inválido");
                return;
            }
        }

        // Actualizar stock si es libro físico
        if ("FISICO".equals(libro.getTipoLibro())) {
            System.out.println("Stock actual: " + libro.getStock());
            System.out.println("Nuevo stock: ");
            String stockStr = lector.nextLine().trim();
            if (!stockStr.isEmpty()) {
                try {
                    int nuevoStock = Integer.parseInt(stockStr);
                    if (nuevoStock >= 0) {
                        libro.setStock(nuevoStock);
                    } else {
                        System.out.println("Error: El stock no puede ser negativo");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Formato de stock inválido");
                    return;
                }
            }
        }

        libroRepository.actualizar(libro);
        System.out.println("Libro actualizado correctamente");
    }

    public void eliminarLibro() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ELIMINAR LIBRO");
        System.out.println("-----------------------------------------------------");

        if (libroRepository.estaVacio()) {
            System.out.println("No hay libros registrados para eliminar");
            return;
        }

        libroRepository.mostrarTodos();
        System.out.println("Ingrese el ID del libro a eliminar:");
        int id = lector.nextInt();
        lector.nextLine(); // Limpiar buffer

        Libro libro = libroRepository.obtenerPorId(id);
        if (libro == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        System.out.println("¿Está seguro de que desea eliminar este libro? (s/n)");
        String confirmacion = lector.nextLine().toLowerCase();

        if (confirmacion.equals("s") || confirmacion.equals("si")) {
            libroRepository.eliminar(id);
            System.out.println("Libro eliminado correctamente");
        } else {
            System.out.println("Operación cancelada");
        }
    }

    public void buscarLibro() {
        System.out.println("-----------------------------------------------------");
        System.out.println("BUSCAR LIBRO");
        System.out.println("-----------------------------------------------------");

        System.out.println("Ingrese el título, editorial o género del libro a buscar:");
        String criterio = lector.nextLine().trim();

        if (criterio.isEmpty()) {
            System.out.println("Debe ingresar un criterio de búsqueda");
            return;
        }

        List<Libro> libros = libroRepository.buscar(criterio);
        if (!libros.isEmpty()) {
            System.out.println("Libros encontrados:");
            for (Libro libro : libros) {
                libro.mostrarLibro();
                System.out.println("-----------------------------------------------------");
            }
        } else {
            System.out.println("No se encontraron libros con ese criterio");
        }
    }

    public Libro obtenerLibro(int id) {
        return libroRepository.obtenerPorId(id);
    }

    public void actualizarStock(int id, int cantidad) {
        libroRepository.actualizarStock(id, cantidad);
    }
}
