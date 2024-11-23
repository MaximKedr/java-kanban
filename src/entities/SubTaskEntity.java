package entities;

public class SubTaskEntity extends TaskEntity {
    private int epicId;
    private int subTaskHistoryCount;

    public SubTaskEntity(int id, int epicId, String name, String description) {
        super(id, name, description);
        this.epicId = epicId;
    }

    public int getSubTaskHistoryCount() {
        return subTaskHistoryCount;
    }

    public void incrementSubTaskHistoryCount() {
        subTaskHistoryCount++;
    }

    public int getEpicId() {
        return this.epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}






