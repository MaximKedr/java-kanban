import entities.EpicEntity;
import entities.SubTaskEntity;
import entities.TaskEntity;
import entities.TaskStatus;

import java.util.*;


public class TaskManager {

    public List<TaskEntity> tasks;
    public List<SubTaskEntity> subTasks;
    public Map<EpicEntity, List<SubTaskEntity>> epics;

    public TaskManager() {
        tasks = new ArrayList<>();
        subTasks = new ArrayList<>();
        epics = new HashMap<>();
    }

    public List<TaskEntity> getAllTasks() {
        return tasks;
    }

    public List<SubTaskEntity> getAllSubTasks() {
        return subTasks;
    }

    public Map<EpicEntity, List<SubTaskEntity>> getAllEpics() {
        return epics;
    }

    //    b. Удаление всех задач.
    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public TaskEntity getTaskById(int id) {
        for (TaskEntity task : tasks) {
            if (task.getTaskId() == id) {
                return task;
            }
        }
        System.out.println("Нет такой задачи");
        return null;
    }

    public SubTaskEntity getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public List<SubTaskEntity> getEpicById(int id) {
        EpicEntity epic = new EpicEntity();
        epic.setEpicId(id);
        if (epics.containsKey(epic.getEpicId())) {
            return epics.get(epic);
        }
        return null;
    }

    public void createTask(TaskEntity task) {
        tasks.add(task);
    }

    public void createSubTask(SubTaskEntity subTask) {
        subTasks.add(subTask);
    }

    public void createEpic(EpicEntity epic) {
        List<SubTaskEntity> subTasksInternal = new ArrayList<>(tasks.size());
        boolean isEpicStatusNew = true;

        for (int i = 0; i < tasks.size(); i++) {
            SubTaskEntity subTask = new SubTaskEntity();
            subTask.setTaskId(tasks.get(i).getTaskId());
            subTask.setEpicId(tasks.get(i).getEpicId());
            subTask.setName(tasks.get(i).getName());
            subTask.setDescription(tasks.get(i).getDescription());
            subTask.setStatus(tasks.get(i).getStatus());
            subTasksInternal.add(subTask);
            if (!tasks.get(i).getStatus().equals(TaskStatus.NEW)) {
                isEpicStatusNew = false;
            }
        }
        epic.setTasks(subTasksInternal);
        if (isEpicStatusNew || subTasksInternal.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        }
        epics.put(epic, epic.getTasks());
    }

    public void updateTask(TaskEntity task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId() == task.getTaskId()) {
                tasks.set(i, task);
            }
        }
    }

    public void updateSubTask(SubTaskEntity subTask) {
        for (int i = 0; i < subTasks.size(); i++) {
            if (subTasks.get(i).getTaskId() == subTask.getTaskId()) {
                subTasks.set(i, subTask);
            }
        }
    }

    public void updateEpic(EpicEntity epic) {
        boolean isEpicStatusDone = false;
        for (int i = 0; i < epic.getTasks().size(); i++) {
            if (epic.getTasks().get(i).getStatus().equals(TaskStatus.DONE)) {
                isEpicStatusDone = true;
                break;
            }
        }
        if (isEpicStatusDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
        epics.put(epic, epic.getTasks());
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubTaskById(int id) {
        subTasks.remove(id);
    }

    public void deleteEpicById(int id) {
        for (EpicEntity epic : epics.keySet()) {
            if (epic.getEpicId() == id) {
                epics.remove(id);
            }
        }
    }

    public List<SubTaskEntity> getSubTasksByEpicId(int id) {
        List<SubTaskEntity> subTasksInternal = new ArrayList<>();
        for (EpicEntity epic : epics.keySet()) {
            if (epic.getEpicId() == id) {
                subTasksInternal = epics.get(epic);
            }
        }
        return subTasksInternal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskManager that = (TaskManager) o;
        return Objects.equals(tasks, that.tasks)
                && Objects.equals(subTasks, that.subTasks)
                && Objects.equals(epics, that.epics);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        result = 31 * result + (subTasks != null ? subTasks.hashCode() : 0);
        result = 31 * result + (epics != null ? epics.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "tasks=" + tasks +
                ", subTasks=" + subTasks +
                ", epics=" + epics +
                '}';
    }
}
