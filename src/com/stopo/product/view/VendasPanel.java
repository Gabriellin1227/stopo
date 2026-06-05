package com.stopo.product.view;

import javax.swing.*;

public class VendasPanel extends JPanel {

    public VendasPanel(MainFrame frame){
        JButton btnVoltar = new JButton("Home vendas");

        btnVoltar.addActionListener(e ->
                frame.showScreen(Screens.HOME));
        add(btnVoltar);
    }
}
