package com.mtanevski.math2d.gui;

import javafx.scene.paint.Color;

public interface Constants {

    String POINT = "Point";
    String VECTOR = "Vector";
    String[] OBJECTS = { "Vector", "Point" };

    interface Radius {
        double INVISIBLE = 10.0;
        double POINT = 3.0;
    }

    interface Resources {
        String ABOUT_FXML = "about.fxml";
        String INDEX_FXML = "index.fxml";
        String COORDINATESYSTEM_PROPERTIES_FXML = "/coordinatesystem/properties.fxml";
        String POINT_2D_PROPERTIES_FXML = "/point2d/properties.fxml";
        String VECTOR_2D_PROPERTIES_FXML = "/vector2d/properties.fxml";
    }

    interface Labels {
        String NEW_POINT_LABEL = "New Point";
        String NEW_VECTOR_LABEL = "New Vector";
        String TITLE = "Math 2D";
        String ADD = "Add...";
        String SUBTRACT = "Subtract...";
        String DOT_PRODUCT = "Dot Product...";
        String PERPENDICULAR_VECTOR = "Perpendicular Vector..";
        String PERPENDICULAR_PRODUCT = "Perpendicular Product...";
        String PROJECTION_TIME = "Projection Time...";
        String ANGLE_BETWEEN = "Angle Between...";
        String DELETE = "Delete";
        String LEFT = "Left";
        String RIGHT = "Right";
        String OK = "OK";
    }

    interface Canvas {
        double WIDTH = 800;
        double HEIGHT = 600;
        double PADDING = 50;
    }

    interface Coordinates {
        double POINT_INCREMENT = 50;
        double NUMBER_INCREMENT = 100;
    }

    interface Widths {
        double ZERO = 0;
        double OBJECT = 1.5;
        double COORDINATE = 0.2;
    }

    interface Colors {
        Color SELECTABLE = Color.BLUE;
        Color OBJECT = Color.BLACK;
        Color TRANSPARENT = new Color(0.0D, 0.0D, 1.0D, 0.0D);
    }

    enum OriginType {
        CENTER,
        DEFAULT,
    }
}
