package main.java.com.app.venta;

import main.java.com.app.libro.Libro;

public class DetalleVenta {

    private int idDetalle;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private Venta venta;
    private Libro libro;

    public DetalleVenta(int idDetalle, int cantidad, double precioUnitario, Venta venta, Libro libro) {
        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
        this.venta = venta;
        this.libro = libro;
    }

    // Getters y Setters
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = this.cantidad * this.precioUnitario; // Recalcular subtotal
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.subtotal = this.cantidad * this.precioUnitario; // Recalcular subtotal
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void mostrarDetalle() {
        System.out.println("ID Detalle: " + idDetalle);
        System.out.println("Libro: " + libro.getTitulo());
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Precio unitario: " + precioUnitario);
        System.out.println("Subtotal: " + subtotal);
    }
}
