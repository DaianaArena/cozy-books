package main.java;

import main.java.com.app.view.ShopView;
import main.java.com.app.util.DatabaseSetup;

public class App {
    public static void main(String[] args) throws Exception {

        // Configurar la base de datos
        System.out.println("Inicializando sistema...");
        DatabaseSetup.setupDatabase();

        // Crea una instancia de la vista que inicia el sistema
        ShopView shopView = new ShopView();
        shopView.iniciar();
    }
}
