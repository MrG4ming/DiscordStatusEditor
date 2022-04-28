package de.mrg4ming.gui.parts;

import de.mrg4ming.Main;
import de.mrg4ming.control.EntryManager;
import de.mrg4ming.control.filecontrol.FileManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuBarGUI extends JMenuBar {

    public MenuBarGUI() {
        super();
        JMenu fileMenu = new JMenu("File");

        JMenuItem file_saveItem = new JMenuItem("Save");
        file_saveItem.addActionListener(file_saveAction);
        fileMenu.add(file_saveItem);

        JMenuItem file_loadItem = new JMenuItem("Load");
        file_loadItem.addActionListener(file_loadAction);
        fileMenu.add(file_loadItem);


        this.add(fileMenu);
    }

    ActionListener file_loadAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Loading file...");

            JFileChooser chooser = new JFileChooser(System.getProperty("user.home")
                    + System.getProperty("file.separator")
                    + "AppData" + System.getProperty("file.separator")
                    + "Roaming" + System.getProperty("file.separator")
                    + "BetterDiscord"
                    + System.getProperty("file.separator")
                    + "plugins"
            );
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            if(chooser.showDialog(Main.mainWindow, "Open") == JFileChooser.OPEN_DIALOG) {
                //EntryManager.instance.load(new File(chooser.getSelectedFile().getPath()));

                //TODO: Add save popup if a file is already loaded but not saved
                if(FileManager.instance != null) FileManager.instance = null;
                new FileManager(chooser.getSelectedFile().getPath());

                System.out.println("Loading: " + chooser.getSelectedFile().getPath());
            }
        }
    };

    ActionListener file_saveAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Saving...");
        }
    };
}
