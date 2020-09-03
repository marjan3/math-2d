package com.mtanevski.collisions.point.gui;

import javafx.scene.paint.Color;

public interface Constants {

    String[] OBJECTS = { "Vector2D", "Point2D" };

    interface Canvas {
        double WIDTH = 800;
        double HEIGHT = 600;
        double PADDING = 50;
    }

    interface Coordinates {
        double POINT_INCREMENT = 50;
        double NUMBER_INCREMENT = 100;
    }

    interface Selectable {
        Color COLOR = Color.BLUE;
    }

    enum OriginType {
        CENTER,
        DEFAULT,
    }
}
