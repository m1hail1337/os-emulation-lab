package org.example.os.scheduler;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;
import org.example.os.processor.Processor;
import org.example.os.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestScheduler {

    @Test
    public void testAddingTasks() {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);

        Task task1 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.FIRST, 1);
        scheduler.getNewTasks().add(task1);
        Task task2 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.ZERO, 1);
        scheduler.getNewTasks().add(task2);
        Task task3 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, 1);
        scheduler.getNewTasks().add(task3);
        Task task4 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.FIRST, 1);
        scheduler.getNewTasks().add(task4);
        Task task5 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.SECOND, 1);
        scheduler.getNewTasks().add(task5);
        Task task6 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.SECOND, 1);
        scheduler.getNewTasks().add(task6);
        Task task7 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, 1);
        scheduler.getNewTasks().add(task7);
        Task task8 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.ZERO, 1);
        scheduler.getNewTasks().add(task8);
        Task task9 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.ZERO, 1);
        scheduler.getNewTasks().add(task9);
        Task task10 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.SECOND, 1);
        scheduler.getNewTasks().add(task10);

        Assertions.assertEquals(task1, scheduler.getNewTasks().poll());
        Assertions.assertEquals(task2, scheduler.getNewTasks().poll());
        Assertions.assertEquals(task3, scheduler.getNewTasks().poll());
        Assertions.assertEquals(task4, scheduler.getNewTasks().poll());
        Assertions.assertEquals(task5, scheduler.getNewTasks().poll());
        Assertions.assertEquals(task6, scheduler.getNewTasks().poll());
        Assertions.assertEquals(task7, scheduler.getNewTasks().poll());
        Assertions.assertEquals(task8, scheduler.getNewTasks().poll());
        Assertions.assertEquals(task9, scheduler.getNewTasks().poll());
        Assertions.assertEquals(task10, scheduler.getNewTasks().poll());
    }

    // Test in order to get ready tasks
    @Test
    public void testTaskMapping() throws InterruptedException {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);

        Task task1 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.FIRST, 1);
        scheduler.getNewTasks().add(task1);
        Task task2 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.ZERO, 1);
        scheduler.getNewTasks().add(task2);
        Task task3 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, 1);
        scheduler.getNewTasks().add(task3);
        Task task4 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.FIRST, 1);
        scheduler.getNewTasks().add(task4);
        Task task5 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.SECOND, 1);
        scheduler.getNewTasks().add(task5);
        Task task6 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.SECOND, 1);
        scheduler.getNewTasks().add(task6);
        Task task7 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, 1);
        scheduler.getNewTasks().add(task7);
        Task task8 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.ZERO, 1);
        scheduler.getNewTasks().add(task8);
        Task task9 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.ZERO, 1);
        scheduler.getNewTasks().add(task9);
        Task task10 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.SECOND, 1);
        scheduler.getNewTasks().add(task10);

        scheduler.launchScheduleDaemon(10000);
        Thread.sleep(800);

        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.ZERO).contains(task2));
        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.ZERO).contains(task8));
        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.ZERO).contains(task9));

        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.FIRST).contains(task1));
        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.FIRST).contains(task4));

        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.SECOND).contains(task5));
        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.SECOND).contains(task6));
        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.SECOND).contains(task10));

        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.THIRD).contains(task3));
        Assertions.assertTrue(scheduler.getReadyTasks().get(Priority.THIRD).contains(task7));
    }

    @Test
    public void testWaitingTasks() throws InterruptedException {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);

        int interval = 50;
        int taskDuration = 4;
        Task task1 = new Task(TaskType.EXTENDED, State.READY, Priority.SECOND, taskDuration);
        Task task2 = new Task(TaskType.EXTENDED, State.READY, Priority.THIRD, taskDuration);

        scheduler.getNewTasks().add(task1);
        scheduler.launchScheduleDaemon(interval);
        while (task1.getState() != State.RUNNING) {
            Thread.sleep(1);
        }
        Assertions.assertEquals(State.RUNNING, task1.getState());
        scheduler.getNewTasks().add(task2);
        while (task1.getState() != State.WAITING) {
            Thread.sleep(1);
        }
        // Task2
        Assertions.assertEquals(State.RUNNING, task2.getState());
        //Task1
        Assertions.assertEquals(State.WAITING, task1.getState());

        while (task2.getState() != State.SUSPENDED) {
            Thread.sleep(1);
        }
        while (task1.getState() != State.RUNNING) {
            Thread.sleep(1);
        }
        Assertions.assertEquals(State.RUNNING, task1.getState());
    }

    // Test in order to get suspended tasks
    @Test
    public void testFinishedTasks() throws InterruptedException {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);
        int interval = 5;

        Task task1 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.FIRST, 1);
        scheduler.getNewTasks().add(task1);
        Task task2 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.ZERO, 1);
        scheduler.getNewTasks().add(task2);
        Task task3 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, 1);
        scheduler.getNewTasks().add(task3);
        Task task4 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.FIRST, 1);
        scheduler.getNewTasks().add(task4);
        Task task5 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.SECOND, 1);
        scheduler.getNewTasks().add(task5);
        Task task6 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.SECOND, 1);
        scheduler.getNewTasks().add(task6);
        Task task7 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, 1);
        scheduler.getNewTasks().add(task7);
        Task task8 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.ZERO, 1);
        scheduler.getNewTasks().add(task8);
        Task task9 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.ZERO, 1);
        scheduler.getNewTasks().add(task9);
        Task task10 = new Task(TaskType.EXTENDED, State.SUSPENDED, Priority.SECOND, 1);
        scheduler.getNewTasks().add(task10);

        scheduler.launchScheduleDaemon(interval);
        while (scheduler.getFinishedTasks().size() != 10) {
            Thread.sleep(5);
        }

        Assertions.assertEquals(task3, scheduler.getFinishedTasks().poll());
        Assertions.assertEquals(task7, scheduler.getFinishedTasks().poll());

        Assertions.assertEquals(task5, scheduler.getFinishedTasks().poll());
        Assertions.assertEquals(task6, scheduler.getFinishedTasks().poll());
        Assertions.assertEquals(task10, scheduler.getFinishedTasks().poll());

        Assertions.assertEquals(task1, scheduler.getFinishedTasks().poll());
        Assertions.assertEquals(task4, scheduler.getFinishedTasks().poll());
        Assertions.assertEquals(task2, scheduler.getFinishedTasks().poll());

        Assertions.assertEquals(task8, scheduler.getFinishedTasks().poll());
        Assertions.assertEquals(task9, scheduler.getFinishedTasks().poll());

        Assertions.assertEquals(State.SUSPENDED, task3.getState());
        Assertions.assertEquals(State.SUSPENDED, task7.getState());

        Assertions.assertEquals(State.SUSPENDED, task5.getState());
        Assertions.assertEquals(State.SUSPENDED, task6.getState());
        Assertions.assertEquals(State.SUSPENDED, task10.getState());

        Assertions.assertEquals(State.SUSPENDED, task1.getState());
        Assertions.assertEquals(State.SUSPENDED, task4.getState());
        Assertions.assertEquals(State.SUSPENDED, task2.getState());

        Assertions.assertEquals(State.SUSPENDED, task8.getState());
        Assertions.assertEquals(State.SUSPENDED, task9.getState());
    }

    @Test
    public void testEmptyScheduler() {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);
        scheduler.launchScheduleDaemon(1);
        Assertions.assertTrue(scheduler.getFinishedTasks().isEmpty());
    }
}
