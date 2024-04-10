package client.ui;

import client.ScreenManager;
import server.Characteristic;
import server.Resistance;
import server.Weakness;
import server.items.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAbilitiesScreen extends EditItemsScreen<Ability> {
    private JPanel frame;
    private JButton backButton;
    private JTextField costField;
    private JTextField nameField;
    private JTextField attackField;
    private JButton saveButton;
    private JPanel container;
    private JTextField defenseField;
    private JButton deleteButton;
    private JButton createButton;
    private JComboBox typeComboBox;
    private Ability currentItem;
    private Ability.AbilityType currentType;
    @Override
    public void start() {
        typeComboBox.addItem(Ability.AbilityType.Blessing);
        typeComboBox.addItem(Ability.AbilityType.Discipline);
        typeComboBox.addItem(Ability.AbilityType.Talent);
        typeComboBox.addActionListener(e -> this.updateType());


        super.start(Blessing.class, container);
    }

    @Override
    protected JPanel getContainerPanel() {
        return container;
    }

    @Override
    protected JButton getCreateButton() {
        return this.createButton;
    }

    @Override
    protected void createButtonActionListener() {
        switch (currentType) {
            case Talent -> {
                setPanelData(new Talent());
            }
            case Blessing -> {
                setPanelData(new Blessing());
            }
            case Discipline -> {
                setPanelData(new Discipline());
            }
        }
    }

    @Override
    protected String getItemName(Ability ability) {
        return ability.getName();
    }

    @Override
    protected void setPanelData(Ability ability) {
        nameField.setText(ability.getName());
        costField.setText(Integer.toString(ability.getCost()));
        attackField.setText(Integer.toString(ability.getAttack()));
        defenseField.setText(Integer.toString(ability.getDefense()));
        currentType = ability.getType();
        currentItem = ability;
    }

    public EditAbilitiesScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        saveButton.addActionListener(e -> saveItem());
        deleteButton.addActionListener(e -> deleteItem(currentItem));
    }

    private void saveItem() {
        Ability ability = getNewAbility(currentType);
        ability.setName(nameField.getText());
        ability.setCost(Integer.parseInt(costField.getText()));
        ability.setAttack(Integer.parseInt(attackField.getText()));
        ability.setDefense(Integer.parseInt(defenseField.getText()));
        if (currentItem != null)
            ability.setId(currentItem.getId());

        saveItem(ability);
    }

    private void updateType() {
        this.currentType = (Ability.AbilityType) typeComboBox.getSelectedItem();
        switch (currentType) {
            case Blessing -> {
                super.start(Blessing.class, container);
            }
            case Talent -> {
                super.start(Talent.class, container);
            }
            case Discipline -> {
                super.start(Discipline.class, container);
            }
        }
    }

    private Ability getNewAbility(Ability.AbilityType type)
    {
        switch (type)
        {
            case Blessing -> {
                return new Blessing();
            }
            case Discipline -> {
                return new Discipline();
            }
            case Talent -> {
                return new Talent();
            }
        }
        return null;
    }

    public JPanel getPanel() {
        return frame;
    }
}