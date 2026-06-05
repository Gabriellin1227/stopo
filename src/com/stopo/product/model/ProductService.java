package com.stopo.product.model;

import com.stopo.utils.CSVUtil;

public class ProductService {
    private final String ARQ = "src/com/stopo/resources/produtos.csv";
    private final String HEAD = "id;name;description;price;quantity";

    public ProductService() {
        CSVUtil.createCsv(ARQ, HEAD);
    }

    public Product[] listProducts() {
        String[] lineNF = CSVUtil.readAllCSV(ARQ);
        Product[] products = new Product[lineNF.length];

        for(int i = 0; i < lineNF.length; i++){
            products[i] = Product.fromCsv(lineNF[i]);
        }
        return products;
    }

    public void saveProduct(Product[] nProduct) {
        String[] lineNF = new String[nProduct.length];
        for(int i = 0; i < nProduct.length; i++) {
            lineNF[i] = nProduct[i].toCsv();
        }
        CSVUtil.saveCSV(ARQ, HEAD, lineNF);
    }

    public void addProduct(Product newProduct) {
        Product[] now = listProducts();
        Product[] newProducts = new Product[now.length + 1];
        for(int i = 0; i < now.length; i++) {
            newProducts[i] = now[i];
        }
        newProducts[newProducts.length - 1] = newProduct;
        saveProduct(newProducts);
    }

    public int getNextId() {
        Product[] now = listProducts();
        if(now.length == 0 ) return 1;
        return now[now.length - 1].getId() + 1; //pegar o ultimo +1
    }
}
