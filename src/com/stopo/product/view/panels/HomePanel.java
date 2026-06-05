package com.stopo.product.view.panels;

import com.stopo.product.view.AppColors;
import com.stopo.product.view.Frame.MainFrame;
import com.stopo.product.view.Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePanel extends JPanel {

    public HomePanel(MainFrame frame) {
        setLayout(new BorderLayout(20, 20));

        // Botões principais
        JButton btnBalcao = criarBotao(Screens.BALCAO + "\n (B)", AppColors.BLUE, 32);
        JButton btnProdutos = criarBotao(Screens.PRODUTOS + "\n (P)", AppColors.GREEN, 32);
        JButton btnVendas = criarBotao(Screens.VENDAS + "\n (V)", AppColors.YELLOW, 32);

        btnBalcao.addActionListener(e ->
                frame.showScreen(Screens.BALCAO));

        btnProdutos.addActionListener(e ->
                frame.showScreen(Screens.PRODUTOS));

        btnVendas.addActionListener(e ->
                frame.showScreen(Screens.VENDAS));


        JPanel content = new JPanel(new GridLayout(1, 2, 20, 20));
        content.setBackground(AppColors.BACKGROUND);
        JPanel esquerda = new JPanel(new BorderLayout());
        esquerda.add(btnBalcao, BorderLayout.CENTER);

        JPanel direita = new JPanel(new GridLayout(2, 1, 20, 20));
        direita.setBackground(AppColors.BACKGROUND);

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

    private JButton criarBotao(String texto, Color cor, int tamanho) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(AppColors.WHITE_TEXT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, tamanho));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(cor, 5));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(AppColors.BACKGROUND);
                btn.setForeground(cor);
                btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, tamanho + 10));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(cor);
                btn.setForeground(AppColors.WHITE_TEXT);
                btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, tamanho));
            }
        });

        return btn;
    }
}