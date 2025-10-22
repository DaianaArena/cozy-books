package main.java.com.app.autor;

import java.util.Date;

public class Autor {

    private int idAutor;
    private String nombre;
    private Date fechaNacimiento;
    private String nacionalidad;
    private String biografia;

    public Autor(int idAutor, String nombre, Date fechaNacimiento, String nacionalidad, String biografia) {
        this.idAutor = idAutor;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.biografia = biografia;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void mostrarAutor() {
        System.out.println("ID Autor: " + idAutor);
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento);
        System.out.println("Nacionalidad: " + nacionalidad);
        System.out.println("Biografía: " + biografia);
    }

}
