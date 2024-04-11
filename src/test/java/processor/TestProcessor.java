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

        int duration = 4;
        Task task = new Task(TaskType.MAIN, State.READY, Priority.THIRD, duration);
        processor.executeTask(task);

        Thread.sleep(1000L * duration / 2);
        Assertions.assertEquals(task, processor.getExecutionTask());
        Assertions.assertEquals(State.RUNNING, processor.getExecutionTask().getState());

        Thread.sleep(1000L * duration);
        Assertions.assertEquals(State.SUSPENDED, processor.getExecutionTask().getState());
    }

    @Test
    public void testExtendedTask() throws InterruptedException {
        Processor processor = new Processor();

        int duration = 4;
        Task task = new Task(TaskType.EXTENDED, State.READY, Priority.THIRD, duration);
        processor.executeTask(task);

        Thread.sleep(1000L * duration / 2);
        Assertions.assertEquals(task, processor.getExecutionTask());
        Assertions.assertEquals(State.RUNNING, processor.getExecutionTask().getState());

        Thread.sleep(1000L * duration);
        Assertions.assertEquals(State.SUSPENDED, processor.getExecutionTask().getState());
    }

    @Test
    public void testInterruptCurrentTask() throws InterruptedException {
        Processor processor = new Processor();

        int duration = 4;
        Task task = new Task(TaskType.MAIN, State.READY, Priority.THIRD, duration);

        processor.executeTask(task);
        Thread.sleep(1000L * duration / 2);
        Assertions.assertEquals(State.RUNNING, processor.getExecutionTask().getState());
        processor.interruptCurrentTask();
        Assertions.assertNotEquals(State.READY, processor.getExecutionTask().getState());
    }
}
