module gui {
    requires lombok;
    requires javafx.controls;
    requires javafx.fxml;
    requires math;

    exports com.mtanevski.math2d.gui.canvas.coordinatesystem;
    exports com.mtanevski.math2d.gui.canvas.point;
    exports com.mtanevski.math2d.gui.canvas.vector;

    opens com.mtanevski.math2d.gui;
}