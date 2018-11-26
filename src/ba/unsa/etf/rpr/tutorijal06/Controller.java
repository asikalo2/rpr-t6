package ba.unsa.etf.rpr.tutorijal06;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;


public class Controller {
    public TextField imeField;
    public TextField prezimeField;
    public TextField brojindeksaField;
    public TextField jmbgField;
    public DatePicker datumRodjenjaField;
    public ComboBox<String> mjestoRodjenjaField;
    public TextField kontaktAdresaField;
    public TextField kontaktTelefonField;
    public TextField emailAdresaField;

    public SimpleStringProperty imeProperty;
    public SimpleStringProperty prezimeProperty;
    public SimpleStringProperty jmbgProperty;
    public SimpleStringProperty emailAdresaProperty;


    public ValidationSupport validation;
    public ComboBox odsjekField;
    public ComboBox godinaStudijaField;
    public ComboBox ciklusStudijaField;
    public ComboBox redovanSamofinansirajuciField;
    public ComboBox posebnaKategorijaField;
    public Button potvrdiBtn;

    public Controller() {
        imeProperty = new SimpleStringProperty("");
        prezimeProperty = new SimpleStringProperty("");
        jmbgProperty = new SimpleStringProperty("");
        emailAdresaProperty = new SimpleStringProperty("");

        validation = new ValidationSupport();
        //validation.registerValidator(imeField, Validator.createEmptyValidator("Ime ne moze biti prazno!"));

    }

    @FXML
    public void initialize() {
        imeField.textProperty().bindBidirectional(imeProperty);
        prezimeField.textProperty().bindBidirectional(prezimeProperty);
        jmbgField.textProperty().bindBidirectional(jmbgProperty);
        emailAdresaField.textProperty().bindBidirectional(emailAdresaProperty);
        datumRodjenjaField.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datumRodjenjaField.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        dodajListenere();
    }

    private boolean validnost(String n) {
        int i = 0;
        char c;

        if (n == null) return false;

        if (n.length() > 20 || n.length() <= 0) return false;

        for (i = 0; i < n.length(); i++)  //Check for `Firstname`
        {
            c = n.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    private boolean validnost2(String n) {
        int i = 0;
        char c;

        if (n == null) return false;

        if (n.length() > 20 || n.length() <= 0) return false;


        return true;
    }

    private boolean ispravanIndeks(String s) {
        if (s.length() != 5 && s.length() > 0 && s.charAt(0) != 1)
            return false;
        else return true;
    }

    private static boolean cifraCheck(String broj) {
        boolean validno = false;

        char[] charovi = broj.toCharArray();
        for (int i = 0; i < charovi.length; i++) {
            validno = false;
            if (((charovi[i] >= '0') && (charovi[i] <= '9')))
                validno = true;
        }
        return validno;
    }

    private boolean ispravanBroj(String n) {
        int i = 0;

        if (n.length() < 9 || n.length() > 10 || n.charAt(0) != '0') return false;

        for (i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= '0' && n.charAt(i) <= '9')) return false;
        }

        return true;
    }

    private boolean ispravanJMBG(String s) {
        // Nadjedno na netu
        List<Integer> lista = new ArrayList<Integer>();
        if (cifraCheck(s)) {
            for (char ch : s.toCharArray()) {
                lista.add(Integer.valueOf(String.valueOf(ch)));
            }

            if (lista.size() != 13)
                return false;

            else {
                double eval = 0.0;
                for (int i = 0; i < 6; i++) {
                    eval += (7 - i) * (lista.get(i) + lista.get(i + 6));
                }
                return lista.get(12) == 11 - eval % 11;
            }
        } else return false;
    }


