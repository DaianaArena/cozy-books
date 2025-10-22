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

    public void actualizarCliente() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ACTUALIZAR CLIENTE");
        System.out.println("-----------------------------------------------------");

        if (clienteRepository.estaVacio()) {
            System.out.println("No hay clientes registrados para actualizar");
            return;
        }

        clienteRepository.mostrarTodos();
        System.out.println("Ingrese el ID del cliente a actualizar:");
        int id = lector.nextInt();
        lector.nextLine(); // Limpiar buffer

        Cliente cliente = clienteRepository.obtenerPorId(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return;
        }

        System.out.println("Cliente encontrado:");
        cliente.mostrarCliente();
        System.out.println("\nIngrese los nuevos datos (presione Enter para mantener el valor actual):");

        System.out.println("Nombre actual: " + cliente.getNombre());
        System.out.println("Nuevo nombre: ");
        String nuevoNombre = lector.nextLine().trim();
        if (!nuevoNombre.isEmpty() && nuevoNombre.length() <= 100) {
            cliente.setNombre(nuevoNombre);
        } else if (!nuevoNombre.isEmpty()) {
            System.out.println("Error: El nombre no puede exceder 100 caracteres");
            return;
        }

        System.out.println("Documento actual: " + cliente.getDocumento());
        System.out.println("Nuevo documento (8 dígitos): ");
        String nuevoDocumento = lector.nextLine().trim();
        if (!nuevoDocumento.isEmpty()) {
            if (validarDocumento(nuevoDocumento) && !documentoExiste(nuevoDocumento, id)) {
                cliente.setDocumento(nuevoDocumento);
            } else {
                System.out.println("Error: Documento inválido o ya existe");
                return;
            }
        }

        System.out.println("Email actual: " + cliente.getEmail());
        System.out.println("Nuevo email: ");
        String nuevoEmail = lector.nextLine().trim();
        if (!nuevoEmail.isEmpty() && validarEmail(nuevoEmail)) {
            cliente.setEmail(nuevoEmail);
        } else if (!nuevoEmail.isEmpty()) {
            System.out.println("Error: Formato de email inválido");
            return;
        }

        System.out.println("Teléfono actual: " + cliente.getTelefono());
        System.out.println("Nuevo teléfono: ");
        String nuevoTelefono = lector.nextLine().trim();
        if (!nuevoTelefono.isEmpty() && nuevoTelefono.length() <= 20) {
            cliente.setTelefono(nuevoTelefono);
        } else if (!nuevoTelefono.isEmpty()) {
            System.out.println("Error: El teléfono no puede exceder 20 caracteres");
            return;
        }

        clienteRepository.actualizar(cliente);
        System.out.println("Cliente actualizado correctamente");
    }

    public void eliminarCliente() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ELIMINAR CLIENTE");
        System.out.println("-----------------------------------------------------");

        if (clienteRepository.estaVacio()) {
            System.out.println("No hay clientes registrados para eliminar");
            return;
        }

        clienteRepository.mostrarTodos();
        System.out.println("Ingrese el ID del cliente a eliminar:");
        int id = lector.nextInt();
        lector.nextLine(); // Limpiar buffer

        Cliente cliente = clienteRepository.obtenerPorId(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado");
            return;
        }

        // Verificar si tiene ventas asociadas
        if (tieneVentasAsociadas(id)) {
            System.out.println("No se puede eliminar el cliente porque tiene ventas asociadas");
            return;
        }

        System.out.println("¿Está seguro de que desea eliminar este cliente? (s/n)");
        String confirmacion = lector.nextLine().toLowerCase();

        if (confirmacion.equals("s") || confirmacion.equals("si")) {
            clienteRepository.eliminar(id);
            System.out.println("Cliente eliminado correctamente");
        } else {
            System.out.println("Operación cancelada");
        }
    }

    public void buscarCliente() {
        System.out.println("-----------------------------------------------------");
        System.out.println("BUSCAR CLIENTE");
        System.out.println("-----------------------------------------------------");

        System.out.println("Ingrese el nombre o documento del cliente a buscar:");
        String criterio = lector.nextLine().trim();

        if (criterio.isEmpty()) {
            System.out.println("Debe ingresar un criterio de búsqueda");
            return;
        }

        Cliente cliente = clienteRepository.buscar(criterio);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            cliente.mostrarCliente();
        } else {
            System.out.println("No se encontró ningún cliente con ese criterio");
        }
    }

    private boolean tieneVentasAsociadas(int idCliente) {
        // Esta validación se implementará cuando tengamos acceso al VentaRepository
        // Por ahora retornamos false para permitir la eliminación
        return false;
    }

    private boolean documentoExiste(String documento, int idExcluir) {
        Cliente clienteExistente = clienteRepository.buscar(documento);
        return clienteExistente != null && clienteExistente.getIdCliente() != idExcluir;
    }

    private boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
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
