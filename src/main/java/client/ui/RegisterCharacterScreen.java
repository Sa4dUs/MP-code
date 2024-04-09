package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.items.Ability;

import javax.swing.*;
import java.util.List;

public class RegisterCharacterScreen extends Screen {
    private Character current = null;
    private JPanel frame;
    private JButton backButton;
    private JPanel container;
    private JTextField nameField;
    private JList<String> weaknessesList;
    private JList<String> strengthsList;
    private JList<String> minionsList;
    private JList<String> armorsList;
    private JList<String> weaponsList;
    private JButton submit;
    private JTextField healthField;
    private JTextField goldField;
    private JComboBox specialAbilityComboBox;

    @Override
    public void start() {
        super.start();
        loadDefaultCharacters();
    }

    private void loadDefaultCharacters() {
        ResponseBody response = Client.request("character/default", new RequestBody());
        List<Character> defaultCharacters = (List<Character>) response.getField("characterList");

        for (Character character : defaultCharacters) {
            JButton button = new DefaultButton(character.getName(), e -> setPanelData(character));
            container.add(button);
        }
    }

    public RegisterCharacterScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        submit.addActionListener(e -> createCharacter());
    }

    private void createCharacter() {
        if (current == null) {
            return;
        }

        PlayerCharacter playerCharacter = new PlayerCharacter(current);

        RequestBody createRequest = new RequestBody();
        createRequest.addField("character", playerCharacter);
        Client.request("character/createPlayerCharacter", createRequest);

        RequestBody setCharacterRequest = new RequestBody();
        setCharacterRequest.addField("nick", Session.getCurrentUser().getNick());
        setCharacterRequest.addField("character", playerCharacter);
        ResponseBody response = Client.request("character/setCharacterOfPlayer", setCharacterRequest);

        if (response.ok) {
            ScreenManager.render(PlayerDashboardScreen.class);
        }
    }

    public JPanel getPanel() {
        return frame;
    }

    public void setPanelData(Character character) {
        current = character;

        nameField.setText(character.getName());
        healthField.setText(Integer.toString(character.getHealth()));
        goldField.setText(Integer.toString(character.getGold()));
        Ability charcarterSpecialAbility = character.getSpecialAbility();
        specialAbilityComboBox.setSelectedItem(charcarterSpecialAbility != null ? charcarterSpecialAbility: "Empty");
        setListData(weaknessesList, character.getDebilitiesList());
        setListData(strengthsList, character.getResistancesList());
        setListData(minionsList, character.getMinionList());
        setListData(weaponsList, character.getWeaponsList());
        setListData(armorsList, character.getArmorList());
    }

    private void setListData(JList<String> list, List<? extends Object> dataList) {
        DefaultListModel<String> model = new DefaultListModel<>();
        dataList.forEach(entity -> {
            model.addElement(entity.toString());
        });
        list.setModel(model);
    }
}