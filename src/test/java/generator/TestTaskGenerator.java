package generator;

import org.example.os.generator.TaskGenerator;
import org.example.os.processor.Processor;
import org.example.os.scheduler.Scheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTaskGenerator {

    @Test
    public void testGenerateTasksNumber() {
        Processor processor = new Processor();
        Scheduler scheduler = new Scheduler(processor);
        TaskGenerator taskGenerator = new TaskGenerator(scheduler);

        taskGenerator.generateTasks(10);
        Assertions.assertEquals(10, scheduler.getNewTasks().size());

        scheduler.getNewTasks().clear();
        taskGenerator.generateTasks(20);
        Assertions.assertEquals(20, scheduler.getNewTasks().size());

        scheduler.getNewTasks().clear();
        taskGenerator.generateTasks(50);
        Assertions.assertEquals(50, scheduler.getNewTasks().size());

        scheduler.getNewTasks().clear();
        taskGenerator.generateTasks(100);
        Assertions.assertEquals(100, scheduler.getNewTasks().size());

        taskGenerator.generateTasks(25);
        Assertions.assertEquals(125, scheduler.getNewTasks().size());
    }
}
