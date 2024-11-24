package test;

import entities.EpicEntity;
import entities.SubTaskEntity;
import entities.TaskEntity;
import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InMemoryTaskManagerTest {
    TaskManager taskManager = new InMemoryTaskManager();

    // ========================= TASK  =========================
    @Test
    public void createTask() {
        TaskEntity task = new TaskEntity(
                1, "task1", "description1");
        taskManager.createTask(task);
        assertEquals(task, taskManager.getTasks().get(task.getId()), "Task не создана");
    }

    @Test
    public void getTaskById() {
        int id = 2;
        TaskEntity task = new TaskEntity(
                id, "task2", "description2");
        taskManager.createTask(task);
        assertEquals(task, taskManager.getTaskById(task.getId()), "Task не найден");
    }

    @Test
    public void updateTask() {
        TaskEntity task = new TaskEntity(
                1, "task1", "description1");
        taskManager.createTask(task);
        task.setDescription("description2");
        taskManager.updateTask(task);
        assertEquals("description2", taskManager.getTasks()
                .get(task.getId())
                .getDescription(), "Task не обновлена");
    }

    @Test
    public void deleteTask() {
        TaskEntity task = new TaskEntity(
                1, "task1", "description1");
        taskManager.createTask(task);
        taskManager.deleteTaskById(task.getId());
        assertNull(taskManager.getTaskById(task.getId()), "Task не удалена");
    }

    // ========================= SUBTASK  =========================
    @Test
    public void createSubTask() {
        EpicEntity epic = new EpicEntity(1, "epic1","description1");
        SubTaskEntity subTask = new SubTaskEntity(1, epic.getId(), "subtask1", "description1");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        assertEquals(subTask,taskManager.getSubTasks().get(subTask.getId()), "Subtask не создана");
    }

    @Test
    public void getSubTaskById() {
        EpicEntity epic = new EpicEntity();
        SubTaskEntity subTask = new SubTaskEntity(1, epic.getId(), "subtask1", "description1");
        taskManager.createSubTask(subTask);
        assertEquals(subTask, taskManager.getSubTaskById(subTask.getId()), "Subtask не найдена");
    }

    @Test
    public void updateSubTask() {
        EpicEntity epic = new EpicEntity();
        SubTaskEntity subTask = new SubTaskEntity(1, epic.getId(), "subtask1", "description1");
        taskManager.createSubTask(subTask);
        subTask.setDescription("description2");
        taskManager.updateSubTask(subTask);
        assertEquals("description2", taskManager.getSubTasks()
                .get(subTask.getId())
                .getDescription(), "Subtask не обновлена");
    }

    @Test
    public void deleteSubTask() {
        EpicEntity epic = new EpicEntity();
        SubTaskEntity subTask = new SubTaskEntity(1, epic.getId(), "subtask1", "description1");
        taskManager.createSubTask(subTask);
        taskManager.deleteSubTaskById(subTask.getId());
        assertNull(taskManager.getSubTaskById(subTask.getId()), "Task не удалена");

    }

    //    ========================= EPIC =========================
    @Test
    public void createEpic() {
        EpicEntity epic = new EpicEntity();
        TaskEntity task1 = new TaskEntity(1, "task1", "description1");
        TaskEntity task2 = new TaskEntity(2, "task2", "description2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        SubTaskEntity subTask1 = new SubTaskEntity(1, epic.getId(), "subtask1", "description1");
        SubTaskEntity subTask2 = new SubTaskEntity(2, epic.getId(), "subtask2", "description2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        assertNotNull(taskManager.getEpicById(epic.getId()), "Epic не создан");
    }

    @Test
    public void getEpicById() {
        EpicEntity epic = new EpicEntity();
        TaskEntity task1 = new TaskEntity(1, "task1", "description1");
        TaskEntity task2 = new TaskEntity(2, "task2", "description2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        SubTaskEntity subTask1 = new SubTaskEntity(1, epic.getId(), "subtask1", "description1");
        SubTaskEntity subTask2 = new SubTaskEntity(2, epic.getId(), "subtask2", "description2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        assertNotNull(taskManager.getEpicById(epic.getId()), "Epic не найден");
    }

    @Test
    public void updateEpic() {
        EpicEntity epic = new EpicEntity();
        TaskEntity task1 = new TaskEntity(1, "task1", "description1");
        TaskEntity task2 = new TaskEntity(2, "task2", "description2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        SubTaskEntity subTask1 = new SubTaskEntity(1, epic.getId(), "subtask1", "description1");
        SubTaskEntity subTask2 = new SubTaskEntity(2, epic.getId(), "subtask2", "description2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        epic.setName("updateEpic1");
        taskManager.updateEpic(epic);
        assertEquals(epic.getName(), "updateEpic1");
    }

    @Test
    public void deleteEpic() {
        EpicEntity epic = new EpicEntity();
        TaskEntity task1 = new TaskEntity(1, "task1", "description1");
        TaskEntity task2 = new TaskEntity(2, "task2", "description2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        SubTaskEntity subTask1 = new SubTaskEntity(1, epic.getId(), "subtask1", "description1");
        SubTaskEntity subTask2 = new SubTaskEntity(2, epic.getId(), "subtask2", "description2");
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        assertFalse(taskManager.getEpics().containsKey(epic.getId()), "Epic не удален");
    }

    //     ========================= HISTORY =========================
    @Test
    public void getHistory() {
        EpicEntity epic = new EpicEntity(1, "epic1","description1");
        taskManager.createEpic(epic);
        TaskEntity task1 = new TaskEntity(1, "task1", "description1");
        TaskEntity task2 = new TaskEntity(2,"task2", "description2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        SubTaskEntity subTask1 = new SubTaskEntity(1, epic.getId(), "subtask1", "description1");
        SubTaskEntity subTask2 = new SubTaskEntity(2, epic.getId(), "subtask2", "description2");
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        int id = 1;
        taskManager.getTaskById(id);
        taskManager.getEpicById(id);
        taskManager.getSubTaskById(id);
        assertEquals(3, taskManager.getHistory().size());
    }

}
