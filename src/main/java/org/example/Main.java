package org.example;
import org.example.Monitoring.Catalog.Product;
import org.example.Monitoring.Watchlist;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Watchlist watchlist = Watchlist.getInstance();
        watchlist.jsonImport();
        Product product = new Product("https://www.costco.com.mx/Linea-Blanca-y-Cocina/Electrodomesticos-de-Cocina/Cafeteras-y-Teteras/Jura-Cafetera-Automatica-X8/p/666240");
        watchlist.addProduct(product);
        watchlist.jsonFlush();

    }
}