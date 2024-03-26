package client;

import javax.swing.*;

public class WelcomeScreen implements Screen{
    private JButton SignUp;
    private JButton LogIn;
    private JLabel GameTittle;
    private JPanel frame;

    public JPanel getPanel() {
        return this.frame;
    }
}
