package client.ui;

import client.ScreenManager;
import server.items.*;

import javax.swing.*;

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
    private Ability currentItem;
    private Ability.AbilityType currentType; //!TODO Añadir selector en la UI @Marcelo
    @Override
    public void start() {
        super.start(Ability.class, container);
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
        setPanelData(new Talent());
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