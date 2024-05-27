package org.example.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DeadScreen extends JPanel {

    // Constructor initializes the died screen with a message and buttons
    public DeadScreen(ActionListener backToMenuAction) {
        // Sets the layout of the died screen to GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds padding around components

        // Creates and configures the died message label
        JLabel diedLabel = new JLabel("You Died!");
        diedLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(diedLabel, gbc); // Adds the died message label to the panel

        // Creates and configures the back to menu button
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.addActionListener(backToMenuAction);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(backButton, gbc); // Adds the back to menu button to the panel
    }
}
