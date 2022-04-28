package de.mrg4ming.control;

import de.mrg4ming.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EntryManager {

    public static EntryManager instance;

    public static List<Entry> entries = new ArrayList<>();

    public EntryManager() {
        if(instance != null) return;
        instance = this;
    }

    public void distribute() {
        List<String> entryNames = new ArrayList<>();
        for(Entry entry : entries) {
            entryNames.add("Entry " + entry.position);
        }
        Main.mainWindow.entryListGUI.updateEntries(entryNames);
    }

    public void updateEntrySort() {
        for(int i = 0; i < Main.mainWindow.entryListGUI.entryListModel.size(); i++) {

            //ONLY functional if list entries are strings built like this: "Entry ID"
            int _index = Integer.parseInt(Main.mainWindow.entryListGUI.entryListModel.elementAt(i).toString().substring(6));
            EntryManager.entries.get(_index).position = i+1;
        }
    }

    public void sortListAndApplyDistribute() {
        Collections.sort(entries);

        distribute();
    }

}
