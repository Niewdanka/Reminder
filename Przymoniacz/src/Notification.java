/**
 * Klada Notification zawierajaca parametry powiadomienia
 */

public class Notification {
    /**
     * Zmienna przechowujaca email adresata
     */
    String email;
    /**
     * Zmienna przechowujaca tytul maila
     */
    String title;
    /**
     * Zmienna przechowujaca wiadomosc do adresata
     */
    String message;
    /**
     * Zmienna przechowujaca wybrana date
     */
    String date;
    /**
     * Zmienna przechowujaca sciezke pliku jako zalacznik
     */
    String filePath;
    /**
     * Zmienna przechowujaca sciezke pliku wczytanana do wiadomosci maila
     */
    String readFilePath;
    /**
     * Zmienna przechowujaca sciezke do aplikacji, ktora ma sie otworzyc
     */
    String appPath;

    /**
     * Kontruktor Notification
     */
    public Notification( String newEmail, String newTitle, String newMessage, String newDate, String newFilePath, String newReadFilePath, String newAppPath){
        email = newEmail;
        title = newTitle;
        message = newMessage;
        date = newDate;
        filePath = newFilePath;
        readFilePath = newReadFilePath;
        appPath = newAppPath;
    }
}
