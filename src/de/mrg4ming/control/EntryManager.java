package de.mrg4ming.control;

import java.util.ArrayList;
import java.util.List;

public final class EntryManager {

    public static EntryManager instance;

    public static List<Entry> entries = new ArrayList<>();

    public EntryManager() {
        if(instance != null) return;
        instance = this;
    }



}
