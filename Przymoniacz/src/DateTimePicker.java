import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.calendar.SingleDaySelectionModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Klasa DateTimePicker odpowiedziala na kalendarz oraz jego wlasciwosci
 */
public class DateTimePicker extends JXDatePicker {
    /**
     * objekt timeSpinner tworzacy spinner godziny
     */
    private JSpinner timeSpinner;
    /**
     * objekt timePanel tworzacy panel wyswietlajacy godzine
     */
    private JPanel timePanel;
    /**
     * objet timeFormat odpowiedzialny za format czasu i daty
     */
    private DateFormat timeFormat;

    /**
     * Konstruktor DateTimePicker
     */
    public DateTimePicker() {
        super();
        getMonthView().setSelectionModel(new SingleDaySelectionModel());
    }

    /**
     * funkcja zatwierdzajaca edytowanie godziny
     */
    public void commitEdit() throws ParseException {
        commitTime();
        super.commitEdit();
    }

    /**
     * wywolanie panelu godziny
     */
    @Override
    public JPanel getLinkPanel() {
        super.getLinkPanel();
        if (timePanel == null) {
            timePanel = createTimePanel();
        }
        setTimeSpinners();
        return timePanel;
    }

    /**
     * utworzenie panelu godziny, oraz nadanie wlasciwosci takich jak np. color czy format czasu
     */
    private JPanel createTimePanel() {
        //utworzenie Janelu
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new FlowLayout());
        SpinnerDateModel dateModel = new SpinnerDateModel();

        //uwtorzenie Jspinnera
        timeSpinner = new JSpinner(dateModel);
        if (timeFormat == null) timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        updateTextFieldFormat();
        newPanel.add(new JLabel("Time:"));
        newPanel.add(timeSpinner);
        newPanel.setBackground(Color.WHITE);
        return newPanel;
    }

    /**
     * funkcja aktualizujaca TextFiel w ktorym wyswietlana jest nasza wybrana data i godzina
     */
    private void updateTextFieldFormat() {
        if (timeSpinner == null) return;
        JFormattedTextField tf = ((JSpinner.DefaultEditor) timeSpinner.getEditor()).getTextField();
        DefaultFormatterFactory factory = (DefaultFormatterFactory) tf.getFormatterFactory();
        DateFormatter formatter = (DateFormatter) factory.getDefaultFormatter();

        //Zmiana formatu aby pokazywać tylko godziny
        formatter.setFormat(timeFormat);
    }

    /**
     * funkcaj zatwierdzajaca wybrany przez nas czas
     */
    private void commitTime() {
        Date date = getDate();
        if (date != null) {
            Date time = (Date) timeSpinner.getValue();
            GregorianCalendar timeCalendar = new GregorianCalendar();
            timeCalendar.setTime(time);

            //utworzenie obiektu kalendarz
            GregorianCalendar calendar = new GregorianCalendar();

            //nadanie własciwosci obiektowi kalendarz
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Date newDate = calendar.getTime();
            setDate(newDate);
        }
    }

    /**
     * funkca nadajaca terazniejsza date do TimeSpinera oraz formatuje date tylko do godziny
     */
    private void setTimeSpinners() {
        Date date = getDate();
        if (date != null) {
            timeSpinner.setValue(date);
        }
    }
}
