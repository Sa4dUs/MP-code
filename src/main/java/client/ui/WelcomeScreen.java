package client.ui;

import client.ScreenManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen extends Screen {
    private JButton signUpButton;
    private JButton logInButton;
    private JLabel gameTitle;
    private JPanel frame;
    private JButton exitButton;

    public WelcomeScreen() {
        initializeListeners();
    }

    private void initializeListeners() {
        signUpButton.addActionListener(e -> goToSignup());
        logInButton.addActionListener(e -> goToLogin());
        exitButton.addActionListener(e -> ScreenManager.exit());
    }

    private void goToSignup() {
        ScreenManager.render(SignupScreen.class);
    }

    private void goToLogin() {
        ScreenManager.render(LoginScreen.class);
    }

    public JPanel getPanel() {
        return frame;
    }
}
