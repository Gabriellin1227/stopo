package com.stopo.product.view.Frame;
import com.stopo.product.view.uiconstants.AppColors;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FrameHeader extends JPanel {
    public FrameHeader(MainFrame frame){

        setBorder(BorderFactory.createEmptyBorder(10, 20 ,10, 20));
        setBackground(AppColors.PRIMARY);
        setPreferredSize(new Dimension(0, 40));
        setLayout(new BorderLayout());

        Color textColorHeader = AppColors.WHITE_TEXT;

        JLabel stopoTitle = new JLabel("Stopo - PDV");
        stopoTitle.setForeground(textColorHeader);

        JLabel date = new JLabel("", SwingConstants.RIGHT);
        JLabel time = new JLabel("", SwingConstants.CENTER);

        date.setForeground(textColorHeader);
        time.setForeground(textColorHeader);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");


        Timer timer = new Timer(1000, _ -> {
           LocalDateTime now = LocalDateTime.now();
           date.setText(now.format(dateFormat));
           time.setText(now.format(timeFormat));
        });

        add(stopoTitle, BorderLayout.WEST);
        add(time, BorderLayout.CENTER);
        add(date, BorderLayout.EAST);
        timer.start();
    }
}
