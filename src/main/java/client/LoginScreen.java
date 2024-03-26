package client;

import javax.swing.*;

public class LoginScreen implements Screen{
    private JButton logInButton;
    private JTextField username;
    private JPasswordField password;
    private JTextArea donTHaveAnTextArea;
    private JButton signUp;
    private JPanel frame;

    public JPanel getPanel() {
        return this.frame;
    }
}
