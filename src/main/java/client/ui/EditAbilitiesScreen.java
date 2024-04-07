package client.ui;

import client.ScreenManager;
import server.items.Ability;
import server.items.Armor;

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
    private Ability currentItem;
    @Override
    public void start() {
        super.start(Ability.class, container);
    }

    @Override
    protected JPanel getContainerPanel() {
        return container;
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
        Ability ability = new Ability();
        ability.setName(nameField.getText());
        ability.setCost(Integer.parseInt(costField.getText()));
        ability.setAttack(Integer.parseInt(attackField.getText()));
        ability.setDefense(Integer.parseInt(defenseField.getText()));
        if (currentItem != null)
            ability.setId(currentItem.getId());

        saveItem(ability);
    }

    public JPanel getPanel() {
        return frame;
    }
}