package com.stopo.product.view.panels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.stopo.product.model.Product;
import com.stopo.product.model.ProductService;
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

    //addBackend
    ProductService productService = new ProductService();
    private int idACTProduct = -1;

    public ProdutosPanel() {
        setLayout(new BorderLayout(10, 10));
        //id;name;description;price;quantity;barcode;
        String[] colunas = {"id", "Nome", "Descrição", "Preço", "Estoque", "Código de Barras"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaProdutos = new JTable(tableModel);
        tabelaProdutos.setRowHeight(30);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.getTableHeader().setReorderingAllowed(false);
        adicionarProdutosTable();

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
        //fieldsPanel.add(new JLabel("Cód. Barras:"));   fieldsPanel.add(txtCodigoBarras);

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
        idACTProduct = -1;
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
        String name = txtNome.getText();
        String description = txtDescricao.getText();
        String priceStr = txtPreco.getText();
        String qntStr = txtEstoque.getText();

        if(name.isEmpty() || priceStr.isEmpty() || qntStr.isEmpty()) {
            System.out.println("valores que n podem ser nulos");
            JOptionPane.showMessageDialog(null,"ERRO! Preencha todos os campos obrigatorios!");
            return;
        }
        try {
            double price = Double.parseDouble(priceStr.replace(",", "."));
            int qnt = Integer.parseInt(qntStr);
            if(price <= 0 || qnt <= 0) {
                JOptionPane.showMessageDialog(null,"ERRO! Valores de preço e estoque devem ser maiores que zero!");
                return;
            }
            String arqImageName = "null";

            if (currentImagePath != null) {
                try {
                    File imageDir = new File("src/resources/images/");
                    if (!imageDir.exists()) {
                        imageDir.mkdir();
                    }
                    String extens = currentImagePath.substring(currentImagePath.lastIndexOf('.'));
                    arqImageName = System.currentTimeMillis() + extens;
                    Path origin = Paths.get(currentImagePath);
                    Path dest = Paths.get("src/resources/images/" + arqImageName);
                    Files.copy(origin, dest, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem!");
                    return;
                }
            }
            if(idACTProduct == -1) {
                productService.addProduct(name, description, price, qnt, arqImageName);
                JOptionPane.showMessageDialog(null,"Produto Adicionado com sucesso!");
            } else {
                String barcode = txtCodigoBarras.getText();
                Product editedProduct = new Product(idACTProduct, name, description, price, qnt, barcode, arqImageName);
                productService.attProduct(editedProduct);
                JOptionPane.showMessageDialog(null,"Produto Atualizado com sucesso!");
            }
            adicionarProdutosTable();
            onNovo();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO SALVAR!" + e.getMessage());
        }
    }

    private void onEditar() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Escolha um produto na tabela para editar.");
            return;
        }
        idACTProduct = Integer.parseInt(tabelaProdutos.getValueAt(selectedRow, 0).toString());
        txtNome.setText(tableModel.getValueAt(selectedRow, 1).toString());
        txtDescricao.setText(tableModel.getValueAt(selectedRow, 2).toString());
        txtPreco.setText(tableModel.getValueAt(selectedRow, 3).toString());
        txtEstoque.setText(tableModel.getValueAt(selectedRow, 4).toString());
        txtCodigoBarras.setText(tableModel.getValueAt(selectedRow, 5).toString());
        Product[] now = productService.listProducts();
        for(Product p:now) {
            if(p.getId() == idACTProduct) {
                String imageName = p.getImagepath();
                if(imageName != null && !imageName.isEmpty() && !imageName.equals("null")) {
                    setImagem("src/resources/images/" + imageName);
                } else{
                    setImagem(null);
                }
                break;
            }
        }
    }

    private void onExcluir() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Escolha um produto para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este produto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int idForDelete = Integer.parseInt(tabelaProdutos.getValueAt(selectedRow, 0).toString());
            productService.deleteProduct(idForDelete);
            tableModel.removeRow(selectedRow);
            adicionarProdutosTable();
            onNovo();
            JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
        }
    }

    private void adicionarProdutosTable() {
        tableModel.setRowCount(0);
        Product[] products = productService.listProducts();
        for(Product p:  products){
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getBarcode()
            });
        }
    }
}