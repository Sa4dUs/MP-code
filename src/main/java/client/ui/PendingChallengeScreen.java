package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.ChallengeRequest;

import javax.swing.*;
import java.awt.*;
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
            JPanel challengePanel = createChallengePanel(challenge);
            pane.add(challengePanel);
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

    private JPanel createChallengePanel(ChallengeRequest challenge) {
        JPanel challengePanel = new JPanel();
        challengePanel.setLayout(new GridLayout(1, 4));

        JLabel label = new JLabel("VS " + challenge.getAttackerId());
        challengePanel.add(label);

        JLabel bet = new JLabel(Integer.toString(challenge.getBet()));
        challengePanel.add(bet);

        JButton acceptButton = createAcceptButton(challenge, challengePanel);
        challengePanel.add(acceptButton);

        JButton declineButton = createDeclineButton(challenge, challengePanel);
        challengePanel.add(declineButton);

        return challengePanel;
    }

    private JButton createAcceptButton(ChallengeRequest challenge, JPanel challengePanel) {
        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(e -> {
            String endpoint = Session.isOperator() ? "challenge/acceptChallengeFromOperator" : "challenge/acceptChallengeFromPlayer";
            handleChallengeResponse(endpoint, challenge, challengePanel);
        });
        return acceptButton;
    }

    private JButton createDeclineButton(ChallengeRequest challenge, JPanel challengePanel) {
        JButton declineButton = new JButton("Decline");
        declineButton.addActionListener(e -> {
            String endpoint = Session.isOperator() ? "challenge/denyChallengeFromOperator" : "challenge/denyChallengeFromPlayer";
            handleChallengeResponse(endpoint, challenge, challengePanel);
        });
        return declineButton;
    }

    private void handleChallengeResponse(String endpoint, ChallengeRequest challenge, JPanel challengePanel) {
        RequestBody request = new RequestBody();
        request.addField("challenge", challenge);
        ResponseBody response = Client.request(endpoint, request);

        if (response != null && response.ok) {
            removeComponentFromPane(challengePanel);
        }
    }

    private void removeComponentFromPane(Component component) {
        if (component == null)
            return;

        pane.remove(component);
        pane.revalidate();
        pane.repaint();
    }

    private Component findComponentByChallenge(Container container, ChallengeRequest challenge) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel challengePanel = (JPanel) component;
                if (challengePanel.getComponentCount() >= 3) { // Assuming at least 3 components are always added
                    ChallengeRequest panelChallenge = (ChallengeRequest) challengePanel.getClientProperty("challenge");
                    if (panelChallenge != null && panelChallenge.equals(challenge)) {
                        return component;
                    }
                }
            }
        }
        return null;
    }

    public PendingChallengeScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
    }

    public JPanel getPanel() {
        return this.frame;
    }
}
