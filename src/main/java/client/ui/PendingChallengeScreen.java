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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PendingChallengeScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JPanel pane;

    @Override
    public void start() {
        super.start();
        displayPendingChallenges();
    }

    private void displayPendingChallenges() {
        List<ChallengeRequest> challengeList = fetchPendingChallenges();

        if (challengeList == null) {
            return;
        }

        pane.setLayout(new GridLayout(challengeList.size(), 4));
        for (ChallengeRequest challenge : challengeList) {
            JLabel label = new JLabel("VS " + challenge.getAttackerId());
            pane.add(label);

            JLabel bet = new JLabel(Integer.toString(challenge.getBet()));
            pane.add(bet);

            JButton acceptButton = createAcceptButton(challenge);
            pane.add(acceptButton);

            JButton declineButton = createDeclineButton(challenge);
            pane.add(declineButton);
        }
    }

    private List<ChallengeRequest> fetchPendingChallenges() {
        ResponseBody response;
        RequestBody request = new RequestBody();
        List<ChallengeRequest> challengeList = new ArrayList<>();

        if (Session.isOperator()) {
            response = Client.request("challenge/getOperatorChallenges", request);
        } else {
            request.addField("clazz", ChallengeRequest.class);
            response = Client.request("item/getAll", request);
        }

        if (response == null || response.getField("data") == null) {
            return null;
        }

        challengeList = (List<ChallengeRequest>) response.getField("data");
        if (!Session.isOperator()) {
            challengeList = challengeList.stream()
                    .filter(e -> Objects.equals(e.getAttackedId(), Session.getCurrentUser().getId()))
                    .toList();
        }

        return challengeList;
    }

    private JButton createAcceptButton(ChallengeRequest challenge) {
        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String endpoint = Session.isOperator() ? "challenge/acceptChallengeFromOperator" : "challenge/acceptChallengeFromPlayer";
                handleChallengeResponse(endpoint, challenge);
            }
        });
        return acceptButton;
    }

    private JButton createDeclineButton(ChallengeRequest challenge) {
        JButton declineButton = new JButton("Decline");
        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String endpoint = Session.isOperator() ? "challenge/denyChallengeFromOperator" : "challenge/denyChallengeFromPlayer";
                handleChallengeResponse(endpoint, challenge);
            }
        });
        return declineButton;
    }

    private void handleChallengeResponse(String endpoint, ChallengeRequest challenge) {
        RequestBody request = new RequestBody();
        request.addField("challenge", challenge);
        ResponseBody response = Client.request(endpoint, request);

        // Remove components from the pane upon response
        if (response != null && response.ok) {
            removeComponentsFromPane();
        }
    }

    private void removeComponentsFromPane() {
        for (Component component : pane.getComponents()) {
            pane.remove(component);
        }
        pane.revalidate();
        pane.repaint();
    }

    public PendingChallengeScreen() {
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