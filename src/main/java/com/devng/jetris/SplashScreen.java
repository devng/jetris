package com.devng.jetris;

import javax.swing.*;
import java.awt.*;

/* SplashScreen created on 28.09.2006 */

public class SplashScreen extends JWindow {

    private static final int W = 567;
    private static final int H = 191;

    SplashScreen() {
        super();
        getContentPane().add(new JLabel(
                        new ImageIcon(JetrisMainFrame.loadImage(GV.IMG_FOLDER + "splash.png"))),
                BorderLayout.CENTER);
        setSize(new Dimension(W, H));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - W / 2, screenSize.height / 2 - H / 2);
        setVisible(true);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }
    }
}
