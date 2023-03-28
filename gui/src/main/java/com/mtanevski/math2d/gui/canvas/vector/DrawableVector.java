package com.mtanevski.math2d.gui.canvas.vector;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.utils.Calculator;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.commands.*;
import com.mtanevski.math2d.gui.dialogs.InfoAlert;
import com.mtanevski.math2d.gui.dialogs.SimpleDialog;
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

import static com.mtanevski.math2d.gui.Constants.Formats.RENAME_FORMAT;
import static com.mtanevski.math2d.gui.utils.FxUtil.switchNode;

public class DrawableVector {

    private final Polyline polyline;
    private final Circle invisibleCircle;
    private final List<Node> children;
    private final Label label;
    private final VBox editablePropertiesPane;

    private Vector2D previousLocation;

    public final ObjectProperty<Vector2D> vector2DProperty;

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
        vector2DProperty = new SimpleObjectProperty<>();
        vector2DProperty.addListener((observableValue, number, t1) -> {
            repositionLabel();
            repositionInvisibleCircle();
            repositionPoliline();
        });
        move(new Vector2D(x, y));

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

        EventHandler<MouseEvent> mouseDragOver = t -> polyline.setStroke(Constants.Colors.OBJECT);
        polyline.setOnMouseDragged(mouseDragged);
        polyline.setOnMouseReleased(mouseDragOver);
        polyline.setOnMouseClicked(mouseClicked);
        invisibleCircle.setOnMouseDragged(mouseDragged);
        invisibleCircle.setOnMouseReleased(mouseDragOver);
        invisibleCircle.setOnMouseClicked(mouseClicked);
        var vector2DProperties = new VectorProperties(vector2DProperty, label.textProperty());
        editablePropertiesPane = vector2DProperties.getPane();

        var contextMenu = new VectorContextMenu();
        contextMenu.setOnRename(e -> {
            var newName = SimpleDialog.showNameDialog(String.format(RENAME_FORMAT, label.getText())).name;
            label.setText(newName);
        });
        contextMenu.setOnDeleteItem(e -> {
            this.editablePropertiesPane.setVisible(false);
            CommandsManager.execute(new DeleteVectorCommand(this));
        });
        contextMenu.setOnAdd(e -> {
            var item = (MenuItem) e.getTarget();
            var target = ((DrawableVector)item.getUserData()).vector2DProperty.get();
            var source = this.vector2DProperty.get();
            source.add(target);
            var aPlusBLabel = "(" + this.label.getText() + ") + " + item.getText();
            var newDialogResult = SimpleDialog.showXYDialog(aPlusBLabel, aPlusBLabel, source.x , source.y);
            CommandsManager.execute(new CreateVectorCommand(CreateRequest.fromDialogResult(newDialogResult)));
        });
        contextMenu.setHandleSubtract(e -> {
            var item = (MenuItem) e.getTarget();
            var target = ((DrawableVector)item.getUserData()).vector2DProperty.get();
            var source = this.vector2DProperty.get();
            source.subtract(target);
            var aPlusBLabel = "(" + this.label.getText() + ") - " + item.getText();
            var newDialogResult = SimpleDialog.showXYDialog(aPlusBLabel, aPlusBLabel, source.x, source.y);
            CommandsManager.execute(new CreateVectorCommand(CreateRequest.fromDialogResult(newDialogResult)));
        });
        contextMenu.setOnPerpendicularVector(e -> {
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
            var newDialogResult = SimpleDialog.showXYDialog(label, label, result.x, result.y);
            CommandsManager.execute(new CreateVectorCommand(CreateRequest.fromDialogResult(newDialogResult)));
        });
        contextMenu.setOnDotProduct(e -> {
            var item = (MenuItem) e.getTarget();
            var target = ((DrawableVector)item.getUserData()).vector2DProperty.get();
            var source = this.vector2DProperty.get();
            double dotProduct = Vector2D.getDotProduct(source, target);
            String aAndB = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("Dot Product between " + aAndB, "Dot product is: " + dotProduct);
        });
        contextMenu.setOnPerpendicularProduct(e -> {
            var item = (MenuItem) e.getTarget();
            var target = ((DrawableVector)item.getUserData()).vector2DProperty.get();
            var source = this.vector2DProperty.get();
            double perpendicularProduct = Math.toDegrees(Vector2D.getPerpendicularProduct(source, target));
            String sourceAndTarget = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("The perpendicular product between " + sourceAndTarget, "The perpendicular product is: " + perpendicularProduct);
        });
        contextMenu.setOnProjectionTime(e -> {
            var item = (MenuItem) e.getTarget();
            var target = ((DrawableVector)item.getUserData()).vector2DProperty.get();
            var source = this.vector2DProperty.get();
            double projectionTime = Math.toDegrees(Vector2D.getProjectionTime(source, target));
            String sourceAndTarget = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("The projection time between " + sourceAndTarget, "The projection time is: " + projectionTime);
        });
        contextMenu.setOnAngleBetween(e -> {
            var item = (MenuItem) e.getTarget();
            var target = ((DrawableVector)item.getUserData()).vector2DProperty.get();
            var source = this.vector2DProperty.get();
            double angle = Math.toDegrees(Vector2D.getAngle(source, target));
            String aAndB = this.label.getText() + " and " + item.getText();
            InfoAlert.alert("Angle (in degrees) between " + aAndB, "The angle (in degrees) is: " + angle);
        });
        label.setOnContextMenuRequested(contextMenu.getEventHandler());
        polyline.setOnContextMenuRequested(contextMenu.getEventHandler());
        invisibleCircle.setOnContextMenuRequested(contextMenu.getEventHandler());
        polyline.setStroke(Constants.Colors.SELECTABLE);

