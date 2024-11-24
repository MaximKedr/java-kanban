package managers;

import entities.EpicEntity;
import entities.SubTaskEntity;
import entities.TaskEntity;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    Map<Integer, TaskEntity> getTasks();

    void setTasks(Map<Integer, TaskEntity> tasks);

    Map<Integer, SubTaskEntity> getSubTasks();

    void setSubTasks(Map<Integer, SubTaskEntity> subTasks);

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

    Map<Integer, SubTaskEntity> getSubTasksByEpicId(int id);

    List<TaskEntity> getHistory();
}
