package com.mtanevski.math2d.gui.utils;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;

import java.util.function.UnaryOperator;

public class TextFormatters {

    public static TextFormatter<Double> newDoubleFormatter() {
        UnaryOperator<TextFormatter.Change> doublesFilter = change -> {
            String newText = change.getControlNewText();
            try {
                Double.parseDouble(newText);
                return change;
            } catch (NumberFormatException exc) {
                return change;
            }
        };

        return new TextFormatter<>(new DoubleStringConverter(), 0.0, doublesFilter);
    }
}
