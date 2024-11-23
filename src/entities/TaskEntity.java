package entities;

public class TaskEntity {
    private int id = 0;
    private String name;
    private String description;
    private TaskStatus status;
    private int taskHistoryCount;

    public TaskEntity(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public TaskEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getTaskHistoryCount() {
        return taskHistoryCount;
    }

    public void incrementTaskHistoryCount() {
        taskHistoryCount++;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "taskId=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
