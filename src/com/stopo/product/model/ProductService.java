package com.stopo.product.model;

import com.stopo.utils.CSVUtil;

public class ProductService {
    private final String ARQ = "src/com/stopo/resources/csv/produtos.csv";
    private final String HEAD = "id;name;description;price;quantity;barcode;";

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

    public void addProduct(String name, String description, double price, int quantity) {
        Product[] now = listProducts();
        int nextId = getNextId();
        String generateBarCode = String.format("1%012d", nextId); // 1 + 12 zeros = 13 digitos
        int checkDigit = calcCheckDigit(generateBarCode);
        String barcode = generateBarCode + checkDigit;

        Product newProduct = new Product(nextId, name, description, price, quantity, barcode);
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
        return now[now.length - 1].getId() + 1; //pegar o ultimo +1
    }

    public void attProduto(Product editProduct) {
        Product[] products = listProducts();
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null && products[i].getId() == editProduct.getId()) {
                // Substitui o produto antigo pelo editado
                products[i] = editProduct;
                break;
            }
        }
        saveProduct(products);
    }
}
