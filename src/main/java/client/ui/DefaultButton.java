package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultButton extends JButton {
    private Color backgroundColor = new Color(0x7F5AF0);
    private final Color textColor = new Color(0xFFFFFE);
    private final Font font = new Font("Inconsolata", Font.BOLD, 12);

    public DefaultButton(String buttonText, ActionListener actionListener) {
        super(buttonText);
        addActionListener(actionListener);
        applyStyles();
    }

    private void applyStyles() {
        setBackground(backgroundColor);
        setForeground(textColor);
        setFont(font);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
