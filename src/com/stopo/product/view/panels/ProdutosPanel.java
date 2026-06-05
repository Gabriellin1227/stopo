package com.stopo.product.view.panels;

import com.stopo.product.view.Frame.MainFrame;
import com.stopo.product.view.Screens;

import javax.swing.*;

public class ProdutosPanel extends JPanel {

    public ProdutosPanel(MainFrame frame) {
        JButton btnVoltar = new JButton("Home produtos");

        btnVoltar.addActionListener(e ->
                frame.showScreen(Screens.HOME));
        add(btnVoltar);
    }
}