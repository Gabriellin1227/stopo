package com.stopo.sell.model;

import com.stopo.product.model.Product;

public class Sell {
    private int idSell;
    private String date;
    private Product product;
    private int sellQuantity;
    private String status;
    private String customerCpf;

    public Sell(int idSell, String date, Product product, int sellQuantity, String status, String customerCpf) {
        this.idSell = idSell;
        this.date = date;
        this.product = product;
        this.sellQuantity = sellQuantity;
        this.status = status;
        this.customerCpf = customerCpf;
    }

    public String toCsv() {
        return idSell + ";" + date + ";" + product.toCsv() + ";" + sellQuantity  + ";" + status + ";" + customerCpf;
    }

    public static Sell fromCsv(String csvLine) {
        String[] fields = csvLine.split(";");
        int idSell = Integer.parseInt(fields[0]);
        String date = fields[1];
        String productCsvPart = fields[2] + ";" + fields[3] + ";" + fields[4] + ";" + fields[5] + ";" + fields[6] + ";" + fields[7] + ";" + fields[8];
        Product product = Product.fromCsv(productCsvPart);
        int sellQuantity = Integer.parseInt(fields[9]);
        String status = fields[10];
        String customerCpf = fields.length > 11 ? fields[11] : "null";

        return new Sell(idSell, date, product, sellQuantity, status, customerCpf);
    }

    public int getIdSell() {return idSell;}
    public int getIdProduct() {return product.getId();}
    public int getSellQuantity() {return sellQuantity;}
    public String getDate() {return date;}
    public Product getProduct() {return product;}
    public String getStatus() {return status;}
    public String getCustomerCpf() {return customerCpf;}
}