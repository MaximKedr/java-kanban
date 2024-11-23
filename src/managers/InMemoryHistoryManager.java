package managers;

import entities.EpicEntity;
import entities.SubTaskEntity;
import entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<TaskEntity> viewedTasks = new ArrayList<>();

    @Override
    public List<TaskEntity> add(TaskEntity task) {
        while (viewedTasks.size() > 9) {
            viewedTasks.removeFirst();
        }
        if (task.getClass() == TaskEntity.class) {
            task.incrementTaskHistoryCount();
        } else if (task.getClass() == EpicEntity.class) {
            ((EpicEntity) task).incrementEpicHistoryCount();
        } else if (task.getClass() == SubTaskEntity.class) {
            ((SubTaskEntity) task).incrementSubTaskHistoryCount();
        }
        viewedTasks.add(task);
        return viewedTasks;
    }

    @Override
    public List<TaskEntity> getHistory() {
        return viewedTasks;
    }
}
