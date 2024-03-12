package smth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileMenu extends JPanel implements ActionListener {
    private String file;
    private JTextArea historyTextArea;
    private int versionNumber = 1;
    private ArrayList<ActionItem> actionItemList;


    public FileMenu() {
        setLayout(new GridLayout(4, 0, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        setPreferredSize(new Dimension(600, 800));

        actionItemList = new ArrayList<ActionItem>();
        JButton save = new JButton("Save");
        JButton retrieve = new JButton("Retrieve Current");
        historyTextArea = new JTextArea(20, 50);
        JScrollPane scrollPane = new JScrollPane(historyTextArea);
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
//            	actionItemList = idek()
                HashMapWriteTest hash = new HashMapWriteTest();
                System.out.println(getActionItemList());
                System.out.println(this.actionItemList.size());
                System.out.println("AList Items");
                for(ActionItem i : this.actionItemList) 
                {
                	System.out.println(i.toFile());
                }
                hash.createVersion(versionNumber, this.actionItemList);
                versionNumber++;
                System.out.println("Properly Saved");
            }
        } else if (eventName.equals("Retrieve Current")) {
            // Retrieve data
            String history = new HashMapReadTest().getHistory();
            displayHistory(history);
        }
    }

    private void displayHistory(String history) {
        historyTextArea.setText(history);
    }
    
    private FileMenu idek(Test t) 
    {
    	return t.getFileMenu();
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
    
    public void updateActionItemList(ArrayList<ActionItem> updatedList) {
        actionItemList = updatedList;
    }
    
    public ArrayList<ActionItem> getActionItemList()
    {
    	return actionItemList;
    }
    
    public void setActionItemList(ArrayList<ActionItem> a) 
    {
    	actionItemList = a;
    }
    
    public void addItem(ActionItem a) 
    {
    	actionItemList.add(a);
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