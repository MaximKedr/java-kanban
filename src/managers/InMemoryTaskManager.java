package managers;

import entities.EpicEntity;
import entities.SubTaskEntity;
import entities.TaskEntity;
import entities.TaskStatus;
import utils.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager {

    private Map<Integer, TaskEntity> tasks;
    private Map<Integer, SubTaskEntity> subTasks;
    private Map<EpicEntity, List<Integer>> epics;
    private int taskId;
    private int epicId;
    private int subTaskId;
    private EpicEntity epic;
    private HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epic = new EpicEntity();
        epics = new HashMap<>();
        taskId = 0;
        epicId = 0;
        subTaskId = 0;
    }

    @Override
    public List<TaskEntity> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void setTasks(List<TaskEntity> tasks) {
        Map<Integer, TaskEntity> newTasks = new HashMap<>();
        for (TaskEntity task : tasks) {
            newTasks.put(task.getId(), task);
        }
        this.tasks = newTasks;
    }

    @Override
    public Map<Integer, SubTaskEntity> getSubTasks() {
        return new HashMap(subTasks);
    }

    @Override
    public void setSubTasks(Map<Integer, SubTaskEntity> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public Map<EpicEntity, List<Integer>> getEpics() {
        return new HashMap<>(epics);
    }

    @Override
    public void setEpics(Map<EpicEntity, List<Integer>> epics) {
        this.epics = epics;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    // ========================= TASK  =========================
    @Override
    public int createTask(TaskEntity task) {
        final int id = ++taskId;
        task.setId(id);
        tasks.put(task.getId(), task);
        return id;
    }

    @Override
    public TaskEntity getTaskById(int id) {
        TaskEntity task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public void updateTask(TaskEntity task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    // ========================= SUBTASK  ======================
    @Override
    public int createSubTask(SubTaskEntity subTask) {
        final int id = ++subTaskId;
        subTask.setId(id);
        subTask.setStatus(TaskStatus.NEW);
        subTasks.put(id, subTask);
        epic.addSubtaskId(subTask.getId());

        for (List<Integer> subTaskIds : epics.values()) {
            if (subTaskIds.contains(subTask.getId())) {
                subTaskIds.add(subTask.getId());
                return id;
            }
        }
        updateEpic(epic);
        return id;
    }

    @Override
    public SubTaskEntity getSubTaskById(int id) {
        SubTaskEntity subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Map<Integer, SubTaskEntity> getSubTasksByEpicId(int id) {
        Map<Integer, SubTaskEntity> subTasksInternal = new HashMap<>();
        List<Integer> subTasksIds;

        for (EpicEntity epic : epics.keySet()) {
            if (epic.getId() == id) {
                subTasksIds = epics.get(epic);
                for (Integer idSubTask : subTasksIds) {
                    subTasksInternal.put(idSubTask, subTasks.get(idSubTask));
                }
            }
        }
        return subTasksInternal;
    }

    @Override
    public void updateSubTask(SubTaskEntity subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    @Override
    public void deleteSubTaskById(int id) {
        for (Integer idSubTask : subTasks.keySet()) {
            if (idSubTask == id) {
                subTasks.remove(idSubTask);
            }
        }
    }

    @Override
    public void deleteAllSubTasks() {
        for (int i = 0; i < epics.size(); i++) {
            epics.get(i).clear();
            updateTask(tasks.get(i));
        }
        subTasks.clear();
    }

    // ========================= EPIC  =========================
    @Override
    public void createEpic(EpicEntity epic) {
        final int id = ++epicId;
        epic.setId(id);

        for (int i = 0; i < tasks.size() && i < subTasks.size(); i++) {
            SubTaskEntity subTask = subTasks.get(i);
            subTask.setId(tasks.get(i).getId());
            subTask.setEpicId(id);
            subTask.setName(tasks.get(i).getName());
            subTask.setDescription(tasks.get(i).getDescription());
            subTask.setStatus(tasks.get(i).getStatus());
            epic.addSubtaskId(subTask.getId());
        }
        updateEpic(epic);
    }

    @Override
    public List<Integer> getEpicById(int id) {
        for (EpicEntity epicEntity : epics.keySet()) {
            if (epicEntity.getId() == id) {
                historyManager.add(epicEntity);
                return epics.get(epicEntity);
            }
        }
        return null;
    }

    @Override
    public void updateEpic(EpicEntity epic) {
        epics.put(epic, epic.getSubtaskIds());
        if (epics.get(epic).isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        int countDone = 0;
        int countNew = 0;
        for (int i = 1; i <= subTasks.size(); i++) {
            TaskStatus status = subTasks.get(i).getStatus();

            switch (status) {
                case DONE:
                    countDone++;
                    break;
                case NEW:
                    countNew++;
                    break;
                default:
                    break;
            }
        }
        if (countNew == subTasks.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else if (countDone == subTasks.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public void deleteEpicById(int id) {
        for (EpicEntity epic : epics.keySet()) {
            if (epic.getId() == id) {
                epics.remove(epic);
                epics.get(epic).clear();
            }
        }
    }

    @Override
    public void deleteAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    @Override
    public List<TaskEntity> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public String toString() {
        return "managers.InMemoryTaskManager{" +
                "tasks=" + tasks +
                ", subTasks=" + subTasks +
                ", epics=" + epics +
                '}';
    }
}
