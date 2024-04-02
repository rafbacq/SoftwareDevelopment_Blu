package org.example;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.*;

public class fileMenuTesting extends JPanel implements ActionListener {
    private String file;
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private int versionNumber = 1;
    public  int publicNumberVersions;
    private ArrayList<ArrayList<ActionItem>> allFiles;
    private ArrayList<ActionItem> actionItemList;
    public int versionSelected;


    public fileMenuTesting() {
        setLayout(new GridLayout(4, 0, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        setPreferredSize(new Dimension(600, 800));

        actionItemList = new ArrayList<ActionItem>();
        JButton save = new JButton("Save");
        JButton retrieve = new JButton("Retrieve Current");

        String [] columns = {"Version Number","Version Name","Date Added"};
        HashMapReadTest readWholeHistory = new HashMapReadTest();
        String fileInfo = readWholeHistory.getHistory();
        int numberVersions=0;
        ArrayList <String> dateArray = new ArrayList<>();
        for(int i=0; i<fileInfo.length(); i++){
            if(fileInfo.charAt(i)==';')
            {
                numberVersions++;
                for(int j=i; j>=0; j--)
                {
                    if(Character.isWhitespace(fileInfo.charAt(j)))
                    {
                        dateArray.add(fileInfo.substring(j,i+1));
                    }
                }
            }

        }
        publicNumberVersions=numberVersions;
        String [][] data = new String[10][3];
//        for(int i=0; i<numberVersions; i++)
//        {
//        	data[i][0]="Version " + (i+1) + "";
//        	data[i][1] = dateArray.get(i);
//        }
        tableModel = new DefaultTableModel(new Object[]{"Version Number", "Version Name", "Date Added"}, 0);
        historyTable = new JTable(tableModel);



        JScrollPane scrollPane = new JScrollPane(historyTable);

        add(scrollPane);
        add(save);
        add(retrieve);

        save.addActionListener(this);
        retrieve.addActionListener(this);

        historyTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = historyTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Get the version number from the selected row
                        versionSelected = (int) historyTable.getValueAt(selectedRow, 0);
                        // Load the selected version
                        loadVersion(versionNumber);
                    }
                }
            }
        });

    }

    private void loadVersion(int versionNumber) {
        // Retrieve the corresponding version from history and update the action items
        Map<Integer, ArrayList<ActionItem>> mapFromFile = HashMapReadTest.HashMapFromTextFile();
        ArrayList<ActionItem> selectedVersionItems = mapFromFile.get(versionNumber);
        if (selectedVersionItems != null) {
            // Update the action item list with the selected version items
            updateActionItemList(selectedVersionItems);
            System.out.println("Version " + versionNumber + " loaded.");
        } else {
            System.out.println("Error: Version not found.");
        }
    }

    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();
        if (eventName.equals("Save")) {
            String result = JOptionPane.showInputDialog(null, "Enter Version Name:");
            if (result != null && !result.trim().isEmpty()) {
                HashMapWriteTest hash = new HashMapWriteTest();
                System.out.println(getActionItemList());
                System.out.println(this.actionItemList.size());
                System.out.println("AList Items");
                for(ActionItem i : this.actionItemList)
                {
                    System.out.println(i.toFile());
                }
                String fileString = hash.toString();
                int versionCount=1;
                for(int i=0; i<fileString.length()-5; i++)
                {
                    if(fileString.substring(i,i+5).equals("split"))
                    {
                        versionCount++;

                    }
                }
                hash.createVersion(versionCount, this.actionItemList);
                updateHistoryTable(versionCount, result, LocalDateTime.now());
                versionNumber++;
                System.out.println("Properly Saved");
            }
        } else if (eventName.equals("Retrieve Current")) {
            // Retrieve data
            System.out.println("Retrieving...");
            Map<Integer, ArrayList<ActionItem>> map = HashMapReadTest.HashMapFromTextFile();
            System.out.println(map);
            System.out.println("Map Recovered");
            int selectedRow = historyTable.getSelectedRow() + 1;
            System.out.println("Selected Row: " + selectedRow);
            if (selectedRow != 1)
            {
                //TableModel model = historyTable.getModel();
                //int versionNumber = (int) model.getValueAt(selectedRow, 0);
                //System.out.println("Version Number: " + versionNumber);
                for(int i = 1; i <= map.keySet().size(); i++)
                {
                    if (i == versionNumber)
                    {
                        actionItemList = map.get(i);
                        System.out.println("Version Restored");
                    }
                }
            }
        }
    }

    private void updateHistoryTable(int versionNumber, String versionName, LocalDateTime date) {

        Object[] rowData = new Object[3];
        rowData[0] = versionNumber;
        rowData[1] = versionName;
        rowData[2] = date;
        tableModel.addRow(rowData);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("File Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.getContentPane().add(new FileMenu());
            frame.pack();
            frame.setVisible(true);
        });
    }

    public String getFile() {
        return file;
    }

    public ArrayList<ActionItem> getVersion(String file, int index1, int index2)
    {
        String lookAtThisFile = file.substring(index1,index2-6);
        StringTokenizer st = new StringTokenizer(lookAtThisFile);
        return null;
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

