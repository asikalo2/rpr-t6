package ba.unsa.etf.rpr.tutorijal06;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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

    public SimpleStringProperty imeProperty;
    public SimpleStringProperty prezimeProperty;
    public SimpleStringProperty jmbgProperty;
    public SimpleStringProperty emailAdresaProperty;

    public Controller() {
        imeProperty = new SimpleStringProperty("");
        prezimeProperty = new SimpleStringProperty("");
        jmbgProperty = new SimpleStringProperty("");
        emailAdresaProperty = new SimpleStringProperty("");

    }

    @FXML
    public void initialize() {
        imeField.textProperty().bindBidirectional(imeProperty);
        prezimeField.textProperty().bindBidirectional(prezimeProperty);
        jmbgField.textProperty().bindBidirectional(jmbgProperty);
        emailAdresaField.textProperty().bindBidirectional(emailAdresaProperty);
        dodajListenere();
    }

    boolean validnost(String n) {
        int duzina = 0;
        int i = 0;
        char c;

        duzina = n.length();

        if (duzina > 20 || duzina <= 0) return false;

        for (i = 0; i < duzina; i++)  //Check for `Firstname`
        {
            c = n.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    boolean ispravanIndeks(String s) {
        if (s.length() != 5 && s.length() > 0 && s.charAt(0) != 1)
            return false;
        else return true;
    }

    private static boolean cifraCheck(String broj)
    {
        boolean validno = false;

        char[] charovi = broj.toCharArray();
        for (int i = 0; i < charovi.length; i++)
        {
            validno = false;
            if (((charovi[i] >= '0') && (charovi[i] <= '9')))
                validno = true;
        }
        return validno;
    }

    boolean ispravanJMBG(String s) {
        // Nadjedno na netu
        List<Integer> lista = new ArrayList<Integer>();
        if(cifraCheck(s))
        {
            for(char ch : s.toCharArray())
            {
                lista.add( Integer.valueOf(String.valueOf(ch)));
            }

            if (lista.size()!= 13)
                return false;

            else
            {
                double eval = 0.0;
                for (int i = 0; i < 6; i++)
                {
                    eval += (7 - i) * (lista.get(i) + lista.get(i + 6));
                }
                return lista.get(12) == 11 - eval % 11;
            }
        }
        else return false;
    }


    boolean ispravanDatum(String s) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        format.setLenient(false);

        try {
            format.parse(s);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    private void dodajListenere() {
        //Listener za imeField
        imeField.textProperty().addListener((observableValue, o, n) -> {
            if (validnost(n)) {
                imeField.getStyleClass().removeAll("poljeNijeIspravno");
                imeField.getStyleClass().add("poljeIspravno");
            } else {
                imeField.getStyleClass().removeAll("poljeIspravno");
                imeField.getStyleClass().add("poljeNijeIspravno");
            }
        });
        //Listener za prezimeField
        prezimeField.textProperty().addListener((observableValue, o, n) -> {
            if (validnost(n)) {
                prezimeField.getStyleClass().removeAll("poljeNijeIspravno");
                prezimeField.getStyleClass().add("poljeIspravno");
            } else {
                prezimeField.getStyleClass().removeAll("poljeIspravno");
                prezimeField.getStyleClass().add("poljeNijeIspravno");
            }
        });
        // Listener za email adresu
        emailAdresaField.textProperty().addListener((obs, o, n) -> {
            EmailValidator validator = EmailValidator.getInstance();
            if (validator.isValid(n)) {
                emailAdresaField.getStyleClass().removeAll("poljeNijeIspravno");
                emailAdresaField.getStyleClass().add("poljeIspravno");
            } else {
                emailAdresaField.getStyleClass().removeAll("poljeIspravno");
                emailAdresaField.getStyleClass().add("poljeNijeIspravno");
            }
        });
        // Listener za kontakt adresu
        kontaktAdresaField.textProperty().addListener((observableValue, o, n) -> {
            if (validnost(n)) {
                kontaktAdresaField.getStyleClass().removeAll("poljeNijeIspravno");
                kontaktAdresaField.getStyleClass().add("poljeIspravno");
            } else {
                kontaktAdresaField.getStyleClass().removeAll("poljeIspravno");
                kontaktAdresaField.getStyleClass().add("poljeNijeIspravno");
            }
        });
        // Listener za broj indexa
        brojindeksaField.textProperty().addListener((observableValue, s, t1) -> {
            if (ispravanIndeks(t1)) {
                brojindeksaField.getStyleClass().removeAll("poljeNijeIspravno");
                brojindeksaField.getStyleClass().add("poljeIspravno");
            } else {
                brojindeksaField.getStyleClass().removeAll("poljeIspravno");
                brojindeksaField.getStyleClass().add("poljeNijeIspravno");
            }
        });
        // Listener za datum rodjenja
        datumrodjenjaField.textProperty().addListener((observableValue, s, t1) -> {
            if (ispravanDatum(t1)) {
                datumrodjenjaField.getStyleClass().removeAll("poljeNijeIspravno");
                datumrodjenjaField.getStyleClass().add("poljeIspravno");
            } else {
                datumrodjenjaField.getStyleClass().removeAll("poljeIspravno");
                datumrodjenjaField.getStyleClass().add("poljeNijeIspravno");
            }
        });
        // Listener za JMBG
        jmbgField.textProperty().addListener((observableValue, s, t1) -> {
            if (ispravanJMBG(t1)) {
                jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                jmbgField.getStyleClass().add("poljeIspravno");
            } else {
                jmbgField.getStyleClass().removeAll("poljeIspravno");
                jmbgField.getStyleClass().add("poljeNijeIspravno");
            }
        });


    }
}