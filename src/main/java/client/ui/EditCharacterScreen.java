package client.ui;

import client.Client;
import client.ScreenManager;
import lib.RequestBody;
import lib.ResponseBody;
import server.Resistance;
import server.Weakness;
import server.characters.Character;
import server.characters.CharacterType;
import server.items.*;
import server.minions.Demon;
import server.minions.Ghoul;
import server.minions.Human;
import server.minions.Minion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class EditCharacterScreen<T extends Character> extends EditItemsScreen<Character> {
    protected abstract JTextField getNameField();
    protected abstract JTextField getHealthField();
    protected abstract JTextField getGoldField();
    protected abstract JComboBox<CharacterType> getBreedComboBox();
    protected abstract JPanel getMinionsPanel();
    protected abstract JPanel getButtonsMinionsPanel();
    protected abstract JButton getMinionAddButton();
    protected abstract JPanel getArmorsPanel();
    protected abstract JPanel getButtonsArmorPanel();
    protected abstract JButton getArmorAddButton();
    protected abstract JPanel getWeaponsPanel();
    protected abstract JPanel getButtonsWeaponsPanel();
    protected abstract JButton getWeaponsAddButton();
    protected abstract JPanel getStrengthsPanel();
    protected abstract JPanel getButtonsStrengthsPanel();
    protected abstract JButton getStrengthsAddButton();
    protected abstract JPanel getWeaknessesPanel();
    protected abstract JPanel getButtonsWeaknessesPanel();
    protected abstract JButton getWeaknessesAddButton();
    protected abstract JComboBox<Ability> getSpecialAbilityField();
    protected abstract JButton getSaveButton();
    protected abstract JButton getDeleteButton();
    protected abstract JButton getBackButton();

    protected abstract Character getCurrent();
    protected abstract void setCurrent(T character);
    protected abstract List<Armor> getArmorList();
    protected abstract List<Weapon> getWeaponList();
    protected abstract List<Ability> getSpecialAbilityList();
    protected abstract List<Weakness> getWeaknessesList();
    protected abstract List<Resistance> getResistancesList();
    protected abstract List<Minion> getMinionList();
    protected abstract List<T> getCharacterList();

    public void start(Class clazz) {
        super.start(clazz, this.getContainerPanel());
        fetchItems();
        initializeComboBoxes();
        updateAbilities();
        setActionListeners();
    }

    protected void fetchItems() {
        fetchItemsOfType(Armor.class, this.getArmorList());
        fetchItemsOfType(Weapon.class, this.getWeaponList());
        fetchItemsOfType(Weakness.class, this.getWeaknessesList());
        fetchItemsOfType(Resistance.class, this.getResistancesList());
        fetchMinions(this.getMinionList());

    }

    <R> void fetchItemsOfType(Class<? extends R> clazz, List<R> itemList) {
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);
        ResponseBody response = Client.request("item/getAll", request);
        itemList.addAll((List<R>) response.getField("data"));
    }

    private void fetchMinions(List<Minion> list) {
        // TODO! Improve this shit
        RequestBody request = new RequestBody();

        {
            request.addField("clazz", Demon.class);
            ResponseBody response = Client.request("item/getAll", request);
            list.addAll((List<Minion>) response.getField("data"));
        }

        {
            request.addField("clazz", Human.class);
            ResponseBody response = Client.request("item/getAll", request);
            list.addAll((List<Minion>) response.getField("data"));
        }

        {
            request.addField("clazz", Ghoul.class);
            ResponseBody response = Client.request("item/getAll", request);
            list.addAll((List<Minion>) response.getField("data"));
        }
    }

    private void fetchAbilities()
    {
        CharacterType type =(CharacterType) getBreedComboBox().getSelectedItem();
        if(type == null)
            type = CharacterType.Hunter;
        switch (type){
            case Hunter -> {
                fetchItemsOfType(Talent.class, this.getSpecialAbilityList());
            }
            case Lycanthrope -> {
                fetchItemsOfType(Blessing.class, this.getSpecialAbilityList());
            }
            case Vampire -> {
                fetchItemsOfType(Discipline.class, this.getSpecialAbilityList());
            }
        }
    }

    private void updateAbilities()
    {
        this.getSpecialAbilityList().clear();
        fetchAbilities();
        this.getSpecialAbilityField().setModel(new DefaultComboBoxModel<Ability>(getSpecialAbilityList().toArray(Ability[]::new)));
    }

    protected void initializeComboBoxes() {
        this.getBreedComboBox().setModel(new DefaultComboBoxModel<>(CharacterType.values()));

        this.getSpecialAbilityField().setModel(new DefaultComboBoxModel<Ability>(getSpecialAbilityList().toArray(Ability[]::new)));
    }

    protected void setActionListeners() {
        this.getBreedComboBox().addActionListener(e -> updateBreed());
        this.getSpecialAbilityField().addActionListener(e -> updateSpecialAbility());
        this.getBackButton().addActionListener(e -> ScreenManager.goBack());
        this.getSaveButton().addActionListener(e -> saveItem(this.getCurrent()));
        this.getDeleteButton().addActionListener(e -> deleteItem(this.getCurrent()));

        getMinionAddButton().addActionListener(e -> displayPopup("Minion", getMinionList(), getMinionsPanel(), getButtonsMinionsPanel(), getCurrent().getMinionList()));
        getArmorAddButton().addActionListener(e -> displayPopup("Armor", getArmorList(), getArmorsPanel(), getButtonsArmorPanel(), getCurrent().getArmorList()));
        getWeaponsAddButton().addActionListener(e -> displayPopup("Weapon", getWeaponList(), getWeaponsPanel(), getButtonsWeaponsPanel(), getCurrent().getWeaponsList()));
        getStrengthsAddButton().addActionListener(e -> displayPopup("Strength", getResistancesList(), getStrengthsPanel(), getButtonsStrengthsPanel(), getCurrent().getResistancesList()));
        getWeaknessesAddButton().addActionListener(e -> displayPopup("Weakness", getWeaknessesList(), getWeaknessesPanel(), getButtonsWeaknessesPanel(), getCurrent().getDebilitiesList()));
    }

    private void updateBreed() {
        this.getCurrent().setBreed((CharacterType) this.getBreedComboBox().getSelectedItem());
        this.updateAbilities();
    }

    private void updateSpecialAbility() {
        this.getCurrent().setSpecialAbility((Ability) this.getSpecialAbilityField().getSelectedItem());
    }

    private <T> void displayPopup(String title, List<T> itemList, JPanel panelToUpdate, JPanel buttonsPanelToUpdate, List<T> listToUpdate) {
        JFrame popupFrame = new JFrame("Select " + title);
        JPanel popupPanel = new JPanel(new BorderLayout());

        JComboBox<T> selectInput = new JComboBox<>();
        DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();
        model.addAll(itemList);
        selectInput.setModel(model);

        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");

        selectButton.addActionListener(e -> {
            T selectedItem = selectInput.getItemAt(selectInput.getSelectedIndex());
            if (selectedItem != null) {
                Label label = new Label();
                label.setText(selectedItem.toString());

                Button button = new Button();
                button.setLabel("-");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        listToUpdate.remove(selectedItem);
                        panelToUpdate.remove(label);
                        buttonsPanelToUpdate.remove(button);
                    }
                });

                GridBagConstraints labelConstraints = ((GridBagLayout) panelToUpdate.getLayout()).getConstraints(label);
                GridBagConstraints buttonConstraints = ((GridBagLayout) buttonsPanelToUpdate.getLayout()).getConstraints(button);

                panelToUpdate.add(label, labelConstraints);
                buttonsPanelToUpdate.add(button, buttonConstraints);
                listToUpdate.add(selectedItem);
                popupFrame.dispose(); // Close the popup
            } else {
                JOptionPane.showMessageDialog(popupFrame, "Please select an item.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            panelToUpdate.updateUI();
            buttonsPanelToUpdate.updateUI();
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


    @Override
    protected String getItemName(Character character) {
        return character.getName();
    }

    @Override
    protected void setPanelData(Character character) {
        this.setCurrent((T) character);

        this.getNameField().setText(character.getName());
        this.getHealthField().setText(Integer.toString(character.getHealth()));
        this.getGoldField().setText(Integer.toString(character.getGold()));
        this.getBreedComboBox().setSelectedItem(character.getBreed());

        this.getSpecialAbilityField().addItem(null);
        this.getSpecialAbilityField().setSelectedItem(character.getSpecialAbility());

        populateItemList(this.getMinionsPanel(), character.getMinionList());
        populateItemList(this.getArmorsPanel(), character.getArmorList());
        populateItemList(this.getWeaponsPanel(), character.getWeaponsList());
        populateItemList(this.getStrengthsPanel(), character.getResistancesList());
        populateItemList(this.getWeaknessesPanel(), character.getDebilitiesList());
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
            panel.add(label, new GridBagConstraints());
            panel.add(removeButton, new GridBagConstraints());
        });
    }
}
