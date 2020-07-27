/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Results.printresults;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author Lenovo-ideaPad-15isk
 */
public class Players extends Thread {

    String name;
    String strategy;
    CyclicBarrier barrier;
    int ID;
    int numberOfAttempts = 0;
    int currentAttempt = 0;
    long penaltyPoints = 0;
    static int count = 0;
    static boolean flag = true;
    long timeStart, timeFinish, alltime = 0;
    static int PlayerIsDone = 0;
    static boolean flagthird = true;

    static List<Integer> containerSecond = Collections.synchronizedList(new ArrayList<Integer>());
    static List<Results> results = Collections.synchronizedList(new ArrayList<Results>());

    Players(String name, String strategy, int ID, CyclicBarrier barrier) {
        this.name = name;
        this.strategy = strategy;
        this.ID = ID;
        this.barrier = barrier;
    }

    @Override
    public void run() {

        try {

            numPredicting(strategy);

            //printresults();
        } catch (IOException | InterruptedException | BrokenBarrierException ex) {
        }
    }

    void numPredicting(String strategy) throws IOException, InterruptedException, BrokenBarrierException {

        switch (strategy) {

            case "strategyFirst":
                strategyFirst(Game.a, Game.b, Game.x);

                this.barrier.await();
                break;

            case "strategySecond":
                strategySecond(Game.a, Game.b, Game.x);

                this.barrier.await();
                break;

            case "strategyThird":
                strategyThird(Game.a, Game.b, Game.x);

                this.barrier.await();
                break;

            case "strategyFourth":

                strategyFourth(Game.a, Game.b, Game.x);

                this.barrier.await();
                break;

            default:
            //System.out.println("Wrong choise!");
            }
    }

    public void strategyFirst(int low, int high, int x) throws IOException, InterruptedException {
        timeStart = startInMiliSeconds();
        numberOfAttempts = high - low;
        boolean isDone = true;
        int count2 = 0;

        while (isDone) {

            currentAttempt++;
            int result = randomNumber(low, high);

            if (result == x) {
                isDone = false;
                count++;

                if (count == 1 && flag) {

                    timeFinish = endInMiliSeconds();
                    alltime = timeFinish - timeStart;
                    results.add(new Results(name, strategy, numberOfAttempts, alltime));
                    count2++;
                    flag = false;
                    PlayerIsDone++;
                    printresults();

                }

            } else {

                if (!containerSecond.contains(result)) {
                    containerSecond.add(result);
                }
                if (numberOfAttempts > 0) {
                    numberOfAttempts--;
                } else {
                    penaltyPoints = abs(x - result);
                    Thread.sleep(penaltyPoints);
                    numberOfAttempts = high - low;
                }
            }
            if (flag == false) {
                count2++;
                if (count2 == 1) {
                    timeFinish = endInMiliSeconds();
                    alltime = timeFinish - timeStart;
                    results.add(new Results(name, strategy, numberOfAttempts, alltime));
                    PlayerIsDone++;

                }

            }
        }
    }

    public void strategySecond(int low, int high, int x) throws IOException, InterruptedException {

        timeStart = startInMiliSeconds();
        numberOfAttempts = high - low;
        boolean isDone = true;
        int count2 = 0;
        ArrayList<Integer> container = new ArrayList<>();

        while (isDone) {

            currentAttempt++;

            int result = randomNumber(low, high);
            boolean TrueORFalse = true;

            while (TrueORFalse) {

                if (flag == false) {
                    count2++;
                    if (count2 == 1) {
                        timeFinish = endInMiliSeconds();
                        alltime = timeFinish - timeStart;
                        results.add(new Results(name, strategy, numberOfAttempts, alltime));
                        PlayerIsDone++;

                    }

                }

                if (!container.contains(result)) {
                    container.add(result);

                    if (result == x) {
                        isDone = false;
                        TrueORFalse = false;
                        count++;

                        if (count == 1 && flag) {
                            timeFinish = endInMiliSeconds();
                            alltime = timeFinish - timeStart;
                            results.add(new Results(name, strategy, numberOfAttempts, alltime));
                            count2++;
                            flag = false;
                            PlayerIsDone++;
                            printresults();

                        }

                    } else {

                        if (!containerSecond.contains(result)) {
                            containerSecond.add(result);
                        }

                        if (numberOfAttempts > 0) {
                            numberOfAttempts--;
                        } else {
                            penaltyPoints = abs(x - result);
                            Thread.sleep(penaltyPoints);
                            numberOfAttempts = high - low;
                        }

                        result = randomNumber(low, high);
                        currentAttempt++;
                    }
                } else {
                    result = randomNumber(low, high);
                    currentAttempt++;
                }
            }

        }

    }

