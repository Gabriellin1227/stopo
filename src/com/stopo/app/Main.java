package com.stopo.app;

import com.stopo.product.controller.ProductController;
import com.stopo.product.view.ProductView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
             ProductView productView = new ProductView();
             ProductController productController = new ProductController(productView);
             productView.setVisible(true);
        });
    }
}