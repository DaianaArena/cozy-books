package main.java.com.app.controller;

import main.java.com.app.model.Autor;
import main.java.com.app.repository.AutorRepository;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Scanner;

public class AutorController {

    private AutorRepository autorRepository;
    private Scanner lector;

    public AutorController() {
        this.autorRepository = new AutorRepository();
        this.lector = new Scanner(System.in);
    }

    public void registrarAutor() {
        System.out.println("-----------------------------------------------------");
        System.out.println("REGISTRO DE NUEVO AUTOR");
        System.out.println("-----------------------------------------------------");

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

        Autor nuevoAutor = new Autor(0, nombre, fechaNacimiento, nacionalidad, biografia);
        autorRepository.registrar(nuevoAutor);

        System.out.println("El autor se agregó correctamente.");
    }

    public void mostrarAutores() {
        autorRepository.mostrarTodos();
    }

    public void actualizarAutor() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ACTUALIZAR AUTOR");
        System.out.println("-----------------------------------------------------");

        if (autorRepository.estaVacio()) {
            System.out.println("No hay autores registrados para actualizar");
            return;
        }

        autorRepository.mostrarTodos();
        System.out.println("Ingrese el ID del autor a actualizar:");
        int id = lector.nextInt();
        lector.nextLine(); // Limpiar buffer

        Autor autor = autorRepository.obtenerPorId(id);
        if (autor == null) {
            System.out.println("Autor no encontrado");
            return;
        }

        System.out.println("Autor encontrado:");
        autor.mostrarAutor();
        System.out.println("\nIngrese los nuevos datos (presione Enter para mantener el valor actual):");

        System.out.println("Nombre actual: " + autor.getNombre());
        System.out.println("Nuevo nombre: ");
        String nuevoNombre = lector.nextLine().trim();
        if (!nuevoNombre.isEmpty() && nuevoNombre.length() <= 100) {
            autor.setNombre(nuevoNombre);
        } else if (!nuevoNombre.isEmpty()) {
            System.out.println("Error: El nombre no puede exceder 100 caracteres");
            return;
        }

        System.out.println("Nacionalidad actual: " + autor.getNacionalidad());
        System.out.println("Nueva nacionalidad: ");
        String nuevaNacionalidad = lector.nextLine().trim();
        if (!nuevaNacionalidad.isEmpty() && nuevaNacionalidad.length() <= 50) {
            autor.setNacionalidad(nuevaNacionalidad);
        } else if (!nuevaNacionalidad.isEmpty()) {
            System.out.println("Error: La nacionalidad no puede exceder 50 caracteres");
            return;
        }

        System.out.println("Biografía actual: " + autor.getBiografia());
        System.out.println("Nueva biografía: ");
        String nuevaBiografia = lector.nextLine().trim();
        if (!nuevaBiografia.isEmpty()) {
            autor.setBiografia(nuevaBiografia);
        }

        autorRepository.actualizar(autor);
        System.out.println("Autor actualizado correctamente");
    }

    public void eliminarAutor() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ELIMINAR AUTOR");
        System.out.println("-----------------------------------------------------");

        if (autorRepository.estaVacio()) {
            System.out.println("No hay autores registrados para eliminar");
            return;
        }

        autorRepository.mostrarTodos();
        System.out.println("Ingrese el ID del autor a eliminar:");
        int id = lector.nextInt();
        lector.nextLine(); // Limpiar buffer

        Autor autor = autorRepository.obtenerPorId(id);
        if (autor == null) {
            System.out.println("Autor no encontrado");
            return;
        }

        // Verificar si tiene libros asociados
        if (tieneLibrosAsociados(id)) {
            System.out.println("No se puede eliminar el autor porque tiene libros asociados");
            return;
        }

        System.out.println("¿Está seguro de que desea eliminar este autor? (s/n)");
        String confirmacion = lector.nextLine().toLowerCase();

        if (confirmacion.equals("s") || confirmacion.equals("si")) {
            autorRepository.eliminar(id);
            System.out.println("Autor eliminado correctamente");
        } else {
            System.out.println("Operación cancelada");
        }
    }

    public void buscarAutor() {
        System.out.println("-----------------------------------------------------");
        System.out.println("BUSCAR AUTOR");
        System.out.println("-----------------------------------------------------");

        System.out.println("Ingrese el nombre del autor a buscar:");
        String criterio = lector.nextLine().trim();

        if (criterio.isEmpty()) {
            System.out.println("Debe ingresar un criterio de búsqueda");
            return;
        }

        Autor autor = autorRepository.buscar(criterio);
        if (autor != null) {
            System.out.println("Autor encontrado:");
            autor.mostrarAutor();
        } else {
            System.out.println("No se encontró ningún autor con ese criterio");
        }
    }

    private boolean tieneLibrosAsociados(int idAutor) {
        // Esta validación se implementará cuando tengamos acceso al LibroRepository
        // Por ahora retornamos false para permitir la eliminación
        return false;
    }

    public Autor obtenerAutor(int id) {
        return autorRepository.obtenerPorId(id);
    }

    public void actualizarAutor(int id, String nombre, Date fechaNacimiento, String nacionalidad, String biografia) {
        Autor autor = new Autor(id, nombre, fechaNacimiento, nacionalidad, biografia);
        autorRepository.actualizar(autor);
    }

    public void eliminarAutor(int id) {
        autorRepository.eliminar(id);
    }
}
