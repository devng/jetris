/* HiScore created on 23.09.2006 */
package net.sourceforge.jetris.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;


public class HiScore implements Serializable {
    
    public int score;
    public int lines;
    public int h, m, s; //TIME Not Used yet
    public String name;
    
    public static void write(HiScore[] score, File file) 
    throws IOException {
        
        FileOutputStream fs = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fs);
        for (int i = 0; i < score.length; i++) {
            os.writeObject(score[i]);
        }
        
        os.close();
        os = null; fs = null;
    }
    
    public static HiScore[] load(String file) 
    throws ClassNotFoundException, IOException{
        HiScore[] r = new HiScore[3];
        FileInputStream fs = new FileInputStream(file);
        ObjectInputStream is = new ObjectInputStream(fs);
        for (int i = 0; i < r.length; i++) {
            HiScore res = (HiScore)is.readObject();
            r[i] = res;
        }
        
        is.close();
        is = null; fs = null;
        return r;
    }
}
