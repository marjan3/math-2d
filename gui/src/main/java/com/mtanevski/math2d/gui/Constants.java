package com.mtanevski.math2d.gui;

import javafx.scene.paint.Color;

public interface Constants {

    String POINT = "Point";
    String VECTOR = "Vector";
    String[] OBJECTS = { POINT, VECTOR };

    interface Formats {
        String RENAME_FORMAT = "Renaming '%s' ...";
        String CANVAS_COORDINATES_FORMAT = "%d, %d";
    }

    interface Offsets {
        int LABEL_OFFSET = 5;
    }

    interface Radius {
        double INVISIBLE = 10.0;
        double POINT = 3.0;
    }

    interface Placeholders {
        String DEFAULT_NAME = "A";
        String DEFAULT_X = "100";
        String DEFAULT_Y = "100";
    }

    interface Ids {
        String OVERLAY = "overlay";
        String PROPERTIES_PANE = "#propertiesPane";
    }

    interface Resources {
        String ABOUT_FXML = "about.fxml";
        String INDEX_FXML = "index.fxml";
        String POINT_2D_PROPERTIES_FXML = "/point2d/properties.fxml";
        String VECTOR_2D_PROPERTIES_FXML = "/vector2d/properties.fxml";
        String SMALL_CIRCLE_PNG = "small-circle.png";
        String ARROW_DOWN_RIGHT_PNG = "arrow-down-right.png";
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
        String RENAME = "Rename";
        String LEFT = "Left";
        String RIGHT = "Right";
        String OK = "OK";
        String INFORMATION_DIALOG = "Information Dialog";
        String NAME = "Name";
        String X = "X";
        String Y = "Y";
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

    enum Origin {
        CENTER,
        DEFAULT,
    }
}
