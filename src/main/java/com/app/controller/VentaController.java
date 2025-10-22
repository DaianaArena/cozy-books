package main.java.com.app.controller;

import main.java.com.app.model.Venta;
import main.java.com.app.model.DetalleVenta;
import main.java.com.app.model.Libro;
import main.java.com.app.model.Cliente;
import main.java.com.app.repository.VentaRepository;
import main.java.com.app.repository.ClienteRepository;
import main.java.com.app.repository.LibroRepository;
import main.java.com.app.util.ArchivoService;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.List;

public class VentaController {

    private VentaRepository ventaRepository;
    private ClienteRepository clienteRepository;
    private LibroRepository libroRepository;
    private ArchivoService archivoService;
    private Scanner lector;

    public VentaController() {
        this.ventaRepository = new VentaRepository();
        this.clienteRepository = new ClienteRepository();
        this.libroRepository = new LibroRepository();
        this.archivoService = new ArchivoService();
        this.lector = new Scanner(System.in);
    }

    public void registrarVenta() {
        System.out.println("-----------------------------------------------------");
        System.out.println("REGISTRO DE NUEVA VENTA");
        System.out.println("-----------------------------------------------------");

        // Seleccionar cliente
        if (clienteRepository.estaVacio()) {
            System.out.println("No hay clientes registrados para realizar ventas");
            return;
        }

        System.out.println("Selecciona el cliente:");
        clienteRepository.mostrarTodos();
        System.out.println("Ingresa el ID del cliente elegido: ");
        int idCliente = lector.nextInt();
        Cliente cliente = clienteRepository.obtenerPorId(idCliente);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

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

        // Crear venta
        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        String estado = "COMPLETADA";
        Venta venta = new Venta(0, fecha, 0.0, metodoPago, estado, cliente);

        // Agregar libros a la venta
        String opcion = "";
        do {
            if (libroRepository.estaVacio()) {
                System.out.println("No hay libros disponibles para vender");
                break;
            }

            System.out.println("Selecciona el libro vendido:");
            System.out.println("-----------------------------------------------------");
            System.out.println("LIBROS DISPONIBLES PARA VENTA");
            System.out.println("-----------------------------------------------------");
            libroRepository.mostrarTodos();
            System.out.println("Ingresa el ID del libro elegido: ");
            int idLibro = lector.nextInt();
            Libro libro = libroRepository.obtenerPorId(idLibro);

            if (libro == null) {
                System.out.println("Libro no encontrado.");
                continue;
            }

            System.out.println("Ingresa la cantidad vendida:");
            int cantidad = lector.nextInt();

            // Validar stock disponible
            if ("FISICO".equals(libro.getTipoLibro())) {
                if (libro.getStock() < cantidad) {
                    System.out.println("Error: No hay suficiente stock disponible.");
                    System.out.println("Stock disponible: " + libro.getStock());
                    System.out.println("Cantidad solicitada: " + cantidad);
                    continue;
                }
                if (libro.getStock() == 0) {
                    System.out.println("Error: Este libro físico no tiene stock disponible.");
                    continue;
                }
            }

            // Crear detalle de venta
            DetalleVenta detalle = new DetalleVenta(0, cantidad, libro.getPrecio(), venta, libro);
            venta.agregarDetalle(detalle);

            // Actualizar stock si es libro físico
            if ("FISICO".equals(libro.getTipoLibro())) {
                libroRepository.actualizarStock(libro.getIdLibro(), libro.getStock() - cantidad);
            }

            System.out.println("El libro fue agregado con éxito.");
            lector.nextLine();

            System.out.println("¿Desea agregar más libros a esta venta? S/N");
            opcion = lector.nextLine();

        } while (opcion.equalsIgnoreCase("S"));

        // Calcular monto total
        double montoTotal = calcularTotal(venta.getDetallesVenta());
        venta.setMonto(montoTotal);

        // Registrar venta
        ventaRepository.registrar(venta);

        // Generar ticket
        archivoService.generarTicket(venta);

        System.out.println("Venta agregada con éxito. ID: " + venta.getIdVenta());
        System.out.println("Monto total: $" + montoTotal);
    }

    public void mostrarVentas() {
        ventaRepository.mostrarTodos();
    }

    public void actualizarVenta() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ACTUALIZAR VENTA");
        System.out.println("-----------------------------------------------------");

        if (ventaRepository.estaVacio()) {
            System.out.println("No hay ventas registradas para actualizar");
            return;
        }

