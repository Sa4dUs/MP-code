package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import server.Characteristic;
import server.minions.Demon;
import server.minions.Ghoul;
import server.minions.Human;
import server.minions.Minion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditMinionsScreen extends EditItemsScreen<Minion> {
    private JPanel frame;
    private JButton backButton;
    private JTextField nameField;
    private JPanel container;
    private JButton saveButton;
    private JTextField healthField;
    private JTextField extraField;
    private JLabel extraLabel;
    private JPanel minionsPanel;
    private JButton addMinionButton;
    private JButton deleteButton;
    private JComboBox<Class<? extends Minion>> breedComboBox;
    private JButton createButton;
    public Minion current;
    private List<Minion> minionList = new ArrayList<>();

    @Override
    public void start() {
        super.start(Human.class, container);

        this.minionList = fetchItems(Human.class);
        this.minionList.addAll(fetchItems(Demon.class));
        this.minionList.addAll(fetchItems(Ghoul.class));

        container.removeAll();

        this.getCreateButton().addActionListener(e -> this.createButtonActionListener());

        this.minionList.forEach(item -> {
            JButton button = new DefaultButton(getItemName(item), e -> setPanelData(item));
            container.add(button, new GridConstraints());
        });

        setPanelData(this.minionList.get(0));

        if (!this.minionList.isEmpty()) {
            setPanelData(this.minionList.get(0));
        }


        breedComboBox.setRenderer(new ClassNameRenderer());

        container.updateUI();
        container.revalidate();
        container.repaint();
    }

    public EditMinionsScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        saveButton.addActionListener(e -> {

            List<Minion> minionList = new ArrayList<>();
            String id = current != null ? current.getId(): null;

            if (current instanceof Demon) {
                minionList = ((Demon) current).getMinions();
            }

            try {
                current = ((Class<? extends Minion>) Objects.requireNonNull(breedComboBox.getSelectedItem())).getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }

            if(id != null)
                current.setId(id);
            current.setName(nameField.getText());
            current.setHealth(Integer.parseInt(healthField.getText()));

            if (current instanceof Demon) {
                ((Demon) current).setPact(extraField.getText());
                ((Demon) current).setMinions(minionList);
            } else if (current instanceof Human) {
                ((Human) current).setLoyalty(Integer.parseInt(extraField.getText()));
            } else if (current instanceof Ghoul) {
                ((Ghoul) current).setDependence(Integer.parseInt(extraField.getText()));
            }

            saveItem(current);
            this.start();
        });

        deleteButton.addActionListener(e -> deleteItem(current));

        addMinionButton.addActionListener(e -> {
            if (current instanceof Demon) {
                displayPopup("Minion", minionList.stream().filter(minion -> !minion.equals(current)).toList(), minionsPanel, ((Demon) current).getMinions());
            }
        });

        breedComboBox.addActionListener(e -> {
            this.setClazz((Class<? extends Minion>) breedComboBox.getSelectedItem());

            if (this.getClazz() == null) {
                return;
            }

            if (this.getClazz().equals(Demon.class)) {
                extraLabel.setText("Pact");
            } else if (this.getClazz().equals(Human.class)) {
                extraLabel.setText("Loyalty");
            } else if (this.getClazz().equals(Ghoul.class)) {
                extraLabel.setText("Dependence");
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }

    @Override
    protected JPanel getContainerPanel() {
        return this.container;
    }

    @Override
    protected JButton getCreateButton() {
        return this.createButton;
    }

    @Override
    protected void createButtonActionListener() {
        current = null;
        nameField.setText("");
        healthField.setText("");
        extraField.setText("");

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(Demon.class);
        model.addElement(Human.class);
        model.addElement(Ghoul.class);

        breedComboBox.setModel(model);
        breedComboBox.setEnabled(true);
    }

    @Override
    protected String getItemName(Minion item) {
        return item.getName();
    }

    public void setPanelData(Minion item) {
        breedComboBox.setEnabled(false);
        this.current = item;

        minionsPanel.setEnabled(false);
        minionsPanel.removeAll();
        addMinionButton.setEnabled(false);

        nameField.setText(item.getName());
        healthField.setText(Integer.toString(item.getHealth()));

        if (item instanceof Demon) {
            extraLabel.setText("Pact:");
            extraField.setText(((Demon) item).getPact());
            minionsPanel.setEnabled(true);
            addMinionButton.setEnabled(true);
            populateItemList(this.minionsPanel, ((Demon) current).getMinions());
        } else if (item instanceof Human) {
            extraLabel.setText("Loyalty:");
            extraField.setText(Integer.toString(((Human) item).getLoyalty()));
        } else if (item instanceof Ghoul) {
            extraLabel.setText("Dependence:");
            extraField.setText(Integer.toString(((Ghoul) item).getDependence()));
        }

        breedComboBox.removeAllItems();
        breedComboBox.addItem(item.getClass());
        breedComboBox.setEnabled(false);
    }

    private void populateItemList(JPanel panel, List<? extends Object> itemList) {
        panel.removeAll();
        panel.setLayout(new GridLayout(0,1));
        itemList.forEach(item -> {
            JPanel container = new JPanel();

            JLabel label = new JLabel(item.toString());
            JButton removeButton = new DefaultButton("-", e -> {
                itemList.remove(item);
                panel.remove(container);
                panel.revalidate();
                panel.revalidate();
                panel.repaint();
            });

            container.add(label, new GridBagConstraints());
            container.add(removeButton, new GridBagConstraints());
            panel.add(container);
        });
    }

    private <T> void displayPopup(String title, List<T> itemList, JPanel panelToUpdate, List<T> listToUpdate) {
        JFrame popupFrame = new JFrame("Select " + title);
        JPanel popupPanel = new JPanel(new BorderLayout());

        JComboBox<T> selectInput = new JComboBox<>();
        DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();
        model.addAll(itemList);
        selectInput.setModel(model);

        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        selectButton.addActionListener(e -> {
            T selectedItem = selectInput.getItemAt(selectInput.getSelectedIndex());
            panelToUpdate.setLayout(new GridLayout(0,1));
            if (selectedItem != null) {
                JPanel container = new JPanel();

                Label label = new Label(selectedItem.toString());

                JButton button = new DefaultButton("-", ev -> {
                    listToUpdate.remove(selectedItem);
                    panelToUpdate.remove(label);
                    panelToUpdate.remove(container);
                    panelToUpdate.revalidate();
                    panelToUpdate.repaint();
                });

                container.add(label);
                container.add(button);
                panelToUpdate.add(container);
                panelToUpdate.revalidate();
                panelToUpdate.repaint();
                listToUpdate.add(selectedItem);
                popupFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(popupFrame, "Please select an item.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> popupFrame.dispose()); // Close the popup

        popupPanel.add(selectInput, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        popupFrame.getContentPane().add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null); // Center the popup
        popupFrame.setVisible(true);
    }
}