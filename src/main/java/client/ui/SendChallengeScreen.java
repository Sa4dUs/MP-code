package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.ChallengeRequest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendChallengeScreen extends Screen {
    private JTextField nick;
    private JTextField gold;
    private JButton challengeButton;
    private JPanel frame;
    private JLabel success;
    private JLabel error;
    private JButton backButton;

    public SendChallengeScreen() {
        challengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                success.setVisible(false);
                error.setVisible(false);

                String challengeNick = nick.getText();
                int challengeGold = Integer.parseInt(gold.getText());

                RequestBody req = new RequestBody();
                ChallengeRequest challenge = new ChallengeRequest(Session.getCurrentUser().getId(), challengeNick, challengeGold);
                req.addField("challenge", challenge);

                ResponseBody response = Client.request("challenge", req);

                nick.setText("");
                gold.setText("");

                if (!response.ok) {
                    // TODO! Show visual feedback
                    error.setVisible(false);
                    return;
                }

                // TODO! Handle challenge successfully sent
                success.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.goBack();
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }
}
