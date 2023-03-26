package com.mtanevski.math2d.gui.utils;

public class FormatUtil {

    public static double parseDouble(String numberAsString) {
        try {
            return Double.parseDouble(numberAsString);
        }catch (NumberFormatException ex) {
            return 0.0;
        }
    }

}
