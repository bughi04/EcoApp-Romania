package org.example.combatereapoluariiapp.src;
import org.example.combatereapoluariiapp.src.ui.MainFrame;
import javax.swing.*;

public class CombatereaPoluariiApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainFrame app = new MainFrame();
            app.setVisible(true);
        });
    }
}