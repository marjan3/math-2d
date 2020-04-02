module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires math;

    exports com.mtanevski.collisions.point.gui.canvas.coordinatesystem;
    exports com.mtanevski.collisions.point.gui.canvas.point2d;
    exports com.mtanevski.collisions.point.gui.canvas.vector2d;

    opens com.mtanevski.collisions.point.gui;
}