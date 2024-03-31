package client.ui;

import client.Client;
import client.ScreenManager;
import client.Session;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;
import server.Characteristic;
import server.Player;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RegisterCharacterScreen extends Screen {
    private Character current = null;
    private JPanel frame;
    private JButton backButton;
    private JPanel charaContainer;
    private JLabel name;
    private JLabel health;
    private JLabel gold;
    private JList abilities;
    private JList weaknesses;
    private JList strengths;
    private JList minions;
    private JList armors;
    private JList weapons;
    private JButton submit;

    @Override
    public void start() {
        super.start();
        ResponseBody response = Client.request("character/default", new RequestBody());
        List<Character> defaultCharacters = (List<Character>) response.getField("characterList");

        defaultCharacters.forEach(chara -> {
            System.out.println(charaContainer);
            JButton button = new JButton();
            button.setText(chara.getName());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setPanelData(chara);
                }
            });
            charaContainer.add(button, new GridConstraints());
        });
    }

    public RegisterCharacterScreen() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.goBack();
            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current == null) {
                    return;
                }

                PlayerCharacter playerCharacter = new PlayerCharacter(current);

                {
                    RequestBody request = new RequestBody();
                    request.addField("character", playerCharacter);
                    Client.request("character/createPlayerCharacter", request);
                }

                {
                    RequestBody request = new RequestBody();
                    request.addField("nick", Session.getCurrentUser().getNick());
                    request.addField("character", playerCharacter);
                    ResponseBody response = Client.request("character/setCharacterOfPlayer", request);

                    // TODO! Add visual feedback
                    if (response.ok) {
                        ScreenManager.render(PlayerDashboardScreen.class);
                    }
                }
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
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
}
