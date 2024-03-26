package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupScreen implements Screen{
    private JButton signUpButton;
    private JTextField username;
    private JPasswordField password;
    private JPanel frame;
    private JButton alreadyHaveAnAccountButton;

    public SignupScreen() {
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO!
            }
        });
        alreadyHaveAnAccountButton.addActionListener(new ActionListener() {
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
