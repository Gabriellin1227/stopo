package com.stopo.product.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;

    public Product(int id, String name, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String toCsv() {
        return id + ";"  + name + ";" + description + ";" + price + ";" + quantity;
    }

    public static Product fromCsv(String csvLine) {
        String[] fields = csvLine.split(";");
        int id = Integer.parseInt(fields[0]);
        String name = fields[1];
        String description = fields[2];
        double price = Double.parseDouble(fields[3]);
        int quantity = Integer.parseInt(fields[4]);
        return new Product(id, name, description, price, quantity);
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}
