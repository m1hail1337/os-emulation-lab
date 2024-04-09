package org.example.os.task;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;

public record Task(
    TaskType type,
    State state,
    Priority priority,
    int duration
) {
    public static TaskBuilder builder() {
        return new TaskBuilder();
    }
}
