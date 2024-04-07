package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;
import server.Characteristic;
import server.characters.Character;
import server.characters.CharacterType;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Demon;
import server.minions.Ghoul;
import server.minions.Human;
import server.minions.Minion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EditDefaultCharactersScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JTextField name;
    private JTextField health;
    private JLabel extraLabel;
    private JTextField gold;
    private JPanel minions;
    private JButton minionAdd;
    private JPanel container;
    private JButton saveButton;
    private JButton deleteButton;
    private JComboBox breed;
    private JPanel armors;
    private JButton armorAdd;
    private JPanel weapons;
    private JButton weaponsAdd;
    private JPanel abilities;
    private JButton abilitiesAdd;
    private JPanel specialAbilities;
    private JButton specialAbilitiesAdd;
    private JPanel weaknesses;
    private JButton resistancesAdd;
    private JPanel strengths;
    private JButton strengthsAdd;
    private JComboBox<Ability> abilityComboBox;
    private JComboBox<Ability> specialAbilityComboBox;

    private Character current;

    private List<Armor> armorList = new ArrayList<>();
    private List<Weapon> weaponList = new ArrayList<>();
    private Ability ability;
    private List<Characteristic> characteristicList = new ArrayList<>();
    private List<Minion> minionList = new ArrayList<>();
    private List<Character> characterList = new ArrayList<>();
    private List<Ability> abilityList = new ArrayList<>();

    @Override
    public void start() {
        super.start();

        DefaultComboBoxModel<CharacterType> model = new DefaultComboBoxModel<>(CharacterType.values());
        breed.setModel(model);

        fetchItems();
        fetchCharacters();

        container.setLayout(new GridLayout(characterList.size(), 1));

        characterList.forEach(el -> {
            JButton button = new DefaultButton(el.getName(), e -> {
                current = el;
                setPanelData(el);
            });
            container.add(button, new GridConstraints());
        });

        minionAdd.addActionListener(e -> displayPopup("Minion", minionList, minions, current.getMinionList()));
        armorAdd.addActionListener(e -> displayPopup("Armor", armorList, armors, current.getArmorList()));
        weaponsAdd.addActionListener(e -> displayPopup("Weapon", weaponList, weapons, current.getWeaponsList()));
        resistancesAdd.addActionListener(e -> displayPopup("Strength", characteristicList, strengths, current.getResistancesList()));
        strengthsAdd.addActionListener(e -> displayPopup("Weakness", characteristicList, weaknesses, current.getDebilitiesList()));
    }

    public EditDefaultCharactersScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestBody request = new RequestBody();
                request.addField("character", current);
                Client.request("character/updateCharacter", request);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO!
            }
        });

        abilityComboBox.addActionListener(e -> updateAbility(abilityComboBox));
        specialAbilityComboBox.addActionListener(e -> updateSpecialAbility(specialAbilityComboBox));
    }

    private void updateAbility(JComboBox<Ability> selection) {
        current.setAbility((Ability) selection.getSelectedItem());
    }

    private void updateSpecialAbility(JComboBox<Ability> selection) {
        current.setSpecialAbility((Ability) selection.getSelectedItem());
    }

    private void fetchItems() {
        fetchItemsOfType(Armor.class, armorList);
        fetchItemsOfType(Weapon.class, weaponList);
        fetchItemsOfType(Ability.class, abilityList);
        fetchItemsOfType(Characteristic.class, characteristicList);
        fetchMinions();
    }

    private <T> void fetchItemsOfType(Class<T> clazz, List<T> itemList) {
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);
        ResponseBody response = Client.request("item/getAll", request);
        itemList.addAll((List<T>) response.getField("data"));
    }

    private void fetchMinions() {
        RequestBody request = new RequestBody();

        {
            request.addField("clazz", Demon.class);
            ResponseBody response = Client.request("item/getAll", request);
            minionList.addAll((List<Minion>) response.getField("data"));
        }

        {
            request.addField("clazz", Human.class);
            ResponseBody response = Client.request("item/getAll", request);
            minionList.addAll((List<Minion>) response.getField("data"));
        }

        {
            request.addField("clazz", Ghoul.class);
            ResponseBody response = Client.request("item/getAll", request);
            minionList.addAll((List<Minion>) response.getField("data"));
        }
    }

    private void fetchCharacters() {
        fetchItemsOfType(Character.class, characterList);
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
            if (selectedItem != null) {
                Label label = new Label();
                label.setText(selectedItem.toString());

                Button button = new Button();
                button.setLabel("-");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        listToUpdate.remove(selectedItem);
                        panelToUpdate.remove(label);
                        panelToUpdate.remove(button);
                        panelToUpdate.revalidate();
                        panelToUpdate.repaint();
                    }
                });

                panelToUpdate.add(label);
                panelToUpdate.add(button);
                panelToUpdate.repaint();
                listToUpdate.add(selectedItem);
                popupFrame.dispose(); // Close the popup
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

    public void setPanelData(Character item) {
        name.setText(item.getName());
        health.setText(Integer.toString(item.getHealth()));
        gold.setText(Integer.toString(item.getGold()));
        breed.setSelectedItem(item.getBreed());

        minions.setLayout(new GridLayout(item.getMinionCount(), 2));
        item.getMinionList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Minion> list = current.getMinionList();
                    list.remove(element);
                    current.setMinionList(list);

                    minions.remove(label);
                    minions.remove(button);
                }
            });

            minions.add(label);
            minions.add(button);
        });

        armors.setLayout(new GridLayout(item.getArmorList().size(), 2));
        item.getArmorList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Armor> list = current.getArmorList();
                    list.remove(element);
                    current.setArmorList(list);

                    armors.remove(label);
                    armors.remove(button);
                }
            });

            armors.add(label);
            armors.add(button);
        });

        weapons.setLayout(new GridLayout(item.getWeaponsList().size(), 2));
        item.getWeaponsList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Weapon> list = current.getWeaponsList();
                    list.remove(element);
                    current.setWeaponsList(list);

                    weapons.remove(label);
                    weapons.remove(button);
                }
            });

            weapons.add(label);
            weapons.add(button);
        });

        weaknesses.setLayout(new GridLayout(item.getDebilitiesList().size(), 2));
        item.getDebilitiesList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Characteristic> list = current.getDebilitiesList();
                    list.remove(element);
                    current.setDebilitiesList(list);

                    weaknesses.remove(label);
                    weaknesses.remove(button);
                }
            });

            weaknesses.add(label);
            weaknesses.add(button);
        });

        strengths.setLayout(new GridLayout(item.getResistancesList().size(), 2));
        item.getResistancesList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Characteristic> list = current.getResistancesList();
                    list.remove(element);
                    current.setResistancesList(list);

                    strengths.remove(label);
                    strengths.remove(button);
                }
            });

            strengths.add(label);
            strengths.add(button);
        });
    }

    @Override
    public Container getPanel() {
        return this.frame;
    }
}
