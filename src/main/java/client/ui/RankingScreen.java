package client.ui;

import client.Client;
import client.ScreenManager;
import lib.RequestBody;
import lib.ResponseBody;
import server.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RankingScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JLabel first;
    private JLabel second;
    private JLabel third;
    private JList<String> rankingList;

    @Override
    public void start() {
        super.start();
        displayRanking();
    }

    private void displayRanking() {
        ResponseBody response = Client.request("challenge/ranking", new RequestBody());

        if (!response.ok) {
            return;
        }

        List<Player> ranking = (List<Player>) response.getField("ranking");

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (int i = 0; i < ranking.size(); i++) {
            Player player = ranking.get(i);
            String playerName = player.getName();
            int gold = (player.getCharacter() != null) ? player.getCharacter().getGold() : 0;
            String rankInfo = (i + 1) + ". " + playerName + " " + gold + " g";

            if (i == 0) {
                first.setText(rankInfo);
            } else if (i == 1) {
                second.setText(rankInfo);
            } else if (i == 2) {
                third.setText(rankInfo);
            } else {
                listModel.addElement(rankInfo);
            }
        }

        rankingList.setModel(listModel);
    }

    public RankingScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
    }

    public JPanel getPanel() {
        return frame;
    }
}