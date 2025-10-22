package main.java.com.app.util;

import main.java.com.app.model.Venta;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class ArchivoService {

    public void generarTicket(Venta venta) {
        try {
            int num = 1;
            String numTicket = "ticket" + num + ".txt";
            File ticket = new File(numTicket);

            while (ticket.exists()) {
                numTicket = "ticket" + (num++) + ".txt";
                ticket = new File(numTicket);
            }

            if (ticket.createNewFile()) {
                FileWriter myWriter = new FileWriter(ticket);

                // Encabezado del ticket
                myWriter.write("-----------------------------------------------------\n\n");
                myWriter.write("  _____                 ____              _        \n\n");
                myWriter.write(" / ____|               |  _ \\            | |       \n\n");
                myWriter.write("| |     ___ _____   _  | |_) | ___   ___ | | _____ \n\n");
                myWriter.write("| |    / _ \\_  / | | | |  _ < / _ \\ / _ \\| |/ / __|\n\n");
                myWriter.write("| |___| (_) / /| |_| | | |_) | (_) | (_) |   <\\__ \\\n\n");
                myWriter.write(" \\_____\\___/___|\\__, | |____/ \\___/ \\___/|_|\\_\\___/\n\n");
                myWriter.write("                __/  |                             \n\n");
                myWriter.write("               |____/                              \n\n");
                myWriter.write("-----------------------------------------------------\n\n");

                // Información de la venta
                myWriter.write("ID Venta: " + venta.getIdVenta() + "\n\n");
                myWriter.write("Fecha: " + venta.getFecha() + "\n\n");
                myWriter.write("Cliente: " + venta.getCliente().getNombre() + "\n\n");
                myWriter.write("Método de pago: " + venta.getMetodoPago() + "\n\n");
                myWriter.write("Estado: " + venta.getEstado() + "\n\n");

                myWriter.write("////////////////////////////////////////////// \n\n");
                myWriter.write("Artículos: \n\n");

                // Detalles de la venta
                if (!venta.getDetallesVenta().isEmpty()) {
                    for (main.java.com.app.model.DetalleVenta detalle : venta.getDetallesVenta()) {
                        myWriter.write("Título: " + detalle.getLibro().getTitulo() + "\n");
                        myWriter.write("Tipo: " + detalle.getLibro().getTipoLibro() + "\n");
                        myWriter.write("Cantidad: " + detalle.getCantidad() + "\n");
                        myWriter.write("Precio unitario: $" + detalle.getPrecioUnitario() + "\n");
                        myWriter.write("Subtotal: $" + detalle.getSubtotal() + "\n\n");
                        myWriter.write("-----------------------------------------------------\n\n");
                    }
                } else {
                    myWriter.write("No hay artículos en esta venta.\n\n");
                }

                // Total
                myWriter.write("-----------------------------------------------------\n\n");
                myWriter.write("TOTAL A PAGAR:      $" + venta.getMonto() + "\n\n");
                myWriter.write("-----------------------------------------------------\n\n");

                myWriter.close();
                System.out.println("Ticket generado: " + ticket.getName());
            } else {
                System.out.println("No se pudo crear el archivo del ticket.");
            }
        } catch (IOException e) {
            System.out.println("Error al generar el ticket.");
            e.printStackTrace();
        }
    }
}
