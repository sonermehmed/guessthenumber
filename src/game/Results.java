/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Game.numberOfPlayers;
import static game.Players.PlayerIsDone;
import static game.Players.results;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Lenovo-ideaPad-15isk
 */
public class Results {

    String name;
    String strategy;
    int numberOfAttempts = 0;
    long alltime = 0;
    static boolean winner = true;

    public Results(String name, String strategy, int numberOfAttempts, long alltime) {

        this.name = name;
        this.strategy = strategy;
        this.numberOfAttempts = numberOfAttempts;
        this.alltime = alltime;
    }

    String getName() {
        String winnername = "";
        if (winner) {
            winnername = "The winner is: " + this.name + "\n\r";
            winner = false;
        } else {
            winnername = " Name: " + this.name + "\n\r";
        }
        return winnername;
    }

    String getStrategy() {
        String strategy = " Strategy:" + this.strategy + "\n\r";

        return strategy;
    }

    String getNumberOfAttempts() {

        String numberOfAttempts = " Number of Attempets: " + this.numberOfAttempts + "\n\r";
        return numberOfAttempts;
    }

    String getAlltime() {

        String alltime = " Time: " + this.alltime + "\n\r \n\r";
        return alltime;
    }

    public static void printresults() throws FileNotFoundException, IOException {

        FileOutputStream writer = new FileOutputStream("src\\game\\Output.txt", true);

        FileWriter fileOut = new FileWriter("src\\game\\Output.txt");
        fileOut.write("");
        fileOut.close();
        if (numberOfPlayers == PlayerIsDone) {
            for (int i = 0; i < numberOfPlayers; i++) {
                byte[] mybytes = results.get(i).getName().getBytes();
                writer.write(mybytes);
                byte[] mybytes1 = results.get(i).getStrategy().getBytes();
                writer.write(mybytes1);
                byte[] mybytes2 = results.get(i).getNumberOfAttempts().getBytes();
                writer.write(mybytes2);
                byte[] mybytes3 = results.get(i).getAlltime().getBytes();
                writer.write(mybytes3);
                writer.flush();
            }
            writer.close();
        }

    }

}
