package de.mrg4ming.gui.parts;

import de.mrg4ming.Main;
import de.mrg4ming.control.filecontrol.DataFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

        JMenuItem file_reloadItem = new JMenuItem("Reload");
        file_reloadItem.addActionListener(file_reloadAction);
        fileMenu.add(file_reloadItem);

        KeyStroke ctrl_L = KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
        KeyStroke ctrl_S = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
        KeyStroke ctrl_R = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());

        file_loadItem.setAccelerator(ctrl_L);
        file_saveItem.setAccelerator(ctrl_S);
        file_reloadItem.setAccelerator(ctrl_R);


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

                loadFile(chooser.getSelectedFile().getPath());

                System.out.println("Loading: " + chooser.getSelectedFile().getPath());
            }
        }
    };

    ActionListener file_saveAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Saving...");
            DataFile.instance.save();
            System.out.println("Saved!");
        }
    };

    ActionListener file_reloadAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Reloading...");
            String _currentLoadedFilePath = DataFile.instance.getFilePath();
            loadFile(_currentLoadedFilePath);
            System.out.println("Reloaded!");
        }
    };

    private void loadFile(String path) {
        //TODO: Add save popup if a file is already loaded but not saved
        System.out.println("Loading...");
        if(DataFile.instance != null) DataFile.instance = null;
        new DataFile(path);
        System.out.println("Loaded!");
    }
}
