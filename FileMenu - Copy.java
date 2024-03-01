package Software_Development;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileMenu extends JPanel implements ActionListener {
    private String file;
    private JTable historyTable;
    public static int numberVersions;

    public FileMenu() {
        setLayout(new GridLayout(4, 0, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        setPreferredSize(new Dimension(600, 800));

        JButton save = new JButton("Save");
        JButton retrieve = new JButton("Retrieve Current");
        historyTable = new JTable();
        String [] columns = {"Version", "Title","Date Added"};
        String [][] data = new String[numberVersions][3];
        HashMapReadTest readWholeHistory = new HashMapReadTest();
        JScrollPane scrollPane = new JScrollPane(historyTable);
        historyTable.
        add(scrollPane);
        add(save);
        add(retrieve);

        save.addActionListener(this);
        retrieve.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand(); 
        if (eventName.equals("Save")) {
            int result = JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Replace Your File?");
            if (result == JOptionPane.OK_OPTION) {
                // Save data
                new HashMapWriteTest().createVersion(1, new Task[]{});
                numberVersions++;
            }
        } else if (eventName.equals("Retrieve Current")) {
            // Retrieve data
           
        }
    }

   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("File Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new FileMenu());
            frame.pack();
            frame.setVisible(true);
        });
    }

    public String getFile() {
        return file;
    }
}

class TaskList {
    public String entireList;

    public TaskList(String list) {
        entireList = list;
    }

    public String getEntireList() {
        return entireList;
    }
}

class Task extends ActionItem {
    private String name;
    private String status;
    private LocalDateTime dateAdded;
    private LocalDateTime dateCompleted;
    private String comments;

    public Task(String name, String status, LocalDateTime dateAdded, LocalDateTime dateCompleted, String comments) {
        this.name = name;
        this.status = status;
        this.dateAdded = dateAdded;
        this.dateCompleted = dateCompleted;
        this.comments = comments;
    }

    public String toString() {
        return "Name: " + name + ", Status: " + status + ", Date Added: " + dateAdded + ", Date Completed: " + dateCompleted + ", Comments: " + comments + "\n";
    }

    public String toFile() {
        return name + "," + status + "," + dateAdded + "," + dateCompleted + "," + comments;
    }
}
