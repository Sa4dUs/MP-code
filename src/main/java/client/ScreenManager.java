package client;

import client.Screen;
import client.WelcomeScreen;

import javax.swing.*;

public class ScreenManager {

    private static JFrame frame;
    public static void start() {
        frame = new JFrame();
        Screen screen = new WelcomeScreen();
        ScreenManager.render(screen);
    }
    public static void render(Screen screen) {
        frame.setContentPane(screen.getPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}