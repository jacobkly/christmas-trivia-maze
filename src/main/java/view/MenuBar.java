package view;

import controller.GameListener;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    private final GameListener myGameListener;

    private final VolumeSliderPanel myVolumeSliderPanel;

    public MenuBar(final GameListener theGameListener,
                   final VolumeSliderPanel theVolumeSliderPanel) {
        myGameListener = theGameListener;
        myVolumeSliderPanel = theVolumeSliderPanel;

        addFileMenu();
        addSettingsMenu();
        addAboutMenu();
    }

    private void addFileMenu() {
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);

        addSaveMenuItem(fileMenu);
        addLoadMenuItem(fileMenu);
        addExitGameMenuItem(fileMenu);
    }

    private void addSaveMenuItem(final JMenu theFileMenu) {
        JMenuItem saveMenuItem = new JMenuItem("Save");
        theFileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(e -> {
            /* save game action here */
            repaint();
        });
    }

    private void addLoadMenuItem(final JMenu theFileMenu) {
        JMenuItem loadMenuItem = new JMenuItem("Load");
        theFileMenu.add(loadMenuItem);
        loadMenuItem.addActionListener(e -> {
            /* load game directory action here */
            repaint();
        });
    }

    private void addExitGameMenuItem(final JMenu theFileMenu) {
        JMenuItem exitGameMenuItem = new JMenuItem("Exit Game");
        theFileMenu.add(exitGameMenuItem);
        exitGameMenuItem.addActionListener(e -> myGameListener.startMainMenu());
    }

    private void addSettingsMenu() {
        JMenu settingsMenu = new JMenu("Settings");
        add(settingsMenu);

        addVolumeMenuItem(settingsMenu);
        addDebugModeMenuItem(settingsMenu);
    }

    private void addVolumeMenuItem(final JMenu theSettingsMenu) {
        JMenuItem volumeMenuItem = new JMenuItem("Volume");
        theSettingsMenu.add(volumeMenuItem);
        volumeMenuItem.addActionListener(e -> {
            myVolumeSliderPanel.showDialog(this, "Volume");
            repaint();
        });
    }

    private void addDebugModeMenuItem(final JMenu theSettingsMenu) {
        JCheckBoxMenuItem debugMenuItem = new JCheckBoxMenuItem("Debug Mode");
        debugMenuItem.setSelected(false);
        theSettingsMenu.add(debugMenuItem);
        debugMenuItem.addActionListener(e -> {
           if (debugMenuItem.isSelected()) {
               /* enable debug mode action here */
           } else {
               /* disable debug mode action here */
           }
           repaint();
        });
    }

    private void addAboutMenu() {
        JMenu aboutMenu = new JMenu("About");
        add(aboutMenu);

        aboutMenu.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "In development by: " +
                            "Mathew Miller\n" +
                            "Jacob Klymenko\n" +
                            "Cai Spidel");
            repaint();
        });
    }
}
