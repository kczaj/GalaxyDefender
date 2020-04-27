package pl.edu.pw.iem.galaxydefender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import pl.edu.pw.iem.galaxydefender.gui.GameScreen;

public class Config {

    public static double blocksVelocity;
    public static int primarySize;
    public static double blocksAcceleration;
    public static int blocksAccelerationFrequency;
    public static int coordinateSets[][];
    public static int lasersCount;

    public Config() throws IOException {
        load();
        loadBlocksConfig();
    }

    private void load() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream("/config")));
        String line;
        String[] parameters = null;
        while ((line = br.readLine()) != null) {
            parameters = line.split(";");
            if (parameters.length != 5) {
                throw new NumberFormatException("Nieprawidłowa liczba parametrów w pliku konfiguracyjnym!");
            }
        }
        if (Double.parseDouble(parameters[0]) > 15 || Double.parseDouble(parameters[0]) < 0) {
            throw new NumberFormatException("Nieprawidłowa wartość prędkości!");
        } else {
            blocksVelocity = Double.parseDouble(parameters[0]);
        }
        if (Integer.parseInt(parameters[1]) < 35) {
            throw new NumberFormatException("Nieprawidłowa wielkość boku klocka!");
        } else {
            primarySize = Integer.parseInt(parameters[1]);
        }
        if (Double.parseDouble(parameters[2]) > 3 || Double.parseDouble(parameters[2]) < 0) {
            throw new NumberFormatException("Nieprawidłowa wartość przyspieszenia!");
        } else {
            blocksAcceleration = Double.parseDouble(parameters[2]);
        }
        if (Integer.parseInt(parameters[3]) < 0) {
            throw new NumberFormatException("Nieprawidłowa wartość częstotliwości utrudniania rozgrywki!");
        } else {
            blocksAccelerationFrequency = Integer.parseInt(parameters[3]);
        }
        if (Integer.parseInt(parameters[4]) <= 0) {
            throw new NumberFormatException("Nieprawidłowy licznik laserów!");
        } else {
            lasersCount = Integer.parseInt(parameters[4]);
        }
    }

    private void loadBlocksConfig() throws IOException {
        coordinateSets = new int[19][6];
        BufferedReader readFile = new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream("/blocksConfig")));
        String line;
        int j = 0;
        int i;
        while ((line = readFile.readLine()) != null) {
            String[] words = line.split(":");
            i = 0;
            for (String s : words) {
                if (Integer.parseInt(s) < 5 || Integer.parseInt(s) > -5) {
                    coordinateSets[j][i] = Integer.parseInt(s);
                    i++;
                } else {
                    throw new NumberFormatException("Nieprawidłowe cyfry w pliku konfiguracyjnym klocków!");
                }
            }
            j++;
        }
    }

    public static void loadGame(File gameFile, GameScreen game) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(gameFile));
        String line;
        int min = 0;
        int sec = 0;
        int level = 1;

        String[] parameters = null;
        while ((line = br.readLine()) != null) {
            parameters = line.split(";");
            if (parameters.length != 10) {
                throw new NumberFormatException("Nieprawidłowa liczba paramterów w pliku konfiguracyjnym programu!");
            }
        }
        game.getLeftPlayer().setName(parameters[0]);
        game.getRightPlayer().setName(parameters[2]);
        game.getLeftPlayer().addPoints(Integer.parseInt(parameters[1]));
        game.getRightPlayer().addPoints(Integer.parseInt(parameters[3]));
        min = Integer.parseInt(parameters[4].substring(0, parameters[4].indexOf(':')));
        game.setMins(min);

        if (Integer.parseInt(parameters[4].substring(parameters[4].indexOf(':') + 1)) > 60 || Integer.parseInt(parameters[4].substring(3, 5)) < 0) {
            throw new NumberFormatException("Nieprawidłowa wartość sekund!");
        } else {
            sec = Integer.parseInt(parameters[4].substring(3, 5));
            game.setSecs(sec);
        }
        if (Double.parseDouble(parameters[5]) > 15 || Double.parseDouble(parameters[5]) < 0) {
            throw new NumberFormatException("Nieprawidłowa wartość prędkości!");
        } else {
            blocksVelocity = Double.parseDouble(parameters[5]);
        }
        if (Double.parseDouble(parameters[7]) > 3 || Double.parseDouble(parameters[7]) < 0) {
            throw new NumberFormatException("Nieprawidłowa wartość przyspieszenia!");
        } else {
            blocksAcceleration = Double.parseDouble(parameters[7]);
        }
        if (Integer.parseInt(parameters[8]) < 0) {
            throw new NumberFormatException("Nieprawidłowa wartość częstotliwości utrudniania rozgrywki!");
        } else {
            blocksAccelerationFrequency = Integer.parseInt(parameters[8]);
        }
        if (Integer.parseInt(parameters[9]) <= 0) {
            throw new NumberFormatException("Nieprawidłowy licznik laserów!");
        } else {
            lasersCount = Integer.parseInt(parameters[9]);
        }

        sec += 60 * min;
        while (sec - blocksAccelerationFrequency >= 0) {
            sec -= blocksAccelerationFrequency;
            level++;
        }
        game.setLevel(level);

        if (Integer.parseInt(parameters[6]) < 35 - (level - 1) * 2) {
            throw new NumberFormatException("Nieprawidłowa wielkość boku klocka!");
        } else {
            primarySize = Integer.parseInt(parameters[6]);
        }
    }

    public static void saveGame(File selectedFile, GameScreen game) throws FileNotFoundException {
        PrintWriter save;
        save = new PrintWriter(selectedFile);
        save.println(
                game.getLeftPlayer().getName() + ";" + game.getLeftPlayer().getPoints() + ";"
                + game.getRightPlayer().getName() + ";" + game.getRightPlayer().getPoints() + ";"
                + game.getTimeLabel().getText() + ";"
                + blocksVelocity + ";" + primarySize + ";"
                + blocksAcceleration + ";" + blocksAccelerationFrequency + ";"
                + lasersCount + ";");
        save.close();
    }

}
