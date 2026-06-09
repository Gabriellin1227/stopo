package com.stopo.product.view.Frame;
import com.stopo.product.view.uiconstants.AppColors;
import com.stopo.product.view.uiconstants.Screens;
import com.stopo.product.view.uiutils.StopoButtonFactory;

import javax.swing.*;
import java.awt.*;

public class FrameFooter extends JPanel {

    public FrameFooter(MainFrame frame){
        setBackground(AppColors.PRIMARY);
        setPreferredSize(new Dimension(0, 40));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setAlignmentY(Component.CENTER_ALIGNMENT);

        setAlignmentY(Component.CENTER_ALIGNMENT);
        add(StopoButtonFactory.createButton(
                "Home",
                AppColors.RED,
                16,
                _ -> frame.changeScreen(Screens.HOME)
        ));


        add(StopoButtonFactory.createButton(
                "Balcão",
                AppColors.BLUE,
                16,
                _ -> frame.changeScreen(Screens.BALCAO)
        ));

        add(StopoButtonFactory.createButton(
                "Produtos",
                AppColors.GREEN,
                16,
                _ -> frame.changeScreen(Screens.PRODUTOS)
        ));

        add(StopoButtonFactory.createButton(
                "Vendas",
                AppColors.YELLOW,
                16,
                _ -> frame.changeScreen(Screens.VENDAS)
        ));


    }
}

