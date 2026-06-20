package com.stopo.app;
import javax.swing.SwingUtilities;
import com.stopo.product.view.Frame.MainFrame;

import com.stopo.product.controller.ProductController;
import com.stopo.product.view.ProductView;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

}
