package com.stopo.product.view.uiutils;

import com.stopo.product.view.uiconstants.AppColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class StopoUiFactory {

    private StopoUiFactory() {}

    /**
     * O StopoUiFactory serve para retorna componentes que são utilizados em diversas telas, fazendo com que seja mais facil a edição em massa
     *
     */

    public static JButton createButton(String texto, Color cor, int fontSize) {
        JButton btn = new JButton(texto);

        btn.setBackground(cor);
        btn.setForeground(AppColors.WHITE_TEXT);
        btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

        return btn;
    }

    public static JButton createButton(String texto, Color cor, int fontSize, ActionListener action) {
        JButton btn = createButton(texto, cor, fontSize);
        btn.addActionListener(action);
        return btn;
    }

    public static JPanel createPanelHeader(String titulo, String subtitulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel(titulo);
        JLabel lblSubtitulo = new JLabel(subtitulo);

        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        lblSubtitulo.setForeground(AppColors.SUBTEXT);

        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(1));
        panel.add(lblSubtitulo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(new JSeparator());

        return panel;
    }

    public static void bindKey(
            JComponent component,
            String key,
            String actionName,
            Runnable action) {

        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(key), actionName);

        actionMap.put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }
}