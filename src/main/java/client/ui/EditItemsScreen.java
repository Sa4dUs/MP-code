package client.ui;

import client.Client;
import client.ScreenManager;
import lib.RequestBody;
import lib.ResponseBody;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class EditItemsScreen<T> extends Screen {

    protected abstract JPanel getContainerPanel();

    protected void start(Class<T> clazz, JPanel container) {
        super.start();

        List<T> items = fetchItems(clazz);

        container.removeAll();

        items.forEach(item -> {
            JButton button = new JButton(getItemName(item));
            button.addActionListener(e -> setPanelData(item));
            container.add(button);
        });

        container.revalidate();
        container.repaint();
    }

    protected List<T> fetchItems(Class<T> clazz) {
        RequestBody request = new RequestBody();
        request.addField("clazz", clazz);

        ResponseBody response = Client.request("item/getAll", request);

        return (List<T>) response.getField("data");
    }

    protected abstract String getItemName(T item);

    protected abstract void setPanelData(T item);

    protected void saveItem(T item) {
        RequestBody request = new RequestBody();
        request.addField("object", item);

        ResponseBody response = Client.request("item/set", request);

        if (!response.ok) {
            // Handle error
            System.out.println("Failed to save item");
        }
    }
}