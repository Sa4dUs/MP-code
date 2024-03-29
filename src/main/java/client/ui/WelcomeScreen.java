package client.ui;

import client.ScreenManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen extends Screen {
    private JButton SignUp;
    private JButton LogIn;
    private JLabel GameTittle;
    private JPanel frame;
    private JButton exitButton;

    public WelcomeScreen()  {
        SignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(SignupScreen.class);
            }
        });

        LogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(LoginScreen.class);
            }
        });
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