    private boolean ispravanDatum(String s) {
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

        imeField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!imeField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.combine(
                            Validator.createEmptyValidator("Ime ne može biti prazno!"),
                            Validator.createPredicateValidator((Predicate<String>) s -> validnost(s),
                                    "Neispravno ime!")
                    );
                    validation.registerValidator(imeField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(imeField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
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
        prezimeField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!prezimeField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.combine(
                            Validator.createEmptyValidator("Prezime ne može biti prazno!"),
                            Validator.createPredicateValidator((Predicate<String>) s -> validnost(s),
                                    "Neispravno prezime!")
                    );
                    validation.registerValidator(prezimeField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(prezimeField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
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

        emailAdresaField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!emailAdresaField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.combine(
                            Validator.createEmptyValidator("Email adresa ne može biti prazna!"),
                            Validator.createPredicateValidator((Predicate<String>) email -> {
                                        EmailValidator emailValidator = EmailValidator.getInstance();
                                        return emailValidator.isValid(email);
                                    },
                                    "Neispravna email adresa!")
                    );
                    validation.registerValidator(emailAdresaField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(emailAdresaField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
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

        kontaktAdresaField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!kontaktAdresaField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.createPredicateValidator((Predicate<String>) s -> validnost2(s),
                            "Neispravna kontakt adresa!");
                    validation.registerValidator(kontaktAdresaField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(kontaktAdresaField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });

        // Listener za kontakt adresu
        kontaktTelefonField.textProperty().addListener((observableValue, o, n) -> {
            if (ispravanBroj(n)) {
                kontaktTelefonField.getStyleClass().removeAll("poljeNijeIspravno");
                kontaktTelefonField.getStyleClass().add("poljeIspravno");
            } else {
                kontaktTelefonField.getStyleClass().removeAll("poljeIspravno");
                kontaktTelefonField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        kontaktTelefonField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!kontaktTelefonField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.createPredicateValidator((Predicate<String>) s -> ispravanBroj(s),
                            "Neispravan kontakt telefon!");

                    validation.registerValidator(kontaktTelefonField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(kontaktTelefonField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
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

        brojindeksaField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!brojindeksaField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.combine(
                            Validator.createEmptyValidator("Broj indeksa ne može biti prazan!"),
                            Validator.createPredicateValidator((Predicate<String>) s -> ispravanIndeks(s),
                                    "Neispravan broj indeksa!")
                    );
                    validation.registerValidator(brojindeksaField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(brojindeksaField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });


        // Listener za mjesto rodjenja
        mjestoRodjenjaField.getEditor().textProperty().addListener((observableValue, o, n) -> {
            if (validnost(n)) {
                mjestoRodjenjaField.getStyleClass().removeAll("poljeNijeIspravno");
                mjestoRodjenjaField.getStyleClass().add("poljeIspravno");
            } else {
                mjestoRodjenjaField.getStyleClass().removeAll("poljeIspravno");
                mjestoRodjenjaField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        mjestoRodjenjaField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!mjestoRodjenjaField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.combine(
                            Validator.createEmptyValidator("Mjesto rođenja ne može biti prazno!"),
                            Validator.createPredicateValidator((Predicate<String>) s -> validnost(mjestoRodjenjaField.getValue()),
                                    "Neispravno mjesto rođenja!")
                    );
                    validation.registerValidator(mjestoRodjenjaField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(mjestoRodjenjaField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });

        // Listener za datum rodjenja
        datumRodjenjaField.getEditor().textProperty().addListener((observableValue, s, t1) -> {
            if (ispravanDatum(t1)) {
                datumRodjenjaField.getStyleClass().removeAll("poljeNijeIspravno");
                datumRodjenjaField.getStyleClass().add("poljeIspravno");
            } else {
                datumRodjenjaField.getStyleClass().removeAll("poljeIspravno");
                datumRodjenjaField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        datumRodjenjaField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!datumRodjenjaField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.combine(
                            Validator.createEmptyValidator("Datum rođenja ne može biti prazan!"),
                            Validator.createPredicateValidator((Predicate<LocalDate>) localDate -> {
                                LocalDate ld = datumRodjenjaField.getValue();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                String strDate = ld.format(formatter);
                                return ispravanDatum(strDate);
                            }, "Neispravan datum!"),
                            Validator.createPredicateValidator((Predicate<LocalDate>) localDate -> {
                                return validirajJmbgDatum(jmbgField.getText(), datumRodjenjaField.getValue());
                            }, "JMBG i datum rođenja ne odgoaraju!")
                    );
                    validation.registerValidator(datumRodjenjaField, true, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(datumRodjenjaField, false, (Control c, LocalDate s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });

        // Listener za JMBG
        jmbgField.textProperty().addListener((observableValue, s, t1) -> {
            if (ispravanJMBG(t1) && validirajJmbgDatum(t1, datumRodjenjaField.getValue())) {
                jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                jmbgField.getStyleClass().add("poljeIspravno");
            } else {
                jmbgField.getStyleClass().removeAll("poljeIspravno");
                jmbgField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        jmbgField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!jmbgField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.combine(
                            Validator.createEmptyValidator("JMBG ne može biti prazan!"),
                            Validator.createPredicateValidator((Predicate<String>) s -> ispravanJMBG(s),
                                    "Neispravan JMBG!"),
                            Validator.createPredicateValidator((Predicate<String>) jmbg -> {
                                        return validirajJmbgDatum(jmbg, datumRodjenjaField.getValue());
                                    },
                                    "JMBG i datum rođenja ne odgovaraju!")
                    );
                    validation.registerValidator(jmbgField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(jmbgField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });


        odsjekField.getEditor().textProperty().addListener((observableValue, o, n) -> {
            if (validnost(n)) {
                odsjekField.getStyleClass().removeAll("poljeNijeIspravno");
                odsjekField.getStyleClass().add("poljeIspravno");
            } else {
                odsjekField.getStyleClass().removeAll("poljeIspravno");
                odsjekField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        odsjekField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!odsjekField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.createEmptyValidator("Odsjek ne može biti prazan!");
                    validation.registerValidator(odsjekField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(mjestoRodjenjaField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });

        ciklusStudijaField.getEditor().textProperty().addListener((observableValue, o, n) -> {
            if (validnost(n)) {
                ciklusStudijaField.getStyleClass().removeAll("poljeNijeIspravno");
                ciklusStudijaField.getStyleClass().add("poljeIspravno");
            } else {
                ciklusStudijaField.getStyleClass().removeAll("poljeIspravno");
                ciklusStudijaField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        ciklusStudijaField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!ciklusStudijaField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.createEmptyValidator("Ciklus studija ne može biti prazan!");
                    validation.registerValidator(ciklusStudijaField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(ciklusStudijaField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });

        godinaStudijaField.getEditor().textProperty().addListener((observableValue, o, n) -> {
            if (validnost(n)) {
                godinaStudijaField.getStyleClass().removeAll("poljeNijeIspravno");
                godinaStudijaField.getStyleClass().add("poljeIspravno");
            } else {
                godinaStudijaField.getStyleClass().removeAll("poljeIspravno");
                godinaStudijaField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        godinaStudijaField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!godinaStudijaField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.createEmptyValidator("Godina studija ne može biti prazna!");
                    validation.registerValidator(godinaStudijaField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(godinaStudijaField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });

        redovanSamofinansirajuciField.getEditor().textProperty().addListener((observableValue, o, n) -> {
            if (validnost(n)) {
                redovanSamofinansirajuciField.getStyleClass().removeAll("poljeNijeIspravno");
                redovanSamofinansirajuciField.getStyleClass().add("poljeIspravno");
            } else {
                redovanSamofinansirajuciField.getStyleClass().removeAll("poljeIspravno");
                redovanSamofinansirajuciField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        redovanSamofinansirajuciField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!mjestoRodjenjaField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.createEmptyValidator("Polje ne može biti prazno!");
                    validation.registerValidator(redovanSamofinansirajuciField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(redovanSamofinansirajuciField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });

        posebnaKategorijaField.getEditor().textProperty().addListener((observableValue, o, n) -> {
            if (validnost(n)) {
                posebnaKategorijaField.getStyleClass().removeAll("poljeNijeIspravno");
                posebnaKategorijaField.getStyleClass().add("poljeIspravno");
            } else {
                posebnaKategorijaField.getStyleClass().removeAll("poljeIspravno");
                posebnaKategorijaField.getStyleClass().add("poljeNijeIspravno");
            }
        });

        posebnaKategorijaField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (!posebnaKategorijaField.isFocused()) {
                    //Kombinacija empty string validatora i
                    // predicate validatora koji poziva metodu validnost
                    Validator validator = Validator.createEmptyValidator("Polje ne može biti prazno!");
                    validation.registerValidator(posebnaKategorijaField, validator);
                } else {
                    // Hack sa controlsFX bitbucketa (u sustini registrira prazan validator ako komponenta nije
                    // fokusirana
                    validation.registerValidator(posebnaKategorijaField, false, (Control c, String s) ->
                            ValidationResult.fromErrorIf(c, "", false));
                }
            }
        });
    }
    public void potvrdiBtnAction(ActionEvent actionEvent) {

        if (!validation.isInvalid() && !imeField.getText().equals("")) {

            System.out.println("Ime i prezime: " + imeField.getText() + ' ' + prezimeField.getText() + '\n' +
                    "Broj indeksa: " + brojindeksaField.getText() + '\n' +
                    "JMBG: " + jmbgField.getText() + '\n' +
                    "Datum rođenja: " + datumRodjenjaField.getValue() + '\n' +
                    "Mjesto rođenja: " + mjestoRodjenjaField.getValue() + '\n' +
                    "Kontakt adresa: " + kontaktAdresaField.getText() + '\n' +
                    "Kontakt telefon: " + kontaktTelefonField.getText() + '\n' +
                    "Email adresa: " + emailAdresaField.getText() + '\n' +
                    "Odsjek: " + odsjekField.getValue() + '\n' +
                    "Ciklus studija: " + ciklusStudijaField.getValue() + '\n' +
                    "Godina studija: " + godinaStudijaField.getValue() + '\n' +
                    "Status studenta: " + redovanSamofinansirajuciField.getValue() + '\n' +
                    "Poseban status studenta: " + posebnaKategorijaField.getValue() + '\n');

            Stage stage = (Stage) potvrdiBtn.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setTitle("Greška");
            alertBox.setContentText("Polja forme nisu ispravna!");
            alertBox.showAndWait();
        }
    }


    private boolean validirajJmbgDatum(String jmbg, LocalDate datumRodjenja) {
        if (ispravanJMBG(jmbg) && datumRodjenja != null) {
            int dan = Integer.parseInt(jmbg.substring(0, 2));
            int mjesec = Integer.parseInt(jmbg.substring(2, 4));
            String tempGodina = jmbg.substring(4, 7);
            int godina = 0;

            if (tempGodina.charAt(0) == '0') {
                godina = Integer.parseInt(tempGodina) + 2000;
            } else {
                godina = Integer.parseInt(tempGodina) + 1000;
            }
            return (dan == datumRodjenja.getDayOfMonth() && mjesec == datumRodjenja.getMonthValue() &&
                    godina == datumRodjenja.getYear());
        }
        return false;

    }
}