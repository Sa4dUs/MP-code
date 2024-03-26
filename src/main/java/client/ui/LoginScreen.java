package client.ui;

import client.Client;
import client.ScreenManager;
import lib.RequestBody;
import lib.ResponseBody;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginScreen extends Screen {
    private JButton logInButton;
    private JTextField username;
    private JPasswordField password;
    private JPanel frame;
    private JButton donTHaveAnButton;

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
                    System.out.println("Login failed");
                    return;
                }

                System.out.println("Login successful");
                ScreenManager.render(Dashboard.class);
            }
        });
        donTHaveAnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(SignupScreen.class);
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }
}
