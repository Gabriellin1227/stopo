package com.stopo.product.view.uiutils;

import com.stopo.product.view.uiconstants.AppColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class StopoButtonFactory {
    private StopoButtonFactory(){}

    public static JButton createButton(
            String texto,
            Color cor,
            int fontSize,
            ActionListener actionListener){
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(AppColors.WHITE_TEXT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(cor, 3));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(AppColors.BACKGROUND);
                btn.setForeground(cor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(cor);
                btn.setForeground(AppColors.WHITE_TEXT);
            }
        });

        btn.addActionListener(actionListener);

        return btn;
    }
}

