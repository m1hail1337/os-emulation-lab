package org.example.os.task;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;

public class Task {
    private final TaskType type;
    private State state;
    private Priority priority;
    private AtomicInteger duration;

    public Task(
        TaskType type,
        State state,
        Priority priority,
        int duration
    ) {
        this.type = type;
        this.state = state;
        this.priority = priority;
        this.duration = new AtomicInteger(duration);
    }

    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    public TaskType getType() {
        return type;
    }

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(final Priority priority) {
        this.priority = priority;
    }

    public AtomicInteger getDuration() {
        return duration;
    }

    public void setDuration(final AtomicInteger duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Task) obj;
        return Objects.equals(this.type, that.type) &&
               Objects.equals(this.state, that.state) &&
               Objects.equals(this.priority, that.priority) &&
               Objects.equals(this.duration.get(), that.duration.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, state, priority, duration);
    }

    @Override
    public String toString() {
        return "Task[" +
               "type=" + type + ", " +
               "state=" + state + ", " +
               "priority=" + priority + ", " +
               "duration=" + duration + ']';
    }
}
