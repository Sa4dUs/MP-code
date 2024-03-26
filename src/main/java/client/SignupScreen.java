package client;

import javax.swing.*;

public class SignupScreen implements Screen{
    private JButton signUpButton;
    private JTextField username;
    private JPasswordField password;
    private JPanel frame;

    public JPanel getPanel() {
        return this.frame;
    }
}
