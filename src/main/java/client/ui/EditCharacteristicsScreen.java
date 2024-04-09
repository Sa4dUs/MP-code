package client.ui;

import client.ScreenManager;
import server.Characteristic;
import server.Resistance;
import server.Weakness;
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
    private JComboBox<Characteristic.CharacteristicType> typeComboBox;
    private Characteristic currentItem;
    private Characteristic.CharacteristicType currentType;

    @Override
    public void start() {
        typeComboBox.addItem(Characteristic.CharacteristicType.Resistance);
        typeComboBox.addItem(Characteristic.CharacteristicType.Weakness);
        typeComboBox.addActionListener(e -> this.updateType());

        super.start(Resistance.class, container);
    }

    private void updateType() {
        switch ((Characteristic.CharacteristicType) typeComboBox.getSelectedItem()) {
            case Weakness -> {
                super.start(Weakness.class, container);
            }
            case Resistance -> {
                super.start(Resistance.class, container);
            }
        }
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
            case Weakness -> {
                setPanelData(new Weakness());
            }
            case Resistance -> {
                setPanelData(new Resistance());
            }
        }
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
        Characteristic characteristic = getNewCharacteristic(currentType);
        characteristic.setName(nameField.getText());
        characteristic.setValue(Integer.parseInt(valueField.getText()));
        if (currentItem != null)
            characteristic.setId(currentItem.getId());

        saveItem(characteristic);
    }

    private Characteristic getNewCharacteristic(Characteristic.CharacteristicType type)
    {
        switch (type){
            case Weakness -> {
                return new Weakness();
            }
            case Resistance -> {
                return new Resistance();
            }
        }
        return null;
    }

    public JPanel getPanel() {
        return frame;
    }
}