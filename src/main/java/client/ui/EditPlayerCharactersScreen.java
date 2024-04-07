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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
    private JButton minionAdd;
    private JButton armorAdd;
    private JButton weaponsAdd;
    private JButton resistancesAdd;
    private JButton strengthsAdd;
    private JComboBox<CharacterType> breedComboBox;
    private JPanel armorsPanel;
    private JPanel weaponsPanel;
    private JPanel abilitiesPanel;
    private JPanel specialAbilitiesPanel;
    private JPanel weaknessesPanel;
    private JPanel strengthsPanel;
    private JComboBox<Ability> abilityField;
    private JComboBox<Ability> specialAbilityField;

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
        abilityField.addActionListener(e -> updateAbility(abilityField));
        specialAbilityField.addActionListener(e -> updateAbility(specialAbilityField));
    }

    private void updateAbility(JComboBox<Ability> selection) {
        current.setAbility((Ability) selection.getSelectedItem());
    }

    private void updateSpecialAbility(JComboBox<Ability> selection) {
        current.setSpecialAbility((Ability) selection.getSelectedItem());
    }

    private void initializeComboBoxes() {
        breedComboBox.setModel(new DefaultComboBoxModel<>(CharacterType.values()));
        abilityField.setModel(new DefaultComboBoxModel<Ability>((Vector<Ability>) abilityList));
        specialAbilityField.setModel(new DefaultComboBoxModel<Ability>((Vector<Ability>) abilityList));
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
    }

    private void updateCharacter() {
        RequestBody request = new RequestBody();
        request.addField("character", current);
        Client.request("character/updateCharacter", request);
    }

    private void deleteCharacter() {
        // TODO: Implement delete functionality
    }

    private <T> T findItemInList(String itemName, List<T> itemList) {
        for (T item : itemList) {
            if (item.toString().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void setPanelData(PlayerCharacter character) {
        name.setText(character.getName());
        health.setText(Integer.toString(character.getHealth()));
        gold.setText(Integer.toString(character.getGold()));
        breedComboBox.setSelectedItem(character.getBreed());
        populateItemList(minionsPanel, character.getMinionList());
        populateItemList(armorsPanel, character.getArmorList());
        populateItemList(weaponsPanel, character.getWeaponsList());
        populateItemList(abilitiesPanel, abilityList);
        populateItemList(specialAbilitiesPanel, abilityList);
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