package client.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendChallengeScreen extends Screen {
    private JTextField textField1;
    private JTextField textField2;
    private JButton challengeButton;
    private JPanel frame;

    public SendChallengeScreen() {
        challengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO!
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }
}
