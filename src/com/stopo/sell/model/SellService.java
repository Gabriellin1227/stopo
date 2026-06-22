package com.stopo.sell.model;

import com.stopo.product.model.Product;
import com.stopo.product.model.ProductService;
import com.stopo.utils.CSVUtil;

public class SellService {
    private final String ARQ = "src/resources/csv/sell.csv";
    private final String HEAD = "idSell;date;id;name;description;price;quantity;barcode;imagepath;sellQuantity;status;customerCpf;";
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

    public void addSells(Sell[] newItems) {
        Sell[] now = listSell();
        Sell[] updatedSells = new Sell[now.length + newItems.length];

        for(int i = 0; i < now.length; i++){
            updatedSells[i] = now[i];
        }

        for(int i = 0; i < newItems.length; i++){
            updatedSells[now.length + i] = newItems[i];

            Product p = newItems[i].getProduct();
            p.setQuantity(p.getQuantity() - newItems[i].getSellQuantity());
            productService.attProduct(p);
        }
        saveSell(updatedSells);
    }

    public void cancelSell(int idSell) {
        Sell[] now  = listSell();
        int itemsToCancel = 0;

        for(Sell s: now) {
            if(s.getIdSell() == idSell) {
                itemsToCancel++;
                if (!s.getStatus().equals("Cancelada")) {
                    Product[] products = productService.listProducts();
                    for(Product p : products) {
                        if(p.getId() == s.getIdProduct()) {
                            p.setQuantity(p.getQuantity() + s.getSellQuantity());
                            productService.attProduct(p);
                            break;
                        }
                    }
                }
            }
        }

        if (itemsToCancel == 0) return;

        Sell[] newSells = new Sell[now.length - itemsToCancel];
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
        return now[now.length - 1].getIdSell() + 1;
    }
}