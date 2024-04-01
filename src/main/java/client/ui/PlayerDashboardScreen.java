package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.Player;

import javax.swing.*;
import java.util.List;

public class PlayerDashboardScreen extends Screen {
    private JPanel frame;
    private JButton deleteAccountBtn;
    private JButton LogOutBtn;
    private JButton CareerBtn;
    private JButton NotificationsBtn;
    private JButton sendChallengeBtn;
    private JButton editCharacterBtn;
    private JButton checkRankBtn;
    private JButton exitBtn;
    private JButton createCharacter;
    private JLabel first;
    private JLabel second;
    private JLabel third;
    private JLabel userInfo;

    @Override
    public void start() {
        super.start();
        setUIInfo();
        displayRanking();
    }

    private void setUIInfo() {
        userInfo.setText(Session.getCurrentUser().getName() + "#" + Session.getCurrentUser().getNick());
    }

    private void displayRanking() {
        ResponseBody response = Client.request("challenge/ranking", new RequestBody());
        List<Player> ranking = (List<Player>) response.getField("ranking");

        if (ranking != null && !ranking.isEmpty()) {
            for (int i = 0; i < Math.min(3, ranking.size()); i++) {
                switch (i) {
                    case 0:
                        first.setText("1º " + ranking.get(i).getName());
                        break;
                    case 1:
                        second.setText("2º " + ranking.get(i).getName());
                        break;
                    case 2:
                        third.setText("3º " + ranking.get(i).getName());
                        break;
                }
            }
        }
    }

    public PlayerDashboardScreen() {
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        deleteAccountBtn.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete your account?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                deleteAccount();
            }
        });

        LogOutBtn.addActionListener(e -> logOut());

        CareerBtn.addActionListener(e -> ScreenManager.render(HistoryScreen.class));

        NotificationsBtn.addActionListener(e -> ScreenManager.render(PendingChallengeScreen.class));

        sendChallengeBtn.addActionListener(e -> ScreenManager.render(SendChallengeScreen.class));

        editCharacterBtn.addActionListener(e -> ScreenManager.render(CharacterScreen.class));

        checkRankBtn.addActionListener(e -> ScreenManager.render(RankingScreen.class));

        exitBtn.addActionListener(e -> ScreenManager.exit());

        createCharacter.addActionListener(e -> ScreenManager.render(RegisterCharacterScreen.class));
    }

    private void deleteAccount() {
        RequestBody req = new RequestBody();
        req.addField("id", Session.getCurrentUser().getId());
        Client.request("auth/delete", req);
        logOut();
    }

    private void logOut() {
        Session.setCurrentUser(null);
        ScreenManager.render(LoginScreen.class);
    }

    public JPanel getPanel() {
        return frame;
    }
}