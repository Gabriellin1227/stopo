package com.stopo.sell.model;

import com.stopo.product.model.Product;
import com.stopo.product.model.ProductService;
import com.stopo.utils.CSVUtil;

public class SellService {
    private final String ARQ = "src/resources/csv/sell.csv";
    private final String HEAD = "idSell;date;id;name;description;price;quantity;barcode;quantity;price;";
    ProductService productService = new ProductService();

    public SellService() { CSVUtil.createCsv(ARQ, HEAD); }

    public Sell[] listSell() {
        String[] lineNF = CSVUtil.readAllCSV(ARQ);
        Sell[] sells = new Sell[lineNF.length];

        for(int i = 0; i < lineNF.length; i++){
            sells[i] = Sell.fromCsv(lineNF[i]);
        }
        return sells;
    }

    public void saveSell(Sell[] nSell){
        String[] lineNF = new String[nSell.length];
        for(int i = 0; i < nSell.length; i++){
            lineNF[i] = nSell[i].toCsv();
        }
        CSVUtil.saveCSV(ARQ,HEAD,lineNF);
    }

    public void addSell(int sell, String date, Product product, int sellQuantity) {
        // verif do estoque do produto
        if(product.getQuantity() < sellQuantity){
            System.out.println("Sell quantity exceeded");
            return;
        }
        product.setQuantity(product.getQuantity() + sellQuantity);
        productService.attProduct(product);
        Sell[] now = listSell();
        int nextIdSell = getNextId();
        double totalPrice = product.getPrice() * sellQuantity;
        Sell newSell = new Sell(nextIdSell, date, product, sellQuantity);

        Sell[] newSells = new Sell[now.length + 1];
        for (int i = 0; i < now.length; i++) {
            newSells[i] = now[i];
        }
    }

    public void cancelSell(int idSell) {
        Sell[] now  = listSell();
        Sell sellToCancel = null;
        for(Sell s: now) {
            if(s.getIdSell() == idSell) {
                sellToCancel = s;
                break;
            }
        }
        if (sellToCancel == null) {
            System.out.println("erro na venda id: " + idSell);
            return;
        }
        Product[] products = productService.listProducts();
        for(Product p : products) {
            if(p.getId() == sellToCancel.getIdProduct()) {
                p.setQuantity(p.getQuantity() - sellToCancel.getSellQuantity());
                productService.attProduct(p);
                break;
            }
        }
        Sell[] newSells = new Sell[now.length - 1]; // cria lista com n-1 e da um copy em td menos no cancel
        int idx = 0;
        for(Sell s: now) {
            if(s.getIdSell() != idSell) {
                newSells[idx] = s;
                idx++;
            }
        }
        saveSell(newSells);
    }

    public int getNextId() {
        Sell[] now = listSell();
        if(now.length == 0 ) return 1;
        return now[now.length - 1].getIdSell() + 1; //pegar o ultimo +1
    }
}
