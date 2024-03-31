package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    @Override
    public void start() {
        super.start();

        ResponseBody response = Client.request("challenge/ranking", new RequestBody());
        List<Player> ranking = (List<Player>) response.getField("ranking");
    }

    public PlayerDashboardScreen() {
        deleteAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestBody req = new RequestBody();
                req.addField("id", Session.getCurrentUser().getId());

                // TODO! Add confirmation dialogue
                ScreenManager.render(LoginScreen.class);
                Session.setCurrentUser(null);

                Client.request("auth/delete", req);
            }
        });
        LogOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(LoginScreen.class);
                Session.setCurrentUser(null);
            }
        });
        CareerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO!
                ScreenManager.render(HistoryScreen.class);
            }
        });
        NotificationsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO! Get pending challenges where current is the attacked user
                ScreenManager.render(PendingChallengeScreen.class);
            }
        });
        sendChallengeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(SendChallengeScreen.class);
            }
        });
        editCharacterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO!
                ScreenManager.render(CharacterScreen.class);
            }
        });
        checkRankBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO!
                ScreenManager.render(RankingScreen.class);
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.exit();
            }
        });
        createCharacter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(RegisterCharacterScreen.class);
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
