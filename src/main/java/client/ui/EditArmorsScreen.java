package client.ui;

import client.ScreenManager;
import server.items.Ability;
import server.items.Armor;

import javax.swing.*;

public class EditArmorsScreen extends EditItemsScreen<Armor> {
    private JPanel frame;
    private JButton backButton;
    private JTextField nameField;
    private JTextField attackField;
    private JButton saveButton;
    private JPanel container;
    private JTextField defenseField;
    private JButton deleteButton;
    private JButton createButton;
    private Armor currentItem;

    @Override
    public void start() {
        super.start(Armor.class, container);
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
        setPanelData(new Armor());
    }

    @Override
    protected String getItemName(Armor armor) {
        return armor.getName();
    }

    @Override
    protected void setPanelData(Armor armor) {
        nameField.setText(armor.getName());
        attackField.setText(Integer.toString(armor.getAttack()));
        defenseField.setText(Integer.toString(armor.getDefense()));
        currentItem = armor;
    }

    public EditArmorsScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        saveButton.addActionListener(e -> saveItem());
        deleteButton.addActionListener(e -> deleteItem(currentItem));
    }

    private void saveItem() {
        Armor armor = new Armor();
        armor.setName(nameField.getText());
        armor.setAttack(Integer.parseInt(attackField.getText()));
        armor.setDefense(Integer.parseInt(defenseField.getText()));
        if (currentItem != null)
            armor.setId(currentItem.getId());

        saveItem(armor);
    }

    public JPanel getPanel() {
        return frame;
    }
}