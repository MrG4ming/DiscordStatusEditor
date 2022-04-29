package de.mrg4ming.gui.parts;

import de.mrg4ming.Main;
import de.mrg4ming.control.Entry;
import de.mrg4ming.control.EntryManager;
import de.mrg4ming.gui.lib.DragDropList;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntryListGUI extends JPanel {

    ArrayList<String> entries;
    public DefaultListModel entryListModel;

    public EntryListGUI() {
        super();
        entries = new ArrayList<>();

        this.setLayout(new BorderLayout());
        this.setBorder(new TitledBorder("Entry list"));

        entryListModel = new DefaultListModel();

        DragDropList _entryNamesList = new DragDropList(entryListModel);
        _entryNamesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //System.out.println(e.getValueIsAdjusting());
                if(!e.getValueIsAdjusting()) {
                    int selectedIndex = _entryNamesList.getSelectedIndex();
                    if(selectedIndex >= 0) {
                        if(selectedIndex > _entryNamesList.model.size()) {
                            selectedIndex = _entryNamesList.model.size();
                        }
                        EntryManager.instance.currentSelectedEntry = Integer.parseInt(_entryNamesList.getSelectedValue().toString().substring(Entry.ENTRY_NAME_PREFIX.length()));

                        Main.mainWindow.entryEditorGUI.loadEntry(EntryManager.instance.currentSelectedEntry);
                        Main.mainWindow.entryEditorGUI.setActive(true);

                        System.out.println("Selected list item: " + selectedIndex + "; Selected entry: " + EntryManager.instance.currentSelectedEntry);
                    } else Main.mainWindow.entryEditorGUI.setActive(false);

                }
            }
        });


        JScrollPane _scrollPane = new JScrollPane(_entryNamesList);

        this.add(_scrollPane, BorderLayout.CENTER);
    }

    public boolean addEntry(String _entry) {
        if(!entries.contains(_entry)) {
            entries.add(_entry);

            updateEntries(this.entries);
            return true;
        }
        return false;
    }

    public boolean removeEntry(String _entry) {
        if(entries.contains(_entry)) {
            entries.remove(_entry);

            updateEntries(this.entries);
            return true;
        }
        return false;
    }

    public void updateEntries(List<String> entries) {
        entryListModel.removeAllElements();

        ButtonGroup _buttonGroup = new ButtonGroup();

        for(int i = 0; i < entries.size(); i++) {
            entryListModel.add(i, Entry.ENTRY_NAME_PREFIX + i);

            JRadioButton _valueButton = new JRadioButton(entries.get(i));
            _buttonGroup.add(_valueButton);
        }
    }

    public void sortEntries() {
        Collections.sort(this.entries);
    }

    private void sortAndUpdateEntries() {
        Collections.sort(this.entries);
        updateEntries(this.entries);
    }
}
