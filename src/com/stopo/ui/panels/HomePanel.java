package com.stopo.ui.panels;

import com.stopo.ui.constants.AppColors;
import com.stopo.ui.Frame.MainFrame;
import com.stopo.ui.constants.Screens;
import com.stopo.ui.uiutils.StopoUiFactory;

import javax.swing.*;
import java.awt.*;


public class HomePanel extends JPanel {
    public HomePanel(MainFrame frame) {
        setLayout(new GridBagLayout());

        JButton btnBalcao = StopoUiFactory.createButton(
                Screens.BALCAO + " (B)",
                AppColors.BLUE,
                32,
                _ -> frame.changeScreen(Screens.BALCAO));

        JButton btnProdutos = StopoUiFactory.createButton(
                Screens.PRODUTOS + " (P)",
                AppColors.GREEN,
                32,
                _ -> frame.changeScreen(Screens.PRODUTOS));

        JButton btnVendas = StopoUiFactory.createButton(
                Screens.VENDAS + " (V)",
                AppColors.YELLOW,
                32,
                _ -> frame.changeScreen(Screens.VENDAS));


        // Desenhando os buttons

        GridBagConstraints gBagConfig = new GridBagConstraints();
        gBagConfig.insets = new Insets(10, 10, 10, 10);
        gBagConfig.fill = GridBagConstraints.BOTH;
        gBagConfig.weightx = 1;
        gBagConfig.weighty = 1;

        gBagConfig.gridx = 0;
        gBagConfig.gridy = 0;
        gBagConfig.gridheight = 2;
        add(btnBalcao, gBagConfig);

        gBagConfig.gridx = 1;
        gBagConfig.gridy = 0;
        gBagConfig.gridheight = 1;
        add(btnProdutos, gBagConfig);

        gBagConfig.gridy = 1;
        add(btnVendas, gBagConfig);

        StopoUiFactory.bindKey(
                this,
                "B",
                "changeToBalcao",
                () -> frame.changeScreen(Screens.BALCAO)
        );

        StopoUiFactory.bindKey(
                this,
                "P",
                "changeToProdutos",
                () -> frame.changeScreen(Screens.PRODUTOS)
        );

        StopoUiFactory.bindKey(
                this,
                "V",
                "changeToVendas",
                () -> frame.changeScreen(Screens.VENDAS)
        );
    }
}