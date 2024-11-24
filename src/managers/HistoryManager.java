package managers;

import entities.TaskEntity;

import java.util.List;

public interface HistoryManager {
    void add(TaskEntity task);
    List<TaskEntity> getHistory();
}
