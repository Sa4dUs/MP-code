package client.ui;

import client.Client;
import client.ScreenManager;
import lib.RequestBody;
import lib.ResponseBody;
import server.characters.Character;

import javax.swing.*;
import java.util.List;
import java.util.stream.Stream;

public class OperatorCharacterScreen extends Screen {
    private JTextField nameField;
    private JButton deleteButton;
    private JButton exitButton;
    private JPanel characterContainer;
    private JButton updateButton;
    private JPanel frame;
    private JTextField breed;
    private JTextField health;
    private JTextField gold;
    private JList strengths;
    private JList weaknesses;
    private JList weapons;
    private JList armors;
    private JList minions;
    private JPanel container;
    private JTextField name;
    private JList<String> strengthsList;
    private JList<String> weaknessesList;
    private JList<String> weaponsList;
    private JList<String> armorsList;
    private JList<String> abilitiesList;
    private JList<String> specialAbilitiesList;
    private JList<String> minionsList;
    private JTextField healthField;
    private JTextField goldField;
    private JTextField breedField;

    private Character currentCharacter;

    public OperatorCharacterScreen() {
        initializeComponents();
        loadCharacterButtons();
    }

    private void initializeComponents() {
        exitButton.addActionListener(e -> ScreenManager.goBack());
    }

    private void loadCharacterButtons() {
        ResponseBody response1 = Client.request("character/default", new RequestBody());
        List<Character> defaultCharacters = (List<Character>) response1.getField("characterList");
        ResponseBody response2 = Client.request("character/player", new RequestBody());
        List<Character> playerCharacters = (List<Character>) response2.getField("characterList");

        List<Character> characters = Stream.concat(defaultCharacters.stream(), playerCharacters.stream()).toList();

        characters.forEach(character -> {
            JButton button = new JButton(character.getName());
            button.addActionListener(e -> setPanelData(character));
            characterContainer.add(button);
        });
    }

    public void setPanelData(Character character) {
        this.currentCharacter = character;

        nameField.setText(character.getName());
        healthField.setText(String.valueOf(character.getHealth()));
        goldField.setText(String.valueOf(character.getGold()));
        breedField.setText(String.valueOf(character.getBreed()));

        setListData(abilitiesList, character.getAbilityList());
        setListData(weaknessesList, character.getDebilitiesList());
        setListData(strengthsList, character.getResistancesList());
        setListData(minionsList, character.getMinionList());
        setListData(weaponsList, character.getWeaponsList());
        setListData(armorsList, character.getArmorList());
    }

    private void setListData(JList<String> list, List<? extends Object> dataList) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Object item : dataList) {
            model.addElement(item.toString());
        }
        list.setModel(model);
    }

    @Override
    public JPanel getPanel() {
        return characterContainer;
    }
}
