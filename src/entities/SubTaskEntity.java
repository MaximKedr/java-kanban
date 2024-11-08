package entities;

public class SubTaskEntity extends TaskEntity {
    private EpicEntity epic;
//    private int epicId = epic.getId();


    public SubTaskEntity(int id, int epicId, String name, String description) {
        super(id, name, description);
        epic = new EpicEntity();
        epic.setId(id);
    }

    public SubTaskEntity() {
        epic = new EpicEntity();
    }

    public int getEpicId() {
        return epic.getId();
    }

    public void setEpicId(int epicId) {
        epic.setId(epicId);
    }
}
