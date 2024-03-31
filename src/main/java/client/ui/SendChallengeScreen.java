package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.ChallengeRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendChallengeScreen extends Screen {
    private JTextField nick;
    private JTextField gold;
    private JButton challengeButton;
    private JPanel frame;
    private JLabel feedback;
    private JButton backButton;

    public SendChallengeScreen() {
        challengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                feedback.setVisible(false);

                String challengeNick = nick.getText();
                int challengeGold = 0;

                try {
                    challengeGold = Integer.parseInt(gold.getText());
                } catch (NumberFormatException error) {
                    feedback.setText("Enter a valid gold value");
                    feedback.setForeground(new Color(255, 0, 0));
                    feedback.setVisible(true);
                    return;
                }

                RequestBody req = new RequestBody();
                ChallengeRequest challenge = new ChallengeRequest(Session.getCurrentUser().getId(), challengeNick, challengeGold);
                req.addField("challenge", challenge);

                ResponseBody response = Client.request("challenge/create", req);

                nick.setText("");
                gold.setText("");

                if (!response.ok) {
                    // TODO! Show visual feedback
                    feedback.setText("Error sending challenge!");
                    feedback.setForeground(new Color(255, 0, 0));
                    feedback.setVisible(true);
                    return;
                }

                // TODO! Handle challenge successfully sent
                feedback.setText("Challenge sent successfully!");
                feedback.setForeground(new Color(0, 255, 0));
                feedback.setVisible(true);
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
