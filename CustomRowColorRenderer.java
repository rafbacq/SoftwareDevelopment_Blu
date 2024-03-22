import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomRowColorRenderer extends DefaultTableCellRenderer {

    private Color backgroundColor; // Background color for the table cells

    // Constructor to set the background color
    public CustomRowColorRenderer(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Call super method to get default rendering
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Set the background color for the component
        component.setBackground(backgroundColor);

        return component;
    }
}
