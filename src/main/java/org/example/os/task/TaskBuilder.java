package org.example.os.task;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;

public class TaskBuilder {
    private TaskType type;
    private State state;
    private Priority priority;
    private int duration;   //seconds

    public TaskBuilder type(final TaskType type) {
        this.type = type;
        return this;
    }

    public TaskBuilder state(final State state) {
        this.state = state;
        return this;
    }

    public TaskBuilder priority(final Priority priority) {
        this.priority = priority;
        return this;
    }

    public TaskBuilder duration(final int duration) {
        this.duration = duration;
        return this;
    }

    public Task build() {
        return new Task(type, state, priority, duration);
    }
}