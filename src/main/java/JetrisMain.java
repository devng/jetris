import com.devng.jetris.JetrisMainFrame;

import javax.swing.*;

public class JetrisMain {

    public static void main(String[] args) {
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if(os.startsWith("mac")) {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new JetrisMainFrame();
    }
}
