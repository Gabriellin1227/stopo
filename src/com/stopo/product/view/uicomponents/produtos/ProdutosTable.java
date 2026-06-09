package com.stopo.product.view.uicomponents.produtos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProdutosTable extends JScrollPane {

    private static final String[] COLUNAS = { "Nome","Descrição", "Preço", "Estoque", "Código de Barras"};

    private final JTable table;
    private final DefaultTableModel model;

    public ProdutosTable() {
        model = new DefaultTableModel(COLUNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        setViewportView(table);
    }

    public void adicionarProdutosDeTeste() {
        Object[][] dados = {
                { "Notebook Dell Inspiron 15", "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", 3499.00, 42,  "7891234567890" },
                { "Mouse Logitech MX Master 3","S",  649.90, 117, "7894321098765" },
                { "Teclado Mecânico Redragon","S",    289.99,   8, "7899876543210" },
                { "Monitor LG 27\" 4K","S",          2199.00,  23, "7892345678901" },
                { "Headset Sony WH-1000XM5","S",     1899.00,   3, "7893456789012" },
                { "Webcam Logitech C920","S",         499.90,   0, "7895678901234" },
                { "SSD Samsung 1TB NVMe","S",         429.00,  65, "7896789012345" },
        };

        for (Object[] linha : dados) {
            model.addRow(linha);
        }
    }

    public List<String> getLineValues(){

        int selectedLine = table.getSelectedRow();
        List<String> values = new ArrayList<>();

        if(selectedLine == -1){
            return values;
        }

        for (int col = 0; col < model.getColumnCount(); col++){
            Object value = model.getValueAt(selectedLine, col);
            values.add(value != null ? value.toString() : "");
        }

        return values;
    }

}