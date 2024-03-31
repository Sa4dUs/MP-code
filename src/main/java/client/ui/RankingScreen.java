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
    private JList ranking;

    @Override
    public void start() {
        super.start();
        ResponseBody response = Client.request("challenge/ranking", new RequestBody());

        if (!response.ok) {
            return;
        }

        List<Player> ranking = (List<Player>) response.getField("ranking");

        DefaultListModel<String> list =  new DefaultListModel<>();

        for (int i = 0; i < ranking.size(); i++) {
            String player = ranking.get(i).getName();
            int gold = ranking.get(i).getCharacter() != null ? ranking.get(i).getCharacter().getGold() : 0;
            switch (i + 1) {
                case 1:
                    first.setText("1. " + player + " " + Integer.toString(gold) + " g");
                    break;
                case 2:
                    second.setText("2. " + player + " " + Integer.toString(gold) + " g");
                    break;
                case 3:
                    third.setText("3. " + player + " " + Integer.toString(gold) + " g");
                    break;
                default:
                    list.addElement(Integer.toString(i+1) + ". " + player + " " + Integer.toString(gold) + " g");
            }
        }

        this.ranking.setModel(list);
    }

    public RankingScreen() {
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
