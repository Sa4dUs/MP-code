package client.ui;

import client.Client;
import client.ScreenManager;
import com.intellij.uiDesigner.core.GridConstraints;
import lib.RequestBody;
import lib.ResponseBody;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class EditItemsScreen<T> extends Screen {

    protected abstract JPanel getContainerPanel();
    protected abstract JButton getCreateButton();
    protected abstract void createButtonActionListener();

    private Class<? extends T> clazz;
    private JPanel container;

    protected void start(Class<? extends T> clazz, JPanel container) {
        super.start();
        this.clazz = clazz;
        this.container = container;

        List<T> items = this.fetchItems(clazz);

        container.removeAll();

        this.getCreateButton().addActionListener(e -> this.createButtonActionListener());

        items.forEach(item -> {
            JButton button = new DefaultButton(getItemName(item), e -> setPanelData(item));
            container.add(button, new GridConstraints());
        });

        try {
            setPanelData(!items.isEmpty() ? items.get(0) : clazz.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        container.updateUI();
        container.revalidate();
        container.repaint();
    }

    protected List<T> fetchItems(Class<? extends T> clazz) {
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
            System.out.println("Failed to save item");
        }

        start(clazz, container);
    }

    protected void deleteItem(T item) {
        RequestBody request = new RequestBody();
        request.addField("item", item);

        ResponseBody response = Client.request("item/delete", request);
    }
}