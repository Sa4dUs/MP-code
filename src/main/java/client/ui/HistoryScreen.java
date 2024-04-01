package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.ChallengeRequest;
import server.ChallengeResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JPanel pane;

    public HistoryScreen() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.goBack();
            }
        });
    }

    @Override
    public void start() {
        super.start();
        displayHistory();
    }

    private void displayHistory() {
        List<ChallengeResult> challengeResultList = fetchDuels();
        String current = Session.getCurrentUser().getId();

        if (challengeResultList == null) {
            return;
        }

        pane.setLayout(new GridLayout(challengeResultList.size(), 3));
        for (ChallengeResult challenge : challengeResultList) {
            String op = Objects.equals(current, challenge.getAttackerPlayerId()) ? challenge.getAttackedPlayerId() : challenge.getAttackerPlayerId();
            boolean isWinning = Objects.equals(current, challenge.getAttackerPlayerId()) && challenge.isWinnerAttacking();

            JLabel result = new JLabel(
                   isWinning ? "VICTORY" : "DEFEAT"
            );
            pane.add(result);

            JLabel opponent = new JLabel(op);
            pane.add(opponent);

            JLabel bet = new JLabel(String.format("%s%d", isWinning ? "+" : "-", challenge.getBet()));
            pane.add(bet);
        }
    }

    private List<ChallengeResult> fetchDuels() {
        ResponseBody response;
        RequestBody request = new RequestBody();
        List<ChallengeResult> duelList = new ArrayList<>();

        request.addField("clazz", ChallengeResult.class);
        response = Client.request("item/getAll", request);

        if (response == null || response.getField("data") == null) {
            return null;
        }

        duelList = (List<ChallengeResult>) response.getField("data");
        if (!Session.isOperator()) {
            duelList = duelList.stream()
                    .filter(e -> Objects.equals(e.getAttackedPlayerId(), Session.getCurrentUser().getId()) || Objects.equals(e.getAttackerPlayerId(), Session.getCurrentUser().getId()))
                    .toList();
        }

        return duelList;
    }


    public JPanel getPanel() {
        return this.frame;
    }

}
