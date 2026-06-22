package com.stopo.product.view.panels;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.stopo.product.model.Product;
import com.stopo.product.model.ProductService;
import com.stopo.product.view.uiconstants.AppColors;
import com.stopo.product.view.uiutils.StopoUiFactory;
import com.stopo.sell.model.SellService;

public class BalcaoPanel extends JPanel {

    private final JTextField txtBuscaProduto;
    private final JSpinner spnQuantidade; // Adicionado o seletor
    private final JTable tabelaCarrinho;
    private final DefaultTableModel tableModel;
    private final JLabel lblTotal;

    private final JButton btnAdicionar = StopoUiFactory.createButton("Adicionar (ENTER)", AppColors.BLUE, 12, _ -> onAdicionar());
    private final JButton btnFinalizar = StopoUiFactory.createButton("Finalizar Venda (F5)",  AppColors.GREEN,  14, _ -> onFinalizar());
    private final JButton btnRemover   = StopoUiFactory.createButton("Remover Item (F3)", AppColors.YELLOW, 12, _ -> onRemover());
    private final JButton btnCancelar  = StopoUiFactory.createButton("Cancelar Venda (F4)", AppColors.RED,    12, _ -> onCancelar());

    private final ProductService productService = new ProductService();
    private final SellService sellService = new SellService();
    private double totalSell = 0.0;

    public BalcaoPanel() {
        setLayout(new BorderLayout(10, 10));

        txtBuscaProduto = new JTextField(15);

        spnQuantidade = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        spnQuantidade.setPreferredSize(new Dimension(60, 25));

        lblTotal = new JLabel("R$ 0,00", SwingConstants.CENTER);
        lblTotal.setFont(UIManager.getFont("Label.font").deriveFont(24f));
        lblTotal.setForeground(AppColors.GREEN);

        String[] colunas = {"Código", "Produto", "Qtd", "Preço Unit.", "Subtotal"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaCarrinho = new JTable(tableModel);
        tabelaCarrinho.setRowHeight(30);
        tabelaCarrinho.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaCarrinho.getTableHeader().setReorderingAllowed(false);

        add(buildNorthPanel(), BorderLayout.NORTH);
        add(new JScrollPane(tabelaCarrinho), BorderLayout.CENTER);
        add(buildEastPanel(), BorderLayout.EAST);

        bindKeys();
    }

    private JPanel buildNorthPanel() {
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(StopoUiFactory.createPanelHeader("Balcao", "Frente de loja"), BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.add(new JLabel("Código de Barras:"));
        searchPanel.add(txtBuscaProduto);
        searchPanel.add(new JLabel("Qtd:"));
        searchPanel.add(spnQuantidade);
        searchPanel.add(btnAdicionar);

        northPanel.add(searchPanel, BorderLayout.SOUTH);
        return northPanel;
    }

    private JPanel buildEastPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonsPanel.add(btnRemover);
        buttonsPanel.add(btnCancelar);
        buttonsPanel.add(btnFinalizar);

        JPanel totalsPanel = new JPanel(new BorderLayout());
        totalsPanel.setBorder(BorderFactory.createTitledBorder("Total da Compra"));
        totalsPanel.add(lblTotal, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel(new BorderLayout(0, 20));
        eastPanel.add(totalsPanel, BorderLayout.NORTH);
        eastPanel.add(buttonsPanel, BorderLayout.SOUTH);
        eastPanel.setPreferredSize(new Dimension(250, 0));

        return eastPanel;
    }

    private void bindKeys() {
        txtBuscaProduto.addActionListener(_ -> onAdicionar());
        StopoUiFactory.bindKey(this, "F3", "removerAction", this::onRemover);
        StopoUiFactory.bindKey(this, "F4", "cancelarAction", this::onCancelar);
        StopoUiFactory.bindKey(this, "F5", "finalizarAction", this::onFinalizar);
    }

    private void attLabelTotal() {
        lblTotal.setText(String.format("R$ %.2f", totalSell));
    }

    private void onAdicionar() {
        String busca = txtBuscaProduto.getText().trim();
        if (busca.isEmpty()) return;

        Product foundProduct = null;
        for(Product p:productService.listProducts()) {
            if(p != null && p.getBarcode().equals(busca)) {
                foundProduct = p;
                break;
            }
        }

        if(foundProduct == null) {
            JOptionPane.showMessageDialog(this, "Produto não encontrado para o código: " + busca);
            return;
        }
        int quantidade = (Integer) spnQuantidade.getValue();
        if(quantidade > foundProduct.getQuantity()) {
            JOptionPane.showMessageDialog(this, "Estoque insuficiente! Disponível: " + foundProduct.getQuantity());
            return;
        }
        double precoUnitario = foundProduct.getPrice();
        double subtotal = precoUnitario * quantidade;

        tableModel.addRow(new Object[]{
                foundProduct.getBarcode(),
                foundProduct.getName(),
                quantidade,
                String.format("R$ %.2f", precoUnitario),
                String.format("R$ %.2f", subtotal) // Formatado para exibição
        });

        totalSell += subtotal;
        attLabelTotal();
        txtBuscaProduto.setText("");
        spnQuantidade.setValue(1);
        txtBuscaProduto.requestFocus();
    }

    private void onRemover() {
        int selectedRow = tabelaCarrinho.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item no carrinho para remover.");
            return;
        }
        String subtotal = (String) tableModel.getValueAt(selectedRow, 4);
        double valueRemoved = Double.parseDouble(subtotal.replace("R$", "").trim().replace(",", "."));

        totalSell -= valueRemoved;
        attLabelTotal();
        tableModel.removeRow(selectedRow);
    }

    private void onFinalizar() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "O carrinho está vazio.");
            return;
        }
        System.out.println("Finalizando a venda...");

        String dateSell = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        for(int i = 0; i < tableModel.getRowCount(); i++) {
            String barcode = tableModel.getValueAt(i, 0).toString();
            int qntSelled = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
            Product productSelled = null;

            for(Product p: productService.listProducts()) {
                if(p.getBarcode().equals(barcode)) {
                    productSelled = p;
                    break;
                }
            }
            if (productSelled != null) {
                sellService.addSell(0, dateSell, productSelled, qntSelled, "Concluída");
            }
        }

        JOptionPane.showMessageDialog(this, "Venda finalizada com sucesso!\nTotal pago: " + String.format("R$ %.2f", totalSell));
        onCancelar();
    }

    private void onCancelar() {
        int confirm = JOptionPane.YES_OPTION;
        if (tableModel.getRowCount() > 0) {
            confirm = JOptionPane.showConfirmDialog(this, "Deseja limpar o carrinho atual?", "Confirmar", JOptionPane.YES_NO_OPTION);
        }

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setRowCount(0);
            totalSell = 0.0;
            attLabelTotal();
            txtBuscaProduto.setText("");
            txtBuscaProduto.requestFocus();
        }
    }
}