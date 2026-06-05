package com.stopo.product.view;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Header extends JPanel {
    public Header(){
        setBorder(BorderFactory.createEmptyBorder(10, 20 ,10, 20));
        setBackground(new Color(179, 235, 242));
        setPreferredSize(new Dimension(0, 40));
        setLayout(new BorderLayout());

        JLabel stopoTitle = new JLabel("Stopo");

        JLabel dateTime = new JLabel("", SwingConstants.RIGHT);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

        Timer timer = new Timer(1000, e -> {
           LocalDateTime now = LocalDateTime.now();
           dateTime.setText(now.format(formatter));
        });

        add(stopoTitle, BorderLayout.WEST);
        add(dateTime, BorderLayout.EAST);
        timer.start();
    }
}
