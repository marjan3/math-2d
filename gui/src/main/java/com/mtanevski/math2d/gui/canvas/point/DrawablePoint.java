package com.mtanevski.math2d.gui.canvas.point;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.math.Point2D;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class DrawablePoint {
    private Circle circle;
    private Circle invisibleCircle;
    private final ObjectProperty<Point2D> point2DProperty;
    public final Text label;
    private final ContextMenu contextMenu;
    private final MenuItem deleteItem;
    private GridPane editablePropertiesPane;

    public DrawablePoint(String name, double x, double y) {
        circle = new Circle();
        circle.setFill(Constants.Colors.OBJECT);
        circle.setStroke(Constants.Colors.OBJECT);
        circle.setStrokeWidth(Constants.Widths.OBJECT);
        circle.setRadius(Constants.Radius.POINT);
        invisibleCircle = new Circle();
        invisibleCircle.setStroke(Constants.Colors.TRANSPARENT);
        invisibleCircle.setFill(Constants.Colors.TRANSPARENT);
        invisibleCircle.setStrokeWidth(Constants.Widths.ZERO);
        invisibleCircle.setRadius(Constants.Radius.INVISIBLE);
        label = new Text(name);

        point2DProperty = new SimpleObjectProperty<>(new Point2D(x, y));
        point2DProperty.addListener((observableValue, number, t1) -> this.updateGraphicsPositions());

        this.updateGraphicsPositions();

        EventHandler<MouseEvent> mouseDragged = t -> {
            circle.setStroke(Constants.Colors.SELECTABLE);
            point2DProperty.set(Point2D.of(t.getX(), t.getY()));
            t.consume();
        };

        EventHandler<MouseEvent> mouseClicked = t -> {
            circle.setStroke(Constants.Colors.SELECTABLE);
            t.consume();
        };

        EventHandler<MouseEvent> mouseDragOver = t -> {
            circle.setStroke(Constants.Colors.OBJECT);
            t.consume();
        };
        circle.setOnMouseDragged(mouseDragged);
        circle.setOnMouseReleased(mouseDragOver);
        circle.setOnMouseClicked(mouseClicked);
        invisibleCircle.setOnMouseDragged(mouseDragged);
        invisibleCircle.setOnMouseReleased(mouseDragOver);
        invisibleCircle.setOnMouseClicked(mouseClicked);
        initializeEditPropertiesPane();

        contextMenu = new ContextMenu();
        deleteItem = new MenuItem(Constants.Labels.DELETE);
        deleteItem.setOnAction(e -> {
            var parent = (Pane)circle.getParent();
            parent.getChildren().removeAll(this.getChildren());
        });
        contextMenu.getItems().add(deleteItem);
        EventHandler<ContextMenuEvent> contextMenuEventEventHandler = e -> {
            contextMenu.show((Node) e.getSource(), Side.RIGHT, 0, 0);
            e.consume();
        };
        circle.setOnContextMenuRequested(contextMenuEventEventHandler);
        invisibleCircle.setOnContextMenuRequested(contextMenuEventEventHandler);
        label.setOnContextMenuRequested(contextMenuEventEventHandler);
        circle.setStroke(Constants.Colors.SELECTABLE);
    }

    private void updateGraphicsPositions() {
        var point2D = point2DProperty.get();
        circle.setCenterX(point2D.x);
        circle.setCenterY(point2D.y);
        invisibleCircle.setCenterX(point2D.x);
        invisibleCircle.setCenterY(point2D.y);
        label.setX(point2D.x + circle.getRadius());
        label.setY(point2D.y + circle.getRadius() * 3);
    }

    public List<Node> getChildren() {
        var nodes = new ArrayList<Node>();
        nodes.add(circle);
        nodes.add(invisibleCircle);
        nodes.add(label);
        return nodes;
    }

    public GridPane getEditPropertiesPane() {
        return editablePropertiesPane;
    }

    private void initializeEditPropertiesPane() {
        var drawablePointProperties = new PointProperties(point2DProperty);
        editablePropertiesPane = drawablePointProperties.getPane();
    }

    public void onDrag(Runnable runnable) {
        var onMouseDragged1 = circle.getOnMouseDragged();
        circle.setOnMouseDragged(t -> {
            onMouseDragged1.handle(t);
            runnable.run();
            t.consume();
        });

        var onMouseDragged2 = invisibleCircle.getOnMouseDragged();
        circle.setOnMouseDragged(t -> {
            onMouseDragged2.handle(t);
            runnable.run();
            t.consume();
        });
    }
}
