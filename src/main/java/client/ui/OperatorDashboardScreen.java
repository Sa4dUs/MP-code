package client.ui;

import client.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorDashboardScreen extends Screen {
    private JPanel frame;
    private JButton exitButton;
    private JButton editWeaponsButton;
    private JButton viewHistoryButton;
    private JButton viewRankingButton;
    private JButton manageChallengesButton;
    private JButton editDefaultCharactersButton;
    private JButton editPlayerCharactersButton;
    private JButton editArmorsButton;
    private JButton editAbilitiesButton;
    private JButton editCharacteristicsButton;
    private JButton editStrenghtsButton;
    private JButton editMinionsButton;

    @Override
    public Container getPanel() {
        return this.frame;
    }

    public OperatorDashboardScreen() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.goBack();
            }
        });
        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(HistoryScreen.class);
            }
        });
        viewRankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(RankingScreen.class);
            }
        });
        manageChallengesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(PendingChallengeScreen.class);
            }
        });
        editDefaultCharactersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(EditDefaultCharactersScreen.class);
            }
        });
        editPlayerCharactersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(EditPlayerCharactersScreen.class);
            }
        });
        editWeaponsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(EditWeaponsScreen.class);
            }
        });
        editArmorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(EditArmorsScreen.class);
            }
        });
        editAbilitiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(EditAbilitiesScreen.class);
            }
        });

        editCharacteristicsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(EditCharacteristicsScreen.class);
            }
        });
        editMinionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.render(EditMinionsScreen.class);
            }
        });
    }
}
