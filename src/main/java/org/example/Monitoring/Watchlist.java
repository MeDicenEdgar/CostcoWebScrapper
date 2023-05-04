package org.example.Monitoring;
import java.util.*;

import org.example.Monitoring.Catalog.Product;
import java.io.IOException;
import java.io.File;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Watchlist {
    private HashMap<Integer, HashMap<String, Object>> productHashtable;
    private static final String location = "src/main/resources/Products.json";
    private static Watchlist instance = null;
    static {
        System.out.println("Static");
        File f = new File(location);
        try {
            if(f.createNewFile()){
                System.out.println("Archivo creado");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private Watchlist(){
        productHashtable = new HashMap<>();

    }
    public static Watchlist getInstance(){
        if(instance==null){
            instance=new Watchlist();
        }
        return instance;
    }

    public void addProduct(Product product){
        HashMap<String, Object> tempHash= new HashMap<>();
        tempHash.put("Link", product.getLink());
        tempHash.put("priceHistory", product.getPriceHistory());
        productHashtable.put(productHashtable.size()+1,tempHash);
    }

    public void jsonFlush() throws IOException {
        try (FileWriter file = new FileWriter(location)) {
            file.write(JSONObject.toJSONString(productHashtable));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void jsonImport(){
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/Products.json")) {
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            for (Object key : jsonObject.keySet()) {
                JSONObject details = (JSONObject) jsonObject.get((String)key);
                Product product = new Product((String) details.get("Link"), (HashMap<String, Double>)details.get("priceHistory"));
                addProduct(product);
            }
            System.out.println(productHashtable.toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
