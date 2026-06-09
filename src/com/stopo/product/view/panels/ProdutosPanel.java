package com.stopo.product.view.panels;

import java.awt.*;
import java.util.List;

import com.stopo.product.view.uicomponents.PanelHeader;
import com.stopo.product.view.uicomponents.produtos.ProdutosCrudForm;
import com.stopo.product.view.uicomponents.produtos.ProdutosTable;
import com.stopo.product.view.uiconstants.AppColors;
import com.stopo.product.view.uiutils.KeyBindingsFactory;
import com.stopo.product.view.uiutils.StopoButtonFactory;

import javax.swing.*;

public class ProdutosPanel extends JPanel {

    private final ProdutosCrudForm form;
    private final ProdutosTable table;

    private final JButton btnNovo = StopoButtonFactory.createButton("Novo (F2)",    AppColors.GREEN,  12, _ -> onNovo());
    private final JButton btnSalvar = StopoButtonFactory.createButton("Salvar (F5)",  AppColors.BLUE,   12, _ -> onSalvar());
    private final JButton btnEditar = StopoButtonFactory.createButton("Editar (F3)",  AppColors.YELLOW, 12, _ -> onEditar());
    private final JButton btnExcluir = StopoButtonFactory.createButton("Excluir (F4)", AppColors.RED,    12, _ -> onExcluir());

    public ProdutosPanel() {
        setLayout(new BorderLayout(10, 10));

        form  = new ProdutosCrudForm();
        table = new ProdutosTable();

        add(new PanelHeader("Produtos", "Cadastre, consulte e gerencie os produtos do seu estoque."), BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
        add(buildEastPanel(), BorderLayout.EAST);

        bindKeys();
    }

    private JPanel buildEastPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonsPanel.add(btnNovo);
        buttonsPanel.add(btnSalvar);
        buttonsPanel.add(btnEditar);
        buttonsPanel.add(btnExcluir);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(form, BorderLayout.NORTH);
        eastPanel.add(buttonsPanel, BorderLayout.SOUTH);
        return eastPanel;
    }

    private void bindKeys() {
        KeyBindingsFactory.bind(this, "F2", "novoAction",    this::onNovo);
        KeyBindingsFactory.bind(this, "F3", "editarAction",  this::onEditar);
        KeyBindingsFactory.bind(this, "F4", "excluirAction", this::onExcluir);
        KeyBindingsFactory.bind(this, "F5", "salvarAction",  this::onSalvar);
    }

    private void onNovo() {
        form.limpar();
    }

    private void onSalvar() {
        // TODO:
    }

    private void onEditar() {

        List<String> values = table.getLineValues();

        if(values.isEmpty()){
            JOptionPane.showMessageDialog(this, "Escolha uma linha válida");
            return;
        }

        form.preencher(
                values.get(0),
                values.get(1),
                values.get(2),
                values.get(3),
                values.get(4),
                "C:\\Users\\bocat\\OneDrive\\Documentos\\sidnelsontest.jpg"
        );
    }

    private void onExcluir() {
        System.out.println(form.getImagePath());
        table.adicionarProdutosDeTeste();
        // TODO: confirmar e excluir item selecionado na tabela
    }
}