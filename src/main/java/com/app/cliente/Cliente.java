package main.java.com.app.cliente;

import java.sql.Timestamp;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String documento; // DNI de 8 dígitos
    private String email;
    private String telefono;
    private Timestamp fechaRegistro;

    public Cliente(int idCliente, String nombre, String documento, String email, String telefono,
            Timestamp fechaRegistro) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.documento = documento;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void mostrarCliente() {
        System.out.println("ID Cliente: " + idCliente);
        System.out.println("Nombre: " + nombre);
        System.out.println("Documento: " + documento);
        System.out.println("Email: " + email);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Fecha de registro: " + fechaRegistro);
    }
}
