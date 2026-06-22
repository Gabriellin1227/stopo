package com.stopo.product.model;

import com.stopo.utils.CSVUtil;

public class ProductService {
    private final String ARQ = "src/resources/csv/produtos.csv";
    private final String HEAD = "id;name;description;price;quantity;barcode;imagepath;";

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

    public String generateBarcodeForId(int id) {
        String generateBarCode = String.format("1%012d", id);
        int checkDigit = calcCheckDigit(generateBarCode);
        return generateBarCode + checkDigit;
    }

    // Agora o método recebe o barcode vindo da interface
    public void addProduct(String name, String description, double price, int quantity, String barcode, String imagePath) {
        Product[] now = listProducts();
        int nextId = getNextId();

        Product newProduct = new Product(nextId, name, description, price, quantity, barcode, imagePath);
        Product[] newProducts = new Product[now.length + 1];
        for(int i = 0; i < now.length; i++) {
            newProducts[i] = now[i];
        }
        newProducts[newProducts.length - 1] = newProduct;
        saveProduct(newProducts);
    }

    private int calcCheckDigit(String code) {
        int sum = 0;
        for (int i = 0; i < code.length(); i++) {
            int digit = Character.getNumericValue(code.charAt(i));
            sum += (i % 2 == 0) ? digit * 3 : digit;
        }
        return (10 - (sum % 10)) % 10;
    }

    public int getNextId() {
        Product[] now = listProducts();
        if(now.length == 0 ) return 1;
        return now[now.length - 1].getId() + 1;
    }

    public void attProduct(Product editProduct) {
        Product[] now = listProducts();
        for (int i = 0; i < now.length; i++) {
            if (now[i] != null && now[i].getId() == editProduct.getId()) {
                now[i] = editProduct;
                break;
            }
        }
        saveProduct(now);
    }

    public void deleteProduct(int idProduct) {
        Product[] now = listProducts();
        if(now.length == 0) return;
        Product[] newProducts = new Product[now.length - 1];
        int index = 0;
        for(Product p:now) {
            if(p.getId() != idProduct) {
                if(index<newProducts.length) {
                    newProducts[index] = p;
                    index++;
                }
            }
        }
        saveProduct(newProducts);
    }
}