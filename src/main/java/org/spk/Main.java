package org.spk;

import org.spk.ui.SPKFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new SPKFrame().setVisible(true);
        });
    }
}