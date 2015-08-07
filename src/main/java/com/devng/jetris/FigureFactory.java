package com.devng.jetris;

import java.util.Random;

/* FigureFactory created on 14.09.2006 */

public class FigureFactory {

    private final Random r;
    private int[] counts;
    private int lastLastOne;
    private int lastOne;

    FigureFactory() {
        r = new Random();
        counts = new int[7];
    }

    Figure getRandomFigure() {
        Figure f;
        int i = r.nextInt(7);
        while (lastLastOne == lastOne && lastOne == i + 1) {
            i = r.nextInt(7);
        }
        switch (i) {
            case 0:
                f = new FigureI();
                break;
            case 1:
                f = new FigureT();
                break;
            case 2:
                f = new FigureO();
                break;
            case 3:
                f = new FigureL();
                break;
            case 4:
                f = new FigureJ();
                break;
            case 5:
                f = new FigureS();
                break;
            default:
                f = new FigureZ();
                break;
        }
        lastLastOne = lastOne;
        lastOne = i + 1;
        counts[i]++;

        i = r.nextInt(4);

        for (int j = 0; j < i; j++) {
            f.rotationRight();
        }

        return f;
    }

    protected int[] getCounts() {
        return counts;
    }

    protected void resetCounts() {
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 0;
        }
    }
}
