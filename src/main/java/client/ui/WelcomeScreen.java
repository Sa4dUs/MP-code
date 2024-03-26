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
    }

    public JPanel getPanel() {
        return this.frame;
    }
}
