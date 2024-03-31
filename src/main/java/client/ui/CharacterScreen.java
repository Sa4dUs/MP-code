package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import lib.RequestBody;
import lib.ResponseBody;
import server.Characteristic;
import server.characters.PlayerCharacter;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterScreen extends Screen {
    private JComboBox rightWeaponSelect;
    private JPanel frame;
    private JButton backButton;
    private JTextField name;
    private JTextField breed;
    private JTextField health;
    private JTextField gold;
    private JList strengths;
    private JList weaknesses;
    private JList weapons;
    private JList armors;
    private JList abilities;
    private JList specialAbilities;
    private JList minions;
    private JComboBox armorSelect;
    private JComboBox leftWeaponSelect;
    private PlayerCharacter character;

    @Override
    public void start() {
        super.start();

        RequestBody request = new RequestBody();
        request.addField("nick", Session.getCurrentUser().getNick());

        ResponseBody response = Client.request("character/get", request);

        if (!response.ok) {
            ScreenManager.render(RegisterCharacterScreen.class); // hasta luego maricarmen
            return;
        }

        this.character = (PlayerCharacter) response.getField("character");


        // Parte de la izquierda

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

        // Parte de PP y VOX
        character.getArmorList().forEach(e -> {
            armorSelect.addItem(e.getName());
        });
        System.out.println(character.getActiveArmor());
        armorSelect.addItem("");
        armorSelect.setSelectedItem(character.getActiveArmor() == null ? "" : character.getActiveArmor().getName());

        character.getWeaponsList().forEach(e -> {
            leftWeaponSelect.addItem(e.getName());
            rightWeaponSelect.addItem(e.getName());
        });

        leftWeaponSelect.addItem("");
        leftWeaponSelect.setSelectedItem(character.getActiveWeaponL() == null ? "" : character.getActiveWeaponL().getName());

        rightWeaponSelect.addItem("");
        rightWeaponSelect.setSelectedItem(character.getActiveWeaponR() == null ? "" : character.getActiveWeaponR().getName());
    }

    public CharacterScreen() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.goBack();
            }
        });
        armorSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = (String) armorSelect.getSelectedItem();

                character.setActiveArmor(null);
                for (Armor armor : character.getArmorList()) {
                    if (armor.getName().equals(item)) {
                        character.setActiveArmor(armor);
                        break;
                    }
                }

                // TODO! Update Player
                RequestBody request = new RequestBody();
                request.addField("character", character);

                ResponseBody response = Client.request("character/update", request);
            }
        });
        leftWeaponSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = (String) armorSelect.getSelectedItem();

                character.setActiveArmor(null);
                for (Armor armor : character.getArmorList()) {
                    if (armor.getName().equals(item)) {
                        character.setActiveArmor(armor);
                        break;
                    }
                }

                // TODO! Update Player
                RequestBody request = new RequestBody();
                request.addField("character", character);

                ResponseBody response = Client.request("character/update", request);
            }
        });
        rightWeaponSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }
}
