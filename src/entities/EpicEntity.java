package entities;

import java.util.ArrayList;
import java.util.List;

public class EpicEntity extends TaskEntity {

    private List<SubTaskEntity> tasks;

    public EpicEntity(String name, String description) {
        super.setName(name);
        super.setDescription(description);
        tasks = new ArrayList<>();
    }

    public EpicEntity() {
    }

    public String getEpicName() {
        return super.getName();
    }

    public void setEpicName(String epicName) {
        super.setName(epicName);
    }

    public List<SubTaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<SubTaskEntity> tasks) {
        this.tasks = tasks;
    }

}
