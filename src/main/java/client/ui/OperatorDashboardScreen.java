package client.ui;

import client.ScreenManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorDashboardScreen extends Screen {
    private JButton placeholderButton;
    private JScrollBar scrollBar1;
    private JTextField textField3;
    private JTextField textField2;
    private JTextField textField1;
    private JButton deleteButton;
    private JPanel frame;
    private JButton exitButton;

    public OperatorDashboardScreen() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.exit();
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }

}