        onDrag(() -> switchNode(Overlay.getScene(), this.editablePropertiesPane, Constants.Ids.PROPERTIES_PANE));
        switchNode(Overlay.getScene(), this.editablePropertiesPane, Constants.Ids.PROPERTIES_PANE);

        this.children.add(polyline);
        this.children.add(label);
        this.children.add(invisibleCircle);
    }

    public void move(Vector2D vector2D) {
        vector2DProperty.set(Vector2D.of(vector2D.x, vector2D.y));
    }

    public String getName() {
        return this.label.getText();
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public void onDrag(Runnable switchPropertiesPane) {
        var polylineMouseDragged = polyline.getOnMouseDragged();
        polyline.setOnMouseDragged(t -> {
            polylineMouseDragged.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
        var polylineMouseClicked = polyline.getOnMouseClicked();
        polyline.setOnMouseClicked(t -> {
            polylineMouseClicked.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
        var circleMouseDragged = invisibleCircle.getOnMouseDragged();
        invisibleCircle.setOnMouseDragged(t -> {
            circleMouseDragged.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
        var circleMouseClicked = invisibleCircle.getOnMouseClicked();
        invisibleCircle.setOnMouseClicked(t -> {
            circleMouseClicked.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
    }

    @Override
    public String toString() {
        return "DrawableVector{" +  "name=" + label.getText()  +
                ", x=" + invisibleCircle.getCenterX() +
                ", y=" + invisibleCircle.getCenterY() +
                '}';
    }

    private void repositionPoliline() {
        var vector2D = vector2DProperty.get();
        polyline.getPoints().clear();
        polyline.getPoints().addAll(Calculator.getArrow(vector2D.x, vector2D.y));
    }

    private void repositionInvisibleCircle() {
        var vector2D = vector2DProperty.get();
        invisibleCircle.setCenterX(vector2D.x);
        invisibleCircle.setCenterY(vector2D.y);
    }

    private void repositionLabel() {
        var vector2D = vector2DProperty.get();
        var divided = vector2DProperty.get().clone().divide(2.0);
        var labelVector = divided.add(vector2D.getPerpendicularRight().normalize().multiply(20.0));
        label.setTranslateX(labelVector.x - label.getWidth() / 2);
        label.setTranslateY(labelVector.y - label.getHeight() / 2);
    }
}
