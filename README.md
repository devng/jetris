# Jetris - A Java-Based Tetris Clone

Jetris is a Java-based Tetris clone for the desktop. Jetris is focused on a fast gaming, so a game usually takes about 10 min.

![](http://jetris.sourceforge.net/img/screen1.png)

### This Readme includes:

1. System Requirements
2. How to Build JETRIS
3. Running JETRIS on Your System
4. How to Play
5. Scoring System
6. Saving Your Old HiScores After Version Update

1. System Requirements
----------------------

JETRIS is written in the JAVA programming language, this means that it can be run on any Operating System which has JAVA Runtime Environment (JRE).

You need JRE 1.6.0 or above. You can download JRE for free at www.java.com

2. How to Build JETRIS
----------------------
If you want to build JETRIS on your local machine you need to install the Java Development Kit (JDK). You need JDK 1.6.0 or above.

JETRIS uses the Gradle build system, you can build the project locally just by typing the following in the console:

```
./gradlew build
```

On Windows use:
```
./gradlew.bat build
```

The generated jar file can be found in the folder _build/libs_

3. Running JETRIS on Your System
--------------------------------

To start JETRIS try one of the following options: 

* Double click on the JAR File to start JETRIS. If this didn't work, then you didn't associate your JAR Files with your JRE. 

* Double click on JETRIS.bat for Windows users or on JETRIS for Linux/OS X users.

* Open the console go to your JETRIS folder and type: 
```
java -jar JETRIS.jar
```

3. How to Play
--------------

Use the following keys to play JETRIS:

* A or Left Arrow - Move the figure to left
* D or Right Arrow - Move the figure to right
* W or Up Arrow - Rotate the figure
* S or Down Arrow - Move the figure quick down
* Space - Drop the figure immediately
* P or 'Pause' Button - Pause
* R or 'Restart' Button - Restart
* H - View HiScore
* Esc - Exit

4. Scoring System
-----------------

* Clearing 1 Line at once, gives You 100 points + 5 x the current level
* Clearing 2 Line at once, gives You 400 points + 20 x the current level
* Clearing 3 Line at once, gives You 900 points + 45 x the current level
* Clearing 4 Line at once, gives You 1600 points + 80 x the current level

### For example: 

The current level is 20 (the highest level) and You clear 4 Lines at once, then You get 1600 + 80 x 20 = 2 x 1600 = 3200. So on level 20 you are making twice as much points as on level 0.

5. Saving Your Old HiScores After Version Update
------------------------------------------------

Copy the old JETRIS.dat File to your new version of Jetris folder. 


