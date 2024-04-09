package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;
import server.minions.Demon;
import server.minions.Ghoul;
import server.minions.Human;
import server.minions.Minion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EditMinionsScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JTextField name;
    private JPanel container;
    private JButton saveButton;
    private JTextField health;
    private JTextField extraField;
    private JLabel extraLabel;
    private JPanel minions;
    private JButton add;
    private JButton deleteButton;
    private JComboBox breedComboBox;
    private JButton createButton;
    public Minion current;

    @Override
    public void start() {
        super.start();

        // Fetch items from server
        List<Minion> items = fetchMinions(Human.class);
        items.addAll(fetchMinions(Demon.class));
        items.addAll(fetchMinions(Ghoul.class));

        // Set container layout
        container.setLayout(new GridLayout(items.size(), 1));

        // Add buttons for each minion
        items.forEach(el -> {
            JButton button = new DefaultButton(el.getName(), e -> {
                current = el;
                setPanelData(el);
            });
            container.add(button, new GridConstraints());
        });
    }

    private List<Minion> fetchMinions(Class<? extends Minion> clazz) {
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);
        ResponseBody response = Client.request("item/getAll", request);
        return (List<Minion>) response.getField("data");
    }

    public EditMinionsScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());

        saveButton.addActionListener(e -> {
            // Update current minion
            current.setName(name.getText());
            current.setHealth(Integer.parseInt(health.getText()));

            // Update specific attributes based on minion type
            if (current instanceof Demon) {
                ((Demon) current).setPact(extraField.getText());
            } else if (current instanceof Human) {
                ((Human) current).setLoyalty(Integer.parseInt(extraField.getText()));
            } else if (current instanceof Ghoul) {
                ((Ghoul) current).setDependence(Integer.parseInt(extraField.getText()));
            }

            // Save minion changes
            RequestBody request = new RequestBody();
            request.addField("object", current);
            Client.request("item/set", request);
        });

        add.addActionListener(e -> createPopup());
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO! Delete items
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }

    public void setPanelData(Minion item) {
        minions.setEnabled(false);

        name.setText(item.getName());
        health.setText(Integer.toString(item.getHealth()));

        if (item instanceof Demon) {
            extraLabel.setText("Pact:");
            extraField.setText(((Demon) item).getPact());
        } else if (item instanceof Human) {
            extraLabel.setText("Loyalty:");
            extraField.setText(Integer.toString(((Human) item).getLoyalty()));
        } else if (item instanceof Ghoul) {
            extraLabel.setText("Dependence:");
            extraField.setText(Integer.toString(((Ghoul) item).getDependence()));
        }

        breedComboBox.removeAllItems();
        breedComboBox.addItem(item.getClass().getSimpleName());
        breedComboBox.setEnabled(false);
    }

    private void createPopup() {
        // Create a new JFrame
        JFrame popupFrame = new JFrame("Create Minion");
        JPanel popupPanel = new JPanel(new GridLayout(5, 2));

        // Create components
        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Demon", "Human", "Ghoul"});
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel healthLabel = new JLabel("Health:");
        JTextField healthField = new JTextField();
        JLabel extraLabel = new JLabel();
        JTextField extraField = new JTextField();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Add components to the panel
        popupPanel.add(typeLabel);
        popupPanel.add(typeComboBox);
        popupPanel.add(nameLabel);
        popupPanel.add(nameField);
        popupPanel.add(healthLabel);
        popupPanel.add(healthField);
        popupPanel.add(extraLabel);
        popupPanel.add(extraField);
        popupPanel.add(saveButton);
        popupPanel.add(cancelButton);

        // Set default state of fields
        nameField.setEnabled(false);
        healthField.setEnabled(false);
        extraField.setEnabled(false);

        // Update extraLabel and enable fields based on selected type
        typeComboBox.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();
            switch (selectedType) {
                case "Demon":
                    extraLabel.setText("Pact:");
                    break;
                case "Human":
                    extraLabel.setText("Loyalty:");
                    break;
                case "Ghoul":
                    extraLabel.setText("Dependence:");
                    break;
            }
            nameField.setEnabled(true);
            healthField.setEnabled(true);
            extraField.setEnabled(true);
        });

        // Handle save button click
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            int health = Integer.parseInt(healthField.getText());
            String extra = extraField.getText();

            // Create minion based on selected type
            Minion minion = null;
            String selectedType = (String) typeComboBox.getSelectedItem();
            switch (selectedType) {
                case "Demon":
                    minion = new Demon();
                    ((Demon) minion).setPact(extra);
                    break;
                case "Human":
                    minion = new Human();
                    ((Human) minion).setLoyalty(Integer.parseInt(extra));
                    break;
                case "Ghoul":
                    minion = new Ghoul();
                    ((Ghoul) minion).setDependence(Integer.parseInt(extra));
                    break;
            }

            minion.setName(name);
            minion.setHealth(health);

            // Save minion to the server
            RequestBody request = new RequestBody();
            request.addField("object", minion);
            Client.request("item/set", request);

            Minion finalMinion = minion;

            JButton button = new DefaultButton(minion.getName(), ev -> {
                current = finalMinion;
                setPanelData(finalMinion);
            });

            container.add(button, new GridConstraints());

            // Close the popup
            popupFrame.dispose();
        });

        // Handle cancel button click
        cancelButton.addActionListener(e -> popupFrame.dispose());

        // Add panel to the frame
        popupFrame.getContentPane().add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null); // Center the popup
        popupFrame.setVisible(true);
    }
}