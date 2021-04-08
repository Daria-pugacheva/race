package ru.gb.pugacheva.race;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    Semaphore semaphoreTunnel;

    public Tunnel() {
        this.semaphoreTunnel = new Semaphore(MainClass.CARS_COUNT/2);
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }


    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу (ждет): " + description);
                semaphoreTunnel.acquire(); // заехать с тоннель могут только половина участников
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                semaphoreTunnel.release();//когда выехал из тоннеля, надо дать знать, что тоннель свободен
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}