package client;

import client.ui.Screen;
import client.ui.WelcomeScreen;

import javax.swing.JFrame;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

public class ScreenManager {
    private static JFrame frame;
    private static final Stack<Class<?extends Screen>> visited = new Stack<>();

    public static void start(Object[] ...args) {
        frame = new JFrame();
        ScreenManager.render(WelcomeScreen.class);
    }
    public static void render(Class<? extends Screen> screenClass) {
        try {
            Constructor<? extends Screen> constructor = screenClass.getDeclaredConstructor();
            Screen screen = constructor.newInstance();
            screen.start();
            frame.setContentPane(screen.getPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);

            visited.add(screenClass);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void goBack() {
        render(visited.pop());
    }

    public static void exit(){
        System.exit(0);
    }
}