package client.ui;

import client.ScreenManager;
import server.Characteristic;
import server.items.Ability;
import server.items.Armor;

import javax.swing.*;

public class EditCharacteristicsScreen extends EditItemsScreen<Characteristic> {
    private JPanel frame;
    private JButton backButton;
    private JTextField nameField;
    private JButton saveButton;
    private JPanel container;
    private JTextField valueField;
    private JButton deleteButton;
    private JButton createButton;
    private Characteristic currentItem;

    @Override
    public void start() {
        super.start(Characteristic.class, container);
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
        setPanelData(new Characteristic());
    }

    @Override
    protected String getItemName(Characteristic characteristic) {
        return characteristic.getName();
    }

    @Override
    protected void setPanelData(Characteristic characteristic) {
        nameField.setText(characteristic.getName());
        valueField.setText(Integer.toString(characteristic.getValue()));
        currentItem = characteristic;
    }

    public EditCharacteristicsScreen() {
        backButton.addActionListener(e -> ScreenManager.goBack());
        saveButton.addActionListener(e -> saveCharacteristic());
        deleteButton.addActionListener(e -> deleteItem(currentItem));
    }

    private void saveCharacteristic() {
        Characteristic characteristic = new Characteristic();
        characteristic.setName(nameField.getText());
        characteristic.setValue(Integer.parseInt(valueField.getText()));
        if (currentItem != null)
            characteristic.setId(currentItem.getId());

        saveItem(characteristic);
    }

    public JPanel getPanel() {
        return frame;
    }
}