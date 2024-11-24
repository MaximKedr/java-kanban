package managers;

import entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<TaskEntity> viewedTasks = new ArrayList<>();

    @Override
    public void add(TaskEntity task) {
        if (task == null){
            return;
        }
        viewedTasks.add(task);
        while (viewedTasks.size() > 10) {
            viewedTasks.removeFirst();
        }
    }

    @Override
    public List<TaskEntity> getHistory() {
        return viewedTasks;
    }
}
