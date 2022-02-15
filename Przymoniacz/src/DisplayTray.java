import java.awt.*;

/**
 * Klada DisplayTray odpowiadajaca za powiadomienie Systemowego
 */
public class DisplayTray {
    /**
     * Konstruktor DisplayTray
     */
    public DisplayTray(){}

    /**
     * funkcja odpowiedziala za wyswietlanie powiadomienia Systemowego
     */
    public void displayTray(String title) throws AWTException {
        //utworzenie obiektu tray
        SystemTray tray = SystemTray.getSystemTray();

        //utworzenie obiektu image, pobranie standardowej obrazu ikony
        Image image = Toolkit.getDefaultToolkit().createImage("");

        //utworzenie obiektu TryIcon, powiadomienia Systemowego
        TrayIcon trayIcon = new TrayIcon(image, "");
        trayIcon.setImageAutoSize(true);
        tray.add(trayIcon);
        trayIcon.displayMessage("Notification", title, TrayIcon.MessageType.INFO);
    }
}
