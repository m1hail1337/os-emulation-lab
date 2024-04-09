package org.example.os.generator;

import java.util.*;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;
import org.example.os.scheduler.Scheduler;
import org.example.os.task.Task;

public class TaskGenerator {

    private static final int TASK_GENERATION_MAX_DELAY = 5; // секунды
    private static final int MAX_TASK_DURATION = 3; // секунды
    private static final Random random = new Random();
    private final Scheduler scheduler;

    public TaskGenerator(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void launch() {
        generateTasksWithDelay();
    }

    private void generateTasksWithDelay() {
        Thread thread = new Thread(() -> {
            while (true) {
                Task task = generateTask();
                scheduler.getNewTasks().add(task);
                try {
                    Thread.sleep(random.nextInt(TASK_GENERATION_MAX_DELAY));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    private Task generateTask() {
        TaskType type = getRandomArrayElement(TaskType.values());
        State state = getRandomArrayElement(State.values());
        Priority priority = getRandomArrayElement(Priority.values());
        int duration = random.nextInt(1, MAX_TASK_DURATION);
        return Task.builder().state(state).priority(priority).type(type).build();
    }

    private static <T> T getRandomArrayElement(T[] array) {
        int index = random.nextInt(array.length);
        return array[index];
    }

}
