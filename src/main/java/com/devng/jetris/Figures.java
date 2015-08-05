package com.devng.jetris;

import java.awt.*;

class FigureI extends Figure {

    protected FigureI() {
        super(new int[]{0, 0, 0, 0},
                new int[]{0, 1, 2, 3});
    }

    protected void rotationRight() {
        int[] tmp = arrX;
        arrX = arrY;
        arrY = tmp;
    }

    protected void rotationLeft() {
        rotationRight();
    }


    protected int getGridVal() {
        return I;
    }

    protected Color getGolor() {
        return COL_I;
    }
}

class FigureT extends Figure {

    private int[][] rotations;

    private int curRotation;

    protected FigureT() {
        super(new int[]{0, 1, 1, 2},
                new int[]{0, 0, 1, 0});

        rotations = new int[8][4];
        rotations[0] = new int[]{0, 1, 1, 2};
        rotations[1] = new int[]{0, 0, 1, 0};

        rotations[2] = new int[]{0, 1, 1, 1};
        rotations[3] = new int[]{1, 0, 1, 2};

        rotations[4] = new int[]{0, 1, 1, 2};
        rotations[5] = new int[]{1, 0, 1, 1};

        rotations[6] = new int[]{0, 0, 0, 1};
        rotations[7] = new int[]{0, 1, 2, 1};
        curRotation = 0;
    }

    protected void rotationRight() {
        if (curRotation == 0) {
            arrX = rotations[2];
            arrY = rotations[3];
            curRotation = 1;
        } else if (curRotation == 1) {
            arrX = rotations[4];
            arrY = rotations[5];
            curRotation = 2;
        } else if (curRotation == 2) {
            arrX = rotations[6];
            arrY = rotations[7];
            curRotation = 3;
        } else if (curRotation == 3) {
            arrX = rotations[0];
            arrY = rotations[1];
            curRotation = 0;
        }
    }

    protected void rotationLeft() {
        if (curRotation == 0) {
            arrX = rotations[6];
            arrY = rotations[7];
            curRotation = 3;
        } else if (curRotation == 3) {
            arrX = rotations[4];
            arrY = rotations[5];
            curRotation = 2;
        } else if (curRotation == 2) {
            arrX = rotations[2];
            arrY = rotations[3];
            curRotation = 1;
        } else if (curRotation == 1) {
            arrX = rotations[0];
            arrY = rotations[1];
            curRotation = 0;
        }
    }


    protected int getGridVal() {
        return T;
    }

    protected Color getGolor() {
        return COL_T;
    }
}

class FigureO extends Figure {

    protected FigureO() {
        super(new int[]{0, 0, 1, 1},
                new int[]{0, 1, 0, 1});
    }

    protected void rotationRight() {
    }

    protected void rotationLeft() {
    }


    protected int getGridVal() {
        return O;
    }

    protected Color getGolor() {
        return COL_O;
    }
}

class FigureL extends Figure {

    private int[][] rotations;

    private int curRotation;

    protected FigureL() {
        super(new int[]{0, 0, 0, 1},
                new int[]{0, 1, 2, 2});

        rotations = new int[8][4];
        rotations[0] = new int[]{0, 0, 0, 1};
        rotations[1] = new int[]{0, 1, 2, 2};

        rotations[2] = new int[]{0, 0, 1, 2};
        rotations[3] = new int[]{0, 1, 0, 0};

        rotations[4] = new int[]{0, 1, 1, 1};
        rotations[5] = new int[]{0, 0, 1, 2};

        rotations[6] = new int[]{0, 1, 2, 2};
        rotations[7] = new int[]{1, 1, 0, 1};
        curRotation = 0;
    }

    protected void rotationRight() {
        if (curRotation == 0) {
            arrX = rotations[2];
            arrY = rotations[3];
            curRotation = 1;
        } else if (curRotation == 1) {
            arrX = rotations[4];
            arrY = rotations[5];
            curRotation = 2;
        } else if (curRotation == 2) {
            arrX = rotations[6];
            arrY = rotations[7];
            curRotation = 3;
        } else if (curRotation == 3) {
            arrX = rotations[0];
            arrY = rotations[1];
            curRotation = 0;
        }
    }

