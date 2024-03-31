package client.ui;

import client.Client;
import client.ScreenManager;
import lib.RequestBody;
import lib.ResponseBody;
import server.Characteristic;
import server.characters.Character;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Stream;

public class OperatorCharacterScreen extends Screen {
    private JTextField name;
    private JButton deleteButton;
    private JPanel frame;
    private JButton exitButton;
    private JPanel charaContainer; // TODO! Fix IntelIJ
    private JButton updateButton;
    private JList strengths;
    private JList weaknesses;
    private JList weapons;
    private JList armors;
    private JList abilities;
    private JList specialAbilities;
    private JList minions;
    private JTextField health;
    private JTextField gold;
    private JTextField breed;

    private Character current;

    @Override
    public void start() {
        super.start();
        ResponseBody response1 = Client.request("character/default", new RequestBody());
        List<Character> defaultCharacters = (List<Character>) response1.getField("characterList");
        ResponseBody response2 = Client.request("character/player", new RequestBody());
        List<Character> playerCharacters = (List<Character>) response2.getField("characterList");

        List<Character> characters = Stream.concat(defaultCharacters.stream(), playerCharacters.stream()).toList();

        characters.forEach(chara -> {
            JButton button = new JButton();
            button.setText(chara.getName());
            button.setSize(button.getPreferredSize());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setPanelData(chara);
                }
            });
            charaContainer.add(button, new GridBagConstraints());
        });
    }

    public OperatorCharacterScreen() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.goBack();
            }
        });
    }

    public void setPanelData(Character character) {
        this.current = character;

        name.setText(character.getName());
        health.setText(Integer.toString(character.getHealth()));
        gold.setText(Integer.toString(character.getGold()));

        DefaultListModel<String> abilityModel = new DefaultListModel<>();
        for (Ability a: character.getAbilityList()) {
            abilityModel.addElement(a.getName());
        }
        abilities.setModel(abilityModel);

        DefaultListModel<String> weaknessesModel = new DefaultListModel<>();
        for (Characteristic c: character.getDebilitiesList()) {
            weaknessesModel.addElement(c.getName());
        }
        weaknesses.setModel(weaknessesModel);

        DefaultListModel<String> strengthsModel = new DefaultListModel<>();
        for (Characteristic c: character.getResistancesList()) {
            strengthsModel.addElement(c.getName());
        }
        strengths.setModel(strengthsModel);

        DefaultListModel<String> minionsModel = new DefaultListModel<>();
        for (Minion m: character.getMinionList()) {
            minionsModel.addElement(m.getName());
        }
        minions.setModel(minionsModel);

        DefaultListModel<String> weaponsModel = new DefaultListModel<>();
        for (Weapon w: character.getWeaponsList()) {
            weaponsModel.addElement(w.getName());
        }
        weapons.setModel(weaponsModel);

        DefaultListModel<String> armorModel = new DefaultListModel<>();
        for (Armor a: character.getArmorList()) {
            armorModel.addElement(a.getName());
        }
        armors.setModel(armorModel);
    }

    public JPanel getPanel() {
        return this.frame;
    }

}
