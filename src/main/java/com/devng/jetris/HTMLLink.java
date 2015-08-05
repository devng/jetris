/* HTMLLink created on 19.09.2006 */
package com.devng.jetris;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class HTMLLink extends JLabel implements MouseListener {

    private final String url;

    private final boolean useDesktopApp;

    private final Desktop desktop;

    public HTMLLink(String url, boolean isMail) {
        super(url);
        this.url = isMail ? "mailto:" + url : url;

        this.desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        final Desktop.Action action = isMail ? Desktop.Action.MAIL : Desktop.Action.BROWSE;
        useDesktopApp = desktop != null && desktop.isSupported(action);

        if (useDesktopApp) {
            setToolTipText(url);
        } else {
            setToolTipText("Copy link to Clipboard");
        }
        setForeground(Color.BLUE);
        addMouseListener(this);
    }

    private void startDesktopApp() {
        try {
            desktop.browse(new URL(url).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyToClipboard() {
        Clipboard c = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = new StringSelection(url);

        c.setContents(t, new ClipboardOwner() {
            public void lostOwnership(Clipboard c, Transferable t) {
            }
        });
    }

    public void mouseClicked(MouseEvent arg0) {
        if (useDesktopApp) {
            startDesktopApp();
        } else {
            copyToClipboard();
        }
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {
        setForeground(Color.RED);
    }

    public void mouseExited(MouseEvent arg0) {
        setForeground(Color.BLUE);
    }
}
