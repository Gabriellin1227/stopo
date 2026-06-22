package com.stopo.ui.Frame;
import com.stopo.ui.constants.AppColors;
import com.stopo.ui.constants.Screens;
import com.stopo.ui.uiutils.StopoUiFactory;

import javax.swing.*;
import java.awt.*;

public class FrameFooter extends JPanel {

    public FrameFooter(MainFrame frame){
        setBackground(AppColors.PRIMARY);
        setPreferredSize(new Dimension(0, 40));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setAlignmentY(Component.CENTER_ALIGNMENT);

        setAlignmentY(Component.CENTER_ALIGNMENT);
        add(StopoUiFactory.createButton(
                "Home",
                AppColors.RED,
                16,
                _ -> frame.changeScreen(Screens.HOME)
        ));


        add(StopoUiFactory.createButton(
                "Balcão",
                AppColors.BLUE,
                16,
                _ -> frame.changeScreen(Screens.BALCAO)
        ));

        add(StopoUiFactory.createButton(
                "Produtos",
                AppColors.GREEN,
                16,
                _ -> frame.changeScreen(Screens.PRODUTOS)
        ));

        add(StopoUiFactory.createButton(
                "Vendas",
                AppColors.YELLOW,
                16,
                _ -> frame.changeScreen(Screens.VENDAS)
        ));
    }
}

