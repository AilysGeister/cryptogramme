package main;

import java.awt.EventQueue;
import java.io.IOException;

import main.controller.Settings;
import main.view.MainFrame;

//import view.*;

/**
 * Main class of the software.
 */
public class Main {
	public static void main(String[] args) {
		try {
            Settings.initialParameters();
        } catch (IOException e) {
            System.err.println("Failed to initialize settings file");
            e.printStackTrace();
            System.exit(1);
        }
		
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}
}
