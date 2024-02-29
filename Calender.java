import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

public class Calender extends JPanel {
    private JButton prevMonthButton;
    private JButton nextMonthButton;
    private JLabel monthYearLabel;
    private JPanel calendarPanel;
    private SimpleDateFormat monthYearFormat;
    private SimpleDateFormat dayFormat;

    private Calendar currentDate;
    private JLabel selectedDayLabel;
    private JButton okButton;
    private JTextField dateTextFieldToUpdate;
    private Date selectedDate;


    public Calender(JTextField dateTextFieldToUpdate) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300));

        // Initialize current date to today
        currentDate = Calendar.getInstance();

        prevMonthButton = new JButton("<");
        nextMonthButton = new JButton(">");
        monthYearLabel = new JLabel();
        calendarPanel = new JPanel(new GridLayout(0, 7));
        selectedDate = currentDate.getTime();
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);

        // Set up date formats
        monthYearFormat = new SimpleDateFormat("MMMM yyyy");
        dayFormat = new SimpleDateFormat("d");

        // Display calendar
        updateCalendar();

        // Add action listeners
        prevMonthButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        nextMonthButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });
        
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateTextFieldToUpdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(getSelectedDate()));
                System.out.println(dateTextFieldToUpdate.getText());
                selectedDate = getSelectedDate();
                System.out.println(selectedDate);
                Container container = okButton.getTopLevelAncestor();
                if (container instanceof JPopupMenu) {
                    ((JPopupMenu) container).setVisible(false);
                }
            }
        });

        add(okButton, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        headerPanel.add(prevMonthButton, BorderLayout.WEST);
        headerPanel.add(monthYearLabel, BorderLayout.CENTER);
        headerPanel.add(nextMonthButton, BorderLayout.EAST);
        return headerPanel;
    }

    private void updateCalendar() {
        calendarPanel.removeAll();

        monthYearLabel.setText(monthYearFormat.format(currentDate.getTime()));

        Calendar calendar = (Calendar) currentDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        // Add day labels to calendar panel
        for (int i = 0; i < 7; i++) {
            JLabel dayLabel = new JLabel(getDayOfWeekAbbreviation(calendar.get(Calendar.DAY_OF_WEEK)));
            dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
            calendarPanel.add(dayLabel);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Add empty labels for days before the first day of the month
        for (int i = 0; i < calendar.get(Calendar.DAY_OF_WEEK) - 1; i++) {
            calendarPanel.add(new JLabel());
        }

        // Add day labels for each day in the month
        for (int day = 1; day <= daysInMonth; day++) {
            JLabel dayLabel = new JLabel(String.valueOf(day));
            dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
            dayLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedDayLabel != null) {
                        selectedDayLabel.setBorder(null);
                    }
                    selectedDayLabel = dayLabel;
                    selectedDayLabel.setBorder(new LineBorder(Color.RED, 2));
                    currentDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayLabel.getText()));
                    
                    selectedDate = currentDate.getTime();
                }
            });
            calendarPanel.add(dayLabel);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private String getDayOfWeekAbbreviation(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sun";
            case Calendar.MONDAY:
                return "Mon";
            case Calendar.TUESDAY:
                return "Tue";
            case Calendar.WEDNESDAY:
                return "Wed";
            case Calendar.THURSDAY:
                return "Thu";
            case Calendar.FRIDAY:
                return "Fri";
            case Calendar.SATURDAY:
                return "Sat";
            default:
                return "";
        }
    }

    public Date getSelectedDate() {
    	return selectedDate;    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
