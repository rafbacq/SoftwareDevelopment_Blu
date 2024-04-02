
package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileMenu extends JPanel implements ActionListener {
   // private JFrame frame;
    private final JTable historyTable;
    private final DefaultTableModel tableModel;
    private int versionNumber = 1;
    private ArrayList<ArrayList<Action>> allFiles;
    private ArrayList<Action> actionItemList;
    private int versionSelected;
    private String versionSelectedInfo;
    //private Test testInstance = Test.getInstance();
    public FileMenu(){
        //frame = new JFrame("File Menu");
        setLayout(new GridLayout(4, 0, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        setPreferredSize(new Dimension(400, 600));

        JButton save = new JButton("Save");
        JButton retrieve = new JButton("Retrieve Current");

        String[] columns = {"Version Number", "Version Name", "Date Added"};
        tableModel = new DefaultTableModel(0, 0);
        historyTable = new JTable(tableModel);
        tableModel.setColumnIdentifiers(columns);
        historyTable.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(historyTable);

        add(scrollPane);
        add(save);
        add(retrieve);

        save.addActionListener(this);
        retrieve.addActionListener(this);
        try {
            BufferedReader br = new BufferedReader(new FileReader("history.txt"));
            String historyElement = br.readLine();
            while(historyElement!=null)
            {
                tableModel.addRow(new Object[]{(tableModel.getRowCount()+1), historyElement, "idkman"});
                historyElement=br.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        historyTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = historyTable.getSelectedRow();
                    if (selectedRow != -1) {
                        versionSelected = (int) historyTable.getValueAt(selectedRow, 0);
                       // loadVersion(versionSelected); // Call loadVersion with the selected version number
                    }
                }
            }
        });


        setVisible(true);
    }

   /* private void loadVersion(int versionNumber) {
        // Retrieve the corresponding version from history and update the action items
        Map<Integer, ArrayList<Action>> mapFromFile = HashMapReadTest.HashMapFromTextFile();
        ArrayList<Action> selectedVersionItems = mapFromFile.get(versionNumber);
        if (selectedVersionItems != null) {
            // Update the action item list with the selected version items
            updateActionItemList(selectedVersionItems);
            System.out.println("Version " + versionNumber + " loaded.");
        } else {
            System.out.println("Error: Version not found.");
        }
    }*/
    public String readFile(String filename) throws IOException {
        this.checkFile(filename);
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    private void checkFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists())
                file.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private void setCurrentFileInfo(String fullFile,  int selectedVersion){

        String versionStr = "Version: "+selectedVersion;
        fullFile = fullFile.substring(fullFile.indexOf(versionStr));
        versionSelectedInfo= fullFile.substring(fullFile.indexOf(versionStr) , fullFile.indexOf("*"));
    }
    public void actionPerformed(ActionEvent event)  {
        String eventName = event.getActionCommand();

        String fullFile= null;
        try {
            fullFile = readFile("version.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (eventName.equals("Save")) {
            if (Test.getInstance().getTable().getRowCount()== 0 ) return;
            String result = JOptionPane.showInputDialog(null, "Enter Version Name:");
            System.out.println(result);
            tableModel.addRow(new Object[]{(tableModel.getRowCount()+1), result, "idkman"});
            checkFile("history.txt");
            try {
                FileWriter historyWriter = new FileWriter("history.txt",true);
                historyWriter.append(result).append("\n");
                historyWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int versionNum=1;
            try {
                if ( fullFile != null && !fullFile.isEmpty()) {
                    for (int i = 0; i < fullFile.length(); i++) {
                        char c = fullFile.charAt(i);
                        if (c == '*') {
                            versionNum++;
                        }
                    }
                }

                FileWriter bf = new FileWriter("version.txt", true);
                TableModel table = Test.getInstance().getTable();
                bf.write("Version: " + versionNum + "\n");
                for(int i=0; i<table.getRowCount(); i++)
                {
                    String taskInfo = String.valueOf(table.getValueAt(i,0));
                    String dateInfo = String.valueOf(table.getValueAt(i,1));
                    String urgencyInfo = String.valueOf(table.getValueAt(i,2));
                    bf.write("Task: " + taskInfo + "\n");
                    bf.write("Date: " + dateInfo + "\n");
                    bf.write("Urgency: " + urgencyInfo + "\n");

                }
                bf.write("*" + "\n");
                bf.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            fullFile= null;
            try {
                fullFile = readFile("version.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setCurrentFileInfo(fullFile, versionNum);
            Test.getInstance().updateTable(versionSelectedInfo);
        } else if (eventName.equals("Retrieve Current")) {
            // Retrieve data
            versionSelected = historyTable.getSelectedRow();
            if (versionSelected == -1) return;
            versionSelected +=1;
            this.setCurrentFileInfo(fullFile, versionSelected);
        }
    }
    public String getSelectedFileInfo()
    {
        return versionSelectedInfo;
    }


   private void updateHistoryTable(int versionNumber, String versionName, LocalDateTime date) {

	   	Object[] rowData = new Object[3];
	   	rowData[0] = versionNumber;
	   	rowData[1] = versionName;
	   	rowData[2] = date;
        tableModel.addRow(rowData);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("File Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new FileMenu());
            frame.pack();
            frame.setVisible(true);
        });
    }



   /* public ArrayList<ActionItem> getVersion(String file, int index1, int index2)
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
    }*/
}


