package ba.unsa.etf.rpr.tutorijal06;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.TextField;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class MainTest {

    private Label display;
    private TextField ime;
    private TextField prezime;
    private TextField jmbg;
    private TextField emailAdresa;



    @Start
    public void start (Stage stage) throws Exception {
            Parent mainNode = FXMLLoader.load(Main.class.getResource("sample.fxml"));
            stage.setScene(new Scene(mainNode));
            stage.show();
            stage.toFront();
    }

    @Test
    public void ispravanJMBG (FxRobot robot) {
        jmbg = robot.lookup("#jmbgField").queryAs(TextField.class);
        robot.clickOn(jmbg);
        robot.write("1406997175093");
        assertEquals("1406997175093", jmbg.getText());
        assertEquals("text-input text-field poljeIspravno", jmbg.getStyleClass().toString());
    }

    @Test
    public void neispravanMail (FxRobot robot) {
        emailAdresa = robot.lookup("#emailAdresaField").queryAs(TextField.class);
        robot.clickOn(emailAdresa);
        robot.write("asikalo2etf.unsa.ba");
        assertEquals("text-input text-field poljeNijeIspravno", emailAdresa.getStyleClass().toString());
    }

    @Test
    public void ispravanEmail (FxRobot robot) {
        emailAdresa = robot.lookup("#emailAdresaField").queryAs(TextField.class);
        robot.clickOn(emailAdresa);
        robot.write("asikalo2@etf.unsa.ba");
        assertEquals("asikalo2@etf.unsa.ba", emailAdresa.getText());
        assertEquals("text-input text-field poljeIspravno", emailAdresa.getStyleClass().toString());
    }
/*
    @Test
    public void potvrdiBtn (FxRobot robot) {
        robot.write("anonymous");
        robot.clickOn("#prijavaBtn");
        assertEquals("anonymous", prijavaBtn.getText());
    }
*/


}