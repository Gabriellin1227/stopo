package com.stopo.product.view;

import javax.swing.*;
import java.awt.*;

public class ProdutosPanel extends JPanel {

    public ProdutosPanel(MainFrame frame) {
        JButton btnVoltar = new JButton("Home produtos");

        btnVoltar.addActionListener(e ->
                frame.showScreen(Screens.HOME));
        add(btnVoltar);
    }
}