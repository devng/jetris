package com.devng.jetris;
/* MainFrame created on 14.09.2006 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;

public class JetrisMainFrame extends JFrame {

    private static final int CELL_H = 24;

    private Font font;
    private JPanel playPanel;
    private JLabel score;
    private JLabel lines;
    private JLabel time;
    private JLabel[] statsF;
    private JLabel[] statsL;
    private JLabel levelLabel;
    private JLabel hiScoreLabel;
    private JPanel[][] cells;
    private TetrisGrid tg;
    private JPanel[][] next;
    private int nextX;
    private int nextY;
    private Figure f;
    private Figure fNext;
    private FigureFactory ff;
    private boolean isNewFigureDroped;
    private boolean isGameOver;
    private boolean isPause;
    private Color nextBg;
    private TimeThread tt;
    private KeyListener keyHandler;

    private JPanel about;

    //MENU
    private JMenuItem jetrisRestart;
    private JMenuItem jetrisPause;
    private JMenuItem jetrisHiScore;
    private JMenuItem jetrisExit;

    private JMenuItem helpAbout;
    private JMenuItem helpJetris;

    private HelpDialog helpDialog;

    private JPanel hiScorePanel;

    private class GridThread extends Thread {

        private int count = 0;

        public void run() {
            try {
                while (true) {
                    if (isGameOver || isPause) {
                        Thread.sleep(50);
                    } else {
                        if (isNewFigureDroped) {
                            isNewFigureDroped = false;
                            count = 0;
                            nextMove();
                            continue;
                        } else {
                            Thread.sleep(50);
                        }
                        count += 50;
                        if (count + 50 * tg.getLevel() >= 1100) {
                            count = 0;
                            nextY++;
                            nextMove();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class TimeThread extends Thread {

        private int hours;
        private int min;
        private int sec;

        private int count;

        private void incSec() {
            sec++;
            if (sec == 60) {
                sec = 0;
                min++;
            }
            if (min == 60) {
                min = 0;
                hours++;
            }
        }

        private void resetTime() {
            hours = min = sec = 0;
        }

        public void run() {
            try {
                while (true) {
                    Thread.sleep(50);
                    if (isGameOver) {
                        Graphics g = playPanel.getGraphics();
                        Font font = new Font(g.getFont().getFontName(), Font.BOLD, 24);
                        g.setFont(font);
                        g.drawString("GAME OVER", 47, 250);
                    } else if (isPause) {
                        time.setText("PAUSED");
                    } else if (count >= 1000) {
                        count = 0;
                        incSec();
                        time.setText(this.toString());
                    } else {
                        count += 50;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            if (hours < 10) {
                sb.append('0');
            }
            sb.append(hours);

            sb.append(':');

            if (min < 10) {
                sb.append('0');
            }
            sb.append(min);

            sb.append(':');

            if (sec < 10) {
                sb.append('0');
            }
            sb.append(sec);

            return sb.toString();
        }
    }

    public JetrisMainFrame() {
        super(GV.NAME + " " + GV.VERSION);

        SplashScreen sp = new SplashScreen();

        setIconImage(loadImage(GV.IMG_FOLDER + "jetris16x16.png"));

        keyHandler = new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                    moveLeft();
                } else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                    moveRight();
                } else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    moveDown();
                } else if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    rotation();
                } else if (code == KeyEvent.VK_SPACE) {
                    moveDrop();
                } /*else if(code == KeyEvent.VK_R) { //Only for the applet needed
                    restart();
                } else if(code == KeyEvent.VK_P) {
                    pause();
                } */
            }
        };
        addKeyListener(keyHandler);

        font = new Font("Dialog", Font.PLAIN, 12);
        tg = new TetrisGrid();
        ff = new FigureFactory();
        nextBg = new Color(238, 238, 238);

        initMenu();

        JPanel all = new JPanel(new BorderLayout());
        all.add(getStatPanel(), BorderLayout.WEST);
        all.add(getPlayPanel(), BorderLayout.CENTER);
        all.add(getMenuPanel(), BorderLayout.EAST);
        all.add(getCopyrightPanel(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(all, BorderLayout.CENTER);
        pack();
        this.setResizable(false);


        fNext = ff.getRandomFigure();
        dropNext();

        GridThread gt = new GridThread();
        tt = new TimeThread();
        gt.start();
        tt.start();

        addWindowFocusListener(new WindowFocusListener() {

            public void windowGainedFocus(WindowEvent arg0) {
            }

            public void windowLostFocus(WindowEvent arg0) {
                isPause = true;
            }
        });


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
        setVisible(true);
        sp.setVisible(false);
        sp.dispose();
    }

    private void initMenu() {

        MenuHandler mH = new MenuHandler();

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);

        JMenu mJetris = new JMenu();
        menu.add(mJetris);
        mJetris.setText("Game");
        mJetris.setMnemonic('G');
        {
            jetrisRestart = new JMenuItem("Restart");
            mJetris.add(jetrisRestart);
            setKeyAcceleratorMenu(jetrisRestart, 'R');
            jetrisRestart.addActionListener(mH);
            jetrisRestart.setMnemonic('R');

            jetrisPause = new JMenuItem("Pause");
            mJetris.add(jetrisPause);
            setKeyAcceleratorMenu(jetrisPause, 'P');
            jetrisPause.addActionListener(mH);
            jetrisPause.setMnemonic('P');

            mJetris.addSeparator();

            jetrisHiScore = new JMenuItem("HiScore...");
            mJetris.add(jetrisHiScore);
            setKeyAcceleratorMenu(jetrisHiScore, 'H');
            jetrisHiScore.addActionListener(mH);
            jetrisHiScore.setMnemonic('H');

            mJetris.addSeparator();

            jetrisExit = new JMenuItem("Exit");
            mJetris.add(jetrisExit);
            setKeyAcceleratorMenu(jetrisExit, KeyEvent.VK_ESCAPE);
            jetrisExit.addActionListener(mH);
            jetrisExit.setMnemonic('X');
        }

        JMenu mHelp = new JMenu();
        menu.add(mHelp);
        mHelp.setText("Help");
        mHelp.setMnemonic('H');
        {
            helpJetris = new JMenuItem("JETRIS Help");
            mHelp.add(helpJetris);
            setKeyAcceleratorMenu(helpJetris, KeyEvent.VK_F1);
            helpJetris.addActionListener(mH);
            helpJetris.setMnemonic('J');

            helpAbout = new JMenuItem("About");
            mHelp.add(helpAbout);
            helpAbout.addActionListener(mH);
            helpAbout.setMnemonic('A');
        }
    }

    private void setKeyAcceleratorMenu(JMenuItem mi, int keyCode) {
        KeyStroke ks = KeyStroke.getKeyStroke(keyCode, 0);
        mi.setAccelerator(ks);
    }

    private JPanel getPlayPanel() {
        playPanel = new JPanel();
        playPanel.setLayout(new GridLayout(20, 10));
        playPanel.setPreferredSize(new Dimension(10 * CELL_H, 20 * CELL_H));

        cells = new JPanel[20][10];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new JPanel();
                cells[i][j].setBackground(Color.WHITE);
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                playPanel.add(cells[i][j]);
            }
        }
        return playPanel;
    }

    private JPanel getMenuPanel() {
        JPanel r = new JPanel();
        BoxLayout rL = new BoxLayout(r, BoxLayout.Y_AXIS);
        r.setLayout(rL);
        r.setBorder(new EtchedBorder());
        Dimension ra = new Dimension(5, 0);
        next = new JPanel[4][4];
        JPanel nextP = new JPanel();
        nextP.setLayout(new GridLayout(4, 4));
        Dimension d = new Dimension(4 * 18, 4 * 18);
        nextP.setMinimumSize(d);
        nextP.setPreferredSize(d);
        nextP.setMaximumSize(d);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                next[i][j] = new JPanel();
                nextP.add(next[i][j]);
            }
        }

        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("NEXT:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        r.add(nextP);

        r.add(Box.createRigidArea(new Dimension(100, 10)));

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("HI-SCORE:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        hiScoreLabel = new JLabel("" + tg.hiScore[0].score);
        hiScoreLabel.setForeground(Color.RED);


        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(hiScoreLabel);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        r.add(Box.createVerticalStrut(5));

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("SCORE:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        score = new JLabel("0");
        score.setForeground(Color.BLUE);

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(score);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("LINES:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        lines = new JLabel("0");
        lines.setForeground(Color.BLUE);

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(lines);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("LEVEL:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        levelLabel = new JLabel("1");
        levelLabel.setForeground(Color.BLUE);

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(levelLabel);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(new JLabel("TIME:"));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        time = new JLabel("00:00:00");
        time.setForeground(Color.BLUE);

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(time);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        r.add(Box.createVerticalGlue());

        r.add(addHelpPanel("A or \u2190 - Left"));
        r.add(addHelpPanel("D or \u2192 - Right"));
        r.add(addHelpPanel("W or \u2191 - Rotate"));
        r.add(addHelpPanel("S or \u2193 - Down"));
        r.add(addHelpPanel("Space - Drop"));

        //BUTTONS
        r.add(Box.createRigidArea(new Dimension(0, 10)));

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        JButton restartBut = new JButton("Restart");
        restartBut.setToolTipText("Press 'R'");
        restartBut.setFocusable(false);
        restartBut.addKeyListener(keyHandler);
        restartBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                restart();
            }
        });
        d = new Dimension(90, 30);
        restartBut.setMinimumSize(d);
        restartBut.setPreferredSize(d);
        restartBut.setMaximumSize(d);
        jp.add(restartBut);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        r.add(Box.createRigidArea(new Dimension(0, 5)));

        jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        JButton pauseBut = new JButton("Pause");
        pauseBut.setToolTipText("Press 'P'");
        pauseBut.setFocusable(false);
        pauseBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                pause();
            }
        });
        pauseBut.setMinimumSize(d);
        pauseBut.setPreferredSize(d);
        pauseBut.setMaximumSize(d);
        jp.add(pauseBut);
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        return r;
    }

    private JPanel addHelpPanel(String help) {
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(new Dimension(5, 0)));
        JLabel jL = new JLabel(help);
        jL.setFont(font);
        jL.setForeground(Color.GRAY);
        jp.add(jL);
        jp.add(Box.createHorizontalGlue());
        return jp;
    }

    private JPanel getCopyrightPanel() {
        JPanel r = new JPanel(new BorderLayout());
        BoxLayout rL = new BoxLayout(r, BoxLayout.X_AXIS);
        r.setLayout(rL);
        r.setBorder(new EtchedBorder());
        r.add(Box.createRigidArea(new Dimension(80, 0)));

        JLabel jL = new JLabel("Copyright (c) 2006 - 2015 Nikolay G. Georgiev ");
        jL.setFont(font);
        r.add(jL);

        return r;
    }

    private JPanel getStatPanel() {
        int h = 12;
        JPanel r = new JPanel();
        BoxLayout rL = new BoxLayout(r, BoxLayout.Y_AXIS);
        r.setLayout(rL);
        r.setBorder(new EtchedBorder());

        JPanel[][] fig;
        JPanel figP, statFP;
        Dimension d = new Dimension(4 * h, 4 * h);
        Figure f;
        statsF = new JLabel[7];
        statsL = new JLabel[4];

        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(new Dimension(5, 0)));
        jp.add(new JLabel("STATISTICS: "));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);

        r.add(Box.createRigidArea(new Dimension(0, 5)));

        for (int k = 0; k < 7; k++) {
            fig = new JPanel[4][4];
            figP = new JPanel();
            statFP = new JPanel();
            statFP.setLayout(new BoxLayout(statFP, BoxLayout.LINE_AXIS));
            figP.setLayout(new GridLayout(4, 4));
            figP.setMinimumSize(d);
            figP.setPreferredSize(d);
            figP.setMaximumSize(d);

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    fig[i][j] = new JPanel();
                    fig[i][j].setBackground(nextBg);
                    figP.add(fig[i][j]);
                }
            }

            switch (k + 1) {
                case Figure.I:
                    f = new FigureI();
                    f.setOffset(2, 0);
                    break;
                case Figure.T:
                    f = new FigureT();
                    f.setOffset(1, 1);
                    break;
                case Figure.O:
                    f = new FigureO();
                    f.setOffset(1, 1);
                    break;
                case Figure.J:
                    f = new FigureJ();
                    f.setOffset(1, 1);
                    break;
                case Figure.L:
                    f = new FigureL();
                    f.setOffset(1, 1);
                    break;
                case Figure.S:
                    f = new FigureS();
                    f.setOffset(1, 1);
                    break;
                default:
                    f = new FigureZ();
                    f.setOffset(1, 1);
                    break;
            }

            for (int i = 0; i < 4; i++) {
                fig[f.arrY[i] + f.offsetY][f.arrX[i] + f.offsetX].setBackground(f.getColor());
                fig[f.arrY[i] + f.offsetY][f.arrX[i] + f.offsetX].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            statFP.add(figP);
            statFP.add(new JLabel("  X  "));

            statsF[k] = new JLabel("0");
            statsF[k].setForeground(Color.BLUE);
            statFP.add(statsF[k]);
            r.add(statFP);
        }

        r.add(Box.createRigidArea(new Dimension(100, 15)));

        for (int i = 0; i < statsL.length; i++) {
            statFP = new JPanel();
            statFP.setLayout(new BoxLayout(statFP, BoxLayout.LINE_AXIS));
            switch (i) {
                case 0:
                    statFP.add(new JLabel("  Single  X  "));
                    break;
                case 1:
                    statFP.add(new JLabel("Double  X  "));
                    break;
                case 2:
                    statFP.add(new JLabel("  Triple  X  "));
                    break;
                default:
                    statFP.add(new JLabel("  Tetris  X  "));
                    break;
            }
            statsL[i] = new JLabel("0");
            statsL[i].setForeground(Color.BLUE);
            statFP.add(statsL[i]);
            r.add(statFP);
            r.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        return r;
    }

    static Image loadImage(String imageName) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            return ImageIO.read(new BufferedInputStream(
                    classloader.getResourceAsStream(imageName)));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    private synchronized void nextMove() {
        f.setOffset(nextX, nextY);

        if (tg.addFigure(f)) {
            dropNext();
            f.setOffset(nextX, nextY);
            paintTG();
        } else {
            clearOldPosition();
        }
        paintNewPosition();

        if (isGameOver) {
            int tmp = tg.updateHiScore();
            if (tmp >= 0) {

                String s;

                do {
                    s = JOptionPane.showInputDialog(this, "Enter Your Name...\nMust be between 1 and 10 characters long", "New HiScore " + (tmp + 1) + ". Place", JOptionPane.PLAIN_MESSAGE);
                } while (s != null && (s.length() < 1 || s.length() > 10));

                if (s == null) {
                    s = "<empty>";
                }

                tg.saveHiScore(s, tmp);

                if (tmp == 0)
                    hiScoreLabel.setText("" + tg.hiScore[0].score);
            }
        }
    }

    private void clearOldPosition() {
        for (int j = 0; j < 4; j++) {
            cells[f.arrY[j] + f.offsetYLast][f.arrX[j] + f.offsetXLast].setBackground(Color.WHITE);
            cells[f.arrY[j] + f.offsetYLast][f.arrX[j] + f.offsetXLast].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
    }

    private void paintNewPosition() {
        for (int j = 0; j < 4; j++) {
            cells[f.arrY[j] + f.offsetY][f.arrX[j] + f.offsetX].setBackground(f.getColor());
            cells[f.arrY[j] + f.offsetY][f.arrX[j] + f.offsetX].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
    }

    private void paintTG() {
        int i = 0;
        Color c;
        for (int[] arr : tg.gLines) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] != 0) {
                    switch (arr[j]) {
                        case Figure.I:
                            c = Figure.COL_I;
                            break;
                        case Figure.T:
                            c = Figure.COL_T;
                            break;
                        case Figure.O:
                            c = Figure.COL_O;
                            break;
                        case Figure.J:
                            c = Figure.COL_J;
                            break;
                        case Figure.L:
                            c = Figure.COL_L;
                            break;
                        case Figure.S:
                            c = Figure.COL_S;
                            break;
                        default:
                            c = Figure.COL_Z;
                            break;
                    }
                    cells[i][j].setBackground(c);
                    cells[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                } else {
                    cells[i][j].setBackground(Color.WHITE);
                    cells[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                }
            }
            i++;
        }
    }

    private void showNext(Figure f) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                next[i][j].setBackground(nextBg);
                next[i][j].setBorder(BorderFactory.createEmptyBorder());
            }
        }

        for (int j = 0; j < f.arrX.length; j++) {
            next[f.arrY[j]][f.arrX[j]].setBackground(f.getColor());
            next[f.arrY[j]][f.arrX[j]].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
    }

    private void dropNext() {
        if (isGameOver) return;
        nextX = 4;
        nextY = 0;

        score.setText("" + tg.getScore());
        lines.setText("" + tg.getLines());
        levelLabel.setText(tg.getLevel() + " / 20");

        f = fNext;
        fNext = ff.getRandomFigure();
        showNext(fNext);

        isGameOver = tg.isGameOver(f);


        isNewFigureDroped = true;
        updateStats();
    }

    private void moveLeft() {
        if (isGameOver || isPause) return;
        if (nextX - 1 >= 0) {
            if (tg.isNextMoveValid(f, f.offsetX - 1, f.offsetY)) {
                nextX--;
                nextMove();
            }
        }
    }

    private void moveRight() {
        if (isGameOver || isPause) return;
        if (f.getMaxRightOffset() + 1 < 10) {
            if (tg.isNextMoveValid(f, f.offsetX + 1, f.offsetY)) {
                nextX++;
                nextMove();
            }
        }
    }

    private synchronized void moveDown() {
        if (isGameOver || isPause) return;
        nextY++;
        nextMove();
    }

    private synchronized void moveDrop() {
        if (isGameOver || isPause) return;

        f.offsetYLast = f.offsetY;
        f.offsetXLast = f.offsetX;
        clearOldPosition();

        while (tg.isNextMoveValid(f, f.offsetX, f.offsetY)) {
            f.setOffset(f.offsetX, f.offsetY + 1);
        }


        tg.addFigure(f);
        paintTG();
        dropNext();
        nextMove();
    }

    private synchronized void rotation() {
        if (isGameOver || isPause) return;
        for (int j = 0; j < f.arrX.length; j++) {
            cells[f.arrY[j] + f.offsetY][f.arrX[j] + f.offsetX].setBackground(Color.WHITE);
            cells[f.arrY[j] + f.offsetY][f.arrX[j] + f.offsetX].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
        f.rotationRight();
        if (!tg.isNextMoveValid(f, f.offsetX, f.offsetY)) {
            f.rotationLeft();
        }
        nextMove();
    }

    private synchronized void pause() {
        isPause = !isPause;
    }

    private void restart() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                tg.gLines.get(i)[j] = 0;
                cells[i][j].setBackground(Color.WHITE);
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }
        }
        ff.resetCounts();
        isGameOver = false;
        isPause = false;
        fNext = ff.getRandomFigure();
        tt.resetTime();
        time.setText("00:00:00");
        tg.resetStats();
        dropNext();
        nextMove();
    }

    private void updateStats() {
        for (int i = 0; i < statsF.length; i++) {
            statsF[i].setText("" + ff.getCounts()[i]);
        }

        for (int i = 0; i < statsL.length; i++) {
            statsL[i].setText("" + tg.getDropLines()[i]);
        }
    }

    private void doHelp() {
        if (helpDialog == null) helpDialog = new HelpDialog(this);
        helpDialog.show();
    }

    private void doAbout() {
        if (about == null) setAboutPanel();
        JOptionPane.showMessageDialog(this, about, "ABOUT",
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon(loadImage(GV.IMG_FOLDER + "jetris.png")));
    }

    private void setAboutPanel() {
        about = new JPanel();
        about.setLayout(new BoxLayout(about, BoxLayout.Y_AXIS));
        JLabel jl = new JLabel("<HTML><B>" + GV.NAME + " " + GV.VERSION + "</B> Copyright (c) 2006 - 2015 Nikolay G. Georgiev</HTML>");
        jl.setFont(font);
        about.add(jl);
        about.add(Box.createVerticalStrut(10));

        jl = new JLabel("WEB PAGE:");
        jl.setFont(font);
        about.add(jl);
        HTMLLink hl = new HTMLLink(GV.WEB_PAGE, false);
        hl.setFont(font);
        about.add(hl);

        about.add(Box.createVerticalStrut(20));

        jl = new JLabel("<HTML>This program is released under the Mozilla Public License 2.0 .<BR> A copy of this is included with your copy of JETRIS<BR>and can also be found at:</HTML>");
        jl.setFont(font);
        about.add(jl);
        about.add(jl);
        hl = new HTMLLink("https://www.mozilla.org/MPL/2.0/", false);
        hl.setFont(font);
        about.add(hl);
    }

    private void showHiScore() {
        setHiScorePanel();

        JOptionPane.showMessageDialog(this, hiScorePanel, "HI SCORE",
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon(loadImage(GV.IMG_FOLDER + "jetris32x32.png")));

        hiScorePanel = null;
    }


    private void setHiScorePanel() {
        hiScorePanel = new JPanel(new BorderLayout());

        String[] colNames = {"Place", "Points", "Lines", "Name"};
        String[][] data = new String[tg.hiScore.length + 1][colNames.length];
        data[0] = colNames;
        for (int i = 0; i < tg.hiScore.length; i++) {
            data[i + 1] = new String[colNames.length];
            data[i + 1][0] = (i + 1) + ".";
            data[i + 1][1] = ("" + tg.hiScore[i].score);
            data[i + 1][2] = ("" + tg.hiScore[i].lines);
            data[i + 1][3] = ("" + tg.hiScore[i].name);
        }

        JTable table = new JTable(data, colNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setBackground(new Color(230, 255, 255));
        table.setEnabled(false);

        hiScorePanel.add(table, BorderLayout.CENTER);
    }

    private class MenuHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                JMenuItem tmp = (JMenuItem) e.getSource();
                if (tmp == jetrisRestart) {
                    restart();
                } else if (tmp == jetrisPause) {
                    pause();
                } else if (tmp == jetrisHiScore) {
                    showHiScore();
                } else if (tmp == jetrisExit) {
                    System.exit(0);
                } else if (tmp == helpJetris) {
                    doHelp();
                } else if (tmp == helpAbout) {
                    doAbout();
                }
            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
        }
    }
}
