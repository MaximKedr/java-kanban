package utils;

import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import managers.TaskManager;

public abstract class Managers {
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
    public static TaskManager getDefaultTaskManager(){
        return new InMemoryTaskManager();
    }
}
