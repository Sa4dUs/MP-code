package client;

import javax.swing.JFrame;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ScreenManager {

    private static JFrame frame;
    public static void start() {
        frame = new JFrame();
        ScreenManager.render(WelcomeScreen.class);
    }
    public static void render(Class<? extends Screen> screenClass) {
        try {
            Constructor<? extends Screen> constructor = screenClass.getDeclaredConstructor();
            Screen screen = constructor.newInstance();
            frame.setContentPane(screen.getPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}