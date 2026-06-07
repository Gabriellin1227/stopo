package com.stopo.product.view.panels;

import com.stopo.product.view.uiconstants.AppColors;
import com.stopo.product.view.Frame.MainFrame;
import com.stopo.product.view.uiComponents.produtos.ProdutosForm;

import javax.swing.*;
import java.awt.*;

public class ProdutosPanel extends JPanel {

    public ProdutosPanel(MainFrame frame) {

        ProdutosForm form = new ProdutosForm();

        setBackground(AppColors.BACKGROUND);
        setLayout(new GridLayout(2, 1));
        add(form);


    }
}