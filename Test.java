
package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.TableRowSorter;
import java.util.List;

public final class Test {

    private static Test INSTANCE;
    private DefaultTableModel tableModel = null;
    private JTable toDoTable;
    public static String color;
    private String currentFile;
    //private FileMenu fileMenuReal;
    public static int currentVersion;
    public static JFrame frame;
    public int selectedMainTableRow;



    public TableModel getTable()
    {
        return tableModel;
    }
    public void updateTable(String s) {
        if ( s == null || s.isEmpty()) return;
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
        int numRows = 0;
        String[] splits = s.split(System.getProperty("line.separator"));
        for (int i = 0; i < splits.length; i++) {
            if (splits[i].indexOf("Task") != -1 ) {
                numRows += 1;
            }
        }
        for (int i = 0; i < numRows; i++) {
            if ( s.indexOf("Task:") != -1 && s.indexOf("Date") != -1 && s.indexOf("Task")!= -1 && s.indexOf("Urgency")!= -1) {
                s = s.substring(s.indexOf("Task"));
                String taskInRow = s.substring(s.indexOf(":") + 2, s.indexOf("Date"));
                s = s.substring(s.indexOf("Date"));
                String dateInRow = s.substring(s.indexOf(":") + 2, s.indexOf("Urgency"));
                s = s.substring(s.indexOf("Urgency"));
                String urgencyInRow = s.substring(s.indexOf(":") + 2, s.indexOf("\n"));
                // Add the row to the table model
                Object[] rowStr = new Object[]{taskInRow.trim(), dateInRow.trim(), urgencyInRow.trim()};
                tableModel.addRow(rowStr);
            }
        }
    }

    public static Test getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Test();
        }

        return INSTANCE;
    }

    private  Test() {
    	color = "Default";
        frame = new JFrame("To-Do Application");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Initialize components
        String[] columns = {"Task", "Date", "Urgency"};
        tableModel = new DefaultTableModel(0, 0);
        tableModel.setColumnIdentifiers(columns);
        toDoTable = new JTable(tableModel);
        for (int c = 0; c < toDoTable.getColumnCount(); c++) {
            Class<?> col_class = toDoTable.getColumnClass(c);
            toDoTable.setDefaultEditor(col_class, null); // remove editor
        }
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");



        // Enable table selection and drag-and-drop
       //XXX toDoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // XXX toDoTable.setDragEnabled(true);
        // XXX toDoTable.setDropMode(DropMode.INSERT);
        // XXX toDoTable.setTransferHandler(new ListItemTransferHandler());

        toDoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = toDoTable.getSelectedRow();
                    System.out.println(selectedRow);

                }
            }
        });
        // Add FocusListener to clear selection when table loses focus
        toDoTable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
           //XXX     toDoTable.clearSelection();
            }
        });

        // Set layout manager
        frame.setLayout(new BorderLayout());

        // Wrap the JTable in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(toDoTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Add ActionListener for the "Add Task" button
        addButton.addActionListener(e -> addTask());

        // Add ActionListener for the "Remove Task" button
        removeButton.addActionListener(e -> removeTask());

        // Add MouseAdapter for handling mouse events (e.g., double-click to edit)
        toDoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                selectedMainTableRow= toDoTable.getSelectedRow();
                System.out.println("selectedMainTableRow :"+selectedMainTableRow);
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {

                    editTask(false);
                    sortTableByUrgency();
                } else if (e.getClickCount() == 2 && SwingUtilities.isRightMouseButton(e)) {
                    // do nothing
                }

            }
        });

        // Add menu bar and menu option
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem fileMenu = new JMenuItem("File Menu");
        JMenuItem colorTheme = new JMenuItem("colorTheme");
        JMenuItem quit = new JMenuItem("quit");


        fileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                org.example.FileMenu fileSystem = new org.example.FileMenu();
                int versionControlDialog = JOptionPane.showConfirmDialog(null, fileSystem, "File Menu", JOptionPane.OK_CANCEL_OPTION);
                if ( versionControlDialog == JOptionPane.OK_OPTION) {
                   updateTable(fileSystem.getSelectedFileInfo());
                }

                // Set focus on the toDoTable after returning from the FileMenu panel
                toDoTable.requestFocusInWindow();
            }
        });
        colorTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                org.example.ColorTheme background = new org.example.ColorTheme();


                switch (color) {
                    case "Love":
                        frame.getContentPane().setBackground(new Color(240, 182, 213));
                        break;
                    case "Halloween":
                        frame.getContentPane().setBackground(Color.BLACK);
                        break;
                    case "Winter":
                        frame.getContentPane().setBackground(new Color(173, 216, 230));
                        break;
                    case "Dark mode":
                        frame.getContentPane().setBackground(Color.BLACK);
                        break;
                    case "Pastel":
                        frame.getContentPane().setBackground(new Color(212, 231, 197));
                        break;
                    case "Default":
                        frame.getContentPane().setBackground(null);
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
        
        menu.add(fileMenu);
        menu.add(colorTheme);
        menu.add(quit);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }
    public static String getColor()
    {
    	return color;
    }

    private void addTask()
    {

        editTask(true);

        
    }

    private void removeTask() {
        int selectedIndex  = toDoTable.getSelectedRow();
        tableModel.removeRow(toDoTable.getSelectedRow());
    }

    private void editTask(boolean b) {
        int selectedIndex = toDoTable.getSelectedRow();

        JPanel myPanel = new JPanel();
        String alreadyInputTask = "";
        String alreadyInputDate = "";
        String alreadyInputUrgency = "";
        if (!b && selectedIndex != -1) {
            alreadyInputTask = tableModel.getValueAt(selectedIndex, 0).toString();
            alreadyInputDate = tableModel.getValueAt(selectedIndex, 1).toString();
            alreadyInputUrgency = tableModel.getValueAt(selectedIndex,2).toString();
        }
        JTextField editTaskInput = new JTextField(15);

        JComboBox<String> urgencyDropdown = new JComboBox<String>();

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

        editTaskInput.setText(alreadyInputTask);
        dateTextField.setText(alreadyInputDate);


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
            if (selectedIndex != -1) {
                tableModel.setValueAt(editTaskInput.getText(), selectedIndex, 0);
                tableModel.setValueAt(dateTextField.getText(), selectedIndex, 1);
                tableModel.setValueAt(urgencyDropdown.getSelectedItem(), selectedIndex, 2);
            } else if (b) {
                Object[] rowData = { editTaskInput.getText(), dateTextField.getText(),
                        urgencyDropdown.getSelectedItem() };
                tableModel.addRow(rowData);
            }
        }
    }
    private Date showDatePicker() {
		org.example.Calender calendar = new org.example.Calender();
		int result = JOptionPane.showConfirmDialog(null, calendar, "Select Date", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			return calendar.getSelectedDate();
		}
		return null;
	}

    private void sortTableByUrgency() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setComparator(2, new UrgencyDateComparator()); // Assuming urgency column index is 2
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        // First, sort by urgency
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING)); // Assuming urgency column index is 2

        // Then, within each urgency level, sort by date (descending order)
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING)); // Assuming date column index is 1

        sorter.setSortKeys(sortKeys);
        toDoTable.setRowSorter(sorter);
    }

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

    public static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        /*try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/main/resources/heartImage.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        frame.setVisible(true); // Make the frame visible

    }


    public static void main(String[] args) {
        // Methods that create and show a GUI should be
        // run from an event-dispatching thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame = new JFrame("To-Do Application"); // Instantiate frame here
                Test.getInstance(); // Instantiate Test object to initialize the frame
                runGUI(); // Call runGUI after frame initialization
            }
        });
    }
}

