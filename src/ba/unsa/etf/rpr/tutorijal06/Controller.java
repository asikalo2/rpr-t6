package ba.unsa.etf.rpr.tutorijal06;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class Controller {
    public Label display;

    private double operand1 = 0;
    private double operand2 = 0;
    private boolean dotAdded = false;

    public void add1Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("1"));
    }

    public void add2Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("2"));
    }

    public void add3Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("3"));
    }

    public void calculateAction(ActionEvent actionEvent) {
        // Do calculate
    }

    public void add4Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("4"));
    }

    public void add5Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("5"));
    }

    public void add6Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("6"));
    }

    public void add7Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("7"));
    }

    public void add8Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("8"));
    }

    public void add9Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("9"));
    }

    public void add0Action(ActionEvent actionEvent) {
        if (display.getText().equals("0"))
            display.setText("");
        display.setText(display.getText().concat("0"));
    }

    public void addDotAction(ActionEvent actionEvent) {
        if (!dotAdded) {
            display.setText(display.getText().concat("."));
            dotAdded = true;
        }

    }
}
