package com.stopo.product.view.uicomponents;

import com.stopo.product.view.uiconstants.AppColors;

import javax.swing.*;
import java.awt.*;

public class PanelHeader extends JPanel {

    public PanelHeader(String titulo, String subtitulo) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel(titulo);
        JLabel lblSubtitulo = new JLabel(subtitulo);

        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        lblSubtitulo.setForeground(AppColors.SUBTEXT);

        add(lblTitulo);
        add(Box.createVerticalStrut(1));
        add(lblSubtitulo);
        add(Box.createVerticalStrut(8));
        add(new JSeparator());
    }
}