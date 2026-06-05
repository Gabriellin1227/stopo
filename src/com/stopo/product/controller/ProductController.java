package com.stopo.product.controller;

import com.stopo.product.model.Product;
import com.stopo.product.model.ProductService;
import com.stopo.product.view.ProductView;

public class ProductController {
    private ProductView view;
    private ProductService service;

    public ProductController(ProductView view) {
        this.view = view;
        this.service = new ProductService();

        this.view.addSaveListener(e -> saveProduct());
        this.view.addCancelListener(e -> cancelProduct());

        refreshList();
    }

    private void cancelProduct() {
        view.clearFields();
        view.showMessage("Cadastro de Produto cancelado");
    }

    private void saveProduct() {
        try {
            String name = view.getProductName();
            String description = view.getProductDescription();
            double price = Double.parseDouble(view.getProductPrice());
            int quantity = Integer.parseInt(view.getProductQuantity());
            if(name.isEmpty() || price <= 0 || quantity <= 0) {
                view.showMessage("Erro: o nome do produto não pode estar vazio");
                return;
            }

            int nextId = service.getNextId();
            Product p = new Product(nextId, name, description, price, quantity);

            service.addProduct(p);

            view.showMessage("Produto Salvo com sucesso!");
            view.clearFields();
            refreshList();
        } catch(NumberFormatException e) {
            view.showMessage("Erro ao salvar!");
        }
    }

    private void refreshList() {
        Product[] products = service.listProducts();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-4s | %-15s | %-9s | %-4s\n", "ID", "Nome", "Preço", "Qtd"));
        sb.append("-------------------------------------------\n");

        // Percorre o vetor nativo tradicional
        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            sb.append(String.format("%-4d | %-15s | R$ %-6.2f | %-4d\n",
                    p.getId(), p.getName(), p.getPrice(), p.getQuantity()));
        }
        view.updateList(sb.toString());
    }
}

