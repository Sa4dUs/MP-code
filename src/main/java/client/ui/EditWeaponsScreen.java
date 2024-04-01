package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;
import server.items.Ability;
import server.items.Stats;
import server.items.Weapon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditWeaponsScreen extends Screen {
    private JPanel frame;
    private JButton backButton;
    private JTextField cost;
    private JTextField name;
    private JTextField attack;
    private JPanel charaContainer;
    private JButton saveButton;
    private JTextField defense;
    private JCheckBox twoHanded;

    private Stats currentItem;

    @Override
    public void start() {
        // CHANGE HERE!
        Class<? extends Stats> clazz = Weapon.class;
        List<Weapon> items;

        super.start();
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);

        ResponseBody response = Client.request("item/getAll", request);

        // CHANGE HERE!
        items = (List<Weapon>) response.getField("data");

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

    public EditWeaponsScreen() {
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
                Weapon item = new Weapon();
                item.setName(name.getText());
                item.setAttack(Integer.parseInt(attack.getText()));
                item.setDefense(Integer.parseInt(defense.getText()));
                item.setTwoHanded(twoHanded.isSelected());
                if (currentItem != null)
                    item.setId(currentItem.getId());

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
    public void setPanelData(Weapon item) {
        name.setText(item.getName());
        attack.setText(Integer.toString(item.getAttack()));
        defense.setText(Integer.toString(item.getDefense()));
        twoHanded.setSelected(item.isTwoHanded());
        this.currentItem = item;
    }
}
