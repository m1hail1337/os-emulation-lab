package scheduler;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;
import org.example.os.processor.Processor;
import org.example.os.scheduler.Scheduler;
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
    }

    @Test
    public void testEmptyScheduler() {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);
        scheduler.launchScheduleDaemon(1);
        Assertions.assertTrue(scheduler.getFinishedTasks().isEmpty());
    }
}
