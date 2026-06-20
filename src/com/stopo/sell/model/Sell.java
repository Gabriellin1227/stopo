package com.stopo.sell.model;

import com.stopo.product.model.Product;

public class Sell {
    private int idSell;
    private String date;
    private Product product;
    private int sellQuantity;

    public Sell(int idSell, String date, Product product, int sellQuantity) {
        this.idSell = idSell;
        this.date = date;
        this.product = product;
        this.sellQuantity = sellQuantity;
    }

    public String toCsv() {
        return idSell + "," + date + "," + product + "," + sellQuantity;
    }

    public static Sell fromCsv(String csvLine) {
        String[] fields = csvLine.split(";");
        int idSell = Integer.parseInt(fields[0]);
        String date = fields[1];
        String productCsvPart = fields[2] + ";" + fields[3] + ";" + fields[4] + ";" + fields[5] + ";" + fields[6] + ";" + fields[7];
        Product product = Product.fromCsv(productCsvPart);
        int sellQuantity = Integer.parseInt(fields[8]);

        return new Sell(idSell, date, product, sellQuantity);

    }

    public int getIdSell() {return idSell;}
    public int getIdProduct() {return product.getId();}
    public int getSellQuantity() {return sellQuantity;}
}
