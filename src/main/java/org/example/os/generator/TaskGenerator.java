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

    public void generateTasks(int nTasks) {
        for (int i = 0; i < nTasks; i++) {
            Task task = generateTask();
            scheduler.getNewTasks().add(task);
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
