package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;
import server.Characteristic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditCharacteristicsScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JTextField name;
    private JPanel charaContainer;
    private JButton saveButton;
    private JTextField value;

    @Override
    public void start() {
        // CHANGE HERE!
        Class<Characteristic> clazz = Characteristic.class;
        List<Characteristic> items;

        super.start();
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);

        ResponseBody response = Client.request("item/getAll", request);

        // CHANGE HERE!
        items = (List<Characteristic>) response.getField("data");

        items.forEach(el -> {
            JButton button = new JButton();
            button.setText(el.getName());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setPanelData(el);
                }
            });
            charaContainer.add(button, new GridConstraints());
        });
    }

    public EditCharacteristicsScreen() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.goBack();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // CHANGE HERE!
                Characteristic item = new Characteristic();
                item.setName(name.getText());
                item.setValue(Integer.parseInt(value.getText()));

                RequestBody request = new RequestBody();
                request.addField("object", item);

                ResponseBody response = Client.request("item/set", request);
            }
        });
    }

    public JPanel getPanel() {
        return this.frame;
    }

    // CHANGE HERE!
    public void setPanelData(Characteristic item) {
        name.setText(item.getName());
        value.setText(Integer.toString(item.getValue()));
    }
}
