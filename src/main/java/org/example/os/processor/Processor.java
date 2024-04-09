package org.example.os.processor;

import org.example.os.task.Task;

public class Processor {
    private Task currentMainTask;
    private Task currentExtendedTask;
    private boolean isExecuteNow = false;

    private void executeTask() {
        this.isExecuteNow = true;
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000L * currentMainTask.duration());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                this.isExecuteNow = false;
            }
        });
        thread.start();
    }

    public Task getCurrentMainTask() {
        return currentMainTask;
    }

    public void setCurrentMainTask(final Task currentMainTask) {
        this.currentMainTask = currentMainTask;
    }

    public Task getCurrentExtendedTask() {
        return currentExtendedTask;
    }

    public void setCurrentExtendedTask(final Task currentExtendedTask) {
        this.currentExtendedTask = currentExtendedTask;
    }

    public boolean isExecuteNow() {
        return isExecuteNow;
    }
}
