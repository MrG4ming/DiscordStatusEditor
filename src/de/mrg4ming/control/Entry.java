package de.mrg4ming.control;

import org.json.simple.JSONObject;

public class Entry {
    public enum Status {
        INVISIBLE("invisible"),
        DND("dnd"),
        IDLE("idle"),
        ONLINE("online");

        private final String value;
        Status(String value) {
            this.value = value;
        }
    }
    public enum ClearTime {
        TODAY("TODAY"),
        HOURS_4("14400000"),
        HOURS_1("3600000"),
        MINUTES_30("1800000"),
        DONT("");

        private final String value;
        ClearTime(String value) {
            this.value = value;
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
        if(clearTime != ClearTime.DONT) obj.put("clearAfter", clearTime.value);
        obj.put("text", text.substring(0, text.length() - Math.max(0, text.length() - MAX_CHAR_COUNT)));
        return obj;
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
}
