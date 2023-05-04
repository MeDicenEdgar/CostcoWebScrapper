package org.example.Monitoring.Catalog;
import java.util.*;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Product {
    private double price;
    private String productName, link;
    private  HashMap<Long , Double> priceHistory = new HashMap<>();
    public Product(String link) throws IOException {
        this.link = link;
        long currentDate = System.currentTimeMillis();
        Document doc = Jsoup.connect(link).get();
        Elements elements = doc.getElementsByTag("h1");
        Elements price = doc.getElementsByClass("product-price-amount ng-star-inserted");
        String priceString = "";
        for(Element x: elements) {
            productName = x.text();
        }
        for(Element x: price) {
            priceString=x.text();
        }
        price = doc.getElementsByClass("price-after-discount");
        for(Element x: price) {
            priceString=x.text();
        }
        this.price = stringToNumber(priceString);
        priceHistory.put(currentDate, this.price);

    }

    public Product(String link, HashMap<String, Double> priceHistory) throws IOException {
        this.link = link;
        long currentDate = System.currentTimeMillis();
        Document doc = Jsoup.connect(link).get();
        Elements elements = doc.getElementsByTag("h1");
        Elements price = doc.getElementsByClass("product-price-amount ng-star-inserted");
        String priceString = "";
        for(Element x: elements) {
            productName = x.text();
        }
        for(Element x: price) {
            priceString=x.text();
        }
        price = doc.getElementsByClass("price-after-discount");
        for(Element x: price) {
            priceString=x.text();
        }
        this.price = stringToNumber(priceString);
        Set<String> keySet = priceHistory.keySet();
        Object[] keyArray = keySet.toArray();
        for (int i = 0; i < keyArray.length; i++) {
            long key = Long.parseLong((String) keyArray[i]);
            this.priceHistory.put(key, priceHistory.get((String) keyArray[i]));
        }
        this.priceHistory.put(currentDate, this.price);
    }

    public double getPrice() {
        return price;
    }
    public String getLink(){
        return link;
    }

    public String getProductName() {
        return productName;
    }
    public HashMap<Long, Double> getPriceHistory(){
        return priceHistory;
    }

    private static double stringToNumber (String moneyString) {
        String cleanString = moneyString.replace("$", "").replace(",", "").replace(" ", "").replace("Precio", "");
        double result = Double.parseDouble(cleanString);
        return result;
    }

}
