package ru.ponkin.scheduler;

import org.joda.time.LocalDateTime;

import java.util.concurrent.TimeUnit;

public class App {

  public static void main(String[] args) throws Exception {
    Scheduler scheduler = new Scheduler(1);
    scheduler.start();
    scheduler.submit(new Task(LocalDateTime.now().plusSeconds(10), () -> { System.out.println("Three"); return null;}));
    scheduler.submit(new Task(LocalDateTime.now().plusSeconds(5), () -> { System.out.println("Two"); return null;}));
    scheduler.submit(new Task(LocalDateTime.now().plusSeconds(1), () -> { System.out.println("One"); return null;}));
    TimeUnit.SECONDS.sleep(20);
    scheduler.shutdown();
  }
}

