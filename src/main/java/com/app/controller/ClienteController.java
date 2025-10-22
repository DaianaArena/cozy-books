package main.java.com.app.controller;

import main.java.com.app.model.Cliente;
import main.java.com.app.repository.ClienteRepository;
import java.sql.Timestamp;
import java.util.Scanner;

public class ClienteController {

    private ClienteRepository clienteRepository;
    private Scanner lector;

    public ClienteController() {
        this.clienteRepository = new ClienteRepository();
        this.lector = new Scanner(System.in);
    }

    public void registrarCliente() {
        System.out.println("-----------------------------------------------------");
        System.out.println("REGISTRO DE NUEVO CLIENTE");
        System.out.println("-----------------------------------------------------");

        System.out.println("Ingrese el nombre completo del nuevo cliente:");
        String nombre = lector.nextLine();

        String documento = "";
        boolean documentoValido = false;
        while (!documentoValido) {
            System.out.println("Ingrese el documento del nuevo cliente (8 dígitos):");
            documento = lector.nextLine();
            if (validarDocumento(documento)) {
                documentoValido = true;
            } else {
                System.out.println("El documento debe tener exactamente 8 dígitos numéricos.");
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

        Timestamp fechaRegistro = new Timestamp(System.currentTimeMillis());
        Cliente nuevoCliente = new Cliente(0, nombre, documento, email, telefono, fechaRegistro);
        clienteRepository.registrar(nuevoCliente);

        System.out.println("Cliente registrado con éxito.");
    }

    public void mostrarClientes() {
        clienteRepository.mostrarTodos();
    }

    public Cliente obtenerCliente(int id) {
        return clienteRepository.obtenerPorId(id);
    }

    public boolean validarDocumento(String documento) {
        return documento.length() == 8 && documento.matches("\\d{8}");
    }

    public void actualizarCliente(int id, String nombre, String documento, String email, String telefono) {
        Cliente cliente = new Cliente(id, nombre, documento, email, telefono, null);
        clienteRepository.actualizar(cliente);
    }

    public void eliminarCliente(int id) {
        clienteRepository.eliminar(id);
    }
}
