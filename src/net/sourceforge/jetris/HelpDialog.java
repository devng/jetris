/* HelpDialog created on 28.09.2006 */
package net.sourceforge.jetris;

import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import res.ResClass;

public class HelpDialog {
    
    private JFrame owner;
    private JScrollPane scroll;
    private static final String NAME = "JETRIS HELP";
    
    HelpDialog(JFrame owner) {
        
        this.owner = owner;
        
        try {
            JEditorPane doc = new JEditorPane(ResClass.class.getResource("help.html"));
            doc.setEditable(false);
            this.scroll = new JScrollPane(doc);
            doc.setPreferredSize(new Dimension(400,450));   
        }
        catch(java.io.IOException e) {
            e.printStackTrace(System.out);
        }
    }
    
    void show() {
        JOptionPane.showMessageDialog(owner,scroll, NAME,JOptionPane.PLAIN_MESSAGE);
    }
}
