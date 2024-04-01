package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupScreen extends Screen {
    private JButton signUpButton;
    private JTextField username;
    private JPasswordField password;
    private JPanel frame;
    private JButton alreadyHaveAnAccountButton;
    private JButton exitButton;

    public SignupScreen() {
        initializeListeners();
    }

    private void initializeListeners() {
        signUpButton.addActionListener(e -> signUp());
        alreadyHaveAnAccountButton.addActionListener(e -> goToLogin());
        exitButton.addActionListener(e -> ScreenManager.exit());
    }

    private void signUp() {
        String user = username.getText();
        String pwd = new String(password.getPassword());

        if (user.isEmpty() || pwd.isEmpty()) {
            showFeedback("Please enter both username and password", Color.RED);
            return;
        }

        RequestBody body = new RequestBody();
        body.addField("username", user);
        body.addField("password", pwd);

        ResponseBody response = Client.request("auth/signup", body);

        if (!response.ok) {
            showFeedback("Failed to sign up. Please try again.", Color.RED);
            return;
        }

        Session.setCurrentUser((User) response.getField("user"));
        ScreenManager.render(PlayerDashboardScreen.class);
    }

    private void goToLogin() {
        ScreenManager.render(LoginScreen.class);
    }

    private void showFeedback(String message, Color color) {
        // TODO: Implement visual feedback display
        JOptionPane.showMessageDialog(frame, message, "Signup Feedback", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel() {
        return frame;
    }
}
