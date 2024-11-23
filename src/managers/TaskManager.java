package managers;

import entities.EpicEntity;
import entities.SubTaskEntity;
import entities.TaskEntity;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    List<TaskEntity> getTasks();

    void setTasks(List<TaskEntity> tasks);

    List<SubTaskEntity> getSubTasks();

    void setSubTasks(List<SubTaskEntity> subTasks);

    Map<EpicEntity, List<Integer>> getEpics();

    void setEpics(Map<EpicEntity, List<Integer>> epics);

    void deleteAllTasks();

    void deleteAllSubTasks();

    void deleteAllEpics();

    TaskEntity getTaskById(int id);

    SubTaskEntity getSubTaskById(int id);

    List<Integer> getEpicById(int id);

    int createTask(TaskEntity task);

    int createSubTask(SubTaskEntity subTask);

    void createEpic(EpicEntity epic);

    void updateTask(TaskEntity task);

    void updateSubTask(SubTaskEntity subTask);

    void updateEpic(EpicEntity epic);

    void deleteTaskById(int id);

    void deleteSubTaskById(int id);

    void deleteEpicById(int id);

    List<SubTaskEntity> getSubTasksByEpicId(int id);

    public List<TaskEntity> getHistory();
}
