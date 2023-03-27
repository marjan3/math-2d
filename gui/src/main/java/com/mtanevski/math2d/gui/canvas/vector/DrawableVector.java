package com.mtanevski.math2d.gui.canvas.vector;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.utils.Calculator;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.commands.*;
import com.mtanevski.math2d.gui.dialogs.InfoAlert;
import com.mtanevski.math2d.gui.dialogs.NewObjectDialog;
import com.mtanevski.math2d.gui.dialogs.NewObjectDialogResult;
import com.mtanevski.math2d.math.Vector2D;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

public class DrawableVector {

    private final Polyline polyline;
    private final Circle invisibleCircle;
    private final List<Node> children;
    private Vector2D previousLocation;
    private VBox editablePropertiesPane;

    public final ObjectProperty<Vector2D> vector2DProperty;
    public final Label label;

    public DrawableVector(String name, double x, double y) {
        children = new ArrayList<>();
        previousLocation = Vector2D.of(x, y);

        polyline = new Polyline();
        polyline.setFill(Constants.Colors.OBJECT);
        polyline.setStroke(Constants.Colors.OBJECT);
        polyline.setStrokeWidth(Constants.Widths.OBJECT);

        invisibleCircle = new Circle();
        invisibleCircle.setStroke(Constants.Colors.TRANSPARENT);
        invisibleCircle.setFill(Constants.Colors.TRANSPARENT);
        invisibleCircle.setStrokeWidth(Constants.Widths.ZERO);
        invisibleCircle.setRadius(10.0);

        label = new Label(name);
        vector2DProperty = new SimpleObjectProperty<>(new Vector2D(x, y));
        vector2DProperty.addListener((observableValue, number, t1) -> move(vector2DProperty.get().clone()));
        move(vector2DProperty.get().clone());

        EventHandler<MouseEvent> mouseClicked = t -> {
            Overlay.deselectAll();
            polyline.setStroke(Constants.Colors.SELECTABLE);
            CommandsManager.execute(new MoveVectorCommand(this, previousLocation, vector2DProperty.get()));
            Vector2D vector2D = vector2DProperty.get();
            previousLocation = vector2D.clone();
            t.consume();
        };

        EventHandler<MouseEvent> mouseDragged = t -> {
            Overlay.deselectAll();
            polyline.setStroke(Constants.Colors.SELECTABLE);
            vector2DProperty.set(Vector2D.of(t.getX(), t.getY()));
        };

        EventHandler<MouseEvent> mouseDragOver = t -> {
            polyline.setStroke(Constants.Colors.OBJECT);
        };
        polyline.setOnMouseDragged(mouseDragged);
        polyline.setOnMouseReleased(mouseDragOver);
        polyline.setOnMouseClicked(mouseClicked);
        invisibleCircle.setOnMouseDragged(mouseDragged);
        invisibleCircle.setOnMouseReleased(mouseDragOver);
        invisibleCircle.setOnMouseClicked(mouseClicked);
        initializeEditPropertiesPane();
        this.children.add(polyline);
        this.children.add(label);
        this.children.add(invisibleCircle);

        VectorContextMenu vectorContextMenu = new VectorContextMenu();
        vectorContextMenu.getDeleteItem().setOnAction(e -> {
            this.editablePropertiesPane.setVisible(false);
            CommandsManager.execute(new DeleteVectorCommand(this));
        });
        vectorContextMenu.setOnAdd(e -> {
            var item = (MenuItem) e.getTarget();
            var vectorGraphic = Overlay.vectors.get(item.getText());
            var vector2D = this.vector2DProperty.get();
            vector2D.add(vectorGraphic.vector2DProperty.get());
            var aPlusBLabel = "(" + this.label.getText() + ") + " + item.getText();
            var newDialogResult = NewObjectDialog.showXYDialog(aPlusBLabel, aPlusBLabel, vector2D.x , vector2D.y);
            CommandsManager.execute(new CreateVectorCommand(CreateRequest.fromDialogResult(newDialogResult)));
        });
        vectorContextMenu.setOnSubtract(e -> {
            var item = (MenuItem) e.getTarget();
            var vectorGraphic = Overlay.vectors.get(item.getText());
            var vector2D = this.vector2DProperty.get();
            vector2D.subtract(vectorGraphic.vector2DProperty.get());
            var aPlusBLabel = "(" + this.label.getText() + ") - " + item.getText();
            var newDialogResult = NewObjectDialog.showXYDialog(aPlusBLabel, aPlusBLabel, vector2D.x, vector2D.y);
            CommandsManager.execute(new CreateVectorCommand(CreateRequest.fromDialogResult(newDialogResult)));
        });
        vectorContextMenu.setOnPerpendicularVector(e -> {
            var item = (MenuItem) e.getTarget();
            var source = this.vector2DProperty.get();
            Vector2D result;
            String text = this.label.getText();
            String label;
            if (item.getText().toLowerCase().contains("left")) {
                result = source.getPerpendicularLeft();
                label = "Perpendicular left of " + text;
            } else {
                result = source.getPerpendicularRight();
                label = "Perpendicular right " + text;
            }
            var newDialogResult = NewObjectDialog.showXYDialog(label, label, result.x, result.y);
            CommandsManager.execute(new CreateVectorCommand(CreateRequest.fromDialogResult(newDialogResult)));
        });
        vectorContextMenu.setOnDotProduct(e -> {
            var item = (MenuItem) e.getTarget();
            var drawableVector = Overlay.vectors.get(item.getText());
            var vector2D = this.vector2DProperty.get();
            double dotProduct = Vector2D.getDotProduct(vector2D, drawableVector.vector2DProperty.get());
            String aAndB = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("Dot Product between " + aAndB, "Dot product is: " + dotProduct);
        });
        vectorContextMenu.setOnPerpendicularProduct(e -> {
            var item = (MenuItem) e.getTarget();
            var target = Overlay.vectors.get(item.getText()).vector2DProperty.get();
            var source = this.vector2DProperty.get();
            double perpendicularProduct = Math.toDegrees(Vector2D.getPerpendicularProduct(source, target));
            String sourceAndTarget = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("The perpendicular product between " + sourceAndTarget, "The perpendicular product is: " + perpendicularProduct);
        });
        vectorContextMenu.setOnProjectionTime(e -> {
            var item = (MenuItem) e.getTarget();
            var target = Overlay.vectors.get(item.getText()).vector2DProperty.get();
            var source = this.vector2DProperty.get();
            double projectionTime = Math.toDegrees(Vector2D.getProjectionTime(source, target));
            String sourceAndTarget = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("The projection time between " + sourceAndTarget, "The projection time is: " + projectionTime);
        });
        vectorContextMenu.setOnAngleBetween(e -> {
            var item = (MenuItem) e.getTarget();
            var target = Overlay.vectors.get(item.getText()).vector2DProperty.get();
            var source = this.vector2DProperty.get();
            double angle = Math.toDegrees(Vector2D.getAngle(source, target));
            String aAndB = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("Angle (in degrees) between " + aAndB, "The angle (in degrees) is: " + angle);
        });
        label.setOnContextMenuRequested(
                vectorContextMenu.getEventHandler()
        );
        polyline.setOnContextMenuRequested(
                vectorContextMenu.getEventHandler()
        );
        invisibleCircle.setOnContextMenuRequested(
                vectorContextMenu.getEventHandler()
        );
        polyline.setStroke(Constants.Colors.SELECTABLE);
    }