    public void strategyThird(int low, int high, int x) throws IOException, InterruptedException {
        timeStart = startInMiliSeconds();
        int count2 = 0;
        numberOfAttempts = high - low;
        boolean isDone = true;

        while (isDone) {
            currentAttempt++;
            int result = randomNumber(low, high);
            boolean TrueORFalse = true;

            while (TrueORFalse) {
                if (flag == false) {
                    count2++;

                    if (count2 == 1) {
                        timeFinish = endInMiliSeconds();
                        alltime = timeFinish - timeStart;
                        results.add(new Results(name, strategy, numberOfAttempts, alltime));
                        PlayerIsDone++;

                    }

                }

                if (result == x) {
                    isDone = false;
                    TrueORFalse = false;
                    count++;

                    if (count == 1 && flag) {
                        timeFinish = endInMiliSeconds();
                        alltime = timeFinish - timeStart;
                        results.add(new Results(name, strategy, numberOfAttempts, alltime));
                        count2++;
                        flag = false;
                        PlayerIsDone++;
                        printresults();

                    }
                } else {
                    if (!containerSecond.contains(result)) {
                        containerSecond.add(result);
                    }

                    if (numberOfAttempts > 0) {
                        numberOfAttempts--;
                    } else {
                        penaltyPoints = abs(x - result);
                        Thread.sleep(penaltyPoints);
                        numberOfAttempts = high - low;
                    }
                    result = randomNumber(low, high);
                    currentAttempt++;
                }

            }
        }

    }

    public void strategyFourth(int low, int high, int x) throws IOException, InterruptedException {
        timeStart = startInMiliSeconds();
        int count2 = 0;
        numberOfAttempts = high - low;

        for (; low < high; low++) {

            if (flag == false) {
                count2++;
                if (count2 == 1) {
                    timeFinish = endInMiliSeconds();
                    alltime = timeFinish - timeStart;
                    results.add(new Results(name, strategy, numberOfAttempts, alltime));
                    PlayerIsDone++;

                }

            }
            currentAttempt++;
            if (low == x) {
                count++;

                if (count == 1 && flag) {
                    timeFinish = endInMiliSeconds();
                    alltime = timeFinish - timeStart;
                    results.add(new Results(name, strategy, numberOfAttempts, alltime));
                    count2++;
                    flag = false;
                    PlayerIsDone++;
                    printresults();

                }
            } else {
                if (!containerSecond.contains(low)) {
                    containerSecond.add(low);
                }
                if (numberOfAttempts > 0) {
                    numberOfAttempts--;
                } else {
                    penaltyPoints = abs(x - low);
                    Thread.sleep(penaltyPoints);
                    numberOfAttempts = high - low;
                }
            }
        }

    }

    static long startInMiliSeconds() {
        Date date = new Date();
        return date.getTime();
    }

    static long endInMiliSeconds() {
        Date date = new Date();
        return date.getTime();
    }

    public int randomNumber(int low, int high) {
        Random r = new Random();
        int result = r.nextInt(high - low) + low;
        return result;
    }

    String getname() {
        return this.name;
    }

    String getStrategy() {
        return this.strategy;
    }

    private Results Results(String name, String strategy, int numberOfAttempts, long alltime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
