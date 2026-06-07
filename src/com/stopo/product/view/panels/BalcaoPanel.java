package com.stopo.product.view.panels;

import com.stopo.product.view.Frame.MainFrame;
import com.stopo.product.view.uiconstants.Screens;

import javax.swing.*;

public class BalcaoPanel extends JPanel {

    public BalcaoPanel(MainFrame frame) {

        JButton btnVoltar = new JButton("Home balcao");

        btnVoltar.addActionListener(e ->
                frame.showScreen(Screens.HOME));

        add(btnVoltar);
    }
}