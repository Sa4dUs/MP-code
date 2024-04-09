package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.characters.PlayerCharacter;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;

import javax.swing.*;
import java.util.List;

public class CharacterScreen extends Screen {
    private JComboBox<Weapon> rightWeaponSelect;
    private JPanel frame;
    private JButton backButton;
    private JTextField nameField;
    private JTextField breedField;
    private JTextField healthField;
    private JTextField goldField;
    private JList<String> strengthsList;
    private JList<String> weaknessesList;
    private JList<String> weaponsList;
    private JList<String> armorsList;
    private JList<String> minionsList;
    private JComboBox<Armor> armorSelect;
    private JComboBox<Weapon> leftWeaponSelect;
    private JTextField specialAbilityField;
    private PlayerCharacter character;

    @Override
    public void start() {
        super.start();
        initializeUI();
        loadCharacterData();
        setupEventHandlers();
    }

    private void initializeUI() {
    }

    private void loadCharacterData() {
        RequestBody request = new RequestBody();
        request.addField("nick", Session.getCurrentUser().getNick());

        ResponseBody response = Client.request("character/get", request);

        if (!response.ok) {
            ScreenManager.render(RegisterCharacterScreen.class); // Redirect to character registration screen
            return;
        }

        character = (PlayerCharacter) response.getField("character");

        // Populate UI elements with character data
        nameField.setText(character.getName());
        healthField.setText(Integer.toString(character.getHealth()));
        goldField.setText(Integer.toString(character.getGold()));
        breedField.setText(character.getBreed().toString());
        Ability charcarterSpecialAbility = character.getSpecialAbility();
        specialAbilityField.setText(charcarterSpecialAbility != null ? charcarterSpecialAbility.getName() : "Empty");

        populateListFromModel(weaknessesList, character.getDebilitiesList());
        populateListFromModel(strengthsList, character.getResistancesList());
        populateListFromModel(minionsList, character.getMinionList());
        populateListFromModel(weaponsList, character.getWeaponsList());
        populateListFromModel(armorsList, character.getArmorList());

        character.getArmorList().forEach(e -> armorSelect.addItem(e));
        armorSelect.addItem(null);
        armorSelect.setSelectedItem(character.getActiveArmor());

        character.getWeaponsList().forEach(e -> {
            leftWeaponSelect.addItem(e);
            rightWeaponSelect.addItem(e);
        });

        leftWeaponSelect.addItem(null);
        leftWeaponSelect.setSelectedItem(character.getActiveWeaponL());

        rightWeaponSelect.addItem(null);
        rightWeaponSelect.setSelectedItem(character.getActiveWeaponR());
    }

    private void setupEventHandlers() {
        backButton.addActionListener(e -> ScreenManager.goBack());

        armorSelect.addActionListener(e -> updateActiveArmor());

        leftWeaponSelect.addActionListener(e -> updateActiveWeapon(leftWeaponSelect));

        rightWeaponSelect.addActionListener(e -> updateActiveWeapon(rightWeaponSelect));
    }

    private void updateActiveArmor() {
        Armor selectedArmor = (Armor) armorSelect.getSelectedItem();
        character.setActiveArmor(selectedArmor);
        updatePlayerCharacter();
    }

    private void updateActiveWeapon(JComboBox<Weapon> weaponSelect) {
        Weapon selectedWeapon = (Weapon) weaponSelect.getSelectedItem();

        if (weaponSelect == leftWeaponSelect && selectedWeapon != null && selectedWeapon.isTwoHanded()) {
            rightWeaponSelect.setSelectedItem("");
            character.setActiveWeaponR(null);
        } else if (weaponSelect == rightWeaponSelect && selectedWeapon != null && selectedWeapon.isTwoHanded()) {
            leftWeaponSelect.setSelectedItem("");
            character.setActiveWeaponL(null);
        }
        if (weaponSelect == leftWeaponSelect) {
            character.setActiveWeaponL(selectedWeapon);
        } else {
            character.setActiveWeaponR(selectedWeapon);
        }
        updatePlayerCharacter();
    }

    private <T> T findItemInList(String itemName, List<T> itemList) {
        for (T item : itemList) {
            if (item.toString().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    private void populateListFromModel(JList<String> list, List<?> modelList) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Object item : modelList) {
            listModel.addElement(item.toString());
        }
        list.setModel(listModel);
    }

    private void updatePlayerCharacter() {
        RequestBody request = new RequestBody();
        request.addField("character", character);
        ResponseBody response = Client.request("character/updatePlayerCharacter", request);
        if (!response.ok) {
            System.out.println("Failed to update player character");
        }
    }

    public JPanel getPanel() {
        return frame;
    }
}