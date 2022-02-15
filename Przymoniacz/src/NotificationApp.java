import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasa glowna zawierajaca gui calego programu, inicjalizacje przyciskow wraz z ich funkcjonalnoscia
 */
public class NotificationApp {
    /**
     * Zmienna przechowujaca email adresata
     */
    String email;
    /**
     * Zmienna przechowujaca wiadomosc do adresata
     */
    String message;
    /**
     * Zmienna przechowujaca tytul maila
     */
    String title;
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
     * Zmienna przechowujaca wybrana date
     */
    String pickedDate = "";

    //Elementy GUI
    /**
     * objekt frame tworzacy okno aplikacji
     */
    JFrame frame = new JFrame("Notification App");
    /**
     * objekt panel tworzacy panel gdzie dodawwany sa poszczegolne elementy
     */
    JPanel panel = new JPanel();
    /**
     * objekt selectAppButton tworzacy przycisk wyboru aplikacji do otworzenia
     */

    JButton selectAppButton = new JButton("Select App");
    /**
     * objekt selectFileButton tworzacy przycisk wyboru pliku do zalacznika
     */
    JButton selectFileButton = new JButton("Select File");
    /**
     * objekt commitButton tworzacy przycisk zatwierdzajacy i dodajacy powiadomienie
     */
    JButton commitButton = new JButton("Commit");
    /**
     * objekt readFileButton tworzacy przycisk pobierajacy tekst z pliku do wiadomosci
     */
    JButton readFileButton = new JButton("Read file");
    /**
     * objekt dateLabel tworzacy label Date Time:
     */
    JLabel dateLabel = new JLabel("Date & Time:");
    /**
     * objekt emailLabel tworzacy label Email
     */
    JLabel emailLabel = new JLabel("Email:");
    /**
     * objekt titleLabel tworzacy label Title
     */
    JLabel titleLabel = new JLabel("Title:");
    /**
     * objekt messageLabel tworzacy label Message
     */
    JLabel messageLabel = new JLabel("Message:");
    /**
     * objekt emailTextField tworzacy textField do wpisania maila
     */
    JTextField emailTextField = new JTextField();
    /**
     * objekt TitleTextField tworzacy textField do wpisania tytulu maila
     */
    JTextField titleTextField = new JTextField();
    /**
     * objekt MessageTextArea tworzacy testArea do wpisania wiadomosci maila
     */
    JTextArea messageTextArea = new JTextArea();
    /**
     * objekt scrollPane tworzacy scroll
     */
    JScrollPane scrollPane = new JScrollPane(messageTextArea);
    /**
     * zmienna size pobierajaca rozmiar dateLabela
     */
    Dimension size = dateLabel.getPreferredSize();
    /**
     * zmienna today pobierajaca terazniejsza date
     */
    Date today = new Date();
    /**
     * obiekt dateTimePicker
     */
    DateTimePicker dateTimePicker = new DateTimePicker();
    /**
     * zmienna displayTray
     */
    DisplayTray displayTray = new DisplayTray();


    /**
     * Utworzenie listy powiadomien
     */
    List<Notification> notifications = new ArrayList<>();

    /**
     * Utworzenie pustego obiektu powiadomienia
     */
    Notification notification = new Notification(null, null, null,"",null, null, null);

    /**
     * Konstruktor NotificationApp
     */
    public NotificationApp() {
        Date date = new Date(today.getTime() - (1000));

        //dodanie pustego powiadomienia do listy powiadomien
        notifications.add(new Notification(null, null, null, date.toString(),null, null, null));

        dateTimePicker.setFormats(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT));
        dateTimePicker.setDate(date);

        //dodanie elementow do gui
        frame.getContentPane().add(dateTimePicker);
        dateTimePicker.setBounds(10, 30, 140, 30);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(312, 440);

        panel.add(dateLabel);
        panel.add(emailLabel);
        panel.add(titleLabel);
        panel.add(messageLabel);
        panel.add(selectAppButton);
        panel.add(selectFileButton);
        panel.add(commitButton);
        panel.add(readFileButton);
        panel.add(emailTextField);
        panel.add(titleTextField);
        panel.add(scrollPane);
        panel.setLayout(null);

