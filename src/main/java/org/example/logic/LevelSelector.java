package org.example.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Set;

public class LevelSelector extends JPanel {

    // Constructor initializes the level selector with level buttons
    public LevelSelector(ActionListener startLevelAction, ActionListener backAction, Set<String> levelNames) {
        // Sets the layout of the level selector to GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds padding around components

        int row = 0;
        for (String levelName : levelNames) {
            JButton levelButton = new JButton(levelName);
            levelButton.setFont(new Font("Arial", Font.BOLD, 24));
            levelButton.addActionListener(startLevelAction);
            gbc.gridx = 0;
            gbc.gridy = row++;
            add(levelButton, gbc);
        }

        // Creates and configures the back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.addActionListener(backAction);
        gbc.gridx = 0;
        gbc.gridy = row;
        add(backButton, gbc); // Adds the back button to the panel
    }
}
