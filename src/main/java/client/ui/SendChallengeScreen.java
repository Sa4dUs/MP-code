package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.ChallengeRequest;
import server.ChallengeResult;
import server.Player;

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
        initializeListeners();
    }

    @Override
    public void start() {
        super.start();
    }

    private void initializeListeners() {
        challengeButton.addActionListener(e -> sendChallenge());
        backButton.addActionListener(e -> ScreenManager.goBack());
    }

    private void sendChallenge() {
        feedback.setVisible(false);

        String challengeNick = nick.getText();
        int challengeGold = 0;

        try {
            challengeGold = Integer.parseInt(gold.getText());
        } catch (NumberFormatException error) {
            showFeedback("Enter a valid gold value", Color.RED);
            return;
        }

        ChallengeRequest challenge = new ChallengeRequest(Session.getCurrentUser().getId(), challengeNick, challengeGold);

        {
            RequestBody request = new RequestBody();
            request.addField("nick", Session.getCurrentUser().getId());
            ResponseBody response = Client.request("character/get", request);

            if (!response.ok) {
                showFeedback("Error! No character available.", Color.RED);
                return;
            }
        }
        RequestBody req = new RequestBody();
        req.addField("challenge", challenge);

        ResponseBody response = Client.request("challenge/create", req);

        clearInputs();

        if (!response.ok) {
            showFeedback("Error sending challenge! Player cannot be selected for a challenge.", Color.RED);
            return;
        }


        showFeedback("Challenge sent successfully!", Color.GREEN);
    }

    private void showFeedback(String message, Color color) {
        feedback.setText(message);
        feedback.setForeground(color);
        feedback.setVisible(true);
    }

    private void clearInputs() {
        nick.setText("");
        gold.setText("");
    }

    public JPanel getPanel() {
        return frame;
    }
}