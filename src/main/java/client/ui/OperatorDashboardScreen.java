package client.ui;

import client.ScreenManager;
import client.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class OperatorDashboardScreen extends Screen {
    private JPanel frame;
    private JButton exitButton;
    private JButton editArmorsButton;
    private JButton viewHistoryButton;
    private JButton editWeaponsButton;
    private JButton editAbilitiesButton;
    private JButton editCharacteristicsButton;
    private JButton manageChallengesButton;
    private JButton editDefaultCharactersButton;
    private JButton editPlayerCharactersButton;
    private JButton viewRankingButton;
    private JButton editMinionsButton;
    private Map<JButton, Class<? extends Screen>> buttonActionMap;

    public OperatorDashboardScreen() {
        initializeComponents();
        mapButtonActions();
        setupButtonListeners();
    }

    private void initializeComponents() {
        // Initialize other components from the .form file
    }

    private void mapButtonActions() {
        buttonActionMap = new HashMap<>();
        buttonActionMap.put(exitButton, null);
        buttonActionMap.put(editArmorsButton, EditArmorsScreen.class);
        buttonActionMap.put(viewHistoryButton, HistoryScreen.class);
        buttonActionMap.put(editWeaponsButton, EditWeaponsScreen.class);
        buttonActionMap.put(editAbilitiesButton, EditAbilitiesScreen.class);
        buttonActionMap.put(editCharacteristicsButton, EditCharacteristicsScreen.class);
        buttonActionMap.put(manageChallengesButton, PendingChallengeScreen.class);
        buttonActionMap.put(editDefaultCharactersButton, EditDefaultCharactersScreen.class);
        buttonActionMap.put(editPlayerCharactersButton, EditPlayerCharactersScreen.class);
        buttonActionMap.put(viewRankingButton, RankingScreen.class);
        buttonActionMap.put(editMinionsButton, EditMinionsScreen.class);
    }

    private void setupButtonListeners() {
        for (Map.Entry<JButton, Class<? extends Screen>> entry : buttonActionMap.entrySet()) {
            JButton button = entry.getKey();
            Class<? extends Screen> screenClass = entry.getValue();
            if (screenClass != null) {
                button.addActionListener(e -> ScreenManager.render(screenClass));
            } else {
                button.addActionListener(e -> {
                    Session.setCurrentUser(null);
                    Session.sudont();
                    ScreenManager.goBack();
                });
            }
        }
    }

    @Override
    public Container getPanel() {
        return frame;
    }
}