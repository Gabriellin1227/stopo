package com.stopo.product.view.panels;

import com.stopo.product.view.Frame.MainFrame;
import com.stopo.product.view.uiconstants.Screens;

import javax.swing.*;

public class VendasPanel extends JPanel {

    public VendasPanel(MainFrame frame){
        JButton btnVoltar = new JButton("Home vendas");

        btnVoltar.addActionListener(e ->
                frame.showScreen(Screens.HOME));
        add(btnVoltar);
    }
}
