package com.stopo.product.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    public MainFrame() {
        setTitle("Stopo - PDV");
        setSize(1200, 800);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/mainicon.jpg"))).getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        Header header = new Header();
        add(header, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new HomePanel(this), Screens.HOME.toString());
        contentPanel.add(new BalcaoPanel(this), Screens.BALCAO.toString());
        contentPanel.add(new ProdutosPanel(this), Screens.PRODUTOS.toString());
        contentPanel.add(new VendasPanel(this), Screens.VENDAS.toString());
        contentPanel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.BLACK));

        add(contentPanel, BorderLayout.CENTER);

        showScreen(Screens.HOME);
    }

    public void showScreen(Screens screen) {
        cardLayout.show(contentPanel, screen.toString());
    }
}