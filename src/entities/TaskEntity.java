package entities;

public class TaskEntity {
    private int taskId = 1;
    private int epicId;
    private String name;
    private String description;
    private TaskStatus status;

    public TaskEntity(int epicId, String name, String description, entities.TaskStatus status) {
        setTaskId(taskId++);
        this.epicId = epicId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public TaskEntity() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public entities.TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "taskId=" + taskId +
                ", epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
