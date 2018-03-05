package ru.ponkin.scheduler;

import org.joda.time.LocalDateTime;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;

public class Scheduler implements Runnable {

  private final PriorityBlockingQueue<Task> scheduled;
  private final ExecutorService pool;
  private volatile boolean running = true;
  private final Thread worker;

  public Scheduler(int poolSize) {
    this.scheduled = new PriorityBlockingQueue<>();
    this.pool = Executors.newFixedThreadPool(poolSize);
    worker = new Thread(this);
  }

  public void start() {
    worker.start();
  }

  @Override
  public void run() {
    try {
      while(running) {
        Task peek = scheduled.peek();
        if (peek != null && peek.getDateTime().isBefore(LocalDateTime.now())) {
          Task task = scheduled.poll();
          pool.submit(task.getCallable()); // ignore return Future since I dunno what to do with result
        } else {
          TimeUnit.SECONDS.sleep(1L);
        }
      }
    } catch (InterruptedException err ) {
      System.out.println("Interrupting scheduler");
    } finally {
      pool.shutdown();
      try {
        if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
          System.err.println("Threads didn't finish in 60 seconds!");
        }
      } catch (InterruptedException err) {
        System.err.println("Error while shutdown scheduler");
        err.printStackTrace();
      }
    }
  }

  public void submit(Task task) {
    scheduled.offer(task);
  }

  public void shutdown() {
    running = false;
  }

}
