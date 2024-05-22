// Level selector panel with test level and back buttons
package org.example.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LevelSelector extends JPanel {

    // Constructor initializes the level selector with test level and back buttons
    public LevelSelector(ActionListener testLevelAction, ActionListener backAction) {
        // Sets the layout of the menu to GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds padding around components

        // Creates and configures the test level button
        JButton testLevelButton = new JButton("Test Level");
        testLevelButton.setFont(new Font("Arial", Font.BOLD, 24));
        testLevelButton.addActionListener(testLevelAction);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(testLevelButton, gbc); // Adds the test level button to the panel

        // Creates and configures the back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.addActionListener(backAction);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(backButton, gbc); // Adds the back button to the panel
    }
}