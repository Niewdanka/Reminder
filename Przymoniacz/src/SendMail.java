import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

/**
 * klasa SendMail odpowiadajaca za wysylanie maila
 */
public class SendMail {
    /**
     * Konstruktor SendMail
     */
    public SendMail(){}

    /**
     * funckja sendMail zawierajace prametry wpisywane przez uzytkowniaka, wysylajaca mail
     */
    public static void sendMail(String mail, String title, String emailMessage, String filePath) throws Exception{
        //nadanie wlasciwosci naszego maila takich jak np. port, rodzaj hosta
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);


        //obiekt sluzacy do autoryzacji loginu oraz hasla naszej poczty
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("", "");
            }
        });

        //Tworzenie obieku wiadomosc oraz nadanie tytulu maila
        Message message = new MimeMessage(session);
        message.setSubject(title);

        //Tworzenie obiektu adresata maila
        Address address = new InternetAddress(mail);
        message.setRecipient(Message.RecipientType.TO, address);

        MimeMultipart multipart = new MimeMultipart();

        //Tworzenie obiektu zalacznika maila
        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(new File(filePath));

        //Tworzenie obiektu ciala wiadomosci oraz typu maila
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailMessage, "text/html");

        //dodanie ciala wiadomosci oraz zalacznika maila
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachment);
        message.setContent(multipart);

        //wyslanie maila
        Transport.send(message);
    }
}
