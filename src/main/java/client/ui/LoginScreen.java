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
    private JPanel frame;
    private JButton donTHaveAnButton;
    private JButton exitButton;

    public LoginScreen() {
        logInButton.addActionListener(new ActionListener() {
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

                ResponseBody response = Client.request("auth/login", body);

                if (!response.ok) {
                    // TODO! Add visual feedback
                    return;
                }

                Session.setCurrentUser((User) response.getField("user"));

                System.out.println(Session.getCurrentUser().getOperator());
                if (Session.getCurrentUser().getOperator()) {
                    ScreenManager.render(OperatorDashboardScreen.class);
                    return;
                }

                ScreenManager.render(PlayerDashboardScreen.class);
            }
        });
        donTHaveAnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(SignupScreen.class);
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
