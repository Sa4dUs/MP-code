package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen implements Screen{
    private JButton logInButton;
    private JTextField username;
    private JPasswordField password;
    private JPanel frame;
    private JButton donTHaveAnButton;

    public LoginScreen() {
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO!
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
