package Software_Development;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test extends JFrame {

    private DefaultTableModel tableModel;
    private JTable toDoTable;
    private String color;
    private String currentFile;

    public Test() {
        setTitle("To-Do Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Task");
        tableModel.addColumn("Date");
        tableModel.addColumn("Urgency");

        toDoTable = new JTable(tableModel);
        for (int c = 0; c < toDoTable.getColumnCount(); c++) {
            Class<?> col_class = toDoTable.getColumnClass(c);
            toDoTable.setDefaultEditor(col_class, null); // remove editor
        }
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");

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

        // Set layout manager
        setLayout(new BorderLayout());

        // Wrap the JTable in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(toDoTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Add ActionListener for the "Add Task" button
        addButton.addActionListener(e -> addTask());

        // Add ActionListener for the "Remove Task" button
        removeButton.addActionListener(e -> removeTask());

        // Add MouseAdapter for handling mouse events (e.g., double-click to edit)
        toDoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                FileMenu fileSystem = new FileMenu();
                int result = JOptionPane.showConfirmDialog(null,  fileSystem, "Select File", JOptionPane.OK_CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION) {
                	currentFile = fileSystem.getFile();
                }
                
                
            }
        });
        colorTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ColorTheme background = new ColorTheme();
                int result = JOptionPane.showConfirmDialog(null, background, "Select Background", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    color = background.getColorTheme();
                }
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
        
        menu.add(fileMenu);
        menu.add(colorTheme);
        menu.add(quit);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void addTask() {
        editTask(true);
        sortTableByUrgency();
    }

    private void removeTask() {
        int selectedIndex = toDoTable.getSelectedRow();
        if (selectedIndex != -1) {
            tableModel.removeRow(selectedIndex);
            sortTableByUrgency();
        }
    }

    private void editTask(boolean b) {
        int selectedIndex = toDoTable.getSelectedRow();

        JPanel myPanel = new JPanel();
        String alreadyInputTask = "";
        if (!b && selectedIndex != -1) {
            alreadyInputTask = tableModel.getValueAt(selectedIndex, 0).toString();
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
        myPanel.add(new JLabel("Date: "));
        myPanel.add(datePanel);
        myPanel.add(new JLabel("Urgency: "));
        myPanel.add(urgencyDropdown);

        editTaskInput.setText(alreadyInputTask);

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
            } else if (b == true) {
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
        // Sort the table based on urgency
    }

    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Test testFrame = new Test();
        testFrame.setVisible(true); // Make the frame visible
    }

    public static void main(String[] args) {
        /* Methods that create and show a GUI should be
         run from an event-dispatching thread */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
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
