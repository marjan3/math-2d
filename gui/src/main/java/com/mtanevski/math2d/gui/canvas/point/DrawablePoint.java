package com.mtanevski.math2d.gui.canvas.point;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.commands.CommandsManager;
import com.mtanevski.math2d.gui.commands.DeletePointCommand;
import com.mtanevski.math2d.gui.commands.MovePointCommand;
import com.mtanevski.math2d.math.Point2D;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class DrawablePoint {
    private final Circle circle;
    private final Circle invisibleCircle;
    private final ObjectProperty<Point2D> point2DProperty;
    public final Label label;
    private final ContextMenu contextMenu;
    private VBox editablePropertiesPane;
    private Point2D previousLocation;

    public DrawablePoint(String name, double x, double y) {
        circle = new Circle();
        previousLocation = Point2D.of(x, y);

        circle.setFill(Constants.Colors.OBJECT);
        circle.setStroke(Constants.Colors.OBJECT);
        circle.setStrokeWidth(Constants.Widths.OBJECT);
        circle.setRadius(Constants.Radius.POINT);
        invisibleCircle = new Circle();
        invisibleCircle.setStroke(Constants.Colors.TRANSPARENT);
        invisibleCircle.setFill(Constants.Colors.TRANSPARENT);
        invisibleCircle.setStrokeWidth(Constants.Widths.ZERO);
        invisibleCircle.setRadius(Constants.Radius.INVISIBLE);
        label = new Label(name);

        point2DProperty = new SimpleObjectProperty<>(new Point2D(x, y));
        point2DProperty.addListener((observableValue, number, t1) -> this.move(this.point2DProperty.get()));

        this.move(this.point2DProperty.get());

        EventHandler<MouseEvent> mouseDragged = t -> {
            circle.setStroke(Constants.Colors.SELECTABLE);
            point2DProperty.set(Point2D.of(t.getX(), t.getY()));
            t.consume();
        };

        EventHandler<MouseEvent> mouseClicked = t -> {
            Overlay.deselectAll();
            circle.setStroke(Constants.Colors.SELECTABLE);
            CommandsManager.execute(new MovePointCommand(this, previousLocation, point2DProperty.get()));
            Point2D point2D = point2DProperty.get();
            previousLocation = point2D.clone();
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
        MenuItem deleteItem = new MenuItem(Constants.Labels.DELETE);
        deleteItem.setOnAction(e -> {
            this.editablePropertiesPane.setVisible(false);
            CommandsManager.execute(new DeletePointCommand(this));
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

    public void move(Point2D point2D) {
        point2DProperty.set(point2D);
        circle.setCenterX(point2D.x);
        circle.setCenterY(point2D.y);
        invisibleCircle.setCenterX(point2D.x);
        invisibleCircle.setCenterY(point2D.y);
        label.setTranslateX(point2D.x + Constants.Offsets.LABEL_OFFSET);
        label.setTranslateY(point2D.y + Constants.Offsets.LABEL_OFFSET);
    }

    public List<Node> getChildren() {
        var nodes = new ArrayList<Node>();
        nodes.add(circle);
        nodes.add(invisibleCircle);
        nodes.add(label);
        return nodes;
    }

    public VBox getEditPropertiesPane() {
        return editablePropertiesPane;
    }

    private void initializeEditPropertiesPane() {
        var drawablePointProperties = new PointProperties(point2DProperty, label.textProperty());
        editablePropertiesPane = drawablePointProperties.getPane();
    }

    public void onDrag(Runnable switchPropertiesPane) {
        var onMouseDragged1 = circle.getOnMouseDragged();
        circle.setOnMouseDragged(t -> {
            onMouseDragged1.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
        var onMouseClicked1 = circle.getOnMouseClicked();
        circle.setOnMouseClicked(t -> {
            onMouseClicked1.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });

        var onMouseDragged2 = invisibleCircle.getOnMouseDragged();
        invisibleCircle.setOnMouseDragged(t -> {
            onMouseDragged2.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
        var onMouseClicked = invisibleCircle.getOnMouseClicked();
        invisibleCircle.setOnMouseClicked(t -> {
            onMouseClicked.handle(t);
            switchPropertiesPane.run();
            t.consume();
        });
    }
}
