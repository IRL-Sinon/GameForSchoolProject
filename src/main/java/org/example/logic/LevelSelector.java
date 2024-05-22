package org.example.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LevelSelector extends JPanel {

    // Constructor initializes the level selector with level buttons
    public LevelSelector(ActionListener startTestLevelAction, ActionListener backAction) {
        // Sets the layout of the level selector to GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds padding around components

        // Creates and configures the start test level button
        JButton startTestLevelButton = new JButton("Test Level");
        startTestLevelButton.setFont(new Font("Arial", Font.BOLD, 24));
        startTestLevelButton.addActionListener(startTestLevelAction);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(startTestLevelButton, gbc); // Adds the start test level button to the panel

        // Creates and configures the back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.addActionListener(backAction);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(backButton, gbc); // Adds the back button to the panel
    }
}
