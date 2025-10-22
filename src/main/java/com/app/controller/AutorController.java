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
