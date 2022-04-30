package de.mrg4ming.control.filecontrol;

import de.mrg4ming.Main;
import de.mrg4ming.control.Entry;
import de.mrg4ming.control.EntryManager;
import de.mrg4ming.gui.lib.JsonStringFormatter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataFile {
    String filePath;
    JSONParser jsonParser;

    public DataFile(String path) {
        this.filePath = path;

        EntryManager.instance.entries.clear();
        Main.mainWindow.entryListGUI.entryListModel.clear();

        parse(filePath);
    }

    private void parse(String filePath) {
        jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject raw = (JSONObject) obj;
            //System.out.println("Raw: " + raw);

            JSONObject all = (JSONObject) raw.get("all");
            //System.out.println("All: " + all);

            JSONObject presets = (JSONObject) all.get("presets");
            //System.out.println("Presets: " + presets);

            for (Object o : presets.keySet()) {
                JSONObject preset = (JSONObject) presets.get(o.toString());
                //System.out.println("O: " + o.toString());

                int pos = Integer.parseInt(preset.get("pos").toString());

                Entry.Status status = Entry.Status.getStatusByString(preset.get("status").toString());
                String text = preset.get("text").toString();

                Entry.ClearTime clearTime;
                if(preset.get("clearAfter") == null) {
                    clearTime = Entry.ClearTime.NEVER;
                } else clearTime = Entry.ClearTime.getClearTimeByString(preset.get("clearAfter").toString());

                //Using pos also as id
                Entry _entry = new Entry(pos, pos, text, status, clearTime);

                EntryManager.instance.entries.add(_entry);
            }

            EntryManager.instance.sortListAndApplyDistribute();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        JSONObject raw = new JSONObject();
        JSONObject all = new JSONObject();
        JSONObject presets = new JSONObject();

        for(Entry e : EntryManager.instance.entries) {
            presets.put(e.getPosition(), e.getJSONData());
        }

        all.put("presets", presets);
        raw.put("all", all);

        try (FileWriter writer = new FileWriter(filePath)) {
            String toWrite = JsonStringFormatter.formatJSONStr(raw.toJSONString(), 4);
            writer.write(toWrite);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return filePath;
    }
}
