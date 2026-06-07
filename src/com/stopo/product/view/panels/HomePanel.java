package com.stopo.product.view.panels;

import com.stopo.product.view.uiconstants.AppColors;
import com.stopo.product.view.Frame.MainFrame;
import com.stopo.product.view.uiconstants.Screens;
import com.stopo.product.view.uiutils.KeyBindingsFactory;
import com.stopo.product.view.uiutils.StopoButtonFactory;

import javax.swing.*;
import java.awt.*;


public class HomePanel extends JPanel {

    public HomePanel(MainFrame frame) {
        setLayout(new BorderLayout(20, 20));
        
        JButton btnBalcao = StopoButtonFactory.createButton(
                Screens.BALCAO + " (B)",
                AppColors.BLUE,
                32,
                _ -> frame.showScreen(Screens.BALCAO)
        );

        JButton btnProdutos = StopoButtonFactory.createButton(
                Screens.PRODUTOS + " (P)",
                AppColors.GREEN,
                32,
                _ -> frame.showScreen(Screens.PRODUTOS));

        JButton btnVendas = StopoButtonFactory.createButton(
                Screens.VENDAS + " (V)",
                AppColors.YELLOW,
                32,
                _ -> frame.showScreen(Screens.VENDAS));

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(AppColors.BACKGROUND);

        GridBagConstraints gBagConfig = new GridBagConstraints();
        gBagConfig.insets = new Insets(10, 10, 10, 10);
        gBagConfig.fill = GridBagConstraints.BOTH;
        gBagConfig.weightx = 1;
        gBagConfig.weighty = 1;

        gBagConfig.gridx = 0;
        gBagConfig.gridy = 0;
        gBagConfig.gridheight = 2;
        content.add(btnBalcao, gBagConfig);

        gBagConfig.gridx = 1;
        gBagConfig.gridy = 0;
        gBagConfig.gridheight = 1;
        content.add(btnProdutos, gBagConfig);

        gBagConfig.gridy = 1;
        content.add(btnVendas, gBagConfig);

        add(content, BorderLayout.CENTER);

        KeyBindingsFactory.bind(
                this,
                "B",
                "changeToBalcao",
                () -> frame.showScreen(Screens.BALCAO)
        );

        KeyBindingsFactory.bind(
                this,
                "P",
                "changeToProdutos",
                () -> frame.showScreen(Screens.PRODUTOS)
        );

        KeyBindingsFactory.bind(
                this,
                "V",
                "changeToVendas",
                () -> frame.showScreen(Screens.VENDAS)
        );
    }
}