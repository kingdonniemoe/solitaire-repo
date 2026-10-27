package com.example;

import javafx.application.Application;

import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private final TextField input = new TextField();
    private final RadioButton fahrenheitToCelsius = new RadioButton("Fahrenheit to Celsius");
    private final RadioButton celsiusToFahrenheit = new RadioButton("Celsius to Fahrenheit");
    private final CheckBox round = new CheckBox("Round to integer");
    private final Label result = new Label();

    @Override
    public void start(Stage stage) throws IOException {
        Text title = new Text("Temperature Converter");
        title.setFont(Font.font(20));

        ToggleGroup conversion = new ToggleGroup();
        fahrenheitToCelsius.setToggleGroup(conversion);
        celsiusToFahrenheit.setToggleGroup(conversion);
        fahrenheitToCelsius.setSelected(true);

        input.textProperty().addListener((obs, oldVal, newVal) -> update());
        conversion.selectedToggleProperty().addListener((obs, oldVal, newVal) -> update());
        round.selectedProperty().addListener((obs, oldVal, newVal) -> update());

        VBox root = new VBox(
            10, 
            title, 
            new Line(0, 0, 280, 0), 
            new Label("Temperature:"), 
            input,
            new HBox(15, fahrenheitToCelsius, celsiusToFahrenheit), 
            round, 
            new Line(0, 0, 280, 0), 
            result);

        root.setPadding(new Insets(20));

        update();
        stage.setTitle("Temperature Converter");
        stage.setScene(new Scene(root, 320, 300));
        stage.show();
    }

    private void update() {
        try {
            double value = Double.parseDouble(input.getText());
            double converted;
            String unit;
            if (fahrenheitToCelsius.isSelected()) {
                converted = (value - 32) * 5 / 9;
                unit = "°C";
            } else {
                converted = value * 9 / 5 + 32;
                unit = "°F";
            }
            String formatted = round.isSelected() ? String.valueOf(Math.round(converted))
                    : String.format("%.2f", converted);
            result.setText("Result: " + formatted + " " + unit);
        } catch (NumberFormatException e) {
            result.setText("Enter a valid number");
        }
    }

    public static void main(String[] args) {
        launch();
    }

}