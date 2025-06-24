package TaskSetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class taskmanager {

    private final List<task> tasks = new ArrayList<>();

    public void addTask(task task) {
        tasks.add(task);
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markCompleted();
        }
    }

    public void sortTasksByDueDate() {
        Collections.sort(tasks, Comparator.comparing(task::getDueDate));
    }

    public List<task> getAllTasks() {
        return tasks;
    }
}
