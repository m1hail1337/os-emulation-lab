package org.example.os.generator;

import java.util.*;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;
import org.example.os.scheduler.Scheduler;
import org.example.os.task.Task;

public class TaskGenerator {
    private static final int MAX_TASK_DURATION = 5; // секунды
    private static final Random random = new Random();
    private final Scheduler scheduler;

    public TaskGenerator(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void generateTasks(int nTasks, int interval) {
        int firstHalf = nTasks / 2;
        for (int i = 0; i < firstHalf; i++) {
            Task task = generateTask();
            scheduler.getNewTasks().add(task);
        }
        printProcessingState("Nзначально в очереди " + nTasks / 2 + " задач: ", interval);
        //System.out.println("Nзначально в очереди " + nTasks / 2 + " задач: ");
        printAllTasks(scheduler.getNewTasks(), interval);

        Thread thread = new Thread(() -> {
            for (int i = 0; i < nTasks - firstHalf; i++) {
                Task task = generateTask();
                scheduler.getNewTasks().add(task);
                printProcessingState("Появилась задача " + task, interval);
                //System.out.println("Появилась задача " + task);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    private void printAllTasks(Queue<Task> tasks, int interval) {
        if (interval >= 1000) {
            for (Task task : tasks) {
                System.out.println(task);
            }
            System.out.println();
        }
    }

    private void printProcessingState(String message, int interval) {
        if (interval >= 1000) {
            System.out.println(message);
        }
    }

    private Task generateTask() {
        TaskType type = getRandomArrayElement(TaskType.values());
        Priority priority = getRandomArrayElement(Priority.values());
        int duration = random.nextInt(1, MAX_TASK_DURATION);
        return Task.builder().state(State.SUSPENDED).priority(priority).type(type).duration(duration).build();
    }

    private static <T> T getRandomArrayElement(T[] array) {
        int index = random.nextInt(array.length);
        return array[index];
    }
}
