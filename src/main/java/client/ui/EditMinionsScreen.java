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
    public Minion current;

    @Override
    public void start() {
        super.start();

        // Fetch items from server
        List<Minion> items = new ArrayList<>();

        // Fetch Humans
        fetchMinions(Human.class, items);

        // Fetch Demons
        fetchMinions(Demon.class, items);

        // Fetch Ghouls
        fetchMinions(Ghoul.class, items);

        // Set container layout
        container.setLayout(new GridLayout(items.size(), 1));

        // Add buttons for each minion
        items.forEach(el -> {
            JButton button = new JButton(el.getName());
            button.addActionListener(e -> {
                current = el;
                setPanelData(el);
            });
            container.add(button, new GridConstraints());
        });
    }

    private void fetchMinions(Class<? extends Minion> clazz, List<Minion> items) {
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);
        ResponseBody response = Client.request("item/getAll", request);
        items.addAll((Collection<? extends Minion>) response.getField("data"));
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

        add.addActionListener(e -> {
            createPopup();
            // No need to call setPanelData() here as it's already called inside createPopup()
        });
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
            minions.setEnabled(true);

            extraLabel.setText("Pact:");
            extraField.setText(((Demon) item).getPact());

            // Set layout for minions panel
            minions.setLayout(new GridLayout(((Demon) item).getMinions().size(), 3));
            for (Minion minion : ((Demon) item).getMinions()) {
                Label label = new Label(minion.getName());
                Button remove = new Button();

                remove.setLabel("-");

                remove.addActionListener(e -> {
                    List<Minion> list = ((Demon) current).getMinions();
                    list.remove(minion);
                    ((Demon) current).setMinions(list);
                    setPanelData(current);
                    minions.remove(label);
                    minions.remove(remove);
                });

                minions.add(label);
                minions.add(add); // Fixed add button here
                minions.add(remove);
            }
        } else if (item instanceof Human) {
            extraLabel.setText("Loyalty:");
            extraField.setText(Integer.toString(((Human) item).getLoyalty()));
        } else if (item instanceof Ghoul) {
            extraLabel.setText("Dependence:");
            extraField.setText(Integer.toString(((Ghoul) item).getDependence()));
        }
    }

    private void createPopup() {
        // Create a new JFrame
        JFrame frame = new JFrame("Character Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Create components
        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Demon", "Human", "Ghoul"});
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel healthLabel = new JLabel("Health:");
        JTextField healthField = new JTextField();
        JLabel extraLabel = new JLabel("Extra:");
        JTextField extraField = new JTextField();

        // Add components to the panel
        panel.add(typeLabel);
        panel.add(typeComboBox);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(healthLabel);
        panel.add(healthField);
        panel.add(extraLabel);
        panel.add(extraField);

        nameField.setEnabled(false);
        healthField.setEnabled(false);
        extraField.setEnabled(false);

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

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            int health = Integer.parseInt(healthField.getText());
            String extra = extraField.getText();

            String selectedType = (String) typeComboBox.getSelectedItem();

            Minion minion = null;
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

            RequestBody request = new RequestBody();
            request.addField("object", minion);
            Client.request("item/set", request);

            // Add new minion to current Demon
            List<Minion> list = ((Demon) current).getMinions();
            list.add(minion);
            ((Demon) current).setMinions(list);

            JButton button = new JButton(minion.getName());
            Minion finalMinion1 = minion;
            button.addActionListener(ev -> {
                current = finalMinion1;
                setPanelData(finalMinion1);
            });
            container.add(button, new GridConstraints());

            Label label = new Label(minion.getName());
            Button remove = new Button();

            remove.setLabel("-");

            Minion finalMinion = minion;
            remove.addActionListener(ev -> {
                List<Minion> minionList = ((Demon) current).getMinions();
                minionList.remove(finalMinion);
                ((Demon) current).setMinions(minionList);
                setPanelData(current);
                minions.remove(label);
                minions.remove(remove);
            });

            minions.add(label);
            minions.add(add); // Fixed add button here
            minions.add(remove);

            frame.dispose();
        });

        cancelButton.addActionListener(e -> frame.dispose());

        panel.add(saveButton);
        panel.add(cancelButton);

        frame.getContentPane().add(panel);

        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
