package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerDashboardScreen extends Screen {


    private JPanel frame;
    private JButton deleteAccountBtn;
    private JButton LogOutBtn;
    private JButton CareerBtn;
    private JButton NotificationsBtn;
    private JButton sendChallengeBtn;
    private JButton editCharacterBtn;
    private JButton checkRankBtn;
    private JButton exitButton;

    public PlayerDashboardScreen() {
        deleteAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestBody req = new RequestBody();
                req.addField("user", Session.getCurrentUser().getId());

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
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.exit();
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
