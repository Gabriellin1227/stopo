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
        UIManager.put("Label.font", new Font(Font.SANS_SERIF, Font.BOLD, 12));
        UIManager.put("Panel.background", AppColors.BACKGROUND);
        UIManager.put("Button.background", AppColors.BACKGROUND);
        UIManager.put("Label.background", AppColors.BACKGROUND);
        UIManager.put("TextField.background", AppColors.BACKGROUND);

        Dimension frameDimensions = new Dimension(1200, 800);

        setTitle("Stopo - PDV");
        setMinimumSize(frameDimensions);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/mainicon.jpg"))).getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        FrameHeader frameHeader = new FrameHeader();
        add(frameHeader, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new HomePanel(this), Screens.HOME.toString());
        contentPanel.add(new BalcaoPanel(), Screens.BALCAO.toString());
        contentPanel.add(new ProdutosPanel(), Screens.PRODUTOS.toString());
        contentPanel.add(new VendasPanel(), Screens.VENDAS.toString());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20 ,20));
        contentPanel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, AppColors.BACKGROUND));

        add(contentPanel, BorderLayout.CENTER);

        FrameFooter frameFooter = new FrameFooter(this);
        add(frameFooter, BorderLayout.SOUTH);
        changeScreen(Screens.HOME);
    }

    public void changeScreen(Screens screen) {
        cardLayout.show(contentPanel, screen.toString());
    }

}