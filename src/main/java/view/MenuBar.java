package view;

import controller.GameListener;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

public final class MenuBar extends JMenuBar {

    private final GameListener myGameListener;

    private final VolumeSliderPanel myVolumeSliderPanel;

    private final Component myParentComponent;

    public MenuBar(final GameListener theGameListener,
                   final VolumeSliderPanel theVolumeSliderPanel,
                   final Component theParentComponent) {
        myGameListener = theGameListener;
        myVolumeSliderPanel = theVolumeSliderPanel;
        myParentComponent = theParentComponent;

        addFileMenu();
        addSettingsMenu();
        addInformationMenu();
    }

    private void addFileMenu() {
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);
        addJMenuActionListener(fileMenu);

        addSaveMenuItem(fileMenu);
        addLoadMenuItem(fileMenu);
        addExitGameMenuItem(fileMenu);
    }

    private void addSaveMenuItem(final JMenu theFileMenu) {
        JMenuItem saveMenuItem = new JMenuItem("Save");
        theFileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(e -> {
            /* save game action here */
            myParentComponent.repaint();
        });
    }

    private void addLoadMenuItem(final JMenu theFileMenu) {
        JMenuItem loadMenuItem = new JMenuItem("Load");
        theFileMenu.add(loadMenuItem);
        loadMenuItem.addActionListener(e -> {
            /* load game directory action here */
            myParentComponent.repaint();
        });
    }

    private void addExitGameMenuItem(final JMenu theFileMenu) {
        JMenuItem exitGameMenuItem = new JMenuItem("Exit Game");
        theFileMenu.add(exitGameMenuItem);
        exitGameMenuItem.addActionListener(e -> {
            myGameListener.startMainMenu();
            myParentComponent.repaint();
        });
    }

    private void addSettingsMenu() {
        JMenu settingsMenu = new JMenu("Settings");
        add(settingsMenu);
        addJMenuActionListener(settingsMenu);

        addVolumeMenuItem(settingsMenu);
        addDebugModeMenuItem(settingsMenu);
    }

    private void addVolumeMenuItem(final JMenu theSettingsMenu) {
        JMenuItem volumeMenuItem = new JMenuItem("Volume");
        theSettingsMenu.add(volumeMenuItem);
        volumeMenuItem.addActionListener(e -> {
            myVolumeSliderPanel.showDialog(myParentComponent, "Volume");
            myParentComponent.repaint();
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
            myParentComponent.repaint();
        });
    }

    private void addInformationMenu() {
        JMenu informationMenu = new JMenu("Information");
        add(informationMenu);
        addJMenuActionListener(informationMenu);

        addAboutMenuItem(informationMenu);
        addInstructionsMenuItem(informationMenu);
    }

    private void addAboutMenuItem(final JMenu theInfoMenu) {
        JMenuItem aboutMenuItem = new JMenuItem("About");
        theInfoMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(myParentComponent,
                    """
                    In development by:
                    
                    Cai Spidel
                    Mathew Miller
                    Jacob Klymenko
                    """);
            myParentComponent.repaint();
        });
    }

    private void addInstructionsMenuItem(final JMenu theInfoMenu) {
        JMenuItem instructionsMenuItem = new JMenuItem("Instructions");
        theInfoMenu.add(instructionsMenuItem);
        instructionsMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    myParentComponent,
                    """
                    How to Play!
                
                    Click on a square containing a lock.
                    
                    A question will appear.
                    
                    If answered correctly continue on!
                    If not, try again at the cost of one health...
                
                    If you are completely stumped, use a gift!
                    
                    A gift answers the question for you.
                    They are limited so use them sparingly!
                
                    Now play and try to find Santa!
                    """);
            myParentComponent.repaint();
        });
    }

    private void addJMenuActionListener(final JMenu theMenu) {
        theMenu.addMenuListener(new MenuListener() {
            // No action needed here for repainting parent component
            @Override
            public void menuSelected(final MenuEvent theEvent) {}

            @Override
            public void menuDeselected(final MenuEvent theEvent) { myParentComponent.repaint(); }

            @Override
            public void menuCanceled(final MenuEvent theEvent) { myParentComponent.repaint(); }
        });
    }
}
