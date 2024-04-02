package client.ui;

import client.ScreenManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefaultButton extends JButton {

    private JButton createDefaultButton(String buttonText, ActionListener actionListener) {
        JButton defaultButton = new JButton(buttonText);
        defaultButton.addActionListener(actionListener);
        return defaultButton;
    }
}
