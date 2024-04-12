package org.example.os.processor;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;
import org.example.os.scheduler.Scheduler;
import org.example.os.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProcessor {

    @Test
    public void testExecuteTask() throws InterruptedException {
        Processor processor = new Processor();

        int duration = 4;
        Task task = new Task(TaskType.MAIN, State.READY, Priority.THIRD, duration);
        processor.executeTask(task, 1000);

        Thread.sleep(1000L * duration / 2);
        Assertions.assertEquals(task, processor.getExecutionTask());
        Assertions.assertEquals(State.RUNNING, processor.getExecutionTask().getState());

        Thread.sleep(1000L * duration);
        Assertions.assertEquals(State.SUSPENDED, processor.getExecutionTask().getState());
    }

    @Test
    public void testExtendedTask() throws InterruptedException {
        Processor processor = new Processor();

        int duration = 4;
        Task task = new Task(TaskType.EXTENDED, State.READY, Priority.THIRD, duration);
        processor.executeTask(task, 1000);

        Thread.sleep(1000L * duration / 2);
        Assertions.assertEquals(task, processor.getExecutionTask());
        Assertions.assertEquals(State.RUNNING, processor.getExecutionTask().getState());

        Thread.sleep(1000L * duration);
        Assertions.assertEquals(State.SUSPENDED, processor.getExecutionTask().getState());
    }

    @Test
    public void testInterruptCurrentTask() throws InterruptedException {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);

        int interval = 5;
        int taskDuration = 4;
        Task task1 = new Task(TaskType.MAIN, State.READY, Priority.SECOND, taskDuration);
        Task task2 = new Task(TaskType.EXTENDED, State.READY, Priority.THIRD, taskDuration);

        scheduler.getNewTasks().add(task1);
        scheduler.launchScheduleDaemon(interval);
        while (task1.getState() != State.RUNNING) {
            Thread.sleep(1);
        }
        Assertions.assertEquals(State.RUNNING, processor.getExecutionTask().getState());
        scheduler.getNewTasks().add(task2);
        while (task1.getState() != State.READY) {
            Thread.sleep(1);
        }
        // Task2
        Assertions.assertEquals(State.RUNNING, processor.getExecutionTask().getState());
        //Task1
        Assertions.assertEquals(State.READY, task1.getState());
    }
}
