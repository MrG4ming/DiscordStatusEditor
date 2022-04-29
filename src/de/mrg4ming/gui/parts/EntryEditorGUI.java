package de.mrg4ming.gui.parts;

import de.mrg4ming.control.Entry;
import de.mrg4ming.control.EntryManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;

public class EntryEditorGUI extends JPanel {

    JLabel positionLabel;

    JTextArea textArea = new JTextArea();
    JPanel statusSwitchPanel = new JPanel();
    public ButtonGroup statusButtonGroup;
    JPanel clearTimePanel = new JPanel();
    public ButtonGroup clearTimeButtonGroup;

    public EntryEditorGUI() {
        this.setLayout(new GridLayout(2, 1));
        this.setBorder(new TitledBorder("Entry editor"));

        JPanel topHalf = new JPanel(new GridLayout(1, 2));
        JPanel bottomHalf = new JPanel(new BorderLayout());

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


        JPanel switchPanels = new JPanel(new GridLayout(1, 2));
        switchPanels.add(statusSwitchPanel);
        switchPanels.add(clearTimePanel);

        JPanel textInputPanel = new JPanel(new BorderLayout());
        textInputPanel.setBorder(new TitledBorder("Text"));
        textArea.setToolTipText("Status text");
        textArea.setLineWrap(true);
        textArea.addKeyListener(textInputKeyListener);
        textInputPanel.add(textArea);

        positionLabel = new JLabel("Position: ");
        JPanel entryInfoPanel = new JPanel(new BorderLayout());
        entryInfoPanel.setBorder(new TitledBorder("Entry info"));
        entryInfoPanel.add(positionLabel, BorderLayout.NORTH);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(applyButtonAction);
        entryInfoPanel.add(applyButton, BorderLayout.SOUTH);

        topHalf.add(switchPanels);
        topHalf.add(textInputPanel);
        bottomHalf.add(entryInfoPanel);

        this.add(topHalf);
        this.add(bottomHalf);
    }

    public void loadEntry(int index) {
        if(index > EntryManager.instance.entries.size()) return;

        updateEntryPositionText(EntryManager.instance.entries.get(index).getPosition());

        textArea.setText(EntryManager.instance.entries.get(index).getText());

        //Status
        for (Enumeration<AbstractButton> buttons = statusButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.setSelected(false);
            if(Entry.Status.getStatusByString(button.getText()) != null) {
                Entry.Status _buttonStatus = Entry.Status.getStatusByString(button.getText());
                if(_buttonStatus == EntryManager.instance.entries.get(index).getStatus()) {
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
                if(_buttonClearTime == EntryManager.instance.entries.get(index).getClearTime()) {
                    button.setSelected(true);
                }
            }
        }

    }

    public void setActive(boolean value) {
        //this.setEnabled(value);
        //this.setVisible(value);

        for(Component c : this.getComponents()) {
            c.setEnabled(value);
            c.setVisible(value);
        }
    }

    public void updateEntryPositionText(int newPosition) {
        positionLabel.setText("Position: " + newPosition);
    }

    KeyListener textInputKeyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            if(textArea.getText().length() >= Entry.MAX_CHAR_COUNT)  {
                textArea.setText(textArea.getText().substring(0, textArea.getText().length() - Math.max(0, textArea.getText().length() - (Entry.MAX_CHAR_COUNT - 1))));
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    public String getText() {
        return textArea.getText();
    }

    public Entry.Status getSelectedStatus() {
        for (Enumeration<AbstractButton> buttons = statusButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if(button.isSelected()) {
                if(Entry.Status.getStatusByString(button.getText()) != null) {
                    return Entry.Status.getStatusByString(button.getText());
                }
            }
        }
        return Entry.Status.INVISIBLE;
    }

    public Entry.ClearTime getSelectedClearTime() {
        for (Enumeration<AbstractButton> buttons = clearTimeButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if(button.isSelected()) {
                if(Entry.ClearTime.getClearTimeByString(button.getText()) != null) {
                    return Entry.ClearTime.getClearTimeByString(button.getText());
                }
            }
        }
        return Entry.ClearTime.TODAY;
    }

    ActionListener applyButtonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //get current selected entry and execute saveEntry()
            EntryManager.getCurrentSelectedEntry().saveEntry();
        }
    };
}
