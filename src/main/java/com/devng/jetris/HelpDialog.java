/* HelpDialog created on 28.09.2006 */
package com.devng.jetris;

import javax.swing.*;
import java.awt.*;

public class HelpDialog {

    private JFrame owner;
    private JScrollPane scroll;
    private static final String NAME = "JETRIS HELP";

    HelpDialog(JFrame owner) {

        this.owner = owner;

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            JEditorPane doc = new JEditorPane(classloader.getResource("help.html"));
            doc.setEditable(false);
            this.scroll = new JScrollPane(doc);
            doc.setPreferredSize(new Dimension(400, 450));
        } catch (java.io.IOException e) {
            e.printStackTrace(System.out);
        }
    }

    void show() {
        JOptionPane.showMessageDialog(owner, scroll, NAME, JOptionPane.PLAIN_MESSAGE);
    }
}
