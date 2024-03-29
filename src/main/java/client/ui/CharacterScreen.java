package client.ui;

import client.ScreenManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterScreen extends Screen {
    private JComboBox comboBox1;
    private JPanel frame;
    private JButton backButton;

    public CharacterScreen() {
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
