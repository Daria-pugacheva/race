package ru.gb.pugacheva.race;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private static String winnerName=null;


//    public String getWinnerName() {
//        return winnerName;
//    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;

    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) Math.random() * 800);
            System.out.println(this.name + " готов");
            MainClass.countForStart.countDown(); // это защелка, чтобы отмашка "Гонка началась" была дана на старте
            MainClass.cyclicBarrierForCommonStart.await(); // это чтобы именно потоки начали одновременно гонку
        } catch (InterruptedException |BrokenBarrierException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if(winnerName==null) {
            winnerName = name;
           // MainClass.countForWin.countDown(); // <--это альтернативный вариант для распечатки победы в Main  с применением защелки.
            System.out.println("Победил " + winnerName); // <-- но вот эта простая реализация мне нравится больше
        }
        MainClass.countForFinish.countDown();
    }

}


