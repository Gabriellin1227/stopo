package com.stopo.product.view.uiutils;

import javax.swing.JComponent;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.ActionMap;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

public final class KeyBindingsFactory {

    private KeyBindingsFactory() {}

    public static void bind(
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