        ventaRepository.mostrarTodos();
        System.out.println("Ingrese el ID de la venta a actualizar:");
        int id = lector.nextInt();
        lector.nextLine(); // Limpiar buffer

        Venta venta = ventaRepository.obtenerPorId(id);
        if (venta == null) {
            System.out.println("Venta no encontrada");
            return;
        }

        System.out.println("Venta encontrada:");
        venta.mostrarVenta();
        System.out.println("\n¿Qué desea actualizar?");
        System.out.println("1. Estado de la venta");
        System.out.println("2. Método de pago");
        System.out.println("0. Cancelar");

        String opcion = lector.nextLine();

        switch (opcion) {
            case "1":
                actualizarEstadoVenta(venta);
                break;
            case "2":
                actualizarMetodoPago(venta);
                break;
            case "0":
                System.out.println("Operación cancelada");
                break;
            default:
                System.out.println("Opción inválida");
                break;
        }
    }

    private void actualizarEstadoVenta(Venta venta) {
        System.out.println("Estado actual: " + venta.getEstado());
        System.out.println("Nuevo estado (COMPLETADA, PENDIENTE, CANCELADA):");
        String nuevoEstado = lector.nextLine().trim().toUpperCase();

        if (nuevoEstado.equals("COMPLETADA") || nuevoEstado.equals("PENDIENTE") || nuevoEstado.equals("CANCELADA")) {
            venta.setEstado(nuevoEstado);
            ventaRepository.actualizar(venta);
            System.out.println("Estado de venta actualizado correctamente");
        } else {
            System.out.println("Error: Estado inválido");
        }
    }

    private void actualizarMetodoPago(Venta venta) {
        System.out.println("Método de pago actual: " + venta.getMetodoPago());
        System.out.println("Nuevo método de pago (EFECTIVO, TARJETA, TRANSFERENCIA):");
        String nuevoMetodo = lector.nextLine().trim().toUpperCase();

        if (nuevoMetodo.equals("EFECTIVO") || nuevoMetodo.equals("TARJETA") || nuevoMetodo.equals("TRANSFERENCIA")) {
            venta.setMetodoPago(nuevoMetodo);
            ventaRepository.actualizar(venta);
            System.out.println("Método de pago actualizado correctamente");
        } else {
            System.out.println("Error: Método de pago inválido");
        }
    }

    public void eliminarVenta() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ELIMINAR VENTA");
        System.out.println("-----------------------------------------------------");

        if (ventaRepository.estaVacio()) {
            System.out.println("No hay ventas registradas para eliminar");
            return;
        }

        ventaRepository.mostrarTodos();
        System.out.println("Ingrese el ID de la venta a eliminar:");
        int id = lector.nextInt();
        lector.nextLine(); // Limpiar buffer

        Venta venta = ventaRepository.obtenerPorId(id);
        if (venta == null) {
            System.out.println("Venta no encontrada");
            return;
        }

        System.out.println("¿Está seguro de que desea eliminar esta venta? (s/n)");
        System.out.println("NOTA: Esto eliminará también todos los detalles de la venta");
        String confirmacion = lector.nextLine().toLowerCase();

        if (confirmacion.equals("s") || confirmacion.equals("si")) {
            ventaRepository.eliminar(id);
            System.out.println("Venta eliminada correctamente");
        } else {
            System.out.println("Operación cancelada");
        }
    }

    public void buscarVenta() {
        System.out.println("-----------------------------------------------------");
        System.out.println("BUSCAR VENTA");
        System.out.println("-----------------------------------------------------");

        System.out.println("Ingrese el nombre del cliente, método de pago o estado:");
        String criterio = lector.nextLine().trim();

        if (criterio.isEmpty()) {
            System.out.println("Debe ingresar un criterio de búsqueda");
            return;
        }

        List<Venta> ventas = ventaRepository.buscar(criterio);
        if (!ventas.isEmpty()) {
            System.out.println("Ventas encontradas:");
            for (Venta venta : ventas) {
                venta.mostrarVenta();
                System.out.println("================================================");
            }
        } else {
            System.out.println("No se encontraron ventas con ese criterio");
        }
    }

    public double calcularTotal(java.util.List<DetalleVenta> detalles) {
        double total = 0;
        for (DetalleVenta detalle : detalles) {
            total += detalle.getSubtotal();
        }
        return total;
    }

    public Venta obtenerVenta(int id) {
        return ventaRepository.obtenerPorId(id);
    }
}
