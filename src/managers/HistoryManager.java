package managers;

import entities.TaskEntity;

import java.util.List;

public interface HistoryManager {
    List<TaskEntity>add(TaskEntity task);
    List<TaskEntity> getHistory();
}
