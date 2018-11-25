package ba.unsa.etf.rpr.tutorijal06;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Controller {
    public TextField imeField;
    public TextField prezimeField;
    public TextField brojindeksaField;
    public TextField jmbgField;
    public TextField datumrodjenjaField;
    public TextField mjestorodjenjaField;
    public TextField kontaktAdresaField;
    public TextField kontaktTelefonField;
    public TextField emailAdresaField;

    boolean validnost(String n){
        int duzina=0;
        int i=0;
        char c;

        duzina=n.length();

        if(duzina>20 || duzina<=0) return false;

        for(i=0;i<duzina;i++)  //Check for `Firstname`
        {
            c = n.charAt(i);
            if(!((c>='a' && c<='z')||(c>='A' && c<='Z')) && c!=' ')
            {
                return false;
            }
        }
    return true;
    }

    boolean ispravanIndeks(String s){
        int i=0;
        int duzina=0;
        char c;

        duzina=s.length();

        if(duzina!=5) return false;

        for(i=0;i<duzina;i++)
        {
            c = s.charAt(i);
            if(((c>='a' && c<='z')||(c>='A' && c<='Z')) && c!=' ')
            {
                return false;
            }
        }
        return true;
    }

    boolean ispravanJMBG(String s){
        int i=0;
        int duzina=0;
        char c;

        duzina=s.length();

        if(duzina!=13) return false;

        for(i=0;i<duzina;i++) {
            c = s.charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    boolean ispravanDatum(String s){
        int i=0;
        int duzina=0;
        char c;

        duzina=s.length();

        if(duzina!=8) return false;

        for(i=0;i<duzina;i++) {
            c = s.charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) && c != ' ') {
                return false;
            }
        }

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        format.setLenient(false);

        try {
            format.parse(s);
        } catch (ParseException e) {
            System.out.println("Date " + s + " is not valid according to " +
                    ((SimpleDateFormat) format).toPattern() + " pattern.");
        }

        return true;
    }

    imeField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
            if (validnost(n)) {
                imeField.getStyleClass().removeAll("poljeNijeIspravno");
                imeField.getStyleClass().add("poljeIspravno");
            } else {
                imeField.getStyleClass().removeAll("poljeIspravno");
                imeField.getStyleClass().add("poljeNijeIspravno");
            }
        }
    });

    prezimeField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
            if (validnost(n)) {
                prezimeField.getStyleClass().removeAll("poljeNijeIspravno");
                prezimeField.getStyleClass().add("poljeIspravno");
            } else {
                prezimeField.getStyleClass().removeAll("poljeIspravno");
                prezimeField.getStyleClass().add("poljeNijeIspravno");
            }
        }
    });

    emailAdresaField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> obs, String o, String n) {
            EmailValidator validator = EmailValidator.getInstance();
            if (validator.isValid(n)) {
                emailAdresaField.getStyleClass().removeAll("poljeNijeIspravno");
                emailAdresaField.getStyleClass().add("poljeIspravno");
            } else {
                emailAdresaField.getStyleClass().removeAll("poljeIspravno");
                emailAdresaField.getStyleClass().add("poljeNijeIspravno");
            }
        }
    });

    kontaktAdresaField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
            if (validnost(n)) {
                kontaktAdresaField.getStyleClass().removeAll("poljeNijeIspravno");
                kontaktAdresaField.getStyleClass().add("poljeIspravno");
            } else {
                kontaktAdresaField.getStyleClass().removeAll("poljeIspravno");
                kontaktAdresaField.getStyleClass().add("poljeNijeIspravno");
            }
        }
    });


}
