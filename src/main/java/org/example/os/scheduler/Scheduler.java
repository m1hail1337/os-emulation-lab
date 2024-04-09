package org.example.os.scheduler;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.example.os.enums.Priority;
import org.example.os.processor.Processor;
import org.example.os.task.Task;

public class Scheduler {

    private final Queue<Task> newTasks = new ConcurrentLinkedQueue<>();

    private final Processor processor;
    private final Map<Priority, Queue<Task>> tasks = new HashMap<>() {{
        put(Priority.ZERO, new LinkedList<>());
        put(Priority.FIRST, new LinkedList<>());
        put(Priority.SECOND, new LinkedList<>());
        put(Priority.THIRD, new LinkedList<>());
    }};

    public Scheduler(Processor processor) {
        this.processor = processor;
        launchScheduleDaemon();
    }

    private void launchScheduleDaemon() {
        launchTaskMapper();
        launchProcessorAccessor();
    }

    private void launchTaskMapper() {
        Thread thread = new Thread(() -> {
            int currentNewTaskSize = 0;
            while (true) {
                if (currentNewTaskSize < newTasks.size()) {
                    while (!newTasks.isEmpty()) {
                        Task newTask = newTasks.poll();
                        tasks.get(newTask.priority()).add(newTask);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    private void launchProcessorAccessor() {
        Thread thread = new Thread(() -> {
           while (true) {
               if (!processor.isExecuteNow()) {
                   // TODO logic to set task
               }
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });
    }

    public Queue<Task> getNewTasks() {
        return newTasks;
    }
}
