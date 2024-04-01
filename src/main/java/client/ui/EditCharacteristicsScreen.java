package client.ui;

import client.ScreenManager;
import server.Characteristic;

import javax.swing.*;

public class EditCharacteristicsScreen extends EditItemsScreen<Characteristic> {
    private JPanel frame;
    private JButton backButton;
    private JTextField nameField;
    private JPanel characteristicsPanel;
    private JButton saveButton;
    private JPanel containe;
    private JTextField name;
    private JTextField value;
    private JTextField valueField;
    private Characteristic currentItem;

    @Override
    protected JPanel getContainerPanel() {
        return characteristicsPanel;
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