package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends Screen {
    private JButton logInButton;
    private JTextField username;
    private JPasswordField password;
    private JButton signUpButton;
    private JButton exitButton;
    private JPanel frame;
    private JButton donTHaveAnButton;
    private JPanel mainPanel;

    public LoginScreen() {
        initializeListeners();
    }

    private void initializeListeners() {
        logInButton.addActionListener(e -> {
            String user = username.getText();
            String pwd = new String(password.getPassword());

            if (user.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username and password are required!", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            authenticateUser(user, pwd);
        });

        signUpButton.addActionListener(e -> ScreenManager.render(SignupScreen.class));

        exitButton.addActionListener(e -> ScreenManager.exit());
    }

    private void authenticateUser(String username, String password) {
        RequestBody body = new RequestBody();
        body.addField("username", username);
        body.addField("password", password);

        ResponseBody response = Client.request("auth/login", body);

        if (!response.ok) {
            JOptionPane.showMessageDialog(null, "Invalid username or password!", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User currentUser = (User) response.getField("user");
        boolean isOperator = (Boolean) response.getField("isOperator");

        Session.setCurrentUser(currentUser);

        if (isOperator) {
            Session.sudo();
            ScreenManager.render(OperatorDashboardScreen.class);
        } else {
            ScreenManager.render(PlayerDashboardScreen.class);
        }
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }
}
