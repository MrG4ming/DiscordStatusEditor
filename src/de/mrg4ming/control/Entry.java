package de.mrg4ming.control;

import de.mrg4ming.Main;
import de.mrg4ming.control.filecontrol.FileManager;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;

public class Entry implements Comparable<Entry> {

    public static final String ENTRY_NAME_PREFIX = "Entry ";

    public enum Status {
        INVISIBLE("invisible", "Invisible"),
        DND("dnd", "Do not disturb"),
        IDLE("idle", "Idle"),
        ONLINE("online", "Online");

        private final String value;
        private final String alias;
        Status(String value, String alias) {
            this.value = value;
            this.alias = alias;
        }

        public static Status getStatusByString(String s) {
            for(Status status : Status.values()) {
                if(status.value.equals(s) || status.alias.equals(s)) {
                    return status;
                }
            }
            return null;
        }
    }
    public enum ClearTime {
        TODAY("TODAY", "Today"),
        HOURS_4("14400000", "4 Hours"),
        HOURS_1("3600000", "1 Hour"),
        MINUTES_30("1800000", "30 Minutes"),
        NEVER("", "Never");

        private final String value;
        private final String alias;
        ClearTime(String value, String alias) {
            this.value = value;
            this.alias = alias;
        }

        public static ClearTime getClearTimeByString(String s) {
            for(ClearTime clearTime : ClearTime.values()) {
                if(clearTime.value.equals(s) || clearTime.alias.equals(s)) {
                    return clearTime;
                }
            }
            return null;
        }
    }

    public static final int MAX_CHAR_COUNT = 128;

    int id;
    int position;

    int charCount = 0;
    String text = "";

    Status status;
    ClearTime clearTime;

    public Entry(int id, int position, String text, Status status, ClearTime clearTime) {
        this.id = id;
        this.position = position;

        this.charCount = text.length();
        this.text = text;

        this.status = status;
        this.clearTime = clearTime;
    }

    public JSONObject getJSONData() {
        JSONObject obj = new JSONObject();
        obj.put("pos", position);
        obj.put("status", status.value);
        if(clearTime != ClearTime.NEVER) obj.put("clearAfter", clearTime.value);
        obj.put("text", text.substring(0, text.length() - Math.max(0, text.length() - (Entry.MAX_CHAR_COUNT - 1))));
        return obj;
    }

    public void saveEntry() {
        this.text = Main.mainWindow.entryEditorGUI.getText();
        this.status = Main.mainWindow.entryEditorGUI.getSelectedStatus();
        this.clearTime = Main.mainWindow.entryEditorGUI.getSelectedClearTime();

        EntryManager.instance.updateEntrySort();

        //FileManager.instance.save();
    }

    public int getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }

    public Status getStatus() {
        return status;
    }

    public ClearTime getClearTime() {
        return clearTime;
    }

    @Override
    public String toString() {
        return "Entry={" +
                "id=" + id +
                ", position=" + position +
                ", charCount=" + charCount +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", clearTime=" + clearTime +
                '}';
    }

    @Override
    public int compareTo(Entry o) {
        return this.getPosition() - o.getPosition();
    }
}
