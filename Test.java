package Software_Development;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Test extends JFrame {

    private DefaultListModel<String> toDoListModel;
    private JList<String> toDoList;
    private JTextField taskTextField;

    public Test() {
        setTitle("To-Do Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        toDoListModel = new DefaultListModel<>();
        toDoList = new JList<>(toDoListModel);
        taskTextField = new JTextField(20);
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");

        // Enable list selection and drag-and-drop
        toDoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toDoList.setDragEnabled(true);
        toDoList.setDropMode(DropMode.INSERT);
        toDoList.setTransferHandler(new ListItemTransferHandler());
        ImageIcon icon = new ImageIcon("Menu Image.png");
       
        // Set layout manager
        setLayout(new BorderLayout());

        // Wrap the JList in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(toDoList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        //inputPanel.add(taskTextField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Add ActionListener for the "Add Task" button
        addButton.addActionListener(e -> addTask());

        // Add ActionListener for the "Remove Task" button
        removeButton.addActionListener(e -> removeTask());

        // Add MouseAdapter for handling mouse events (e.g., double-click to edit)
        toDoList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editTask();
                }
            }
        });
        // Add menu bar and menu option
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        
        JMenuItem fileMenu = new JMenuItem("File Menu");
        JMenuItem colorTheme = new JMenuItem("colorTheme");
        JMenuItem history = new JMenuItem("history");
        JMenuItem quit = new JMenuItem("quit");
        
        
        fileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open your menu application here
                JOptionPane.showMessageDialog(null, "Menu Application Opened!");
            }
        });
        quit.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		Runtime.getRuntime().exit(ABORT);
        	}
        	
        });
        menu.add(fileMenu);
        menu.add(colorTheme);
        menu.add(history);
        menu.add(quit);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void addTask() {
        editTask();
    }

    private void removeTask() {
        int selectedIndex = toDoList.getSelectedIndex();
        if (selectedIndex != -1) {
            toDoListModel.remove(selectedIndex);
        }
    }

    private void editTask() {
        int selectedIndex = toDoList.getSelectedIndex();
        
        	ArrayList <String>  list = new ArrayList<>();
        	JPanel myPanel = new JPanel();
        	String alreadyInputTask ="";
        	String alreadyInputDate = "";
        	if(selectedIndex!=-1)
        	{
        		String original = toDoListModel.getElementAt(selectedIndex);
        		alreadyInputTask = original.substring(0, original.indexOf("-")-1);
        		alreadyInputDate = original.substring(original.indexOf("-")+2);
        	}
        	JTextField editTaskInput = new JTextField(15);
        	JTextField editDate = new JTextField(15);
        	JComboBox urgencyDropdown = new JComboBox();
        	
        	myPanel.add(new JLabel("Edit Task: "));
        	myPanel.add(editTaskInput);
        	myPanel.add(Box.createHorizontalStrut(15));
        	myPanel.add(new JLabel("Date: "));
        	myPanel.add(editDate);
        	myPanel.add(new JLabel("Urgency: "));
        	
        	
        	editTaskInput.setText(alreadyInputTask);
        	editDate.setText(alreadyInputDate);
        	
        	

        	
            int editedTask = JOptionPane.showConfirmDialog(null, myPanel, "Yuh",JOptionPane.OK_CANCEL_OPTION);
            if (editedTask == JOptionPane.OK_OPTION) {
            	if(selectedIndex!=-1)
            		toDoListModel.setElementAt(editTaskInput.getText() + " - " + editDate.getText(), selectedIndex);
            	else
            		toDoListModel.addElement(editTaskInput.getText() + " - " + editDate.getText());
                
            }
        
            
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Test().setVisible(true));
    }
}


// TransferHandler for handling drag-and-drop reordering
class ListItemTransferHandler extends TransferHandler {
    @Override
    protected Transferable createTransferable(JComponent c) {
        JList<String> sourceList = (JList<String>) c;
        int index = sourceList.getSelectedIndex();
        return new StringSelection(sourceList.getModel().getElementAt(index));
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        JList.DropLocation dropLocation = (JList.DropLocation) support.getDropLocation();
        int dropIndex = dropLocation.getIndex();
        JList<String> targetList = (JList<String>) support.getComponent();

        try {
            Transferable transferable = support.getTransferable();
            String data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
            DefaultListModel<String> targetModel = (DefaultListModel<String>) targetList.getModel();

            // If the source and target lists are the same, perform the move
            if (support.getSourceDropActions() == MOVE && targetList.equals(support.getComponent())) {
                // Check if the task already exists in the target list
                if (targetModel.contains(data)) {
                    targetModel.add(dropIndex, data);
                    targetModel.remove(targetModel.indexOf(data));
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
