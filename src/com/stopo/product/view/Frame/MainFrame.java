package com.stopo.product.view.Frame;

import com.stopo.product.view.uiconstants.AppColors;
import com.stopo.product.view.uiconstants.Screens;
import com.stopo.product.view.panels.BalcaoPanel;
import com.stopo.product.view.panels.HomePanel;
import com.stopo.product.view.panels.ProdutosPanel;
import com.stopo.product.view.panels.VendasPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainFrame extends JFrame{

    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    public MainFrame() {
        setTitle("Stopo - PDV");
        setSize(1200, 800);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/mainicon.jpg"))).getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        FrameHeader frameHeader = new FrameHeader();
        add(frameHeader, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new HomePanel(this), Screens.HOME.toString());
        contentPanel.add(new BalcaoPanel(this), Screens.BALCAO.toString());
        contentPanel.add(new ProdutosPanel(this), Screens.PRODUTOS.toString());
        contentPanel.add(new VendasPanel(this), Screens.VENDAS.toString());
        contentPanel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, AppColors.BACKGROUND));

        add(contentPanel, BorderLayout.CENTER);

        showScreen(Screens.HOME);
    }

    public void showScreen(Screens screen) {
        cardLayout.show(contentPanel, screen.toString());
    }
}