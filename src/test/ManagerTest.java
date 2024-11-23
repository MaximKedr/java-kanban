package test;

import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import utils.Managers;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagerTest {

    @Test
    public void getDefaultHistory(){
        HistoryManager historyManager = Managers.getDefaultHistory();
             assertTrue(historyManager.getClass().equals(InMemoryHistoryManager.class),
                     "getDefaultHistory не создаёт InMemoryHistoryManager");
    }

    @Test
    public void getDefaultTaskManager(){
        TaskManager taskManager = Managers.getDefaultTaskManager();
        assertTrue(taskManager.getClass().equals(InMemoryTaskManager.class),
                "getDefaultTaskManager не создает InMemoryTaskManager");
    }
}
