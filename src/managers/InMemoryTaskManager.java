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

    private List<TaskEntity> tasks;
    private List<SubTaskEntity> subTasks;
    private Map<EpicEntity, List<Integer>> epics;
    private int generatedId = 0;
    private EpicEntity epic;
//    private List<TaskEntity> viewedTasks;
    HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        tasks = new ArrayList<>();
        subTasks = new ArrayList<>();
        epic = new EpicEntity();
        epics = new HashMap<>();
    }

    @Override
    public List<TaskEntity> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    @Override
    public List<SubTaskEntity> getSubTasks() {
        return new ArrayList<>(subTasks);
    }

    @Override
    public void setSubTasks(List<SubTaskEntity> subTasks) {
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


    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        for (int i = 0; i < epics.size(); i++) {
            epics.get(i).clear();
            updateTask(tasks.get(i));
        }
    }

    @Override
    public void deleteAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    @Override
    public TaskEntity getTaskById(int id) {
        for (TaskEntity task : tasks) {
            if (task.getId() == id) {
                historyManager.add(task);
                return task;
            }
        }
        return null;
    }

    @Override
    public SubTaskEntity getSubTaskById(int id) {
        SubTaskEntity subTask = subTasks.get(id-1);
        historyManager.add(subTask);
        return subTask;
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
    public int createTask(TaskEntity task) {
        final int id = generatedId;
        task.setId(id);
        tasks.add(task);
        return id;
    }

    @Override
    public int createSubTask(SubTaskEntity subTask) {
        final int id = ++generatedId;
        subTask.setId(id);
        subTask.setStatus(TaskStatus.NEW);
        subTasks.add(subTask);
        epic.addSubtaskId(subTask.getId());
        final int epicId = subTask.getEpicId();

        for (List<Integer> subTaskIds : epics.values()) {
            if (subTaskIds.contains(subTask)) {
                subTaskIds.add(subTask.getId());
                return id;
            }
        }
        updateEpic(epic);

        return id;
    }

    @Override
    public void createEpic(EpicEntity epic) {
        boolean isEpicStatusNew = true;
        final int id = ++generatedId;
        epic.setId(id);

        for (int i = 0; i < tasks.size() && i < subTasks.size(); i++) {
            SubTaskEntity subTask = subTasks.get(i);
            subTask.setId(tasks.get(i).getId());
            subTask.setEpicId(id);
            subTask.setName(tasks.get(i).getName());
            subTask.setDescription(tasks.get(i).getDescription());
            subTask.setStatus(tasks.get(i).getStatus());
            epic.addSubtaskId(subTask.getId());
            if (!tasks.get(i).getStatus().equals(TaskStatus.NEW)) {
                isEpicStatusNew = false;
            }
        }
        updateEpic(epic);
    }

    @Override
    public void updateTask(TaskEntity task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
            }
        }
    }

    @Override
    public void updateSubTask(SubTaskEntity subTask) {
        for (int i = 0; i < subTasks.size(); i++) {
            if (subTasks.get(i).getId() == subTask.getId()) {
                subTasks.set(i, subTask);
            }
        }
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
        for (int i = 0; i < subTasks.size(); i++) {
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
//        epics.put(epic, epic.getSubtaskIds());
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        for (EpicEntity epic : epics.keySet()) {
            boolean isRemove = subTasks
                    .removeIf(subTaskEntity ->
                            subTaskEntity.getId() == id
                    );
            if (isRemove) {
                updateEpic(epic);
                return;
            }
        }
    }

    @Override
    public void deleteEpicById(int id) {
        for (EpicEntity epic : epics.keySet()) {
            if (epic.getId() == id) {
                epics.remove(epic);
            }
        }
    }

    @Override
    public List<SubTaskEntity> getSubTasksByEpicId(int id) {
        List<SubTaskEntity> subTasksInternal = new ArrayList<>();
        for (EpicEntity epic : epics.keySet()) {
            if (epic.getId() == id) {
                subTasksInternal = subTasks;
            }
        }
        return subTasksInternal;
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
