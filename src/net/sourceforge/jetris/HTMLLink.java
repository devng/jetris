/* HTMLLink created on 19.09.2006 */
package net.sourceforge.jetris;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.datatransfer.*;

import javax.swing.JLabel;

public class HTMLLink extends JLabel implements MouseListener{
    
    private String url;
    
    private boolean copyToClipBoard;
    
    private boolean startBrowser;
    
    public HTMLLink(String url, boolean isMail) {
        super(url);
        this.url = isMail ? "mailto:"+url : url;
        
        if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            copyToClipBoard = false;
            startBrowser = true;
            setToolTipText(url);
        } else {
            copyToClipBoard = true;
            startBrowser = false;
            setToolTipText("Copy link to Clipboard");
        }
        setForeground(Color.BLUE);
        addMouseListener(this);
    }
    
    private void startBrowser() {
        String cmdLine = "cmd.exe /c start " + url;
        try {
            Process p = Runtime.getRuntime().exec(cmdLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void copyToClipboard() {
        Clipboard c = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = new StringSelection(url);

        c.setContents(t, new ClipboardOwner() {
            public void lostOwnership(Clipboard c, Transferable t) {}
        });
    }

    public void mouseClicked(MouseEvent arg0) {
        if(copyToClipBoard)
            copyToClipboard();
        if(startBrowser)
            startBrowser();
    }

    public void mousePressed(MouseEvent arg0) {}

    public void mouseReleased(MouseEvent arg0) {}

    public void mouseEntered(MouseEvent arg0) {
        setForeground(Color.RED);
    }
    
    public void mouseExited(MouseEvent arg0) {
        setForeground(Color.BLUE);
    }
}
