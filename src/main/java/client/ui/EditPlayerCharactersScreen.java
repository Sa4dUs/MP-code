package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;
import server.Characteristic;
import server.Player;
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

public class EditPlayerCharactersScreen extends EditItemsScreen<PlayerCharacter> {
    private JPanel frame;
    private JButton backButton;
    private JPanel container;
    private JTextField name;
    private JTextField health;
    private JTextField gold;
    private JPanel minionsPanel;
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
        super.start(PlayerCharacter.class, this.getContainerPanel());
        fetchItems();
        initializeComboBoxes();
        setActionListeners();
    }

    private void fetchItems() {
        characterList = this.fetchItems(PlayerCharacter.class);

        fetchItemsOfType(Armor.class, armorList);
        fetchItemsOfType(Weapon.class, weaponList);
        fetchItemsOfType(Ability.class, abilityList);
        fetchItemsOfType(Characteristic.class, characteristicList);
        fetchItemsOfType(Minion.class, minionList);
    }

    private void initializeComboBoxes() {
        breedComboBox.setModel(new DefaultComboBoxModel<>(CharacterType.values()));
        abilityField.setModel(new DefaultComboBoxModel<Ability>(abilityList.toArray(Ability[]::new)));
        specialAbilityField.setModel(new DefaultComboBoxModel<Ability>(abilityList.toArray(Ability[]::new)));
    }


    private <T> void fetchItemsOfType(Class<T> clazz, List<T> itemList) {
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);
        ResponseBody response = Client.request("item/getAll", request);
        itemList.addAll((List<T>) response.getField("data"));
    }

    private void setActionListeners() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        saveButton.addActionListener(e -> saveItem(current));
        deleteButton.addActionListener(e -> deleteItem(current));
        abilityField.addActionListener(e -> updateAbility(abilityField));
        specialAbilityField.addActionListener(e -> updateSpecialAbility(specialAbilityField));
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
            panel.add(label, new GridConstraints());
            panel.add(removeButton, new GridConstraints());
        });
    }

    private void updateAbility(JComboBox<Ability> selection) {
        current.setAbility((Ability) selection.getSelectedItem());
    }

    private void updateSpecialAbility(JComboBox<Ability> selection) {
        current.setSpecialAbility((Ability) selection.getSelectedItem());
    }

    @Override
    public Container getPanel() {
        return this.frame;
    }

    @Override
    protected JPanel getContainerPanel() {
        return this.container;
    }

    @Override
    protected String getItemName(PlayerCharacter character) {
        return character.getName();
    }

    @Override
    protected void setPanelData(PlayerCharacter character) {
        name.setText(character.getName());
        health.setText(Integer.toString(character.getHealth()));
        gold.setText(Integer.toString(character.getGold()));
        breedComboBox.setSelectedItem(character.getBreed());
        populateItemList(minionsPanel, character.getMinionList());
        populateItemList(armorsPanel, character.getArmorList());
        populateItemList(weaponsPanel, character.getWeaponsList());
        abilityField.setSelectedItem(character.getAbility());
        specialAbilityField.setSelectedItem(character.getAbility());
        populateItemList(weaknessesPanel, character.getDebilitiesList());
        populateItemList(strengthsPanel, character.getResistancesList());
    }
}