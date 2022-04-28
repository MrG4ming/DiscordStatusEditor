package de.mrg4ming.gui;

import de.mrg4ming.gui.parts.EntryEditorGUI;
import de.mrg4ming.gui.parts.EntryListGUI;
import de.mrg4ming.gui.parts.MenuBarGUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private int width = 1280, height = 720;

    public EntryListGUI entryListGUI;
    public EntryEditorGUI entryEditorGUI;

    public Window(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setMinimumSize(new Dimension(width, height));

        Dimension _location = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        this.setLocation(_location.width - width / 2, _location.height - height / 2);



        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));

        entryListGUI = new EntryListGUI();
        entryEditorGUI = new EntryEditorGUI();

        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(entryListGUI);
        splitPane.setRightComponent(entryEditorGUI);

        mainPanel.add(splitPane);


        MenuBarGUI menuBarGUI = new MenuBarGUI();
        this.setJMenuBar(menuBarGUI);

        this.add(mainPanel);

        this.setVisible(true);
    }
}
