package com.stopo.product.view.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.stopo.product.view.uiconstants.AppColors;
import com.stopo.product.view.uiutils.StopoUiFactory;

public class VendasPanel extends JPanel {

    private final JTable tabelaVendasRealizadas;
    private final DefaultTableModel tableModel;

    private final JButton btnAtualizar = StopoUiFactory.createButton("Atualizar Lista (F2)", AppColors.GREEN, 12, _ -> onAtualizar());
    private final JButton btnDetalhes  = StopoUiFactory.createButton("Ver Detalhes (F3)", AppColors.BLUE, 12, _ -> onDetalhes());
    private final JButton btnEstornar  = StopoUiFactory.createButton("Estornar Venda (F4)", AppColors.RED, 12, _ -> onEstornar());

    private static int sequencialVendasId = 1;

    public VendasPanel() {
        setLayout(new BorderLayout(10, 10));

        String[] colunas = {"ID Venda", "Data da Venda", "Qtd. Itens", "Valor Total", "Status"};
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
        tableModel.addRow(new Object[]{String.format("%04d", sequencialVendasId++), "18/06/2026 14:30", "3", "R$ 150,00", "Concluída"});
        tableModel.addRow(new Object[]{String.format("%04d", sequencialVendasId++), "18/06/2026 15:45", "1", "R$ 49,90", "Concluída"});
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
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        String[] colunasProdutos = {"Código", "Produto", "Qtd", "Subtotal"};
        DefaultTableModel modelProdutos = new DefaultTableModel(colunasProdutos, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        // TODO: Aqui o backend buscaria os itens vinculados ao idVenda no banco
        modelProdutos.addRow(new Object[]{"789123", "Item Exemplo A", "2", "R$ 100,00"});
        modelProdutos.addRow(new Object[]{"789456", "Item Exemplo B", "1", "R$ 50,00"});

        JTable tabelaProdutos = new JTable(modelProdutos);
        tabelaProdutos.setRowHeight(25);

        dialog.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

        JButton btnFechar = StopoUiFactory.createButton("Fechar", AppColors.RED, 12, _ -> dialog.dispose());
        JPanel panelBtn = new JPanel();
        panelBtn.add(btnFechar);
        dialog.add(panelBtn, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void onEstornar() {
        int row = tabelaVendasRealizadas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para estornar.");
            return;
        }

        String status = tableModel.getValueAt(row, 4).toString();
        if (status.equals("Cancelada")) {
            JOptionPane.showMessageDialog(this, "Esta venda já está cancelada.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja estornar esta venda?", "Estornar Venda", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setValueAt("Cancelada", row, 4);
        }
    }
}