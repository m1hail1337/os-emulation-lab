package processor;

import org.example.os.enums.Priority;
import org.example.os.enums.State;
import org.example.os.enums.TaskType;
import org.example.os.processor.Processor;
import org.example.os.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProcessor {

    @Test
    public void testExecuteTask() throws InterruptedException {
        Processor processor = new Processor();

        int duration1 = 1;
        Task task1 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, duration1);
        long startTime = System.currentTimeMillis();
        processor.executeTask(task1);
        Thread.sleep(1000L * duration1);
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        Assertions.assertEquals(duration1, elapsedTime);

        int duration2 = 2;
        Task task2 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, duration2);
        startTime = System.currentTimeMillis();
        processor.executeTask(task2);
        Thread.sleep(1000L * duration2);
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        Assertions.assertEquals(duration2, elapsedTime);

        int duration3 = 3;
        Task task3 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, duration3);
        startTime = System.currentTimeMillis();
        processor.executeTask(task3);
        Thread.sleep(1000L * duration3);
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        Assertions.assertEquals(duration3, elapsedTime);

        int duration4 = 1;
        Task task4 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, duration4);
        startTime = System.currentTimeMillis();
        processor.executeTask(task4);
        Thread.sleep(1000L * duration4);
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        Assertions.assertEquals(duration4, elapsedTime);

        int duration5 = 1;
        Task task5 = new Task(TaskType.MAIN, State.SUSPENDED, Priority.THIRD, duration5);
        startTime = System.currentTimeMillis();
        processor.executeTask(task5);
        Thread.sleep(1000L * duration5);
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        Assertions.assertEquals(duration5, elapsedTime);
    }
}
