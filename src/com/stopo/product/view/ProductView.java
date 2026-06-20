package com.stopo.product.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProductView extends JFrame {

    //texto
    private JTextField txtName = new JTextField(30);
    private JTextField txtPrice = new JTextField(7);
    private JTextField txtQuantity = new JTextField(3);
    private JTextField txtDescription = new JTextField(100);
    private JTextField txtBarCode = new JTextField(10);
    private JTextArea txtAreaList = new JTextArea(10, 30);

    //buttons
    private JButton btnSave = new JButton("Salvar");
    private JButton btnCancel = new JButton("Cancelar");


    public ProductView() {
        setTitle("Gerenciador de Produtos - ESTOPO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1200);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        add(new JLabel("Nome:")); add(txtName);
        add(new JLabel("Descrição")); add(txtDescription);
        add(new JLabel("Preco:")); add(txtPrice);
        add(new JLabel("Quantidade:")); add(txtQuantity);
        add(new JLabel("Barcode:")); add(txtBarCode);
        add(btnSave);
        add(btnCancel);

        txtAreaList.setEditable(false);
        add(new JScrollPane(txtAreaList));

        setLocationRelativeTo(null);
    }

    public void clearFields() {
        txtName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtDescription.setText("");
    }

    public String getProductName() {return txtName.getText();}
    public String getProductPrice() {return txtPrice.getText();}
    public String getProductQuantity() {return txtQuantity.getText();}
    public String getProductDescription() {return txtDescription.getText();}
    public String getProductBarCode() {return txtBarCode.getText();}

    public void updateList(String text) {txtAreaList.setText(text);}
    public void showMessage(String msg) {JOptionPane.showMessageDialog(this, msg);}

    public void addSaveListener(ActionListener actionListener) {btnSave.addActionListener(actionListener);}
    public void addCancelListener(ActionListener actionListener) {btnCancel.addActionListener(actionListener);}

}
