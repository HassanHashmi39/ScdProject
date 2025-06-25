package taskmanager;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskManager extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTextPane txtDueDate;
    private JTextPane txtTitle;
    private List list;

    private ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TaskManager frame = new TaskManager();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public TaskManager() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(135, 206, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnAddTask = new JButton("Add Task");
        btnAddTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        btnAddTask.setBackground(new Color(154, 205, 50));
        btnAddTask.setBounds(103, 113, 89, 23);
        contentPane.add(btnAddTask);

        JButton btnSort = new JButton("Sort by Date");
        btnSort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortTasks();
            }
        });
        btnSort.setHorizontalAlignment(SwingConstants.LEFT);
        btnSort.setBounds(202, 113, 110, 23);
        contentPane.add(btnSort);

        JButton btnMarkComplete = new JButton("Mark Complete");
        btnMarkComplete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markTaskComplete();
            }
        });
        btnMarkComplete.setHorizontalAlignment(SwingConstants.LEFT);
        btnMarkComplete.setBounds(202, 240, 125, 23);
        contentPane.add(btnMarkComplete);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });
        btnDelete.setBackground(Color.RED);
        btnDelete.setBounds(103, 240, 89, 23);
        contentPane.add(btnDelete);

        txtDueDate = new JTextPane(); 
        txtDueDate.setBounds(103, 82, 194, 20);
        contentPane.add(txtDueDate);

        JLabel lblTitle = new JLabel("Enter the Title of Task");
        lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        lblTitle.setBounds(103, 22, 150, 14);
        contentPane.add(lblTitle);

        JLabel lblDueDate = new JLabel("Enter the Due Date (DD-MM-YYYY)");
        lblDueDate.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        lblDueDate.setBounds(103, 63, 202, 14);
        contentPane.add(lblDueDate);

        txtTitle = new JTextPane();
        txtTitle.setBounds(103, 36, 194, 20);
        contentPane.add(txtTitle);

        list = new List(); 
        list.setBounds(103, 142, 194, 92);
        contentPane.add(list);
    }

    

  //Functions//

    private void addTask() {
        String title = txtTitle.getText().trim();
        String dueDateStr = txtDueDate.getText().trim();

        if (title.isEmpty() || dueDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill both fields.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dueDate = LocalDate.parse(dueDateStr, formatter);

            Task newTask = new Task(title, dueDate);
            taskList.add(newTask);
            list.add(newTask.toString());

            txtTitle.setText("");
            txtDueDate.setText("");

            JOptionPane.showMessageDialog(this, "Task Added Successfully.");

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Please enter date in DD-MM-YYYY format.");
        }
    }

    private void deleteTask() {
        int selectedIndex = list.getSelectedIndex();

        if (selectedIndex != -1) {
            taskList.remove(selectedIndex);
            list.remove(selectedIndex);

            JOptionPane.showMessageDialog(this, "Task Deleted.");
            replayOption();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.");
        }
    }

    private void markTaskComplete() {
        int selectedIndex = list.getSelectedIndex();

        if (selectedIndex != -1) {
            Task selectedTask = taskList.get(selectedIndex);
            selectedTask.markAsCompleted();

            list.replaceItem(selectedTask.toString(), selectedIndex);

            JOptionPane.showMessageDialog(this, "Task Marked as Completed.");
            replayOption();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark complete.");
        }
    }

    private void sortTasks() {
        Collections.sort(taskList, Comparator.comparing(Task::getDueDate));
        list.removeAll();

        for (Task t : taskList) {
            list.add(t.toString());
        }

        JOptionPane.showMessageDialog(this, "Tasks Sorted by Date.");
    }

    private void replayOption() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to continue?", "Continue?", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}
