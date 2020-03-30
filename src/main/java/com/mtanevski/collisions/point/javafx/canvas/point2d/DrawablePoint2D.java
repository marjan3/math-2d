package com.mtanevski.collisions.point.javafx.canvas.point2d;

import com.mtanevski.collisions.point.javafx.Constants;
import com.mtanevski.collisions.point.javafx.dialogs.NewDialogResult;
import com.mtanevski.collisions.point.javafx.dialogs.Vector2DDialog;
import com.mtanevski.collisions.point.lib.Point2D;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class DrawablePoint2D {
    private static double LINE_WIDTH = 1.5;
    private static Color COLOR = Color.BLACK;

    private Circle circle;
    private final ObjectProperty<Point2D> point2DProperty;
    private final Text label;
    private final ContextMenu contextMenu;
    private final MenuItem deleteItem;
    private GridPane editablePropertiesPane;

    public DrawablePoint2D() {
        NewDialogResult result = Vector2DDialog.showNewVector2DDialog("New Point2D");

        circle = new Circle();
        circle.setFill(COLOR);
        circle.setStroke(COLOR);
        circle.setStrokeWidth(LINE_WIDTH);
        circle.setRadius(3);
        label = new Text(result.label);

        point2DProperty = new SimpleObjectProperty<>(new Point2D(result.x, result.y));
        point2DProperty.addListener((observableValue, number, t1) -> this.updateGraphicsPositions());

        this.updateGraphicsPositions();

        EventHandler<MouseEvent> mouseDragged = t -> {
            Circle p = ((Circle) (t.getSource()));
            p.setStroke(Constants.Selectable.COLOR);
            point2DProperty.set(Point2D.of(t.getX(), t.getY()));
        };

        EventHandler<MouseEvent> mouseDragOver = t -> {
            Circle p = ((Circle) (t.getSource()));
            p.setStroke(COLOR);
        };
        circle.setOnMouseDragged(mouseDragged);
        circle.setOnMouseReleased(mouseDragOver);
        initializeEditPropertiesPane();

        contextMenu = new ContextMenu();
        deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            Pane parent = (Pane)circle.getParent();
            parent.getChildren().removeAll(this.getChildren());
        });
        contextMenu.getItems().add(deleteItem);
        EventHandler<ContextMenuEvent> contextMenuEventEventHandler = e -> {
            contextMenu.show((Node) e.getSource(), Side.RIGHT, 0, 0);
            e.consume();
        };
        circle.setOnContextMenuRequested(
                contextMenuEventEventHandler
        );
        label.setOnContextMenuRequested(
                contextMenuEventEventHandler
        );
    }


    private void updateGraphicsPositions() {
        Point2D point2D = point2DProperty.get();
        circle.setCenterX(point2D.x);
        circle.setCenterY(point2D.y);
        label.setX(point2D.x + circle.getRadius());
        label.setY(point2D.y + circle.getRadius()*3);
    }

    public List<Node> getChildren() {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(circle);
        nodes.add(label);
        return nodes;
    }

    public GridPane getEditPropertiesPane() {
        return editablePropertiesPane;
    }

    private void initializeEditPropertiesPane() {
        DrawablePoint2DProperties drawablePoint2DProperties = new DrawablePoint2DProperties(point2DProperty);
        editablePropertiesPane = drawablePoint2DProperties.getPane();
    }

    public void onDrag(Runnable runnable) {
        EventHandler<? super MouseEvent> onMouseDragged = circle.getOnMouseDragged();
        circle.setOnMouseDragged(t -> {
            onMouseDragged.handle(t);
            runnable.run();
        });

    }
}
