package managers;

import entities.EpicEntity;
import entities.SubTaskEntity;
import entities.TaskEntity;
import entities.TaskStatus;

import java.util.*;


public class TaskManager {

    private List<TaskEntity> tasks;
    private List<SubTaskEntity> subTasks;
    private Map<EpicEntity, List<SubTaskEntity>> epics;
    private int generatedId = 0;

    public TaskManager() {
        tasks = new ArrayList<>();
        subTasks = new ArrayList<>();
        epics = new HashMap<>();
    }

    public List<TaskEntity> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public List<SubTaskEntity> getSubTasks() {
        return new ArrayList<>(subTasks);
    }

    public void setSubTasks(List<SubTaskEntity> subTasks) {
        this.subTasks = subTasks;
    }

    public Map<EpicEntity, List<SubTaskEntity>> getEpics() {
        return new HashMap<>(epics);
    }

    public void setEpics(Map<EpicEntity, List<SubTaskEntity>> epics) {
        this.epics = epics;
    }


    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubTasks() {
        for (int i = 0; i < epics.size(); i++) {
            epics.get(i).clear();
            updateTask(tasks.get(i));
        }
    }

    public void deleteAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    public TaskEntity getTaskById(int id) {
        for (TaskEntity task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public SubTaskEntity getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public List<SubTaskEntity> getEpicById(int id) {
        for (EpicEntity epicEntity : epics.keySet()) {
            if (epicEntity.getId() == id) {
                return epics.get(epicEntity);
            }
        }
        return null;
    }

    public int createTask(TaskEntity task) {
        final int id = ++generatedId;
        task.setId(id);
        tasks.add(task);
        return id;
    }

    public int createSubTask(SubTaskEntity subTask) {
        final int id = ++generatedId;
        subTask.setId(id);
        subTasks.add(subTask);
        return id;
    }

    public void createEpic(EpicEntity epic) {
        List<SubTaskEntity> subTasksInternal = new ArrayList<>(tasks.size());
        boolean isEpicStatusNew = true;
        final int id = ++generatedId;
        for (int i = 0; i < tasks.size(); i++) {
            SubTaskEntity subTask = new SubTaskEntity();
            subTask.setId(tasks.get(i).getId());
            subTask.setEpicId(id);
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
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
            }
        }
    }

    public void updateSubTask(SubTaskEntity subTask) {
        for (int i = 0; i < subTasks.size(); i++) {
            if (subTasks.get(i).getId() == subTask.getId()) {
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
        for (EpicEntity epic : epics.keySet()) {
            boolean isRemove = epic
                    .getTasks()
                    .removeIf(subTaskEntity ->
                            subTaskEntity.getId() == id
                    );
            if (isRemove) {
                updateEpic(epic);
                return;
            }
        }
    }

    public void deleteEpicById(int id) {
        for (EpicEntity epic : epics.keySet()) {
            if (epic.getId() == id) {
                epics.remove(epic);
            }
        }
    }

    public List<SubTaskEntity> getSubTasksByEpicId(int id) {
        List<SubTaskEntity> subTasksInternal = new ArrayList<>();
        for (EpicEntity epic : epics.keySet()) {
            if (epic.getId() == id) {
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
        return "managers.TaskManager{" +
                "tasks=" + tasks +
                ", subTasks=" + subTasks +
                ", epics=" + epics +
                '}';
    }
}
