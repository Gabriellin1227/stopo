package com.stopo.product.view.panels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.stopo.product.view.uiconstants.AppColors;
import com.stopo.product.view.uiutils.StopoUiFactory;

public class ProdutosPanel extends JPanel {

    private final JTable tabelaProdutos;
    private final DefaultTableModel tableModel;

    private final JTextField txtNome = new JTextField();
    private final JTextField txtDescricao = new JTextField();
    private final JTextField txtPreco = new JTextField();
    private final JTextField txtEstoque = new JTextField();
    private final JTextField txtCodigoBarras = new JTextField();

    private String currentImagePath = null;
    private final JLabel lblImagem = new JLabel("Sem imagem", SwingConstants.CENTER);

    private final JButton btnNovo    = StopoUiFactory.createButton("Novo (F2)",    AppColors.GREEN,  12, _ -> onNovo());
    private final JButton btnEditar  = StopoUiFactory.createButton("Editar (F3)",  AppColors.YELLOW, 12, _ -> onEditar());
    private final JButton btnExcluir = StopoUiFactory.createButton("Excluir (F4)", AppColors.RED,    12, _ -> onExcluir());
    private final JButton btnSalvar  = StopoUiFactory.createButton("Salvar (F5)",  AppColors.BLUE,   12, _ -> onSalvar());

    private final JButton btnSelecionarImg = StopoUiFactory.createButton("Selecionar", AppColors.BLUE, 12, _ -> escolherImagem());
    private final JButton btnRemoverImg    = StopoUiFactory.createButton("Remover", AppColors.RED, 12, _ -> setImagem(null));

    public ProdutosPanel() {
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"Nome", "Descrição", "Preço", "Estoque", "Código de Barras"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaProdutos = new JTable(tableModel);
        tabelaProdutos.setRowHeight(30);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.getTableHeader().setReorderingAllowed(false);
        adicionarProdutosDeTeste();

        add(StopoUiFactory.createPanelHeader("Produtos", "Crud de produtos"), BorderLayout.NORTH);
        add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);
        add(buildEastPanel(), BorderLayout.EAST);

        bindKeys();
    }

    private JPanel buildEastPanel() {
        JPanel eastPanel = new JPanel(new BorderLayout(0, 10));
        eastPanel.setPreferredSize(new Dimension(280, 0));

        lblImagem.setPreferredSize(new Dimension(250, 250));
        lblImagem.setBorder(BorderFactory.createLineBorder(AppColors.TEXT));

        JPanel imageButtonsPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        imageButtonsPanel.add(btnSelecionarImg);
        imageButtonsPanel.add(btnRemoverImg);

        JPanel imageContainer = new JPanel(new BorderLayout(0, 5));
        imageContainer.add(lblImagem, BorderLayout.CENTER);
        imageContainer.add(imageButtonsPanel, BorderLayout.SOUTH);

        JPanel fieldsPanel = new JPanel(new GridLayout(10, 1, 0, 2));
        fieldsPanel.add(new JLabel("Nome:"));          fieldsPanel.add(txtNome);
        fieldsPanel.add(new JLabel("Descrição:"));     fieldsPanel.add(txtDescricao);
        fieldsPanel.add(new JLabel("Preço:"));         fieldsPanel.add(txtPreco);
        fieldsPanel.add(new JLabel("Qnt. Estoque:"));  fieldsPanel.add(txtEstoque);
        fieldsPanel.add(new JLabel("Cód. Barras:"));   fieldsPanel.add(txtCodigoBarras);

        JPanel formPanel = new JPanel(new BorderLayout(0, 10));
        formPanel.add(imageContainer, BorderLayout.NORTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);

        JPanel mainButtonsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        mainButtonsPanel.add(btnNovo);
        mainButtonsPanel.add(btnSalvar);
        mainButtonsPanel.add(btnEditar);
        mainButtonsPanel.add(btnExcluir);

        eastPanel.add(formPanel, BorderLayout.NORTH);
        eastPanel.add(mainButtonsPanel, BorderLayout.SOUTH);

        return eastPanel;
    }

    private void bindKeys() {
        StopoUiFactory.bindKey(this, "F2", "novoAction",    this::onNovo);
        StopoUiFactory.bindKey(this, "F3", "editarAction",  this::onEditar);
        StopoUiFactory.bindKey(this, "F4", "excluirAction", this::onExcluir);
        StopoUiFactory.bindKey(this, "F5", "salvarAction",  this::onSalvar);
    }

    private void escolherImagem() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imagens (JPG, PNG)", "jpg", "jpeg", "png"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            setImagem(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void setImagem(String path) {
        if (path == null) {
            currentImagePath = null;
            lblImagem.setIcon(null);
            lblImagem.setText("Sem imagem");
            return;
        }
        try {
            BufferedImage buffered = ImageIO.read(new File(path));
            if (buffered == null) {
                JOptionPane.showMessageDialog(this, "Imagem inválida.");
                return;
            }
            Image img = buffered.getScaledInstance(250, 200, Image.SCALE_SMOOTH);
            lblImagem.setIcon(new ImageIcon(img));
            lblImagem.setText(null);
            currentImagePath = path;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar a imagem.");
        }
    }

    private void onNovo() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
        txtEstoque.setText("");
        txtCodigoBarras.setText("");
        setImagem(null);
        txtNome.requestFocus();
    }

    private void onSalvar() {
        System.out.println("Salvando produto...");
        // TODO: Pegar os dados dos txtFields e enviar para o backend
    }

    private void onEditar() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Escolha um produto na tabela para editar.");
            return;
        }

        txtNome.setText(tableModel.getValueAt(selectedRow, 0).toString());
        txtDescricao.setText(tableModel.getValueAt(selectedRow, 1).toString());
        txtPreco.setText(tableModel.getValueAt(selectedRow, 2).toString());
        txtEstoque.setText(tableModel.getValueAt(selectedRow, 3).toString());
        txtCodigoBarras.setText(tableModel.getValueAt(selectedRow, 4).toString());
    }

    private void onExcluir() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Escolha um produto para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este produto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            onNovo();
            // TODO: Chamar backend para deletar do banco
        }
    }

    private void adicionarProdutosDeTeste() {
        Object[][] dados = {
                {"Teclado Mecânico Redragon", "Switch Azul", "R$ 289,99", "8", "7899876543210"},
                {"Mouse Logitech MX Master 3", "Mouse sem fio", "R$ 649,90", "11", "7894321098765"},
                {"Monitor LG 27\" 4K", "IPS HDR", "R$ 2199,00", "23", "7892345678901"}
        };
        for (Object[] linha : dados) {
            tableModel.addRow(linha);
        }
    }
}