    protected void rotationLeft() {
        if (curRotation == 0) {
            arrX = rotations[7];
            arrY = rotations[6];
            curRotation = 3;
        } else if (curRotation == 3) {
            arrX = rotations[4];
            arrY = rotations[5];
            curRotation = 2;
        } else if (curRotation == 2) {
            arrX = rotations[2];
            arrY = rotations[3];
            curRotation = 1;
        } else if (curRotation == 1) {
            arrX = rotations[0];
            arrY = rotations[1];
            curRotation = 0;
        }
    }


    protected int getGridVal() {
        return L;
    }

    protected Color getGolor() {
        return COL_L;
    }
}

class FigureJ extends Figure {

    private int[][] rotations;

    private int curRotation;

    protected FigureJ() {
        super(new int[]{0, 1, 1, 1},
                new int[]{2, 0, 1, 2});

        rotations = new int[8][4];
        rotations[0] = new int[]{0, 1, 1, 1};
        rotations[1] = new int[]{2, 0, 1, 2};

        rotations[2] = new int[]{0, 0, 1, 2};
        rotations[3] = new int[]{0, 1, 1, 1};

        rotations[4] = new int[]{0, 0, 0, 1};
        rotations[5] = new int[]{0, 1, 2, 0};

        rotations[6] = new int[]{0, 1, 2, 2};
        rotations[7] = new int[]{0, 0, 0, 1};
        curRotation = 0;
    }

    protected void rotationRight() {
        if (curRotation == 0) {
            arrX = rotations[2];
            arrY = rotations[3];
            curRotation = 1;
        } else if (curRotation == 1) {
            arrX = rotations[4];
            arrY = rotations[5];
            curRotation = 2;
        } else if (curRotation == 2) {
            arrX = rotations[6];
            arrY = rotations[7];
            curRotation = 3;
        } else if (curRotation == 3) {
            arrX = rotations[0];
            arrY = rotations[1];
            curRotation = 0;
        }
    }

    protected void rotationLeft() {
        if (curRotation == 0) {
            arrX = rotations[7];
            arrY = rotations[6];
            curRotation = 3;
        } else if (curRotation == 3) {
            arrX = rotations[4];
            arrY = rotations[5];
            curRotation = 2;
        } else if (curRotation == 2) {
            arrX = rotations[2];
            arrY = rotations[3];
            curRotation = 1;
        } else if (curRotation == 1) {
            arrX = rotations[0];
            arrY = rotations[1];
            curRotation = 0;
        }
    }

    protected int getGridVal() {
        return J;
    }

    protected Color getGolor() {
        return COL_J;
    }
}

class FigureS extends Figure {

    private int[][] rotations;

    private int curRotation;

    protected FigureS() {
        super(new int[]{0, 1, 1, 2},
                new int[]{1, 0, 1, 0});

        rotations = new int[4][4];
        rotations[0] = new int[]{0, 1, 1, 2};
        rotations[1] = new int[]{1, 0, 1, 0};
        rotations[2] = new int[]{0, 0, 1, 1};
        rotations[3] = new int[]{0, 1, 1, 2};
        curRotation = 0;
    }

    protected void rotationRight() {
        if (curRotation == 0) {
            arrX = rotations[2];
            arrY = rotations[3];
            curRotation = 1;

        } else {
            arrX = rotations[0];
            arrY = rotations[1];
            curRotation = 0;
        }
    }

    protected void rotationLeft() {
        rotationRight();
    }

    protected int getGridVal() {
        return S;
    }

    protected Color getGolor() {
        return COL_S;
    }
}

class FigureZ extends Figure {

    private int[][] rotations;

    private int curRotation;

    protected FigureZ() {
        super(new int[]{0, 1, 1, 2},
                new int[]{0, 0, 1, 1});

        rotations = new int[4][4];
        rotations[0] = new int[]{0, 1, 1, 2};
        rotations[1] = new int[]{0, 0, 1, 1};
        rotations[2] = new int[]{1, 0, 1, 0};
        rotations[3] = new int[]{0, 1, 1, 2};
        curRotation = 0;
    }

    protected void rotationRight() {
        if (curRotation == 0) {
            arrX = rotations[2];
            arrY = rotations[3];
            curRotation = 1;

        } else {
            arrX = rotations[0];
            arrY = rotations[1];
            curRotation = 0;
        }
    }

    protected void rotationLeft() {
        rotationRight();
    }

    protected int getGridVal() {
        return Z;
    }

    protected Color getGolor() {
        return COL_Z;
    }
}
