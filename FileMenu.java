package Software_Development;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
public class FileMenu extends JPanel implements ActionListener {
    private String file;
    private JTable historyTable;
    public  int publicNumberVersions;
    private ArrayList<ArrayList<ActionItem>> allFiles;

    public FileMenu() {
        setLayout(new GridLayout(4, 0, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        setPreferredSize(new Dimension(600, 800));

        JButton save = new JButton("Save");
        JButton retrieve = new JButton("Retrieve Current");
        String [] columns = {"Version","Date Added"};
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
        String [][] data = new String[numberVersions][2];
        for(int i=0; i<numberVersions; i++)
        {
        	data[i][0]="Version " + (i+1) + "";
        	data[i][1] = dateArray.get(i);
        }
        historyTable = new JTable(data,columns);


        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane);
        add(save);
        add(retrieve);

        save.addActionListener(this);
        retrieve.addActionListener(this);
        
        historyTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
					int rowSelected = historyTable.getSelectedRow();
					String lookForThis = "V" + rowSelected + "";
					int indexOfVersion= fileInfo.indexOf(lookForThis);
					int indexOfSplit =0;
					for(int i=indexOfVersion; i<fileInfo.length(); i++)
					{
						if(fileInfo.substring(i,i+5).equals("split"))
						{
							indexOfSplit=i;
							break;
						}
						
					}
					getVersion(fileInfo, indexOfVersion,indexOfSplit);
					
				} 

			}
		});
    }
    public ArrayList<ActionItem> getVersion(String file, int index1, int index2)
    {
    	String lookAtThisFile = file.substring(index1,index2-6);
    	StringTokenizer st = new StringTokenizer(lookAtThisFile);
    	return null;
    }
    
	/**
	 * 
	 * V1; 
	 * Nidhin,Urgent,2024-03-06T10:08:06.268875,2024-05-02T11:59,Doing work,
	 * Sam,Current,2024-03-06T10:08:06.273502,2024-04-02T11:59,Not doing work,
	 * Tristan,Awaiting,2024-03-06T10:08:06.273528,2024-03-02T11:59,Working, split
	 * V2; 
	 * Maddie,Lost,2024-03-06T10:08:06.273537,2024-06-02T11:59,Playing Games,
	 * Lily,Current,2024-03-06T10:08:06.273544,2024-04-02T11:59,Actively Working,
	 * Aiden,Awaiting,2024-03-06T10:08:06.273549,2024-03-02T11:59,Chipotle, split
	 * 
	 */

    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand(); 
        if (eventName.equals("Save")) {} //saves current table as a file {
            
       else if(eventName.equals("Replace Current"))
       {
    	   
    	   
    	   
    	   
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
