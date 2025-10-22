package main.java.com.app.repository;

import main.java.com.app.model.DetalleVenta;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaRepository {

    private List<DetalleVenta> detalles;
    private int contadorId;

    public DetalleVentaRepository() {
        this.detalles = new ArrayList<>();
        this.contadorId = 1;
    }

    public void registrar(DetalleVenta detalle) {
        detalle.setIdDetalle(contadorId++);
        detalles.add(detalle);
    }

    public void eliminar(int id) {
        detalles.removeIf(detalle -> detalle.getIdDetalle() == id);
    }

    public List<DetalleVenta> listar() {
        return new ArrayList<>(detalles);
    }

    public List<DetalleVenta> buscar(int idVenta) {
        List<DetalleVenta> resultados = new ArrayList<>();
        for (DetalleVenta detalle : detalles) {
            if (detalle.getVenta().getIdVenta() == idVenta) {
                resultados.add(detalle);
            }
        }
        return resultados;
    }

    public DetalleVenta obtenerPorId(int id) {
        for (DetalleVenta detalle : detalles) {
            if (detalle.getIdDetalle() == id) {
                return detalle;
            }
        }
        return null;
    }

    public void mostrarTodos() {
        if (detalles.isEmpty()) {
            System.out.println("No hay detalles de venta registrados");
        } else {
            for (DetalleVenta detalle : detalles) {
                detalle.mostrarDetalle();
                System.out.println("-------------------");
            }
        }
    }

    public boolean estaVacio() {
        return detalles.isEmpty();
    }
}