    public void move(Vector2D vector2D) {
        if (vector2D.x != vector2DProperty.get().x && vector2D.y != vector2DProperty.get().y) {
            vector2DProperty.set(Vector2D.of(vector2D.x, vector2D.y));
        }
        var divided = vector2DProperty.get().clone().divide(2.0);
        var labelVector = divided.add(vector2D.getPerpendicularRight().normalize().multiply(20.0));
        label.setTranslateX(labelVector.x - label.getWidth() / 2);
        label.setTranslateY(labelVector.y - label.getHeight() / 2);
        invisibleCircle.setCenterX(vector2D.x);
        invisibleCircle.setCenterY(vector2D.y);
        polyline.getPoints().clear();
        polyline.getPoints().addAll(Calculator.getArrow(vector2D.x, vector2D.y));
    }

    private void updateGraphicsPositions() {
        var clone = vector2DProperty.get().clone();
        var divided = vector2DProperty.get().clone().divide(2.0);
        var labelVector = divided.add(clone.getPerpendicularRight().normalize().multiply(20.0));
        label.setTranslateX(labelVector.x - label.getWidth() / 2);
        label.setTranslateY(labelVector.y - label.getHeight() / 2);
        invisibleCircle.setCenterX(clone.x);
        invisibleCircle.setCenterY(clone.y);
        polyline.getPoints().clear();
        polyline.getPoints().addAll(Calculator.getArrow(clone.x, clone.y));
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public VBox getEditPropertiesPane() {
        return this.editablePropertiesPane;
    }

    private void initializeEditPropertiesPane() {
        var vector2DProperties = new VectorProperties(vector2DProperty, label.textProperty());
        editablePropertiesPane = vector2DProperties.getPane();
    }

    public void onDrag(Runnable switchPropertiesPane) {
        var onMouseDragged1 = polyline.getOnMouseDragged();
        var onMouseClicked1 = polyline.getOnMouseClicked();
        polyline.setOnMouseDragged(t -> {
            onMouseDragged1.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
        polyline.setOnMouseClicked(t -> {
            onMouseClicked1.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
        var onMouseDragged2 = invisibleCircle.getOnMouseDragged();
        var onMouseClicked2 = invisibleCircle.getOnMouseClicked();
        invisibleCircle.setOnMouseDragged(t -> {
            onMouseDragged2.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
        invisibleCircle.setOnMouseClicked(t -> {
            onMouseClicked2.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
    }
}
