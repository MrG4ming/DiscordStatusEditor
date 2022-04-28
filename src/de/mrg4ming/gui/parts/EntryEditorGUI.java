package de.mrg4ming.gui.parts;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class EntryEditorGUI extends JPanel {

    JTextField textField = new JTextField();
    JPanel statusSwitchPanel = new JPanel();
    public ButtonGroup statusButtonGroup;
    JPanel clearTimePanel = new JPanel();
    public ButtonGroup clearTimeButtonGroup;

    public EntryEditorGUI() {
        this.setLayout(new BorderLayout());
        this.setBorder(new TitledBorder("Entry editor"));

        statusButtonGroup = new ButtonGroup();
        clearTimeButtonGroup = new ButtonGroup();

        ///region Status panel
        statusSwitchPanel.setLayout(new BoxLayout(statusSwitchPanel, BoxLayout.Y_AXIS));
        statusSwitchPanel.setBorder(new TitledBorder("Status"));
        JRadioButton statusInvisible = new JRadioButton("Invisible");
        statusInvisible.setSelected(true);
        JRadioButton statusDnd = new JRadioButton("Do not disturb");
        JRadioButton statusIdle = new JRadioButton("Idle");
        JRadioButton statusOnline = new JRadioButton("Online");
        statusSwitchPanel.add(statusInvisible);
        statusSwitchPanel.add(statusDnd);
        statusSwitchPanel.add(statusIdle);
        statusSwitchPanel.add(statusOnline);

        statusButtonGroup.add(statusInvisible);
        statusButtonGroup.add(statusDnd);
        statusButtonGroup.add(statusIdle);
        statusButtonGroup.add(statusOnline);
        ///endregion

        ///region Clear time panel
        clearTimePanel.setLayout(new BoxLayout(clearTimePanel, BoxLayout.Y_AXIS));
        clearTimePanel.setBorder(new TitledBorder("Clear time"));
        JRadioButton timeToday = new JRadioButton("Today");
        timeToday.setSelected(true);
        JRadioButton time4Hours = new JRadioButton("4 Hours");
        JRadioButton time1Hours = new JRadioButton("1 Hour");
        JRadioButton time30Minutes = new JRadioButton("30 Minutes");
        JRadioButton timeNever = new JRadioButton("Never");
        clearTimePanel.add(timeToday);
        clearTimePanel.add(time4Hours);
        clearTimePanel.add(time1Hours);
        clearTimePanel.add(time30Minutes);
        clearTimePanel.add(timeNever);

        clearTimeButtonGroup.add(timeToday);
        clearTimeButtonGroup.add(time4Hours);
        clearTimeButtonGroup.add(time1Hours);
        clearTimeButtonGroup.add(time30Minutes);
        clearTimeButtonGroup.add(timeNever);
        ///endregion

        this.add(statusSwitchPanel, BorderLayout.NORTH);
        this.add(clearTimePanel, BorderLayout.NORTH);
    }

    public void loadEntry(int index) {

    }

}
