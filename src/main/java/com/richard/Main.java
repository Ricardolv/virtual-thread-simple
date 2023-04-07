package com.richard;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().runExecutor3();
    }

    void runExecutor() {
       for (;;) {
           long start = System.currentTimeMillis();
           try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {

               for (int i = 0; i < 100_000; i++) {
                   executor.submit(() -> {
                      Thread.sleep(2000);
                      return null;
                   });
               }
               long end = System.currentTimeMillis();

               System.out.println(end - start + " ms");
           }

       }
    }
    void runExecutor2() {

        TimerTask task1 = new MyTimeTask("task1");
        TimerTask task2 = new MyTimeTask("task2");

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(task1);
            executor.submit(task2);
        }

    }

    void runExecutor3() throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();

        // only support 2 tasks
        TaskLimitSemaphore obj = new TaskLimitSemaphore(executor, 2);

        obj.submit(() -> {
            System.out.println(getCurrentDateTime() + " : task1 is running!");
            Thread.sleep(2000);
            System.out.println(getCurrentDateTime() + " : task1 is done!");
            return 1;
        });

        obj.submit(() -> {
            System.out.println(getCurrentDateTime() + " : task2 is running!");
            Thread.sleep(2000);
            System.out.println(getCurrentDateTime() + " task2 is done!");
            return 2;
        });

        obj.submit(() -> {
            System.out.println(getCurrentDateTime() + " task3 is running!");
            Thread.sleep(2000);
            System.out.println(getCurrentDateTime() + " task3 is done!");
            return 3;
        });

        obj.submit(() -> {
            System.out.println(getCurrentDateTime() + " task4 is running!");
            Thread.sleep(2000);
            System.out.println(getCurrentDateTime() + " task4 is done!");
            return 4;
        });

        obj.submit(() -> {
            System.out.println(getCurrentDateTime() + " task5 is running!");
            Thread.sleep(2000);
            System.out.println(getCurrentDateTime() + " task5 is done!");
            return 5;
        });

        executor.shutdown();


    }




}


class MyTimeTask extends TimerTask {

    private final String name;

    MyTimeTask(String name) {
        this.name = name;
    }


    @Override
    public void run() {
        System.out.println("task name: " + name);
    }
}