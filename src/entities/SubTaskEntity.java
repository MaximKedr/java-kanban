package entities;

public class SubTaskEntity extends TaskEntity {
    private int epicId;

    public SubTaskEntity(int id, int epicId, String name, String description) {
        super(id, name, description);
        this.epicId = epicId;
    }

      public int getEpicId() {
        return this.epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}






