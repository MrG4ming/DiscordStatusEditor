package de.mrg4ming.gui.parts;

import de.mrg4ming.control.Entry;
import de.mrg4ming.control.EntryManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Enumeration;

public class EntryEditorGUI extends JPanel {

    JLabel positionLabel;

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

        positionLabel = new JLabel("Position: ");
        this.add(positionLabel, BorderLayout.WEST);

        textField.setToolTipText("Status text");
        textField.setBorder(new TitledBorder("Status text"));

        this.add(textField, BorderLayout.WEST);
    }

    public void loadEntry(int index) {
        if(index < EntryManager.entries.size()) return;

        updateEntryPositionText(EntryManager.entries.get(index).getPosition());

        textField.setText(EntryManager.entries.get(index).getText());

        //Status
        for (Enumeration<AbstractButton> buttons = statusButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.setSelected(false);
            if(Entry.Status.getStatusByString(button.getText()) != null) {
                Entry.Status _buttonStatus = Entry.Status.getStatusByString(button.getText());
                if(_buttonStatus == EntryManager.entries.get(index).getStatus()) {
                    button.setSelected(true);
                }
            }
        }

        //ClearTime
        for (Enumeration<AbstractButton> buttons = clearTimeButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.setSelected(false);
            if(Entry.ClearTime.getClearTimeByString(button.getText()) != null) {
                Entry.ClearTime _buttonClearTime = Entry.ClearTime.getClearTimeByString(button.getText());
                if(_buttonClearTime == EntryManager.entries.get(index).getClearTime()) {
                    button.setSelected(true);
                }
            }
        }

    }

    public void updateEntryPositionText(int newPosition) {
        positionLabel.setText("Position: " + newPosition);
    }

}
