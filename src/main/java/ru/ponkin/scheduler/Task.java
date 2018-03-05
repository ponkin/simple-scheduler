package ru.ponkin.scheduler;

import org.joda.time.LocalDateTime;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

public class Task<T> implements Comparable<Task<T>> {

  private static final AtomicLong seq = new AtomicLong(0);
  private final long seqNum;
  private final LocalDateTime dateTime;
  private final Callable fun;

  public Task(LocalDateTime dateTime, Callable<T> fun) {
    this.seqNum = seq.getAndIncrement();
    this.dateTime = dateTime;
    this.fun = fun;
  }

  public LocalDateTime getDateTime() {
    return this.dateTime;
  }

  public Callable getCallable() {
    return this.fun;
  }

  @Override
  public int compareTo(Task<T> other) {
    int comparsionResult = this.dateTime.compareTo(other.dateTime);
    if (comparsionResult == 0 && this != other)
      comparsionResult = (seqNum < other.seqNum ? -1 : 1);
    return comparsionResult;
  }

}
