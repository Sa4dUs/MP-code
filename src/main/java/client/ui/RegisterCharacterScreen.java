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
    private JLabel name;
    private JLabel health;
    private JLabel gold;
    private JList<String> abilities;
    private JList<String> weaknesses;
    private JList<String> strengths;
    private JList<String> minions;
    private JList<String> armors;
    private JList<String> weapons;
    private JButton submit;
    private JLabel ability;
    private JLabel specialAbility;

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

        name.setText(character.getName());
        health.setText(Integer.toString(character.getHealth()));
        gold.setText(Integer.toString(character.getGold()));
        Ability charcarterAbility = character.getAbility();
        ability.setText(charcarterAbility != null ? charcarterAbility.getName(): "Empty");
        Ability charcarterSpecialAbility = character.getSpecialAbility();
        specialAbility.setText(charcarterSpecialAbility != null ? charcarterSpecialAbility.getName(): "Empty");
        setListData(weaknesses, character.getDebilitiesList());
        setListData(strengths, character.getResistancesList());
        setListData(minions, character.getMinionList());
        setListData(weapons, character.getWeaponsList());
        setListData(armors, character.getArmorList());
    }

    private void setListData(JList<String> list, List<? extends Object> dataList) {
        DefaultListModel<String> model = new DefaultListModel<>();
        dataList.forEach(entity -> {
            model.addElement(entity.toString());
        });
        list.setModel(model);
    }
}