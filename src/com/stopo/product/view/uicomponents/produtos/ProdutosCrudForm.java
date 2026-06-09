package com.stopo.product.view.uicomponents.produtos;

import com.stopo.product.view.uiconstants.AppColors;
import com.stopo.product.view.uiutils.StopoButtonFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProdutosCrudForm extends JPanel {

    private final JTextField nomeField = new JTextField();
    private final JTextField descricaoField = new JTextField();
    private final JTextField precoField = new JTextField();
    private final JTextField estoqueField = new JTextField();
    private final JTextField codigoBarrasField = new JTextField();

    private String imagePath = null;
    private final JLabel imageLabel = new JLabel("Sem imagem", SwingConstants.CENTER);
    private final JButton selectImageBtn = StopoButtonFactory.createButton("Selecionar", AppColors.BLUE, 12, _ -> imageChooser());
    private final JButton removeImageBtn = StopoButtonFactory.createButton("Remover", AppColors.RED, 12, _ -> setImage(null));

    public ProdutosCrudForm() {
        setLayout(new BorderLayout());
        add(buildFormPanel(), BorderLayout.CENTER);
    }

    private JPanel buildFormPanel() {
        imageLabel.setPreferredSize(new Dimension(250, 250));
        imageLabel.setBorder(BorderFactory.createLineBorder(AppColors.TEXT));

        JPanel imageButtonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        imageButtonsPanel.add(selectImageBtn);
        imageButtonsPanel.add(removeImageBtn);

        JPanel imagePanel = new JPanel(new BorderLayout(0, 5));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(imageButtonsPanel, BorderLayout.SOUTH);

        JPanel fieldPanel = new JPanel(new GridLayout(10, 1, 0, 2));
        fieldPanel.add(new JLabel("Nome: "));
        fieldPanel.add(nomeField);
        fieldPanel.add(new JLabel("Descrição: "));
        fieldPanel.add(descricaoField);
        fieldPanel.add(new JLabel("Preço: "));
        fieldPanel.add(precoField);
        fieldPanel.add(new JLabel("Qnt. Estoque: "));
        fieldPanel.add(estoqueField);
        fieldPanel.add(new JLabel("Cod. Barras: "));
        fieldPanel.add(codigoBarrasField);

        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.add(imagePanel, BorderLayout.NORTH);
        panel.add(fieldPanel, BorderLayout.SOUTH);
        return panel;
    }

    public void preencher(String nome, String descricao, String preco, String estoque, String codBarras, String imagemPath) {
        nomeField.setText(nome);
        descricaoField.setText(descricao);
        precoField.setText(preco);
        estoqueField.setText(estoque);
        codigoBarrasField.setText(codBarras);
        setImage(imagemPath);
    }

    public void limpar() {
        preencher("", "", "", "", "", null);
    }

    public void setImage(String path) {
        if (path == null) {
            imagePath = null;
            imageLabel.setIcon(null);
            imageLabel.setText("Sem imagem");
            return;
        }

        try {
            BufferedImage buffered = ImageIO.read(new File(path));

            if (buffered == null) {
                JOptionPane.showMessageDialog(this, "Imagem inválida");
                return;
            }

            Image img = buffered.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
            imageLabel.setText(null);
            imagePath = path;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem");
        }
    }

    private void imageChooser() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
        chooser.setFileFilter(filter);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            setImage(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    public String getNome() { return nomeField.getText(); }
    public String getDescricao() { return descricaoField.getText(); }
    public String getPreco() { return precoField.getText(); }
    public String getEstoque() { return estoqueField.getText(); }
    public String getCodigoBarras() { return codigoBarrasField.getText(); }
    public String getImagePath() { return imagePath; }
}