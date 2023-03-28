package com.mtanevski.math2d.gui.canvas.point;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.commands.*;
import com.mtanevski.math2d.gui.dialogs.SimpleDialog;
import com.mtanevski.math2d.math.Point2D;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import static com.mtanevski.math2d.gui.Constants.Formats.RENAME_FORMAT;
import static com.mtanevski.math2d.gui.utils.FxUtil.switchNode;

@EqualsAndHashCode
public class DrawablePoint {

    private final Label label;
    private final Circle circle;
    private final Circle invisibleCircle;
    private final ObjectProperty<Point2D> point2DProperty = new SimpleObjectProperty<>();
    private final ContextMenu contextMenu;
    private final List<Node> children = new ArrayList<>();
    private VBox editablePropertiesPane;
    private Point2D previousLocation;

    public DrawablePoint(String name, double x, double y) {
        previousLocation = Point2D.of(x, y);

        circle = createCircle();
        invisibleCircle = createInvisibleCircle();
        label = new Label(name);
        contextMenu = setupContextMenu();

        point2DProperty.addListener((observableValue, number, t1) -> reposition());
        this.point2DProperty.set(new Point2D(x, y));

        editablePropertiesPane = new PointProperties(this, point2DProperty, label.textProperty()).getPane();
        switchNode(Overlay.getScene(), editablePropertiesPane, Constants.Ids.PROPERTIES_PANE);

        setupMouseHandlers();

        children.add(circle);
        children.add(invisibleCircle);
        children.add(label);
    }

    public void move(Point2D point2D) {
        point2DProperty.set(point2D);
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getName() {
        return label.getText();
    }

    public void setName(String nextName) {
        this.label.setText(nextName);
    }

    @Override
    public String toString() {
        return "DrawablePoint{" +
                "hashCode=" + hashCode() +
                ", name=" + label.getText() +
                ", x=" + circle.getCenterX() +
                ", y=" + circle.getCenterY() +
                '}';
    }

    private Circle createInvisibleCircle() {
        var invisibleCircle = new Circle();
        invisibleCircle.setStroke(Constants.Colors.TRANSPARENT);
        invisibleCircle.setFill(Constants.Colors.TRANSPARENT);
        invisibleCircle.setStrokeWidth(Constants.Widths.ZERO);
        invisibleCircle.setRadius(Constants.Radius.INVISIBLE);
        return invisibleCircle;
    }

    private Circle createCircle() {
        var circle = new Circle();
        circle.setFill(Constants.Colors.OBJECT);
        circle.setStroke(Constants.Colors.SELECTABLE);
        circle.setStrokeWidth(Constants.Widths.OBJECT);
        circle.setRadius(Constants.Radius.POINT);
        return circle;
    }

    private ContextMenu setupContextMenu() {
        final ContextMenu contextMenu;
        contextMenu = new ContextMenu();
        MenuItem renameItem = new MenuItem(Constants.Labels.RENAME);
        renameItem.setOnAction(e -> {
            var newName = SimpleDialog.showNameDialog(String.format(RENAME_FORMAT, label.getText())).name;
            CommandsManager.execute(new RenamePointCommand(this, label.getText(), newName));
        });
        MenuItem deleteItem = new MenuItem(Constants.Labels.DELETE);
        deleteItem.setOnAction(e -> {
            this.editablePropertiesPane.setVisible(false);
            CommandsManager.execute(new DeletePointCommand(this));
        });
        contextMenu.getItems().addAll(renameItem, new SeparatorMenuItem(), deleteItem);
        EventHandler<ContextMenuEvent> contextMenuEventEventHandler = e -> {
            contextMenu.show((Node) e.getSource(), Side.RIGHT, 0, 0);
            e.consume();
        };
        circle.setOnContextMenuRequested(contextMenuEventEventHandler);
        invisibleCircle.setOnContextMenuRequested(contextMenuEventEventHandler);
        label.setOnContextMenuRequested(contextMenuEventEventHandler);
        return contextMenu;
    }

    private void setupMouseHandlers() {
        EventHandler<MouseEvent> mouseDragged = t -> {
            circle.setStroke(Constants.Colors.SELECTABLE);
            point2DProperty.set(Point2D.of(t.getX(), t.getY()));
            t.consume();
        };
        EventHandler<MouseEvent> mouseClicked = t -> {
            if (MouseButton.PRIMARY == t.getButton()) {
                Overlay.deselectAll();
                circle.setStroke(Constants.Colors.SELECTABLE);
                CommandsManager.execute(new MovePointCommand(this, previousLocation, point2DProperty.get()));
                Point2D point2D = point2DProperty.get();
                previousLocation = point2D.clone();
                t.consume();
            }
        };
        EventHandler<MouseEvent> mouseDragOver = t -> {
            if (MouseButton.PRIMARY == t.getButton()) {
                circle.setStroke(Constants.Colors.OBJECT);
                t.consume();
            }
        };
        circle.setOnMouseDragged(mouseDragged);
        circle.setOnMouseReleased(mouseDragOver);
        circle.setOnMouseClicked(mouseClicked);
        invisibleCircle.setOnMouseDragged(mouseDragged);
        invisibleCircle.setOnMouseReleased(mouseDragOver);
        invisibleCircle.setOnMouseClicked(mouseClicked);

        var circleOnMouseDragged = circle.getOnMouseDragged();
        circle.setOnMouseDragged(t -> {
            circleOnMouseDragged.handle(t);
            switchNode(Overlay.getScene(), editablePropertiesPane, Constants.Ids.PROPERTIES_PANE);
            t.consume();
        });
        var circleOnMouseClicked = circle.getOnMouseClicked();
        circle.setOnMouseClicked(t -> {
            circleOnMouseClicked.handle(t);
            switchNode(Overlay.getScene(), editablePropertiesPane, Constants.Ids.PROPERTIES_PANE);
            t.consume();
        });

        var invisibleCircleOnMouseDragged = invisibleCircle.getOnMouseDragged();
        invisibleCircle.setOnMouseDragged(t -> {
            invisibleCircleOnMouseDragged.handle(t);
            switchNode(Overlay.getScene(), editablePropertiesPane, Constants.Ids.PROPERTIES_PANE);
            t.consume();
        });
        var invisibleCircleOnMouseClicked = invisibleCircle.getOnMouseClicked();
        invisibleCircle.setOnMouseClicked(t -> {
            invisibleCircleOnMouseClicked.handle(t);
            switchNode(Overlay.getScene(), editablePropertiesPane, Constants.Ids.PROPERTIES_PANE);
            t.consume();
        });
    }

    private void reposition() {
        var point2D = point2DProperty.get();
        circle.setCenterX(point2D.x);
        circle.setCenterY(point2D.y);
        invisibleCircle.setCenterX(point2D.x);
        invisibleCircle.setCenterY(point2D.y);
        label.setTranslateX(point2D.x + Constants.Offsets.LABEL_OFFSET);
        label.setTranslateY(point2D.y + Constants.Offsets.LABEL_OFFSET);
    }
}
