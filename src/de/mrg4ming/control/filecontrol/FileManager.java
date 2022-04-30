package de.mrg4ming.control.filecontrol;

import de.mrg4ming.control.EntryManager;

public class FileManager {

    public static FileManager instance;

    public DataFile dataFile;

    private boolean fileIsLoaded = false;
    private String currentLoadedFilePath;

    public FileManager() {
        if(instance != null) return;
        instance = this;
    }

    public boolean fileIsLoaded() {
        return this.fileIsLoaded;
    }

    public void loadFile(String path) {
        currentLoadedFilePath = path;
        dataFile = new DataFile(path);

        fileIsLoaded = true;
    }

    public boolean saveFile() {
        if(fileIsLoaded) {
            dataFile.save();
        }
        return false;
    }

    public void unloadFile() {
        fileIsLoaded = false;

        dataFile = null;
        currentLoadedFilePath = null;

        EntryManager.instance.entries.clear();
        EntryManager.instance.distribute();
    }

    public void reloadFile() {
        fileIsLoaded = false;

        dataFile = null;

        EntryManager.instance.entries.clear();
        EntryManager.instance.distribute();


        this.loadFile(currentLoadedFilePath);
    }

}
