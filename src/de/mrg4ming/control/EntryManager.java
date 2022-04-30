package de.mrg4ming.control;

import de.mrg4ming.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EntryManager {

    public static EntryManager instance;

    public List<Entry> entries = new ArrayList<>();
    public int currentSelectedEntry = 0;

    public EntryManager() {
        if(instance != null) return;
        instance = this;
    }

    public void distribute() {
        List<String> entryNames = new ArrayList<>();
        for(Entry entry : entries) {
            entryNames.add(Entry.ENTRY_NAME_PREFIX + entry.position);
        }

        for(String s : entryNames) {
            Main.mainWindow.entryListGUI.addEntry(s);
        }
        //Main.mainWindow.entryListGUI.updateEntries(entryNames);
    }

    public void addEmptyEntry() {
        Entry e = new Entry(entries.size() + 1, entries.size() + 1, "", Entry.Status.INVISIBLE, Entry.ClearTime.TODAY);

        this.entries.add(e);

        Main.mainWindow.entryListGUI.addEntry(Entry.ENTRY_NAME_PREFIX + e.position);
    }

    public boolean removeEntry(Entry e) {
        if(entries.contains(e)) {
            entries.remove(e);
            Main.mainWindow.entryListGUI.removeEntry(Entry.ENTRY_NAME_PREFIX + e.position);
            return true;
        }
        return false;
    }

    public void updateEntrySort() {
        for(int i = 0; i < Main.mainWindow.entryListGUI.entryListModel.size(); i++) {

            //ONLY functional if list entries are strings built like this: "Entry " + id
            int _index = Integer.parseInt(Main.mainWindow.entryListGUI.entryListModel.elementAt(i).toString().substring(Entry.ENTRY_NAME_PREFIX.length()));
            EntryManager.instance.entries.get(_index).position = i+1;
        }
    }

    public void sortListAndApplyDistribute() {
        Collections.sort(entries);

        distribute();
    }

    public static Entry getCurrentSelectedEntry() {
        return instance.entries.get(instance.currentSelectedEntry);
    }

}
