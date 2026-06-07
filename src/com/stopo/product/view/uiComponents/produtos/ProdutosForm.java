package com.stopo.product.view.uiComponents.produtos;

import javax.swing.*;
import java.awt.*;

public class ProdutosForm extends JPanel {
    private JTextField nome = new JTextField();
    private JTextField preco = new JTextField();
    private JTextField qtdEstoque = new JTextField();
    private JTextField codBarras = new JTextField();

    public ProdutosForm(){
        setLayout(new GridLayout(1, 2));

        JPanel formPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Nome"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(nome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Preço"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(preco, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Qtd Estoque"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(qtdEstoque, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Código Barras"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(codBarras, gbc);

        JPanel imagePanel = new JPanel(new BorderLayout());

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnSelecionar = new JButton("Selecionar");
        JButton btnLimpar = new JButton("Limpar");

        JPanel buttons = new JPanel(new GridLayout(1, 2, 5, 0));
        buttons.add(btnSelecionar);
        buttons.add(btnLimpar);

        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(buttons, BorderLayout.SOUTH);

        add(formPanel);
        add(imagePanel);
    }
}
