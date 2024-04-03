package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;
import server.Characteristic;
import server.characters.PlayerCharacter;
import server.characters.CharacterType;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EditPlayerCharactersScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JTextField name;
    private JTextField health;
    private JLabel extraLabel;
    private JTextField gold;
    private JPanel minionsPanel;
    private JPanel container;
    private JButton saveButton;
    private JButton deleteButton;
    private JPanel minions;
    private JButton minionAdd;
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
    private JComboBox<CharacterType> breedComboBox;
    private JPanel armorsPanel;
    private JPanel weaponsPanel;
    private JPanel abilitiesPanel;
    private JPanel specialAbilitiesPanel;
    private JPanel weaknessesPanel;
    private JPanel strengthsPanel;

    private PlayerCharacter current;
    private List<Armor> armorList = new ArrayList<>();
    private List<Weapon> weaponList = new ArrayList<>();
    private List<Ability> abilityList = new ArrayList<>();
    private List<Characteristic> characteristicList = new ArrayList<>();
    private List<Minion> minionList = new ArrayList<>();
    private List<PlayerCharacter> characterList = new ArrayList<>();

    @Override
    public void start() {
        super.start();
        initializeComboBoxes();
        fetchItems();
        populateCharacterButtons();
        setActionListeners();
    }

    public EditPlayerCharactersScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        saveButton.addActionListener(e -> updateCharacter());
        deleteButton.addActionListener(e -> deleteCharacter());
    }

    private void initializeComboBoxes() {
        breedComboBox.setModel(new DefaultComboBoxModel<>(CharacterType.values()));
    }

    private void fetchItems() {
        fetchItemsOfType(Armor.class, armorList);
        fetchItemsOfType(Weapon.class, weaponList);
        fetchItemsOfType(Ability.class, abilityList);
        fetchItemsOfType(Characteristic.class, characteristicList);
        fetchItemsOfType(Minion.class, minionList);
        fetchItemsOfType(PlayerCharacter.class, characterList);
    }

    private <T> void fetchItemsOfType(Class<T> clazz, List<T> itemList) {
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);
        ResponseBody response = Client.request("item/getAll", request);
        itemList.addAll((List<T>) response.getField("data"));
    }

    private void populateCharacterButtons() {
        container.setLayout(new GridLayout(characterList.size(), 1));
        characterList.forEach(character -> {
            JButton button = new JButton(character.getName());
            button.addActionListener(e -> {
                current = character;
                setPanelData(character);
            });
            container.add(button, new GridConstraints());
        });
    }

    private void setActionListeners() {
        minionsPanel.add(new JButton("Add Minion"));
        armorsPanel.add(new JButton("Add Armor"));
        weaponsPanel.add(new JButton("Add Weapon"));
        abilitiesPanel.add(new JButton("Add Ability"));
        specialAbilitiesPanel.add(new JButton("Add Special Ability"));
        weaknessesPanel.add(new JButton("Add Weakness"));
        strengthsPanel.add(new JButton("Add Strength"));
    }

    private void updateCharacter() {
        RequestBody request = new RequestBody();
        request.addField("character", current);
        Client.request("character/updateCharacter", request);
    }

    private void deleteCharacter() {
        // TODO: Implement delete functionality
    }

    public void setPanelData(PlayerCharacter character) {
        name.setText(character.getName());
        health.setText(Integer.toString(character.getHealth()));
        gold.setText(Integer.toString(character.getGold()));
        breedComboBox.setSelectedItem(character.getBreed());
        populateItemList(minionsPanel, character.getMinionList());
        populateItemList(armorsPanel, character.getArmorList());
        populateItemList(weaponsPanel, character.getWeaponsList());
        populateItemList(abilitiesPanel, character.getAbilityList());
        populateItemList(specialAbilitiesPanel, character.getSpecialAbilityList());
        populateItemList(weaknessesPanel, character.getDebilitiesList());
        populateItemList(strengthsPanel, character.getResistancesList());
    }

    private void populateItemList(JPanel panel, List<? extends Object> itemList) {
        panel.removeAll();
        itemList.forEach(item -> {
            JLabel label = new JLabel(item.toString());
            JButton removeButton = new JButton("-");
            removeButton.addActionListener(e -> {
                itemList.remove(item);
                panel.remove(label);
                panel.remove(removeButton);
                panel.revalidate();
                panel.repaint();
            });
            panel.add(label);
            panel.add(removeButton);
        });
    }

    @Override
    public Container getPanel() {
        return this.frame;
    }
}