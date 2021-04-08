package ru.gb.pugacheva.race;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
    public static final int CARS_COUNT = 4;
    static final CyclicBarrier cyclicBarrierForCommonStart = new CyclicBarrier(CARS_COUNT);
    static final CountDownLatch countForStart = new CountDownLatch(CARS_COUNT);
    static final CountDownLatch countForFinish = new CountDownLatch(CARS_COUNT);
    //static final CountDownLatch countForWin = new CountDownLatch(1); // счетчик для альтернативного варианта сообщеия о победе.

    public static void main(String[] args) {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car [] cars = new Car [CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int)Math.random()*10);
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            countForStart.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>>> Гонка началась!!!");


        // Закомментирован альтернативный вариант для вывода сообщения о победе отсюда, а не в
        // методе run. Он какой-то более громоздкий.
//        try {
//            countForWin.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Победил " + cars[1].getWinnerName()); // поле winnerName общее для всех объектов


        try {
            countForFinish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>>> Гонка закончилась!!!");
    }
}
