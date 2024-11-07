package entities;

import java.util.ArrayList;
import java.util.List;

public class EpicEntity extends TaskEntity {

    private int epicId;
    private String epicName;
    private TaskStatus epicStatus;
    private List<SubTaskEntity> tasks;

    public EpicEntity(int aepicId, String name, String description, TaskStatus status) {
        super();
        setEpicId(epicId++);
        tasks = new ArrayList<>();
    }

    public EpicEntity() {
    }

    @Override
    public int getEpicId() {
        return epicId;
    }

    @Override
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public String getEpicName() {
        return epicName;
    }

    public void setEpicName(String epicName) {
        this.epicName = epicName;
    }

    public TaskStatus getEpicStatus() {
        return epicStatus;
    }

    public void setEpicStatus(TaskStatus epicStatus) {
        this.epicStatus = epicStatus;
    }

    public List<SubTaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<SubTaskEntity> tasks) {
        this.tasks = tasks;
    }
}
