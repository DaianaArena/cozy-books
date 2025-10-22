package main.java.com.app.repository;

import main.java.com.app.model.Venta;
import java.util.ArrayList;
import java.util.List;

public class VentaRepository {

    private List<Venta> ventas;
    private int contadorId;

    public VentaRepository() {
        this.ventas = new ArrayList<>();
        this.contadorId = 1;
    }

    public void registrar(Venta venta) {
        venta.setIdVenta(contadorId++);
        ventas.add(venta);
    }

    public void actualizar(Venta venta) {
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getIdVenta() == venta.getIdVenta()) {
                ventas.set(i, venta);
                break;
            }
        }
    }

    public void eliminar(int id) {
        ventas.removeIf(venta -> venta.getIdVenta() == id);
    }

    public List<Venta> listar() {
        return new ArrayList<>(ventas);
    }

    public Venta obtenerPorId(int id) {
        for (Venta venta : ventas) {
            if (venta.getIdVenta() == id) {
                return venta;
            }
        }
        return null;
    }

    public List<Venta> buscar(String criterio) {
        List<Venta> resultados = new ArrayList<>();
        for (Venta venta : ventas) {
            if (venta.getCliente().getNombre().toLowerCase().contains(criterio.toLowerCase()) ||
                    venta.getMetodoPago().toLowerCase().contains(criterio.toLowerCase()) ||
                    venta.getEstado().toLowerCase().contains(criterio.toLowerCase())) {
                resultados.add(venta);
            }
        }
        return resultados;
    }

    public void mostrarTodos() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas");
        } else {
            System.out.println("------------------------");
            System.out.println("| $ | Ventas registradas");
            System.out.println("------------------------");

            double totalVentas = 0;
            for (Venta venta : ventas) {
                venta.mostrarVenta();
                totalVentas += venta.getMonto();
                System.out.println("================================================");
            }

            System.out.println("------------------------------------------------");
            System.out.println("| $ | Total de ventas: $" + totalVentas);
            System.out.println("------------------------------------------------");
        }
    }

    public boolean estaVacio() {
        return ventas.isEmpty();
    }
}
