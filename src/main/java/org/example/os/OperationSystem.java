package org.example.os;

import org.example.os.generator.TaskGenerator;
import org.example.os.processor.Processor;
import org.example.os.scheduler.Scheduler;

public class OperationSystem {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);
        TaskGenerator taskGenerator = new TaskGenerator(scheduler);
        taskGenerator.generateTasks(10);
        scheduler.launchScheduleDaemon();
    }
}
