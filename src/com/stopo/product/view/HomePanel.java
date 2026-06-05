package com.stopo.product.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomePanel extends JPanel {

    public HomePanel(MainFrame frame) {
        setLayout(new BorderLayout(20, 20));

        // Botões principais
        JButton btnBalcao = criarBotao(Screens.BALCAO + "\n (B)");
        JButton btnProdutos = criarBotao(Screens.PRODUTOS + "\n (P)");
        JButton btnVendas = criarBotao(Screens.VENDAS + "\n (V)");

        btnBalcao.addActionListener(e ->
                frame.showScreen(Screens.BALCAO));

        btnProdutos.addActionListener(e ->
                frame.showScreen(Screens.PRODUTOS));

        btnVendas.addActionListener(e ->
                frame.showScreen(Screens.VENDAS));


        // Layout dos cards
        JPanel content = new JPanel(new GridLayout(1, 2, 20, 20));

        JPanel esquerda = new JPanel(new BorderLayout());
        esquerda.add(btnBalcao, BorderLayout.CENTER);

        JPanel direita = new JPanel(new GridLayout(2, 1, 20, 20));

        direita.add(btnProdutos);
        direita.add(btnVendas);

        content.add(esquerda);
        content.add(direita);

        add(content, BorderLayout.CENTER);

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("B"), "bAction");
        inputMap.put(KeyStroke.getKeyStroke("P"), "pAction");
        inputMap.put(KeyStroke.getKeyStroke("V"), "vAction");

        actionMap.put("bAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showScreen(Screens.BALCAO);
            }
        });

        actionMap.put("pAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showScreen(Screens.PRODUTOS);
            }
        });

        actionMap.put("vAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showScreen(Screens.VENDAS);
            }
        });
    }

    private JButton criarBotao(String texto) {

        JButton btn = new JButton(texto);

        btn.setFont(new Font("Arial", Font.BOLD, 22));
        btn.setFocusPainted(false);

        return btn;
    }

}