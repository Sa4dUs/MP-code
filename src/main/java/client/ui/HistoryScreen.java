package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
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

    private void displayTop()
    {
        JLabel result = new JLabel("Result  ");
        pane.add(result);

        JLabel opponent = new JLabel("Oponent  ");
        pane.add(opponent);

        JLabel bet = new JLabel("Gold  ");
        pane.add(bet);

        JLabel attackerMinionsLeft = new JLabel("Attacker minions left  ");
        pane.add(attackerMinionsLeft);

        JLabel attackedMinionsLeft = new JLabel("Attacked minions left  ");
        pane.add(attackedMinionsLeft);

        JLabel turns = new JLabel("Turns  ");
        pane.add(turns);

        JLabel date = new JLabel("Date  ");
        pane.add(date);

        JLabel show = new JLabel("Resume");
        pane.add(show);
    }
    private void displayHistory() {
        List<ChallengeResult> challengeResultList = fetchDuels();
        String current = Session.getCurrentUser().getId();

        if (challengeResultList == null) {
            return;
        }

        pane.setLayout(new GridLayout(challengeResultList.size() + 1, 6));
        displayTop();
        for (ChallengeResult challenge : challengeResultList) {
            String op = Objects.equals(current, challenge.getAttackerId()) ? challenge.getAttackedPlayerId() : challenge.getAttackerId();
            boolean isWinning = Objects.equals(current, challenge.getAttackerId()) && challenge.isWinnerAttacking();

            JLabel result = new JLabel(
                   isWinning ? "VICTORY" : "DEFEAT"
            );
            pane.add(result);

            JLabel opponent = new JLabel(op);
            pane.add(opponent);

            JLabel bet = new JLabel(String.format("%s%d", isWinning ? "+" : "-", challenge.getBet()));
            pane.add(bet);

            JLabel attackerMinionsLeft = new JLabel(Integer.toString(challenge.getAttackerMinionsLeft()));
            pane.add(attackerMinionsLeft);

            JLabel attackedMinionsLeft = new JLabel(Integer.toString(challenge.getAttackerMinionsLeft()));
            pane.add(attackedMinionsLeft);

            JLabel turns = new JLabel(Integer.toString(challenge.getTurns()));
            pane.add(turns);

            JLabel date = new JLabel(challenge.getDate() + " ");
            pane.add(date);

            JButton show = new DefaultButton("Show", e -> {
                showListElements(challenge.getHistory());});
            pane.add(show);
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
                    .filter(e -> Objects.equals(e.getAttackedPlayerId(), Session.getCurrentUser().getId()) || Objects.equals(e.getAttackerId(), Session.getCurrentUser().getId()))
                    .toList();
        }

        return duelList;
    }

    public static void showListElements(List<String> list) {
        StringBuilder message = new StringBuilder();
        for (String element : list) {
            message.append(element).append("\n");
        }

        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(200, 100));

        JOptionPane.showMessageDialog(null, scrollPane, "List of Turns", JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getPanel() {
        return this.frame;
    }

}
