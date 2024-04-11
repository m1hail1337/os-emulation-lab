package org.example.os.scheduler;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;
import org.example.os.processor.Processor;
import org.example.os.task.Task;

public class Scheduler {

    private final Queue<Task> newTasks = new ConcurrentLinkedQueue<>();

    private final Processor processor;
    private final Queue<Task> finishedTasks = new LinkedList<>();
    private final Map<Priority, Queue<Task>> readyTasks = new HashMap<>() {{
        put(Priority.ZERO, new LinkedList<>());
        put(Priority.FIRST, new LinkedList<>());
        put(Priority.SECOND, new LinkedList<>());
        put(Priority.THIRD, new LinkedList<>());
    }};
    private final Map<Priority, Queue<Task>> waitingTasks = new HashMap<>() {{
        put(Priority.ZERO, new LinkedList<>());
        put(Priority.FIRST, new LinkedList<>());
        put(Priority.SECOND, new LinkedList<>());
        put(Priority.THIRD, new LinkedList<>());
    }};
    private boolean isAllTasksExecuted = false;

    public Scheduler(Processor processor) {
        this.processor = processor;
    }

    public void launchScheduleDaemon() {
        launchTaskMapper();
        launchProcessorAccessor();
    }

    private void launchTaskMapper() {
        Thread thread = new Thread(() -> {
            int newTasksCounter = 0;
            while (!isAllTasksExecuted) {
                if (newTasksCounter < newTasks.size()) {
                    while (!newTasks.isEmpty()) {
                        Task newTask = newTasks.poll();
                        newTask.setState(State.READY);
                        readyTasks.get(newTask.getPriority()).add(newTask);
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
               Task currentTask = processor.getExecutionTask();
               Task toExecute = decideWhichTaskWillExecuted();

               if (toExecute == null) {
                   break;
               }

               if (currentTask == null) {
                   removeTaskFromQueues(toExecute);
                   processor.executeTask(toExecute);
                   System.out.println("Задача " + toExecute + " начала выполняться");
               } else if (currentTask.getState() == State.SUSPENDED) {
                   finishedTasks.add(processor.getExecutionTask());
                   removeTaskFromQueues(toExecute);
                   processor.executeTask(toExecute);
                   System.out.println("Задача " + toExecute + " начала выполняться");
               } else if (currentTask.getState() == State.RUNNING) {
                   if (toExecute.getPriority().ordinal() > currentTask.getPriority().ordinal()) {
                       processor.interruptCurrentTask();
                       removeTaskFromQueues(toExecute);
                       processor.executeTask(toExecute);
                       System.out.println("Задача " + toExecute + " заменила " + currentTask);
                       if (currentTask.getType() == TaskType.EXTENDED) {
                           currentTask.setState(State.WAITING);
                           waitingTasks.get(currentTask.getPriority()).add(currentTask);
                           System.out.println(currentTask + " теперь WAITING");
                       } else {
                           currentTask.setState(State.SUSPENDED);
                           newTasks.add(currentTask);
                           System.out.println(currentTask + " теперь SUSPENDED");
                       }
                   } else {
                       System.out.println("Задача " + toExecute + " пока не может быть выполнена");
                   }
               }
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
           isAllTasksExecuted = true;
        });
        thread.start();
    }

    private Task decideWhichTaskWillExecuted() {
        Task maxPriorityTask = null;
        for (Priority priority : Priority.values()) {
            Task readyTaskForCurPriority = readyTasks.get(priority).peek();
            Task waitingTaskForCurPriority = waitingTasks.get(priority).peek();
            if (readyTaskForCurPriority != null) {
                maxPriorityTask = Objects.requireNonNullElse(waitingTaskForCurPriority, readyTaskForCurPriority);
            } else {
                maxPriorityTask = waitingTaskForCurPriority != null ? waitingTaskForCurPriority : maxPriorityTask;
            }
        }
        return maxPriorityTask;
    }

    private void removeTaskFromQueues(Task task) {
        if (task.getState() == State.READY) {
            readyTasks.get(task.getPriority()).remove();
        } else if (task.getState() == State.WAITING) {
            waitingTasks.get(task.getPriority()).remove();
        } else {
            throw new RuntimeException();
        }
    }

    public Queue<Task> getNewTasks() {
        return newTasks;
    }
}
