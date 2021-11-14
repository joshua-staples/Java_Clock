// Libraries for time zones, and date/time retrieval
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
// Swing (visual design) libraries
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
// Formatting libraries
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

public class Clock{

    // a function to format the time as AM/PM
    public String amPmDisplay(LocalDateTime now){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String timeFormatted = now.format(formatter);
        return timeFormatted;
    }

    // a function to format the time as 24 Hour
    public String militaryDisplay(LocalDateTime now){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeFormatted = now.format(formatter);
        return timeFormatted;
    }

    public Clock(){
        // create the frame for the GUI
        JFrame frame = new JFrame();

        // create the panel that will hold all our things (date, time, list, buttons, etc.)
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        panel.setBackground(Color.BLACK);

        //create time and date labels to pass to our frame
        JLabel timeLabel = new JLabel("Time");
        JLabel dateLabel = new JLabel("Date");

        // create a radio button to change the formatting of the date/time
        JRadioButton amPm = new JRadioButton("AM/PM");
        JRadioButton military = new JRadioButton("24 Hour");
        amPm.setText("AM/PM");
        military.setText("24 Hour");
        amPm.setForeground(Color.white);
        military.setForeground(Color.white);
        amPm.setSelected(true);

        //group the radio buttons so only one can be selected
        ButtonGroup timeGroup = new ButtonGroup();
        timeGroup.add(amPm);
        timeGroup.add(military);

        // create a list of time zones to pass into our frame
        String[] timeZones = TimeZone.getAvailableIDs();
        JComboBox<String> timeZoneSelector = new JComboBox<>(timeZones);

        // set the default selected item of the JComboBox 
        timeZoneSelector.setSelectedItem(TimeZone.getDefault().getID());
        timeZoneSelector.getSelectedItem();
        
        // set font and location for date/time
        timeLabel.setFont(new Font("Helvetica",Font.BOLD,50));
        timeLabel.setForeground(Color.green);
        dateLabel.setFont(new Font("Helvetica",Font.PLAIN,30));
        dateLabel.setForeground(Color.white);

        // formatting for the columns/rows and centering
        // c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(30, 30, 30, 30);

        // add all the different items to the panel
        c.gridwidth = 2;
        c.gridy = 0;
        panel.add(timeLabel, c);
        c.gridwidth = 2;
        c.gridy = 1;
        panel.add(dateLabel, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(amPm, c);
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(military, c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(timeZoneSelector, c);

        // add the panel to the frame, set frame text size, relative location, default closing operation, and visibility
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // get the current time in a 100 millisecond interval (to minimize time lag) 
        // and set the JLabels text to be the generated time/date.
        while(true) {
            // LocalDateTime now = LocalDateTime.now(ZoneId.of(selectedTimeZone));
            LocalDateTime now = (LocalDateTime.now((TimeZone.getTimeZone((String) timeZoneSelector
                                                            .getSelectedItem())
                                                            .toZoneId())));

            //create a conditional statement and functions to call the correct time format
            if (amPm.isSelected()){
                timeLabel.setText(amPmDisplay(now));
            }else{
                timeLabel.setText(militaryDisplay(now));
            }

            int year = now.getYear();
            int month = now.getMonthValue();
            int day_num = now.getDayOfMonth();
            String date = String.format("%02d-%02d-%04d", month, day_num, year);
            dateLabel.setText(date);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
    // call the Clock method
    public static void main(String[] args){
        new Clock();
    }
}