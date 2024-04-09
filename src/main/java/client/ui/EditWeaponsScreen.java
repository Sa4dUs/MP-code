package client.ui;

import client.ScreenManager;
import server.items.Armor;
import server.items.Weapon;

import javax.swing.*;

public class EditWeaponsScreen extends EditItemsScreen<Weapon> {

    private JPanel frame;
    private JButton backButton;
    private JTextField cost;
    private JTextField name;
    private JTextField attack;
    private JPanel container;
    private JButton saveButton;
    private JTextField defense;
    private JCheckBox twoHanded;
    private JButton deleteButton;
    private JButton createButton;

    private Weapon currentItem;

    @Override
    public void start() {
        super.start(Weapon.class, container);
    }

    public EditWeaponsScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        saveButton.addActionListener(e -> saveItem());
        deleteButton.addActionListener(e -> deleteItem(currentItem));
    }

    @Override
    public JPanel getPanel() {
        return this.frame;
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
        setPanelData(new Weapon());
    }

    @Override
    protected String getItemName(Weapon item) {
        return item.getName();
    }

    @Override
    public void setPanelData(Weapon item) {
        name.setText(item.getName());
        attack.setText(Integer.toString(item.getAttack()));
        defense.setText(Integer.toString(item.getDefense()));
        twoHanded.setSelected(item.isTwoHanded());
        this.currentItem = item;
    }

    private void saveItem() {
        Weapon weapon = new Weapon();
        weapon.setName(name.getText());
        weapon.setAttack(Integer.parseInt(attack.getText()));
        weapon.setDefense(Integer.parseInt(defense.getText()));
        weapon.setTwoHanded(twoHanded.isSelected());
        if (currentItem != null)
            weapon.setId(currentItem.getId());

        saveItem(weapon);
    }
}