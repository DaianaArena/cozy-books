package main.java.com.app.view;

import main.java.com.app.controller.ShopController;

public class ShopView {

    private ShopController shopController;

    public ShopView() {
        this.shopController = new ShopController();
    }

    public void iniciar() {
        System.out.println("=====================================================");
        System.out.println("  Bienvenido al Sistema de gestión de ventas de ");
        System.out.println("  _____                 ____              _        ");
        System.out.println(" / ____|               |  _ \\            | |       ");
        System.out.println("| |     ___ _____   _  | |_) | ___   ___ | | _____ ");
        System.out.println("| |    / _ \\_  / | | | |  _ < / _ \\ / _ \\| |/ / __|");
        System.out.println("| |___| (_) / /| |_| | | |_) | (_) | (_) |   <\\__ \\");
        System.out.println(" \\_____\\___/___|\\__, | |____/ \\___/ \\___/|_|\\_\\___/");
        System.out.println("                __/  |                             ");
        System.out.println("               |____/                              ");
        System.out.println("=====================================================");
        System.out.println("Nombre de la tienda: Cozy Books");
        System.out.println("Sobre nosotros: Cozy Books es una tienda en línea de libros");
        System.out.println("fundada en 2013 por una familia apasionada por la lectura.");
        System.out.println("=====================================================");

        // Delegar el control del menú al controlador
        shopController.menu();
    }
}
