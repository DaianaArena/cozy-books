package main.java.com.app.model;

import java.sql.Timestamp;

public class Libro {

    private int idLibro;
    private String titulo;
    private String isbn;
    private String editorial;
    private int año;
    private double precio;
    private String genero;
    private String tipoLibro; // FISICO, DIGITAL, AUDIOLIBRO
    private int stock;
    private Timestamp fechaRegistro;
    private Autor autor;

    // Campos específicos para FISICO
    private String encuadernado;
    private int numEdicion;

    // Campos específicos para DIGITAL
    private String extension;
    private Boolean permisosImpresion;

    // Campos específicos para AUDIOLIBRO
    private int duracion; // en minutos
    private String plataforma;
    private String narrador;

    public Libro(int idLibro, String titulo, String isbn, String editorial, int año, double precio,
            String genero, String tipoLibro, int stock, Timestamp fechaRegistro, Autor autor) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.editorial = editorial;
        this.año = año;
        this.precio = precio;
        this.genero = genero;
        this.tipoLibro = tipoLibro;
        this.stock = stock;
        this.fechaRegistro = fechaRegistro;
        this.autor = autor;
    }

    // Getters y Setters básicos
    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipoLibro() {
        return tipoLibro;
    }

    public void setTipoLibro(String tipoLibro) {
        this.tipoLibro = tipoLibro;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    // Getters y Setters para campos específicos de FISICO
    public String getEncuadernado() {
        return encuadernado;
    }

    public void setEncuadernado(String encuadernado) {
        this.encuadernado = encuadernado;
    }

    public int getNumEdicion() {
        return numEdicion;
    }

    public void setNumEdicion(int numEdicion) {
        this.numEdicion = numEdicion;
    }

    // Getters y Setters para campos específicos de DIGITAL
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Boolean getPermisosImpresion() {
        return permisosImpresion;
    }

    public void setPermisosImpresion(Boolean permisosImpresion) {
        this.permisosImpresion = permisosImpresion;
    }

    // Getters y Setters para campos específicos de AUDIOLIBRO
    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getNarrador() {
        return narrador;
    }

    public void setNarrador(String narrador) {
        this.narrador = narrador;
    }

    public void mostrarLibro() {
        System.out.println("ID Libro: " + idLibro);
        System.out.println("Título: " + titulo);
        System.out.println("ISBN: " + isbn);
        System.out.println("Autor: " + autor.getNombre());
        System.out.println("Editorial: " + editorial);
        System.out.println("Año: " + año);
        System.out.println("Precio: " + precio);
        System.out.println("Género: " + genero);
        System.out.println("Tipo: " + tipoLibro);
        System.out.println("Stock: " + stock);
        System.out.println("Fecha de registro: " + fechaRegistro);

        // Mostrar campos específicos según el tipo
        if ("FISICO".equals(tipoLibro)) {
            System.out.println("Encuadernado: " + encuadernado);
            System.out.println("Número de edición: " + numEdicion);
        } else if ("DIGITAL".equals(tipoLibro)) {
            System.out.println("Extensión: " + extension);
            System.out.println("Permisos de impresión: " + permisosImpresion);
        } else if ("AUDIOLIBRO".equals(tipoLibro)) {
            System.out.println("Duración: " + duracion + " minutos");
            System.out.println("Plataforma: " + plataforma);
            System.out.println("Narrador: " + narrador);
        }
    }
}
