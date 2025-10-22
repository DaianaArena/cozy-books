package main.java.com.app.venta;

import java.sql.Timestamp;
import java.util.ArrayList;
import main.java.com.app.cliente.Cliente;

public class Venta {

    private int idVenta;
    private Timestamp fecha;
    private double monto;
    private String metodoPago; // EFECTIVO, TARJETA, TRANSFERENCIA
    private String estado; // COMPLETADA, PENDIENTE, CANCELADA
    private Cliente cliente;
    private ArrayList<DetalleVenta> detallesVenta;

    public static int gananciaTotal;

    public Venta(int idVenta, Timestamp fecha, double monto, String metodoPago, String estado, Cliente cliente) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.cliente = cliente;
        this.detallesVenta = new ArrayList<>();

        gananciaTotal += monto;
    }

    // Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(ArrayList<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        this.detallesVenta.add(detalle);
    }

    public void mostrarVenta() {
        System.out.println("ID Venta: " + idVenta);
        System.out.println("Fecha: " + fecha);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Monto: " + monto);
        System.out.println("Método de pago: " + metodoPago);
        System.out.println("Estado: " + estado);
        System.out.println("Detalles de la venta:");

        if (detallesVenta.isEmpty()) {
            System.out.println("No hay detalles registrados");
        } else {
            for (DetalleVenta detalle : detallesVenta) {
                detalle.mostrarDetalle();
                System.out.println("-------------------");
            }
        }
    }
}