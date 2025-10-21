import javax.swing.*;
public class Main {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal appWindow = new JanelaPrincipal();
            appWindow.setVisible(true);
        });
    }
}