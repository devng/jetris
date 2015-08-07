# JETRIS - A Java-Based Tetris Clone

## TL;DR

JETRIS is a Java-based Tetris clone for the desktop. JETRIS is focused on a fast gaming, so a game usually takes about 10 min.

![](screenshot.png?raw=true)

### This Readme includes:

1. System Requirements
2. How to Build JETRIS
  * How to build an Windows EXE
  * How to build a Mac OS APP and DMG
  * How to extend JETRIS
3. Running JETRIS on Your System
4. How to Play
5. Scoring System
6. Saving Your Old HiScores

1. System Requirements
----------------------

JETRIS is written in the JAVA programming language, this means that it can be run on any Operating System which has JAVA Runtime Environment (JRE).

You need JRE 1.7.0 or above. You can download JRE for free at www.java.com

2. How to Build JETRIS
----------------------
If you want to build JETRIS on your local machine you need to install the Java Development Kit (JDK). You need JDK 1.7.0 or above.

JETRIS uses the Gradle build system, you can build the project locally just by typing the following in the console:

```
./gradlew build
```

On Windows use:
```
./gradlew.bat build
```

The generated jar file can be found in the folder _build/libs_.

### How to build an Windows EXE
The Gradle build script provides a way to build an Windows executable. You can even do this on a non-Windows machine. For that purpose you need an external application called
[Launch4j](http://launch4j.sourceforge.net/). Download it and install it on your local machine. The version used to test this feature is 3.8, but it will probably work with
other versions too. In addition to this, you need to create an environment variable called `LAUNCH4J_HOME` which points to the folder where you have installed the application.

After that you can build the exe file by running:
```
./gradlew launch4j
```

The generated exe file can be found in the folder _build/launch4j_.

### How to build a Mac OS APP and DMG
The JETRIS Gradle build script uses [MacAppBundle Plugin](https://github.com/crotwell/gradle-macappbundle) to generate Mac OS X app packages _and_ a _dmg_ distribution file.
This is done automatically with each Gradle build.

The generated app package can be found in the folder _build/macApp_ and the dmg file in _build/distributions_.

### How to extend JETRIS
If you want to import the project in an IDE such as Eclipse or IntelliJ IDEA then Gradle provides a way to generate all the necessary project files.

Generate Eclipse project:
```
./gradlew eclipse
```

Generate IntelliJ IDEA project:
```
./gradlew idea
```

3. Running JETRIS on Your System
--------------------------------

To start JETRIS try one of the following options: 

* Double click on the JAR File to start JETRIS. If this didn't work, then you didn't associate your JAR Files with your JRE.

* For Windows users we also provide an exe file, which ist just a wrapper for the JAR file. Double click on the exe file to start the application.

* Double click on `JETRIS.bat` for Windows users or on `JETRIS` for Linux users. This will start the application, but only if you have built it with Gradle first.

* Open the console go to your JETRIS folder and type: 
```
java -jar jetris-1.2.jar
```

4. How to Play
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

5. Scoring System
-----------------

* Clearing 1 Line at once, gives You 100 points + 5 x the current level
* Clearing 2 Line at once, gives You 400 points + 20 x the current level
* Clearing 3 Line at once, gives You 900 points + 45 x the current level
* Clearing 4 Line at once, gives You 1600 points + 80 x the current level

### For example: 

The current level is 20 (the highest level) and You clear 4 Lines at once, then You get 1600 + 80 x 20 = 2 x 1600 = 3200. So on level 20 you are making twice as much points as on level 0.

6. Saving Your Old HiScores
---------------------------

Copy the old `JETRIS.DAT` File to your new version of JETRIS folder.
