package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

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
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
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
