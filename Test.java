import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Test extends JFrame {

    private DefaultTableModel tableModel;
    private JTable toDoTable;
    private String color;
    private String currentFile;
    private FileMenu fileMenuReal;
    private JPanel topPanel;
    private JLabel title;
    private JButton fileMenuButton;
    private ColorTheme colorThemePage;
    private JPanel inputPanel;
    private JButton addButton;
    private JButton removeButton;
    public int selectedMainTableRow;
    public Test() {
        setTitle("To Do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setPreferredSize(new Dimension(600, 800));
        getContentPane().setBackground(new Color(173, 216, 230));

        // Initialize components
        tableModel = new DefaultTableModel();
        
        tableModel.addColumn("Task");
        tableModel.addColumn("Date");
        tableModel.addColumn("Urgency");

        toDoTable = new JTable(tableModel);


        getContentPane().add(new JScrollPane(toDoTable), BorderLayout.CENTER);
        topPanel = new JPanel(new BorderLayout());
       
        ImageIcon fileImage = new ImageIcon(getClass().getResource("menu_456243 (3).png"));
        fileMenuButton = new JButton(fileImage);
        fileMenuButton.setActionCommand("File");
        fileMenuButton.setOpaque(false);
        fileMenuButton.setContentAreaFilled(false);
        fileMenuButton.setBorderPainted(false);

        title = new JLabel("To-Do List");
        title.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(fileMenuButton, BorderLayout.EAST);
        topPanel.add(title, BorderLayout.CENTER);

        inputPanel = new JPanel();
        addButton = new JButton("Add Task");
        removeButton = new JButton("Remove Task");
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        fileMenuReal = new FileMenu();

        // Enable table selection and drag-and-drop
        toDoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toDoTable.setDragEnabled(true);
        toDoTable.setDropMode(DropMode.INSERT);
        toDoTable.setTransferHandler(new ListItemTransferHandler());

        // Add FocusListener to clear selection when table loses focus
        toDoTable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                toDoTable.clearSelection();
            }
        });

        // Add ActionListener for the "Add Task" button
        addButton.addActionListener(e -> addTask());

        // Add ActionListener for the "Remove Task" button
        removeButton.addActionListener(e -> removeTask());
        toDoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = toDoTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        selectedMainTableRow = selectedRow;

                    }
                }
            }
        });
        // Add MouseAdapter for handling mouse events (e.g., double-click to edit)
        toDoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1)
                {
                    selectedMainTableRow = toDoTable.rowAtPoint(e.getPoint());


                }
                if (e.getClickCount() == 2) {
                    int row = toDoTable.rowAtPoint(e.getPoint());
                    int column = toDoTable.columnAtPoint(e.getPoint());
                    if (row >= 0 && column >= 0) {
                        editTask(row, column);
                    }
                }
            }
        });
        
        

        // Add ActionListener for the file menu button
        fileMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileMenuPopup(fileMenuButton);
            }
        });
        updateTableColors();

    }
    public void onThemeChanged(String theme) {
        // Implement theme change logic here
        updateUIWithTheme(theme);
    }
    private void updateTableColors() {
        if (ColorTheme.color != null) {
            switch (ColorTheme.color) {
                case "Love":
                    updateTableColor(Color.RED);
                    break;
                case "Halloween":
                    updateTableColor(Color.BLACK);
                    break;
                case "Winter":
                    updateTableColor(Color.GREEN);
                    break;
                case "Dark mode":
                    updateTableColor(Color.GRAY);
                    break;
                case "Pastel":
                    updateTableColor(Color.BLUE);
                    break;
                case "Default":
                    // Reset to default table colors
                    break;
            }
        }
    }

    private void updateUIWithTheme(String theme) {
    	 ImageIcon winterFileImage = new ImageIcon(getClass().getResource("whiteMenuIcon.png"));
        
    	switch (theme) {
            case "Love":
            	this.topPanel.setBackground(Color.RED);
                this.addButton.setForeground(Color.WHITE);
                addButton.setBorderPainted(false);
                addButton.setOpaque(true);
                addButton.setBackground(Color.RED);
                this.removeButton.setForeground(Color.WHITE);
                removeButton.setBorderPainted(false);
                removeButton.setOpaque(true);
                removeButton.setBackground(Color.RED);
                this.getContentPane().setForeground(Color.MAGENTA);
                this.title.setForeground(Color.white);
                // Make table columns red
                if (toDoTable != null) {
                	updateTableColor(Color.RED);
                }
                break;
            case "Halloween":
            	this.topPanel.setBackground(Color.BLACK);
            	topPanel.setForeground(Color.ORANGE);
            	this.addButton.setForeground(Color.BLACK);
            	addButton.setBorderPainted(false);
                addButton.setOpaque(true);
                addButton.setBackground(Color.ORANGE);
                this.removeButton.setForeground(Color.BLACK);
                removeButton.setBorderPainted(false);
                removeButton.setOpaque(true);
                removeButton.setBackground(Color.ORANGE);
                //this.getContentPane().setForeground(Color.MAGENTA);
                this.title.setForeground(Color.black);
                // Make table columns red
                if (toDoTable != null) {
                	updateTableColor(Color.ORANGE);
                }
                break;
            case "Winter":
            	this.topPanel.setBackground(new Color(173,216,230));
            	this.addButton.setForeground(Color.WHITE);
            	addButton.setBorderPainted(false);
                addButton.setOpaque(true);
                addButton.setBackground(new Color(173,216,230));
                this.removeButton.setForeground(Color.WHITE);
                removeButton.setBorderPainted(false);
                removeButton.setOpaque(true);
                removeButton.setBackground(new Color(173,216,230));
                fileMenuButton.setIcon(winterFileImage);
                this.title.setForeground(Color.white);
                if (toDoTable != null) {
                    updateTableColor(new Color(173,216,230));
                }
                break;
            case "Dark mode":
                this.topPanel.setBackground(Color.BLACK);
                this.addButton.setForeground(Color.WHITE);
            	addButton.setBorderPainted(false);
                addButton.setOpaque(true);
                addButton.setBackground(Color.BLACK);
                this.removeButton.setForeground(Color.WHITE);
                removeButton.setBorderPainted(false);
                removeButton.setOpaque(true);
                removeButton.setBackground(Color.BLACK);
                fileMenuButton.setIcon(winterFileImage);

                this.title.setForeground(Color.white);
                if (toDoTable != null) {
                    updateTableColor(Color.black);
                }
                break;
            case "Pastel":
                this.topPanel.setBackground(new Color(250, 218, 221));
            
                this.addButton.setForeground(Color.WHITE);
            	addButton.setBorderPainted(false);
                addButton.setOpaque(true);
                addButton.setBackground(new Color(250, 218, 221));
                this.removeButton.setForeground(Color.WHITE);
                removeButton.setBorderPainted(false);
                removeButton.setOpaque(true);
                removeButton.setBackground(new Color(250, 218, 221));
                fileMenuButton.setIcon(winterFileImage);

                this.title.setForeground(Color.white);
                if (toDoTable != null) {
                     updateTableColor(new Color(250, 218, 221));
                }      	
                break;
            case "Default":
            	  this.topPanel.setBackground(Color.gray);
                  
                  this.addButton.setForeground(Color.BLACK);
              	addButton.setBorderPainted(false);
                  addButton.setOpaque(true);
                  addButton.setBackground(Color.gray);
                  this.removeButton.setForeground(Color.BLACK);
                  removeButton.setBorderPainted(false);
                  removeButton.setOpaque(true);
                  removeButton.setBackground(Color.gray);
                 
                  this.title.setForeground(Color.white);
                  if (toDoTable != null) {
                       updateTableColor(Color.gray);
                  }      	
                  break;
                // Handle other themes or default case
        }
    }
    // Method to update table colors
    private void updateTableColor(Color color) {
        // Update table's default renderer with new background color
        toDoTable.setDefaultRenderer(Object.class, new CustomRowColorRenderer(color));
        // Repaint the table
        toDoTable.repaint();
    }

    private void addTask() {
    	editTask(-1,-1);
        sortTableByUrgency();
    }

    private void removeTask() {
    	int selectedIndex = toDoTable.getSelectedRow();
        if (selectedIndex != -1) { // Check if a row is selected
            tableModel.removeRow(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.", "No Task Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editTask(int row, int column) {
    	JPanel myPanel = new JPanel();
        JTextField editTaskInput = new JTextField(15);
        JComboBox<String> urgencyDropdown = new JComboBox<>();
        urgencyDropdown.addItem("");
        urgencyDropdown.addItem("Urgent");
        urgencyDropdown.addItem("Current");
        urgencyDropdown.addItem("Eventual");
        urgencyDropdown.addItem("Inactive");
        JButton selectDateButton = new JButton("Select Date");
        JTextField dateTextField = new JTextField(10);
        dateTextField.setEditable(false);
        JPanel datePanel = new JPanel();
        datePanel.add(dateTextField);
        datePanel.add(selectDateButton);
        myPanel.add(new JLabel("Edit Task: "));
        myPanel.add(editTaskInput);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Date to be Completed: "));
        myPanel.add(datePanel);
        myPanel.add(new JLabel("Urgency: "));
        myPanel.add(urgencyDropdown);

        // If editing an existing row, populate fields with current data
        if (row >= 0 && column >= 0) {
            editTaskInput.setText((String) tableModel.getValueAt(row, 0));
            dateTextField.setText((String) tableModel.getValueAt(row, 1));
            urgencyDropdown.setSelectedItem((String) tableModel.getValueAt(row, 2));
        }

        selectDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date selectedDate = showDatePicker();
                if (selectedDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    dateTextField.setText(sdf.format(selectedDate));
                }
            }
        });

        int editedTask = JOptionPane.showConfirmDialog(null, myPanel, "Edit Task", JOptionPane.OK_CANCEL_OPTION);

        if (editedTask == JOptionPane.OK_OPTION) {
            if (row >= 0 && column >= 0) { // If editing existing row
                tableModel.setValueAt(editTaskInput.getText(), row, 0);
                tableModel.setValueAt(dateTextField.getText(), row, 1);
                tableModel.setValueAt(urgencyDropdown.getSelectedItem(), row, 2);
            } else { // If adding new row
                Object[] rowData = {editTaskInput.getText(), dateTextField.getText(), urgencyDropdown.getSelectedItem()};
                tableModel.addRow(rowData);
            }
        }
	}
    
    private Date showDatePicker() {
		Calender calendar = new Calender();
		int result = JOptionPane.showConfirmDialog(null, calendar, "Select Date", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			return calendar.getSelectedDate();
		}
		return null;
	}
    private void sortTableByUrgency() {
    	   TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
           sorter.setComparator(2, new UrgencyDateComparator()); // Assuming urgency column index is 2
           ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();

           // First, sort by urgency
           sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING)); // Assuming urgency column index is 2

           // Then, within each urgency level, sort by date (descending order)
           sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING)); // Assuming date column index is 1

           sorter.setSortKeys(sortKeys);
           toDoTable.setRowSorter(sorter);    }
    
    // Custom comparator to sort by urgency and then by date
    class UrgencyDateComparator implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            String urgency1 = (String) o1;
            String urgency2 = (String) o2;

            // Custom ordering for urgency levels
            Map<String, Integer> urgencyOrder = new HashMap<>();
            urgencyOrder.put("Urgent", 4);
            urgencyOrder.put("Current", 3);
            urgencyOrder.put("Eventual", 2);
            urgencyOrder.put("Inactive", 1);

            int result = Integer.compare(urgencyOrder.getOrDefault(urgency2, 0), urgencyOrder.getOrDefault(urgency1, 0));
            if (result != 0) {
                return result;
            }

            // If urgency levels are the same, compare dates (assuming the date is in String format)
            String date1 = (String) o1; // Assuming date is stored as String
            String date2 = (String) o2; // Assuming date is stored as String
            // Assuming the date format is "yyyy-MM-dd"
            return date2.compareTo(date1); // Compare dates in descending order
        }
    }

    private void showFileMenuPopup(Component component) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem fileMenu = new JMenuItem("File Menu");
        JMenuItem colorTheme = new JMenuItem("Color Theme");
        JMenuItem quit = new JMenuItem("Quit");

        fileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenu fileSystem = fileMenuReal;
                int result = JOptionPane.showConfirmDialog(null, fileSystem, "Select File", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    currentFile = fileSystem.getFile();
                }
            }
        });

        colorTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Test.this.colorThemePage = new ColorTheme(Test.this);
                colorThemePage.color = "Default";
                //int result = JOptionPane.showConfirmDialog(null, colorTheme, "Select Background", JOptionPane.OK_CANCEL_OPTION);
                //if (result == JOptionPane.OK_OPTION) {
                    //color = colorThemePage.getColorTheme();
               // }
                color = colorThemePage.getColorTheme();
                switch (color) {
                    case "Love":
                        getContentPane().setBackground(new Color(240, 182, 213));
                        break;
                    case "Halloween":
                        getContentPane().setBackground(Color.BLACK);
                        break;
                    case "Winter":
                        getContentPane().setBackground(new Color(173, 216, 230));
                        break;
                    case "Dark mode":
                        getContentPane().setBackground(Color.BLACK);
                        break;
                    case "Pastel":
                        getContentPane().setBackground(new Color(212, 231, 197));
                        break;
                    case "Default":
                        getContentPane().setBackground(null);
                        break;
                }
            }
        });


        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        popupMenu.add(fileMenu);
        popupMenu.add(colorTheme);
        popupMenu.add(quit);

        popupMenu.show(component, 0, component.getHeight());
    }

    // Main method
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Test testFrame = new Test();
                testFrame.pack();
                testFrame.setVisible(true); // Make the frame visible
            }
        });
    }
}

// class ListItemTransferHandler for handling drag-and-drop reordering
class ListItemTransferHandler extends TransferHandler {
    @Override
    protected Transferable createTransferable(JComponent c) {
        JTable sourceTable = (JTable) c;
        int row = sourceTable.getSelectedRow();
        return new StringSelection(sourceTable.getValueAt(row, 0).toString());
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
        JTable targetTable = (JTable) support.getComponent();
        JTable.DropLocation dropLocation = (JTable.DropLocation) support.getDropLocation();
        int dropRow = dropLocation.getRow();

        try {
            Transferable transferable = support.getTransferable();
            String data = (String) transferable.getTransferData(DataFlavor.stringFlavor);

            if (support.getSourceDropActions() == MOVE && targetTable.equals(support.getComponent())) {
                DefaultTableModel targetModel = (DefaultTableModel) targetTable.getModel();
                int sourceRow = targetTable.getSelectedRow();
                targetModel.moveRow(sourceRow, sourceRow, dropRow);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
