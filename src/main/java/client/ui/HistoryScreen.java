package client.ui;

import client.ScreenManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistoryScreen extends Screen {
    private JPanel frame;
    private JButton backButton;

    public HistoryScreen() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.goBack();
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }

}
