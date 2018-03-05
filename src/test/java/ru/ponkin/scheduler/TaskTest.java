package ru.ponkin.scheduler;

import org.joda.time.LocalDateTime;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class TaskTest {

  @Test
  public void testNaturalOrder() {
    LocalDateTime now = LocalDateTime.now();

    Task t1 = new Task(now, () -> { return null; });
    Task t2 = new Task(now, () -> { return null; });
    Task t3 = new Task(now, () -> { return null; });

    List<Task> tasks = Arrays.asList(t3, t1, t2);
    Collections.sort(tasks);
    assertEquals(tasks.get(0), t1);
    assertEquals(tasks.get(1), t2);
    assertEquals(tasks.get(2), t3);
  }
}
