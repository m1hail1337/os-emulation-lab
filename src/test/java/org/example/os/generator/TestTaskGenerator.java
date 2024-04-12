package org.example.os.generator;

import org.example.os.processor.Processor;
import org.example.os.scheduler.Scheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTaskGenerator {

    @Test
    public void testGenerateTasksNumber() throws InterruptedException {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);
        TaskGenerator taskGenerator = new TaskGenerator(scheduler);

        int interval = 1;

        taskGenerator.generateTasks(10, interval);
        while (scheduler.getNewTasks().size() != 10) {
            Thread.sleep(1);
        }
        Assertions.assertEquals(10, scheduler.getNewTasks().size());

        scheduler.getNewTasks().clear();
        taskGenerator.generateTasks(20, interval);
        while (scheduler.getNewTasks().size() != 20) {
            Thread.sleep(1);
        }
        Assertions.assertEquals(20, scheduler.getNewTasks().size());

        scheduler.getNewTasks().clear();
        taskGenerator.generateTasks(50, interval);
        while (scheduler.getNewTasks().size() != 50) {
            Thread.sleep(1);
        }
        Assertions.assertEquals(50, scheduler.getNewTasks().size());

        scheduler.getNewTasks().clear();
        taskGenerator.generateTasks(100, interval);
        while (scheduler.getNewTasks().size() != 100) {
            Thread.sleep(1);
        }
        Assertions.assertEquals(100, scheduler.getNewTasks().size());

        taskGenerator.generateTasks(25, interval);
        while (scheduler.getNewTasks().size() != 125) {
            Thread.sleep(1);
        }
        Assertions.assertEquals(125, scheduler.getNewTasks().size());
    }
}