        frame.add(panel);
        frame.setLocationRelativeTo(null);

        //nadanie wlasciowsci poszczegolnym elementom GUI
        selectAppButton.setBounds(185, 75, 100, 30);
        selectAppButton.setFocusable(false);
        selectFileButton.setBounds(10, 75, 140, 30);
        selectFileButton.setFocusable(false);

        commitButton.setBounds(95, 350, 110, 30);
        commitButton.setFocusable(false);

        readFileButton.setBounds(185, 30, 100, 30);
        readFileButton.setFocusable(false);

        emailTextField.setBounds(10, 140, 275, 25);
        titleTextField.setBounds(10, 195, 275, 25);

        Border border = BorderFactory.createLineBorder(Color.gray);

        scrollPane.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(3, 1, 3, 1)));
        messageTextArea.setLineWrap(true);
        scrollPane.setBounds(10, 250, 275, 80);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        dateLabel.setBounds(10, 10, size.width, size.height);
        emailLabel.setBounds(10, 120, size.width, size.height);
        titleLabel.setBounds(10, 175, size.width, size.height);
        messageLabel.setBounds(10, 230, size.width, size.height);

        frame.setVisible(true);

        //nadanie funkcjonalnosci przyciku odpowiedzialnego za wybor sciezki aplikacji
        selectAppButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Apps (*.exe , *.lnk)", "exe", "lnk");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null); //select file to open
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                appPath = file.toString();
            }
        });

        //nadanie funkcjonalnosci przyciku odpowiedzialnego za wybor zalacznika
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt , *.pdf , *.doc , *.docx)", "txt", "pdf","doc", "docx");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                filePath = file.toString();
            }
        });

        //nadanie funkcjonalnosci przyciku odpowiedzialnego za wybor pliku, z ktorego zostanie wczytana wiadomosc
        readFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null); //select file to open
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                readFilePath = file.toString();
            }
            try {
                String fileText = Files.readString(Paths.get(readFilePath));
                messageTextArea.setText(fileText);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //nadanie funkcjonalnosci przyciku odpowiedzialnego za zatwierdzenie wprowadzonych danych oraz dodanie powiadomienia
        commitButton.addActionListener(e -> {
            try {
                //pobranie i zapisanie wartosci do zmiennych
                email = emailTextField.getText();
                title = titleTextField.getText();
                message = messageTextArea.getText();
                pickedDate = dateTimePicker.getDate().toString();

                //utworznie obiektu Notification z naszymi wartosciami
                notification = new Notification(email, title, message, pickedDate, filePath,readFilePath, appPath);

                //dodanie obiektu notification do listy powiadomien
                notifications.add(notification);

                //Tworzenie Dialogu dodania powiadomienia
                JOptionPane.showMessageDialog(frame, "Notification added", "Notification", JOptionPane.INFORMATION_MESSAGE);

                //czyszcznie TextArea do pustego pola
                messageTextArea.setText(null);
                emailTextField.setText(null);
                titleTextField.setText(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        /*
         * petla trwajaca w nieskonczonosc, ktora sprwdza warunek czy ktorys element z listy powiadomien ktorego data i godzina
         * jest rowna obecnej terazniejszej dacie i godzinie. Jesli warunek jest spelniony to nastepuje wyslanie maila
         * otworzenie aplikacji oraz ukaznie powiadomienia
         */
        while (true) {
            Date today = new Date();
            if (notifications.stream().anyMatch(notification -> notification.date.equals(today.toString()))){
                try {
                    System.out.println("Work");
                    SendMail.sendMail(email, title, message, filePath);
                    ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", appPath, "-n", "100");
                    processBuilder.start();
                    displayTray.displayTray(title);
                    JOptionPane.showMessageDialog(frame, title, "Notification", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Doesn't Work");
        }
    }

    /**
     * funkcja main budujaca nasza aplikacje
     */
    public static void main(String[] args) {
        new NotificationApp();
    }
}
