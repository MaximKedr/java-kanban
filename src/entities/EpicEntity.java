package entities;

import java.util.ArrayList;
import java.util.List;

public class EpicEntity extends TaskEntity {

    public EpicEntity() {
    }

    protected List<Integer> subtaskIds = new ArrayList<>();

    public EpicEntity(int id, String name, String description) {
        super(id, name, description);
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }

    public void removeSubtask(int id) {
        subtaskIds.remove(Integer.valueOf(id));
    }
}
