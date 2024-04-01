package client.ui;

import client.ScreenManager;
import server.items.Armor;

import javax.swing.*;

public class EditArmorsScreen extends EditItemsScreen<Armor> {
    private JPanel frame;
    private JButton backButton;
    private JTextField nameField;
    private JTextField attackField;
    private JPanel armorsPanel;
    private JButton saveButton;
    private JTextField name;
    private JTextField attack;
    private JPanel container;
    private JTextField defense;
    private JTextField defenseField;
    private Armor currentItem;

    @Override
    protected JPanel getContainerPanel() {
        return armorsPanel;
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