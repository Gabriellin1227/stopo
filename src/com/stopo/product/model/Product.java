package com.stopo.product.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String barcode;
    private String imagepath;

    public Product(int id, String name, String description, double price, int quantity, String barcode, String imagepath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.barcode = barcode;
        this.imagepath = imagepath;
    }

    public String toCsv() {
        return id + ";"  + name + ";" + description + ";" + price + ";" + quantity + ";" + barcode  + ";" + imagepath;
    }

    public static Product fromCsv(String csvLine) {
        String[] fields = csvLine.split(";");
        int id = Integer.parseInt(fields[0]);
        String name = fields[1];
        String description = fields[2];
        double price = Double.parseDouble(fields[3]);
        int quantity = Integer.parseInt(fields[4]);
        String barcode = fields[5];
        String imagepath = fields[6];

        return new Product(id, name, description, price, quantity, barcode, imagepath);
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
    public String getBarcode() {return barcode;}
    public String getImagepath() {return imagepath;}
    public void setImagepath(String imagepath) {this.imagepath = imagepath;}
}
