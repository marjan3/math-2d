package com.mtanevski.collisions.point.gui.canvas.vector2d;

import com.mtanevski.collisions.point.gui.Constants;
import com.mtanevski.collisions.point.gui.canvas.Calculator;
import com.mtanevski.collisions.point.gui.canvas.Overlay;
import com.mtanevski.collisions.point.gui.dialogs.InfoAlert;
import com.mtanevski.collisions.point.gui.dialogs.NewDialogResult;
import com.mtanevski.collisions.point.gui.dialogs.Vector2DDialog;
import com.mtanevski.collisions.point.math.Vector2D;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Vector2DGraphic {
    private static double LINE_WIDTH = 1.5;
    private static Color COLOR = Color.BLACK;

    private final Polyline polyline;
    private final List<Node> children;
    public final ObjectProperty<Vector2D> vector2DProperty;
    private final Vector2DContextMenu vector2DContextMenu;
    private GridPane editablePropertiesPane;
    public final Text label;

    public Vector2DGraphic() {
        NewDialogResult dialogResult = Vector2DDialog.showNewVector2DDialog("New Vector2D");
        children = new ArrayList<>();

        polyline = new Polyline();
        polyline.setFill(COLOR);
        polyline.setStroke(COLOR);
        polyline.setStrokeWidth(LINE_WIDTH);

        label = new Text(dialogResult.label);
        vector2DProperty = new SimpleObjectProperty<>(new Vector2D(dialogResult.x, dialogResult.y));
        vector2DProperty.addListener((observableValue, number, t1) -> updateGraphicsPositions());
        updateGraphicsPositions();

        EventHandler<MouseEvent> mouseDragged = t -> {
            Polyline p = ((Polyline) (t.getSource()));
            p.setStroke(Constants.Selectable.COLOR);
            vector2DProperty.set(Vector2D.of(t.getX(), t.getY()));
        };

        EventHandler<MouseEvent> mouseDragOver = t -> {
            Polyline p = ((Polyline) (t.getSource()));
            p.setStroke(COLOR);
        };
        polyline.setOnMouseDragged(mouseDragged);
        polyline.setOnMouseReleased(mouseDragOver);
        initializeEditPropertiesPane();
        this.children.add(polyline);
        this.children.add(label);

        vector2DContextMenu = new Vector2DContextMenu();
        vector2DContextMenu.getDeleteItem().setOnAction(e -> {
            Pane parent = (Pane) label.getParent();
            this.editablePropertiesPane.setVisible(false);
            Overlay.remove(this);
            parent.getChildren().removeAll(this.getChildren());
        });
        vector2DContextMenu.setOnAdd(e -> {
            MenuItem item =  (MenuItem)e.getTarget();
            Vector2DGraphic vector2DGraphic = Overlay.vectors.get(item.getText());
            Vector2D vector2D = this.vector2DProperty.get();
            vector2D.add(vector2DGraphic.vector2DProperty.get());
            String aPlusBLabel = "(" + this.label.getText() + ") + " + item.getText();
            NewDialogResult newDialogResult = Vector2DDialog.showResultingVector2DDialog(aPlusBLabel, aPlusBLabel, vector2D);
            this.label.setText(newDialogResult.label);
            this.vector2DProperty.set(vector2D.clone());
        });
        vector2DContextMenu.setOnSubtract(e -> {
            MenuItem item =  (MenuItem)e.getTarget();
            Vector2DGraphic vector2DGraphic = Overlay.vectors.get(item.getText());
            Vector2D vector2D = this.vector2DProperty.get();
            vector2D.subtract(vector2DGraphic.vector2DProperty.get());
            String aPlusBLabel = "(" + this.label.getText() + ") - " + item.getText();
            NewDialogResult newDialogResult = Vector2DDialog.showResultingVector2DDialog(aPlusBLabel, aPlusBLabel, vector2D);
            this.label.setText(newDialogResult.label);
            this.vector2DProperty.set(vector2D.clone());
        });
        vector2DContextMenu.setOnDotProduct(e -> {
            MenuItem item =  (MenuItem)e.getTarget();
            Vector2DGraphic vector2DGraphic = Overlay.vectors.get(item.getText());
            Vector2D vector2D = this.vector2DProperty.get();
            double dotProduct = Vector2D.getDotProduct(vector2D, vector2DGraphic.vector2DProperty.get());
            String aAndB = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("Dot Product between " + aAndB, "Dot product is: " + dotProduct);
        });
        vector2DContextMenu.setOnPerpendicularVector(e -> {
            MenuItem item =  (MenuItem)e.getTarget();
            Vector2D source = this.vector2DProperty.get();
            Vector2D result;
            String text = this.label.getText();
            String label;
            if(item.getText().toLowerCase().contains("left")){
                result = source.getPerpendicularLeft();
                label = "Perpendicular left of " + text;
            } else {
                result = source.getPerpendicularRight();
                label = "Perpendicular right " + text;
            }
            NewDialogResult newDialogResult = Vector2DDialog.showResultingVector2DDialog(label, label, result);
            this.label.setText(newDialogResult.label);
            this.vector2DProperty.set(result.clone());
        });
        vector2DContextMenu.setOnPerpendicularProduct(e -> {
            MenuItem item =  (MenuItem)e.getTarget();
            Vector2D target = Overlay.vectors.get(item.getText()).vector2DProperty.get();
            Vector2D source = this.vector2DProperty.get();
            double perpendicularProduct = Math.toDegrees(Vector2D.getPerpendicularProduct(source, target));
            String sourceAndTarget = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("The perpendicular product between " + sourceAndTarget, "The perpendicular product is: " + perpendicularProduct);
        });
        vector2DContextMenu.setOnProjectionTime(e -> {
            MenuItem item =  (MenuItem)e.getTarget();
            Vector2D target = Overlay.vectors.get(item.getText()).vector2DProperty.get();
            Vector2D source = this.vector2DProperty.get();
            double projectionTime = Math.toDegrees(Vector2D.getProjectionTime(source, target));
            String sourceAndTarget = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("The projection time between " + sourceAndTarget, "The projection time is: " + projectionTime);
        });
        vector2DContextMenu.setOnAngleBetween(e -> {
            MenuItem item =  (MenuItem)e.getTarget();
            Vector2D target = Overlay.vectors.get(item.getText()).vector2DProperty.get();
            Vector2D source = this.vector2DProperty.get();
            double angle = Math.toDegrees(Vector2D.getAngle(source, target));
            String aAndB = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("Angle (in degrees) between " + aAndB, "The angle (in degrees) is: " + angle);
        });
        label.setOnContextMenuRequested(
                vector2DContextMenu.getEventHandler()
        );
        polyline.setOnContextMenuRequested(
                vector2DContextMenu.getEventHandler()
        );
    }

    private void updateGraphicsPositions() {
        Vector2D vector2D = vector2DProperty.get().clone();
        vector2D.divide(2);
        label.setX(vector2D.x);
        label.setY(vector2D.y);
        polyline.getPoints().clear();
        polyline.getPoints().addAll(Calculator.getArrow(vector2DProperty.get().x, vector2DProperty.get().y));
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public GridPane getEditPropertiesPane() {
        return editablePropertiesPane;
    }

    private void initializeEditPropertiesPane() {
        Vector2DProperties vector2DProperties = new Vector2DProperties(vector2DProperty);
        editablePropertiesPane = vector2DProperties.getPane();
    }


    public void onDrag(Runnable runnable) {
        EventHandler<? super MouseEvent> onMouseDragged = polyline.getOnMouseDragged();
        polyline.setOnMouseDragged(t -> {
            onMouseDragged.handle(t);
            runnable.run();
            t.consume();
        });
    }
}
