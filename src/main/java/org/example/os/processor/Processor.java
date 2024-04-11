package org.example.os.processor;

import java.util.concurrent.atomic.AtomicInteger;

import org.example.os.enums.State;
import org.example.os.enums.TaskType;
import org.example.os.task.Task;

public class Processor {
    private Task executionTask;
    private Thread executionThread;

    public void executeTask(Task task) {
        executionTask = task;
        AtomicInteger initialDuration = new AtomicInteger(task.getDuration().get());
        AtomicInteger timeToFinish = new AtomicInteger(task.getDuration().get());
        task.setState(State.RUNNING);
        executionThread = new Thread(() -> {
            while (timeToFinish.get() != 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                if (task.getType() == TaskType.EXTENDED) {
                    task.getDuration().decrementAndGet();
                }
                timeToFinish.decrementAndGet();
            }
            executionTask.setState(State.SUSPENDED);
            executionTask.setDuration(initialDuration);
            System.out.println("Задача " + executionTask + " выполнена");
        });
        executionThread.start();
    }

    public void interruptCurrentTask() {
        executionThread.interrupt();
        System.out.println("Задача " + executionTask + " прервана");
    }

    public Task getExecutionTask() {
        return executionTask;
    }
}
