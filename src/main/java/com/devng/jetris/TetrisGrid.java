package com.devng.jetris;

import com.devng.jetris.io.HiScore;

import javax.swing.*;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TetrisGrid implements Serializable {

    List<int[]> gLines;
    private int lines;
    private int score;
    private int[] dropLines;
    private int level;
    HiScore[] hiScore;

    TetrisGrid() {
        gLines = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            gLines.add(new int[10]);
        }
        lines = score = 0;
        dropLines = new int[4];

        try {
            hiScore = HiScore.load(GV.DAT_FILE);
        } catch (Exception e) {
            hiScore = new HiScore[3];
            for (int i = 0; i < hiScore.length; i++) {
                hiScore[i] = new HiScore();
                hiScore[i].name = "<empty>";
            }
            File f = new File(GV.DAT_FILE);
            try {
                HiScore.write(hiScore, f);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Could not load HiScore!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    boolean addFigure(Figure f) {
        for (int j = 0; j < f.arrX.length; j++) {
            if (f.arrY[j] + f.offsetY >= 20) {
                f.setOffset(f.offsetXLast, f.offsetYLast);
                addFigureToGrid(f);
                eliminateLines();
                return true;
            }
            if (gLines.get(f.arrY[j] + f.offsetY)[f.arrX[j] + f.offsetX] != 0) {
                f.setOffset(f.offsetXLast, f.offsetYLast);
                addFigureToGrid(f);
                eliminateLines();
                return true;
            }
        }
        return false;
    }

    boolean isNextMoveValid(Figure f, int xOffset, int yOffset) {
        boolean b = true;
        try {
            for (int j = 0; j < f.arrX.length; j++) {
                if (gLines.get(f.arrY[j] + yOffset)[f.arrX[j] + xOffset] != 0) {
                    b = false;
                }
            }
            return b;
        } catch (Exception e) {
            return false;
        }
    }

    private void addFigureToGrid(Figure f) {
        for (int j = 0; j < f.arrX.length; j++) {
            gLines.get(f.arrY[j] + f.offsetY)[f.arrX[j] + f.offsetX] = f.getGridVal();
        }
    }

    private void eliminateLines() {
        int lines = 0;
        for (Iterator iter = gLines.iterator(); iter.hasNext(); ) {
            int[] el = (int[]) iter.next();
            boolean isFull = true;
            for (int j = 0; j < 10; j++) {
                if (el[j] == 0) isFull = false;
            }
            if (isFull) {
                iter.remove();
                lines++;
            }
        }

        switch (lines) {
            case 1:
                score += 100 + 5 * level;
                break;
            case 2:
                score += 400 + 20 * level;
                break;
            case 3:
                score += 900 + 45 * level;
                break;
            case 4:
                score += 1600 + 80 * level;
                break;
        }

        this.lines += lines;

        level = this.lines / 10;
        //level = 20;
        if (level > 20) level = 20;

        if (lines > 0) {
            dropLines[lines - 1]++;
        }

        for (int i = 0; i < lines; i++) {
            gLines.add(0, new int[10]);
        }
    }

    boolean isGameOver(Figure f) {

        return !isNextMoveValid(f, 4, 0);
    }

    int getLevel() {
        return level;
    }

    int getLines() {
        return lines;
    }

    int getScore() {
        return score;
    }

    int[] getDropLines() {
        return dropLines;
    }

    void resetStats() {
        lines = score = level = 0;
        for (int i = 0; i < dropLines.length; i++) {
            dropLines[i] = 0;
        }
    }

    int updateHiScore() {
        for (int i = 0; i < hiScore.length; i++) {
            HiScore s = hiScore[i];
            if ((s.score < score) ||
                    ((s.score == score) && (s.lines >= lines))) {
                //Stack the HiScore
                switch (i) {
                    case 0:
                        s = hiScore[1];
                        hiScore[1] = hiScore[0];
                        hiScore[2] = s;
                        s = new HiScore();
                        hiScore[0] = s;
                        break;
                    case 1:
                        hiScore[2] = s;
                        s = new HiScore();
                        hiScore[1] = s;
                        break;
                }
                s.score = score;
                s.lines = lines;
                return i;
            }
        }
        return -1;
    }

    void saveHiScore(String Name, int pos) {
        File f = new File(GV.DAT_FILE);
        try {
            hiScore[pos].name = Name;
            HiScore.write(hiScore, f);
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Could not save HiScore!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (int[] arr : gLines) {
            for (int j = 0; j < arr.length; j++) {
                sb.append(arr[j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
