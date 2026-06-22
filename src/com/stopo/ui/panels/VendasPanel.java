package com.stopo.ui.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.stopo.product.model.Product;
import com.stopo.ui.constants.AppColors;
import com.stopo.ui.uiutils.StopoUiFactory;
import com.stopo.sell.model.Sell;
import com.stopo.sell.model.SellService;

public class VendasPanel extends JPanel {

    private final JTable tabelaVendasRealizadas;
    private final DefaultTableModel tableModel;

    private final JButton btnAtualizar = StopoUiFactory.createButton("Atualizar Lista (F2)", AppColors.GREEN, 12, _ -> onAtualizar());
    private final JButton btnDetalhes  = StopoUiFactory.createButton("Ver Detalhes (F3)", AppColors.BLUE, 12, _ -> onDetalhes());
    private final JButton btnEstornar  = StopoUiFactory.createButton("Estornar Venda (F4)", AppColors.RED, 12, _ -> onEstornar());

    private static int sequencialVendasId = 1;

    private final SellService sellService = new SellService();

    public VendasPanel() {
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"ID Venda", "Data da Venda", "Produto", "Qtd. Itens", "Valor Total", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaVendasRealizadas = new JTable(tableModel);
        tabelaVendasRealizadas.setRowHeight(30);
        tabelaVendasRealizadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVendasRealizadas.getTableHeader().setReorderingAllowed(false);

        add(StopoUiFactory.createPanelHeader("Vendas", "Vendas realizadas"), BorderLayout.NORTH);
        add(new JScrollPane(tabelaVendasRealizadas), BorderLayout.CENTER);
        add(buildEastPanel(), BorderLayout.EAST);

        bindKeys();

        onAtualizar(); // adicionei pra att ao abrir
    }

    private JPanel buildEastPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonsPanel.add(btnAtualizar);
        buttonsPanel.add(btnDetalhes);
        buttonsPanel.add(btnEstornar);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(buttonsPanel, BorderLayout.NORTH);
        eastPanel.setPreferredSize(new Dimension(250, 0));
        return eastPanel;
    }

    private void bindKeys() {
        StopoUiFactory.bindKey(this, "F2", "atualizarAction", this::onAtualizar);
        StopoUiFactory.bindKey(this, "F3", "detalhesAction", this::onDetalhes);
        StopoUiFactory.bindKey(this, "F4", "estornarAction", this::onEstornar);
    }

    private void onAtualizar() {
        tableModel.setRowCount(0);
        Sell[] now = sellService.listSell();
        for(Sell s:now){
            if(s != null){
                tableModel.addRow(new Object[]{
                        s.getIdSell(),
                        s.getDate(),
                        s.getProduct().getName(),
                        s.getSellQuantity(),
                        String.format("R$ %.2f", (s.getProduct().getPrice() * s.getSellQuantity())),
                        s.getStatus()
                });
            }
        }
    }

    private void onDetalhes() {
        int row = tabelaVendasRealizadas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda na tabela para ver os produtos.");
            return;
        }

        String idVenda = tableModel.getValueAt(row, 0).toString();

        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog((Frame) parentWindow, "Produtos da Venda ID: " + idVenda, true);
        dialog.setSize(550, 300); // alarguei um pk mais p caber
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        String[] colunasProdutos = {"Código", "Produto", "Preço Unit.", "Qtd", "Subtotal"};
        DefaultTableModel modelProdutos = new DefaultTableModel(colunasProdutos, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        // Busca a venda
        Sell targetSale = null;
        for(Sell s : sellService.listSell()){
            if(s != null && s.getIdSell() == Integer.parseInt(idVenda)){
                targetSale = s;
                break;
            }
        }

        if (targetSale != null) {
            Product p = targetSale.getProduct();
            double subtotal = p.getPrice() * targetSale.getSellQuantity();

            modelProdutos.addRow(new Object[]{
                    p.getBarcode(),
                    p.getName(),
                    String.format("R$ %.2f", p.getPrice()),
                    targetSale.getSellQuantity(),
                    String.format("R$ %.2f", subtotal)
            });
        }

        JTable tabelaProdutos = new JTable(modelProdutos);
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.getTableHeader().setReorderingAllowed(false);

        dialog.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

        JPanel panelBtn = new JPanel(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margem interna

        if (targetSale != null) {
            JLabel lblInfo = new JLabel("Data: " + targetSale.getDate() + "  |  Status: " + targetSale.getStatus());
            lblInfo.setFont(new Font("Arial", Font.BOLD, 12));
            panelBtn.add(lblInfo, BorderLayout.WEST);
        }

        JButton btnFechar = StopoUiFactory.createButton("Fechar", AppColors.RED, 12, _ -> dialog.dispose());
        panelBtn.add(btnFechar, BorderLayout.EAST);

        dialog.add(panelBtn, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void onEstornar() {
        int row = tabelaVendasRealizadas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para estornar.");
            return;
        }

        int idSell = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        String status  = tableModel.getValueAt(row, 5).toString();

        if (status.equals("Cancelada")) {
            JOptionPane.showMessageDialog(this, "Esta venda já está cancelada.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja estornar a venda " + idSell + "? O estoque será devolvido.", "Estornar Venda", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            sellService.cancelSell(idSell);
            onAtualizar();
            JOptionPane.showMessageDialog(this, "Venda estornada com sucesso!");
        }
    }
}