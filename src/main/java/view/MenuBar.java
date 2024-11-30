package view;

import controller.GameListener;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

/**
 * A custom menu bar for a game application. Provides menus and menu items for various game
 * actions such as saving, loading, adjusting settings, and accessing information about the game.
 *
 * This class extends JMenuBar and organizes the menu structure, ensuring the parent component is
 * repainted as necessary when menu interactions occur.
 *
 * @author Jacob Klymenko
 * @version 1.0
 */
public final class MenuBar extends JMenuBar {

    /** Listener to handle game-related actions. */
    private final GameListener myGameListener;

    /** Panel for managing volume settings in the game. */
    private final VolumeSliderPanel myVolumeSliderPanel;

    /** The parent component that hosts this menu bar. */
    private final Component myParentComponent;

    /**
     * Constructs a MenuBar with the specified game listener, volume slider panel, and parent component.
     *
     * @param theGameListener the game listener to handle game-related actions
     * @param theVolumeSliderPanel the panel for adjusting volume settings
     * @param theParentComponent the parent component for this menu bar
     */
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

    /**
     * Adds the "File" menu to the menu bar and its corresponding menu items.
     */
    private void addFileMenu() {
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);
        addMenuListener(fileMenu);

        addSaveMenuItem(fileMenu);
        addLoadMenuItem(fileMenu);
        addExitGameMenuItem(fileMenu);
    }

    /**
     * Adds the "Save" menu item to the specified file menu.
     *
     * @param theFileMenu the file menu to which the save menu item is added
     */
    private void addSaveMenuItem(final JMenu theFileMenu) {
        JMenuItem saveMenuItem = new JMenuItem("Save");
        theFileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(theEvent -> { /* save game action here */ } );
    }

    /**
     * Adds the "Load" menu item to the specified file menu.
     *
     * @param theFileMenu the file menu to which the load menu item is added
     */
    private void addLoadMenuItem(final JMenu theFileMenu) {
        JMenuItem loadMenuItem = new JMenuItem("Load");
        theFileMenu.add(loadMenuItem);
        loadMenuItem.addActionListener(theEvent -> { /* load game directory action here */ });
    }

    /**
     * Adds the "Exit Game" menu item to the specified file menu.
     *
     * @param theFileMenu the file menu to which the exit game menu item is added
     */
    private void addExitGameMenuItem(final JMenu theFileMenu) {
        JMenuItem exitGameMenuItem = new JMenuItem("Exit Game");
        theFileMenu.add(exitGameMenuItem);
        exitGameMenuItem.addActionListener(theEvent -> myGameListener.startMainMenu());
    }

    /**
     * Adds the "Settings" menu to the menu bar and its corresponding menu items.
     */
    private void addSettingsMenu() {
        JMenu settingsMenu = new JMenu("Settings");
        add(settingsMenu);
        addMenuListener(settingsMenu);

        addVolumeMenuItem(settingsMenu);
        addDebugModeMenuItem(settingsMenu);
    }

    /**
     * Adds the "Volume" menu item to the specified settings menu.
     *
     * @param theSettingsMenu the settings menu to which the volume menu item is added
     */
    private void addVolumeMenuItem(final JMenu theSettingsMenu) {
        JMenuItem volumeMenuItem = new JMenuItem("Volume");
        theSettingsMenu.add(volumeMenuItem);
        volumeMenuItem.addActionListener(theEvent -> {
            myVolumeSliderPanel.showDialog(myParentComponent, "Volume");
        });
    }

    /**
     * Adds the "Debug Mode" menu item (as a checkbox) to the specified settings menu.
     *
     * @param theSettingsMenu the settings menu to which the debug mode menu item is added
     */
    private void addDebugModeMenuItem(final JMenu theSettingsMenu) {
        JCheckBoxMenuItem debugMenuItem = new JCheckBoxMenuItem("Debug Mode");
        debugMenuItem.setSelected(false);
        theSettingsMenu.add(debugMenuItem);
        debugMenuItem.addActionListener(theEvent -> {
           if (debugMenuItem.isSelected()) {
               myGameListener.debugIsSelected(true);
           } else {
               myGameListener.debugIsSelected(false);
           }
        });
    }

    /**
     * Adds the "Information" menu to the menu bar and its corresponding menu items.
     */
    private void addInformationMenu() {
        JMenu informationMenu = new JMenu("Information");
        add(informationMenu);
        addMenuListener(informationMenu);

        addAboutMenuItem(informationMenu);
        addInstructionsMenuItem(informationMenu);
    }

    /**
     * Adds the "About" menu item to the specified information menu.
     *
     * @param theInfoMenu the information menu to which the about menu item is added
     */
    private void addAboutMenuItem(final JMenu theInfoMenu) {
        JMenuItem aboutMenuItem = new JMenuItem("About");
        theInfoMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(theEvent -> {
            JOptionPane.showMessageDialog(myParentComponent,
                    """
                    In development by:
                    
                    Cai Spidel
                    Mathew Miller
                    Jacob Klymenko
                    """);
        });
    }

    /**
     * Adds the "Instructions" menu item to the specified information menu.
     *
     * @param theInfoMenu the information menu to which the instructions menu item is added
     */
    private void addInstructionsMenuItem(final JMenu theInfoMenu) {
        JMenuItem instructionsMenuItem = new JMenuItem("Instructions");
        theInfoMenu.add(instructionsMenuItem);
        instructionsMenuItem.addActionListener(theEvent -> {
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
        });
    }

    /**
     * Adds a MenuListener to repaint the parent component when the menu is deselected or canceled.
     *
     * @param theMenu the menu to which the listener is added
     */
    private void addMenuListener(final JMenu theMenu) {
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
