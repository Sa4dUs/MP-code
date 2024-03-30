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

public class SignupScreen extends Screen {
    private JButton signUpButton;
    private JTextField username;
    private JPasswordField password;
    private JPanel frame;
    private JButton alreadyHaveAnAccountButton;
    private JButton exitButton;

    public SignupScreen() {
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                String pwd = new String(password.getPassword());

                if (user == null) {
                    // TODO! Add visual feedback
                    return;
                }

                RequestBody body = new RequestBody();
                body.addField("username", user);
                body.addField("password", pwd);

                ResponseBody response = Client.request("auth/signup", body);

                if (!response.ok) {
                    // TODO! Add visual feedback
                    return;
                }

                Session.setCurrentUser((User) response.getField("user"));

                ScreenManager.render(PlayerDashboardScreen.class);
            }
        });
        alreadyHaveAnAccountButton.addActionListener(new ActionListener() {
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
