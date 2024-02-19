package ca.ulaval.glo2004;
import ca.ulaval.glo2004.gui.MainWindow;

import javax.swing.*;


public class App {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}

