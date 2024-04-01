package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;
import server.Characteristic;
import server.characters.Character;
import server.characters.CharacterType;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Demon;
import server.minions.Ghoul;
import server.minions.Human;
import server.minions.Minion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EditDefaultCharactersScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JTextField name;
    private JTextField health;
    private JLabel extraLabel;
    private JTextField gold;
    private JPanel minions;
    private JButton minionAdd;
    private JPanel container;
    private JButton saveButton;
    private JButton deleteButton;
    private JComboBox breed;
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

    private Character current;

    private List<Armor> armorList = new ArrayList<>();
    private List<Weapon> weaponList = new ArrayList<>();
    private List<Ability> abilityList = new ArrayList<>();
    private List<Characteristic> characteristicList = new ArrayList<>();
    private List<Minion> minionList = new ArrayList<>();
    private List<Character> characterList = new ArrayList<>();

    @Override
    public void start() {
        super.start();

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(CharacterType.Hunter);
        model.addElement(CharacterType.Lycanthrope);
        model.addElement(CharacterType.Vampire);
        breed.setModel(model);

        fetchAbility();
        fetchArmor();
        fetchCharacteristic();
        fetchMinion();
        fetchWeapon();
        fetchCharacter();

        container.setLayout(new GridLayout(characterList.size(), 1));

        characterList.forEach(el -> {
            JButton button = new JButton(el.getName());
            button.addActionListener(e -> {
                current = el;
                setPanelData(el);
            });
            container.add(button, new GridConstraints());
        });

        minionAdd.addActionListener(e -> displayMinionPopup());
        armorAdd.addActionListener(e -> displayArmorPopup());
        weaponsAdd.addActionListener(e -> displayWeaponPopup());
        abilitiesAdd.addActionListener(e -> displayAbilityPopup());
        specialAbilitiesAdd.addActionListener(e -> displaySpecialAbilityPopup());
        resistancesAdd.addActionListener(e -> displayWeaknessPopup());
        strengthsAdd.addActionListener(e -> displayStrengthPopup());
    }

    public EditDefaultCharactersScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestBody request = new RequestBody();
                request.addField("character", current);
                Client.request("character/updateCharacter", request);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO!
            }
        });
    }

    private void fetchArmor() {
        RequestBody request = new RequestBody();
        request.addField("clazz", Armor.class);
        ResponseBody response = Client.request("item/getAll", request);
        armorList.addAll((List<Armor>) response.getField("data"));
    }

    private void fetchWeapon() {
        RequestBody request = new RequestBody();
        request.addField("clazz", Weapon.class);
        ResponseBody response = Client.request("item/getAll", request);
        weaponList.addAll((List<Weapon>) response.getField("data"));
    }

    private void fetchAbility() {
        RequestBody request = new RequestBody();
        request.addField("clazz", Ability.class);
        ResponseBody response = Client.request("item/getAll", request);
        abilityList.addAll((List<Ability>) response.getField("data"));
    }

    private void fetchCharacteristic() {
        RequestBody request = new RequestBody();
        request.addField("clazz", Characteristic.class);
        ResponseBody response = Client.request("item/getAll", request);
        characteristicList.addAll((List<Characteristic>) response.getField("data"));
    }

    private void fetchMinion() {
        RequestBody request = new RequestBody();
        {
            request.addField("clazz", Demon.class);
            ResponseBody response = Client.request("item/getAll", request);
            minionList.addAll((List<Minion>) response.getField("data"));
        }

        {
            request.addField("clazz", Human.class);
            ResponseBody response = Client.request("item/getAll", request);
            minionList.addAll((List<Minion>) response.getField("data"));
        }

        {
            request.addField("clazz", Ghoul.class);
            ResponseBody response = Client.request("item/getAll", request);
            minionList.addAll((List<Minion>) response.getField("data"));
        }
    }

    private void fetchCharacter() {
        RequestBody request = new RequestBody();
        request.addField("clazz", Character.class);
        ResponseBody response = Client.request("item/getAll", request);
        characterList.addAll((List<Character>) response.getField("data"));
    }

    private void displayArmorPopup() {
        JFrame popupFrame = new JFrame("Select " + "Armor");
        JPanel popupPanel = new JPanel(new BorderLayout());

        JComboBox<Armor> selectInput = new JComboBox<Armor>();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addAll(armorList);
        selectInput.setModel(model);

        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        selectButton.addActionListener(e -> {
            Armor selectedItem = (Armor) selectInput.getSelectedItem();
            if (selectedItem != null) {
                System.out.println("Selected " + "Armor" + ": " + selectedItem);

                Label label = new Label();
                label.setText(selectedItem.getName());

                Button button = new Button();
                button.setLabel("-");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Armor> list = current.getArmorList();
                        list.remove(selectedItem);
                        current.setArmorList(list);

                        armors.remove(label);
                        armors.remove(button);
                    }
                });

                armors.add(label);
                armors.add(button);
                popupFrame.dispose(); // Close the popup
            } else {
                JOptionPane.showMessageDialog(popupFrame, "Please select an item.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> popupFrame.dispose()); // Close the popup

        popupPanel.add(selectInput, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        popupFrame.getContentPane().add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null); // Center the popup
        popupFrame.setVisible(true);
    }

    private void displayWeaponPopup() {
        JFrame popupFrame = new JFrame("Select Weapon");
        JPanel popupPanel = new JPanel(new BorderLayout());

        JComboBox<Weapon> selectInput = new JComboBox<Weapon>();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addAll(weaponList);
        selectInput.setModel(model);

        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        selectButton.addActionListener(e -> {
            Weapon selectedItem = (Weapon) selectInput.getSelectedItem();
            if (selectedItem != null) {
                System.out.println("Selected Weapon: " + selectedItem);

                Label label = new Label();
                label.setText(selectedItem.getName());

                Button button = new Button();
                button.setLabel("-");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Weapon> list = current.getWeaponsList();
                        list.remove(selectedItem);
                        current.setWeaponsList(list);

                        weapons.remove(label);
                        weapons.remove(button);
                    }
                });

                weapons.add(label);
                weapons.add(button);
                popupFrame.dispose(); // Close the popup
            } else {
                JOptionPane.showMessageDialog(popupFrame, "Please select an item.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> popupFrame.dispose()); // Close the popup

        popupPanel.add(selectInput, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        popupFrame.getContentPane().add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null); // Center the popup
        popupFrame.setVisible(true);
    }

    private void displayMinionPopup() {
        JFrame popupFrame = new JFrame("Select Minion");
        JPanel popupPanel = new JPanel(new BorderLayout());

        JComboBox<Minion> selectInput = new JComboBox<Minion>();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addAll(minionList);
        selectInput.setModel(model);

        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        selectButton.addActionListener(e -> {
            Minion selectedItem = (Minion) selectInput.getSelectedItem();
            if (selectedItem != null) {
                System.out.println("Selected Minion: " + selectedItem);

                Label label = new Label();
                label.setText(selectedItem.getName());

                Button button = new Button();
                button.setLabel("-");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Minion> list = current.getMinionList();
                        list.remove(selectedItem);
                        current.setMinionList(list);

                        minions.remove(label);
                        minions.remove(button);
                    }
                });

                minions.add(label);
                minions.add(button);
                popupFrame.dispose(); // Close the popup
            } else {
                JOptionPane.showMessageDialog(popupFrame, "Please select a minion.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> popupFrame.dispose()); // Close the popup

        popupPanel.add(selectInput, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        popupFrame.getContentPane().add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null); // Center the popup
        popupFrame.setVisible(true);
    }


    private void displayAbilityPopup() {
        JFrame popupFrame = new JFrame("Select Ability");
        JPanel popupPanel = new JPanel(new BorderLayout());

        JComboBox<Ability> selectInput = new JComboBox<Ability>();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addAll(abilityList);
        selectInput.setModel(model);

        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        selectButton.addActionListener(e -> {
            Ability selectedItem = (Ability) selectInput.getSelectedItem();
            if (selectedItem != null) {
                System.out.println("Selected Ability: " + selectedItem);

                Label label = new Label();
                label.setText(selectedItem.getName());

                Button button = new Button();
                button.setLabel("-");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Ability> list = current.getAbilityList();
                        list.remove(selectedItem);
                        current.setAbilityList(list);

                        abilities.remove(label);
                        abilities.remove(button);
                    }
                });

                abilities.add(label);
                abilities.add(button);
                popupFrame.dispose(); // Close the popup
            } else {
                JOptionPane.showMessageDialog(popupFrame, "Please select an ability.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> popupFrame.dispose()); // Close the popup

        popupPanel.add(selectInput, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        popupFrame.getContentPane().add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null); // Center the popup
        popupFrame.setVisible(true);
    }

    private void displaySpecialAbilityPopup() {
        JFrame popupFrame = new JFrame("Select Special Ability");
        JPanel popupPanel = new JPanel(new BorderLayout());

        JComboBox<Ability> selectInput = new JComboBox<Ability>();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addAll(abilityList);
        selectInput.setModel(model);

        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        selectButton.addActionListener(e -> {
            Ability selectedItem = (Ability) selectInput.getSelectedItem();
            if (selectedItem != null) {
                System.out.println("Selected Special Ability: " + selectedItem);

                Label label = new Label();
                label.setText(selectedItem.getName());

                Button button = new Button();
                button.setLabel("-");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Ability> list = current.getSpecialAbilityList();
                        list.remove(selectedItem);
                        current.setSpecialAbilityList(list);

                        specialAbilities.remove(label);
                        specialAbilities.remove(button);
                    }
                });

                specialAbilities.add(label);
                specialAbilities.add(button);
                popupFrame.dispose(); // Close the popup
            } else {
                JOptionPane.showMessageDialog(popupFrame, "Please select a special ability.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> popupFrame.dispose()); // Close the popup

        popupPanel.add(selectInput, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        popupFrame.getContentPane().add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null); // Center the popup
        popupFrame.setVisible(true);
    }

    private void displayWeaknessPopup() {
        JFrame popupFrame = new JFrame("Select Weakness");
        JPanel popupPanel = new JPanel(new BorderLayout());

        JComboBox<Characteristic> selectInput = new JComboBox<Characteristic>();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addAll(characteristicList);
        selectInput.setModel(model);

        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        selectButton.addActionListener(e -> {
            Characteristic selectedItem = (Characteristic) selectInput.getSelectedItem();
            if (selectedItem != null) {
                System.out.println("Selected Weakness: " + selectedItem);

                Label label = new Label();
                label.setText(selectedItem.getName());

                Button button = new Button();
                button.setLabel("-");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Characteristic> list = current.getDebilitiesList();
                        list.remove(selectedItem);
                        current.setDebilitiesList(list);

                        weaknesses.remove(label);
                        weaknesses.remove(button);
                    }
                });

                weaknesses.add(label);
                weaknesses.add(button);
                popupFrame.dispose(); // Close the popup
            } else {
                JOptionPane.showMessageDialog(popupFrame, "Please select a weakness.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> popupFrame.dispose()); // Close the popup

        popupPanel.add(selectInput, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        popupFrame.getContentPane().add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null); // Center the popup
        popupFrame.setVisible(true);
    }

    private void displayStrengthPopup() {
        JFrame popupFrame = new JFrame("Select Strength");
        JPanel popupPanel = new JPanel(new BorderLayout());

        JComboBox<Characteristic> selectInput = new JComboBox<Characteristic>();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addAll(characteristicList);
        selectInput.setModel(model);

        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        selectButton.addActionListener(e -> {
            Characteristic selectedItem = (Characteristic) selectInput.getSelectedItem();
            if (selectedItem != null) {
                System.out.println("Selected Strength: " + selectedItem);

                Label label = new Label();
                label.setText(selectedItem.getName());

                Button button = new Button();
                button.setLabel("-");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Characteristic> list = current.getResistancesList();
                        list.remove(selectedItem);
                        current.setResistancesList(list);

                        strengths.remove(label);
                        strengths.remove(button);
                    }
                });

                strengths.add(label);
                strengths.add(button);
                popupFrame.dispose(); // Close the popup
            } else {
                JOptionPane.showMessageDialog(popupFrame, "Please select a strength.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> popupFrame.dispose()); // Close the popup

        popupPanel.add(selectInput, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        popupPanel.add(buttonPanel, BorderLayout.SOUTH);

        popupFrame.getContentPane().add(popupPanel);
        popupFrame.pack();
        popupFrame.setLocationRelativeTo(null); // Center the popup
        popupFrame.setVisible(true);
    }

    public void setPanelData(Character item) {
        minions.setEnabled(false);

        name.setText(item.getName());
        health.setText(Integer.toString(item.getHealth()));
        gold.setText(Integer.toString(item.getGold()));
        breed.setSelectedItem(item.getBreed());

        minions.setLayout(new GridLayout(item.getMinionCount(), 2));
        item.getMinionList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Minion> list = current.getMinionList();
                    list.remove(element);
                    current.setMinionList(list);

                    minions.remove(label);
                    minions.remove(button);
                }
            });

            minions.add(label);
            minions.add(button);
        });

        armors.setLayout(new GridLayout(item.getArmorList().size(), 2));
        item.getArmorList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Armor> list = current.getArmorList();
                    list.remove(element);
                    current.setArmorList(list);

                    armors.remove(label);
                    armors.remove(button);
                }
            });

            armors.add(label);
            armors.add(button);
        });

        weapons.setLayout(new GridLayout(item.getWeaponsList().size(), 2));
        item.getWeaponsList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Weapon> list = current.getWeaponsList();
                    list.remove(element);
                    current.setWeaponsList(list);

                    weapons.remove(label);
                    weapons.remove(button);
                }
            });

            weapons.add(label);
            weapons.add(button);
        });

        abilities.setLayout(new GridLayout(item.getAbilityList().size(), 2));
        item.getAbilityList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Ability> list = current.getAbilityList();
                    list.remove(element);
                    current.setAbilityList(list);

                    abilities.remove(label);
                    abilities.remove(button);
                }
            });

            abilities.add(label);
            abilities.add(button);
        });

        specialAbilities.setLayout(new GridLayout(item.getSpecialAbilityList().size(), 2));
        item.getSpecialAbilityList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Ability> list = current.getSpecialAbilityList();
                    list.remove(element);
                    current.setSpecialAbilityList(list);

                    specialAbilities.remove(label);
                    specialAbilities.remove(button);
                }
            });

            specialAbilities.add(label);
            specialAbilities.add(button);
        });

        weaknesses.setLayout(new GridLayout(item.getDebilitiesList().size(), 2));
        item.getDebilitiesList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Characteristic> list = current.getDebilitiesList();
                    list.remove(element);
                    current.setDebilitiesList(list);

                    weaknesses.remove(label);
                    weaknesses.remove(button);
                }
            });

            weaknesses.add(label);
            weaknesses.add(button);
        });

        strengths.setLayout(new GridLayout(item.getResistancesList().size(), 2));
        item.getResistancesList().forEach(element -> {
            Label label = new Label();
            label.setText(element.getName());

            Button button = new Button();
            button.setLabel("-");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<Characteristic> list = current.getResistancesList();
                    list.remove(element);
                    current.setResistancesList(list);

                    strengths.remove(label);
                    strengths.remove(button);
                }
            });

            strengths.add(label);
            strengths.add(button);
        });

    }
    @Override
    public Container getPanel() {
        return this.frame;
    }
}
