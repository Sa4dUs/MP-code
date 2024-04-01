package client.ui;

import client.ScreenManager;

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
    private JPanel buttonPanel; // Assuming this panel contains buttons

    private Map<JButton, Class<? extends Screen>> buttonActionMap;

    public OperatorDashboardScreen() {
        initializeComponents();
        mapButtonActions();
        setupButtonListeners();
    }

    private void initializeComponents() {
        exitButton = new JButton("Exit");
        // Initialize other components from the .form file
        buttonPanel.setLayout(new GridLayout(0, 1)); // Assuming buttons are arranged vertically
    }

    private void mapButtonActions() {
        buttonActionMap = new HashMap<>();
        buttonActionMap.put(exitButton, null); // No action for exit button initially
        // Add mappings for other buttons
    }

    private void setupButtonListeners() {
        for (Map.Entry<JButton, Class<? extends Screen>> entry : buttonActionMap.entrySet()) {
            JButton button = entry.getKey();
            Class<? extends Screen> screenClass = entry.getValue();
            if (screenClass != null) {
                button.addActionListener(e -> ScreenManager.render(screenClass));
            } else {
                button.addActionListener(e -> ScreenManager.goBack());
            }
        }
    }

    @Override
    public Container getPanel() {
        return frame;
    }
}