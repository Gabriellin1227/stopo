package com.stopo.product.view.uiComponents.produtos;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProdutosTable extends JTable {

    private final DefaultTableModel model;

    public ProdutosTable() {

        model = new DefaultTableModel(
                new Object[]{
                        "Código",
                        "Nome",
                        "Preço",
                        "Qtd. Estoque",
                        "Código de Barras"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setModel(model);

        setRowHeight(25);
        setAutoCreateRowSorter(true);
        getTableHeader().setReorderingAllowed(false);
    }

    public void adicionarProduto(
            int codigo,
            String nome,
            double preco,
            int qtdEstoque,
            String codBarras
    ) {
        model.addRow(new Object[]{
                codigo,
                nome,
                preco,
                qtdEstoque,
                codBarras
        });
    }

    public void removerProduto(int linha) {
        if (linha >= 0) {
            model.removeRow(linha);
        }
    }

    public void limpar() {
        model.setRowCount(0);
    }

    public DefaultTableModel getTableModel() {
        return model;
    }
}
