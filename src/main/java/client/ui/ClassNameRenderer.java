package client.ui;

import javax.swing.*;

import java.awt.*;

import static javax.swing.text.StyleConstants.setBackground;
import static javax.swing.text.StyleConstants.setForeground;

public class ClassNameRenderer extends JLabel implements ListCellRenderer<Class<?>> {
    public ClassNameRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Class<?>> list, Class<?> value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value != null) {
            setText(value.getSimpleName());
        } else {
            setText("");
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}