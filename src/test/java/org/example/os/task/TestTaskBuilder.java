package org.example.os.task;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestTaskBuilder {

    @Test
    public void testFieldsNull() {
        TaskType taskType3 = TaskType.MAIN;
        State state3 = State.RUNNING;
        Priority priority3 = Priority.SECOND;
        Task task3 = Task.builder().type(taskType3).state(state3).priority(priority3).build();
        Assertions.assertEquals(TaskType.MAIN, task3.getType());
        Assertions.assertEquals(State.RUNNING, task3.getState());
        Assertions.assertEquals(Priority.SECOND, task3.getPriority());
        Assertions.assertEquals(0, task3.getDuration().intValue());

        Task task4 = Task.builder().type(taskType3).priority(priority3).build();
        Assertions.assertNull(task4.getState());

        Task task5 = Task.builder().priority(priority3).build();
        Assertions.assertNull(task5.getState());
        Assertions.assertNull(task5.getType());

        Task task6 = Task.builder().build();
        Assertions.assertNull(task6.getState());
        Assertions.assertNull(task6.getType());
        Assertions.assertNull(task6.getPriority());
    }

    @Test
    public void testSetters() {
        Task task = Task.builder().build();

        // Fields with null and duration 0
        Assertions.assertNull(task.getState());
        Assertions.assertNull(task.getType());
        Assertions.assertNull(task.getPriority());
        Assertions.assertEquals(0, task.getDuration().intValue());

        // State setter
        task.setState(State.RUNNING);
        Assertions.assertEquals(State.RUNNING, task.getState());
        Assertions.assertNull(task.getType());
        Assertions.assertNull(task.getPriority());
        Assertions.assertEquals(0, task.getDuration().intValue());

        // Priority setter
        task.setPriority(Priority.ZERO);
        Assertions.assertEquals(Priority.ZERO, task.getPriority());
        Assertions.assertNull(task.getType());
        Assertions.assertEquals(0, task.getDuration().intValue());

        // Duration setter
        task.setDuration(new AtomicInteger(25));
        Assertions.assertEquals(25, task.getDuration().intValue());
        Assertions.assertNull(task.getType());
    }

    @Test
    public void testEquals() {
        Task task1 = Task.builder().type(TaskType.MAIN).state(State.READY).priority(Priority.SECOND).duration(40).build();
        Task task2 = Task.builder().type(TaskType.MAIN).build();
        task2.setState(State.READY);
        task2.setPriority(Priority.SECOND);
        task2.setDuration(new AtomicInteger(40));

        Assertions.assertTrue(task1.equals(task2));

        task1.setState(State.SUSPENDED);
        Assertions.assertFalse(task2.equals(task1));
    }

    @Test
    public void testAllPossibleTasks() {
        // Checking of all possible tasks
        TaskType[] types = new TaskType[TaskType.values().length * State.values().length * Priority.values().length * 5];
        State[] states = new State[TaskType.values().length * State.values().length * Priority.values().length * 5];
        Priority[] priorities = new Priority[TaskType.values().length * State.values().length * Priority.values().length * 5];
        int[] durations = new int[TaskType.values().length * State.values().length * Priority.values().length * 5];

        TaskType[] receivedTypes = new TaskType[TaskType.values().length * State.values().length * Priority.values().length * 5];
        State[] receivedStates = new State[TaskType.values().length * State.values().length * Priority.values().length * 5];
        Priority[] receivedPriorities = new Priority[TaskType.values().length * State.values().length * Priority.values().length * 5];
        int[] receivedDurations = new int[TaskType.values().length * State.values().length * Priority.values().length * 5];
        int k = 0;
        for (TaskType type : TaskType.values()) {
            for (State state : State.values()) {
                for (Priority priority : Priority.values()) {
                    for (int i = 1; i < 6; i++) {
                        types[k] = type;
                        states[k] = state;
                        priorities[k] = priority;
                        durations[k] = i;

                        Task createdTask = Task.builder().type(type).state(state).priority(priority).duration(i).build();
                        receivedTypes[k] = createdTask.getType();
                        receivedStates[k] = createdTask.getState();
                        receivedPriorities[k] = createdTask.getPriority();
                        receivedDurations[k] = createdTask.getDuration().intValue();
                        k++;
                    }
                }
            }
        }
        Assertions.assertArrayEquals(types, receivedTypes);
        Assertions.assertArrayEquals(states, receivedStates);
        Assertions.assertArrayEquals(priorities, receivedPriorities);
        Assertions.assertArrayEquals(durations, receivedDurations);
    }
}
