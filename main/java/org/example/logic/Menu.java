package org.example.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    // Constructor initializes the menu with start and exit buttons
    public Menu(ActionListener startAction, ActionListener exitAction) {
        // Sets the layout of the menu to GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds padding around components

        // Creates and configures the start button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.addActionListener(startAction);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(startButton, gbc); // Adds the start button to the panel

        // Creates a panel for the exit button and configures it
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false); // Makes the bottom panel transparent
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Positions the panel at the bottom right
        add(bottomPanel, gbc); // Adds the bottom panel to the main panel

        // Creates and configures the exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.addActionListener(exitAction);
        bottomPanel.add(exitButton, BorderLayout.SOUTH); // Adds the exit button to the bottom panel
    }
}
