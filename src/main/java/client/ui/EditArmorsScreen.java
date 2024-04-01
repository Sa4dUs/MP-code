package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;
import server.items.Armor;
import server.items.Armor;
import server.items.Stats;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditArmorsScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JTextField name;
    private JTextField attack;
    private JPanel charaContainer;
    private JButton saveButton;
    private JTextField defense;

    @Override
    public void start() {
        // CHANGE HERE!
        Class<? extends Stats> clazz = Armor.class;
        List<Armor> items;

        super.start();
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);

        ResponseBody response = Client.request("item/getAll", request);

        // CHANGE HERE!
        items = (List<Armor>) response.getField("data");

        items.forEach(el -> {
            System.out.println(charaContainer);
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

    public EditArmorsScreen() {
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
                Armor item = new Armor();
                item.setName(name.getText());
                item.setAttack(Integer.parseInt(attack.getText()));
                item.setDefense(Integer.parseInt(defense.getText()));

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
    public void setPanelData(Armor item) {
        name.setText(item.getName());
        attack.setText(Integer.toString(item.getAttack()));
        defense.setText(Integer.toString(item.getDefense()));
    }
}
