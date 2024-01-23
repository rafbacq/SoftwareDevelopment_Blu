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

public class Test extends JFrame {

    private DefaultListModel<String> toDoListModel;
    private JList<String> toDoList;
    private JTextField taskTextField;

    public Test() {
        // Set up the JFrame
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

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the frame
        add(new JScrollPane(toDoList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.add(taskTextField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Add ActionListener for the "Add Task" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskTextField.getText().trim();
                if (!task.isEmpty()) {
                    toDoListModel.addElement(task);
                    taskTextField.setText("");
                }
            }
        });

        // Add ActionListener for the "Remove Task" button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = toDoList.getSelectedIndex();
                if (selectedIndex != -1) {
                    toDoListModel.remove(selectedIndex);
                }
            }
        });

        // Add MouseAdapter for handling mouse events (e.g., double-click to edit)
        toDoList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = toDoList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String editedTask = JOptionPane.showInputDialog(Test.this, "Edit Task:", toDoListModel.get(selectedIndex));
                        if (editedTask != null) {
                            toDoListModel.setElementAt(editedTask, selectedIndex);
                        }
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Test().setVisible(true);
            }
        });
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
