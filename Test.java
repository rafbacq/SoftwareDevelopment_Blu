package Software_Development;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


public class Test extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
    private JTable toDoTable;

    public Test() {
        setTitle("To-Do Application");
        setSize(400, 300);
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
        JMenuItem history = new JMenuItem("history");
        JMenuItem quit = new JMenuItem("quit");

        fileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open your menu application here
                JOptionPane.showMessageDialog(null, "Menu Application Opened!");
            }
        });
        colorTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your color theme logic here
            }
        });
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your history logic here
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
        
        UtilDateModel model = new UtilDateModel();
        model.setDate(2020, 1, 1);
        JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        Date selectedDate = (Date) datePicker.getModel().getValue();

        myPanel.add(new JLabel("Edit Task: "));
        myPanel.add(editTaskInput);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Date: "));
        myPanel.add(datePicker);
        myPanel.add(new JLabel("Urgency: "));
        myPanel.add(urgencyDropdown);

        editTaskInput.setText(alreadyInputTask);

        // Initialize the date picker
       
        
        int editedTask = JOptionPane.showConfirmDialog(null, myPanel, "Bruh this sucks", JOptionPane.OK_CANCEL_OPTION);
        
        if (editedTask == JOptionPane.OK_OPTION) {
            if (selectedIndex != -1) {
                tableModel.setValueAt(editTaskInput.getText(), selectedIndex, 0);
                tableModel.setValueAt(selectedDate.toString(), selectedIndex, 1);
            } else if(b==true) {
                Object[] rowData = {editTaskInput.getText(), selectedDate.toString(), urgencyDropdown.getSelectedItem()};
                tableModel.addRow(rowData);
            }
        }
        
         
        
    }
    public class DateLabelFormatter extends AbstractFormatter {
    	 
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
         
        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }
     
        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
             
            return "";
        }
     
    }
    

    // Custom formatter for JDatePicker


    private void sortTableByUrgency() {
        // Sort the table based on urgency
        tableModel.getDataVector().sort((row1, row2) -> {
            String urgency1 = (String) row1.get(2); // Assuming urgency is at column index 2
            String urgency2 = (String) row2.get(2);

            return compareUrgency(urgency1, urgency2);
        });

        // Fire table data changed to update the view
        tableModel.fireTableDataChanged();
    }

    private int compareUrgency(String urgency1, String urgency2) {
        // Define the order of urgency levels
        String[] urgencyOrder = {"Urgent", "Current", "Eventual", "Inactive"};

        // Compare the indices of urgency levels
        int index1 = Arrays.asList(urgencyOrder).indexOf(urgency1);
        int index2 = Arrays.asList(urgencyOrder).indexOf(urgency2);

        return Integer.compare(index1, index2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Test().setVisible(true));
    }
}

//class DateFormatter extends AbstractFormatter {
//
//    private final SimpleDateFormat dateFormatter;
//
//    public DateFormatter(SimpleDateFormat dateFormatter) {
//        this.dateFormatter = dateFormatter;
//    }
//
//    @Override
//    public Object stringToValue(String text) throws ParseException {
//        return dateFormatter.parseObject(text);
//    }
//
//    @Override
//    public String valueToString(Object value) throws ParseException {
//        if (value instanceof Date) {
//            return dateFormatter.format((Date) value);
//        }
//        return "";
//    }
//}

// TransferHandler for handling drag-and-drop reordering
class ListItemTransferHandler extends TransferHandler {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

