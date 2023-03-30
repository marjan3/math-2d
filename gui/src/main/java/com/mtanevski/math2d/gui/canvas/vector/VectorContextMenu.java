package com.mtanevski.math2d.gui.canvas.vector;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.gui.canvas.Overlay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.ContextMenuEvent;

public class VectorContextMenu extends ContextMenu {

    private final Menu addMenu;
    private final Menu subtractMenu;
    private final Menu dotProductMenu;
    private final Menu perpendicularVectorMenu;
    private final Menu perpendicularProductMenu;
    private final Menu projectionTimeMenu;
    private final Menu angleBetweenMenu;
    private final Menu reflectMenu;
    private final MenuItem deleteItem;
    private final MenuItem renameItem;

    private EventHandler<ActionEvent> handleAdd;
    private EventHandler<ActionEvent> handleSubtract;
    private EventHandler<ActionEvent> handleDotProduct;
    private EventHandler<ActionEvent> handlePerpendicularVector;
    private EventHandler<ActionEvent> handlePerpendicularProduct;
    private EventHandler<ActionEvent> handleProjectionTime;
    private EventHandler<ActionEvent> handleAngleBetween;
    private EventHandler<ActionEvent> handleReflect;
    private EventHandler<ActionEvent> handleDelete;
    private EventHandler<ActionEvent> handleRename;

    public VectorContextMenu() {
        super();
        addMenu = new Menu(Constants.Labels.ADD);
        subtractMenu = new Menu(Constants.Labels.SUBTRACT);
        dotProductMenu = new Menu(Constants.Labels.DOT_PRODUCT);
        perpendicularVectorMenu = new Menu(Constants.Labels.PERPENDICULAR_VECTOR);
        perpendicularProductMenu = new Menu(Constants.Labels.PERPENDICULAR_PRODUCT);
        projectionTimeMenu = new Menu(Constants.Labels.PROJECTION_TIME);
        angleBetweenMenu = new Menu(Constants.Labels.ANGLE_BETWEEN);
        reflectMenu = new Menu(Constants.Labels.REFLECT);
        deleteItem = new MenuItem(Constants.Labels.DELETE);
        renameItem = new MenuItem(Constants.Labels.RENAME);
        this.getItems().addAll(
                addMenu,
                subtractMenu,
                dotProductMenu,
                perpendicularVectorMenu,
                perpendicularProductMenu,
                projectionTimeMenu,
                angleBetweenMenu,
                reflectMenu);

        this.getItems().addAll(new SeparatorMenuItem(), renameItem, new SeparatorMenuItem(), deleteItem);
    }

    public EventHandler<ContextMenuEvent> getEventHandler() {
        return e -> {
            this.getItems().forEach(m -> {
                if (m instanceof Menu menu) {
                    menu.getItems().clear();
                    Overlay.getVectors().forEach(drawableVector -> {
                        MenuItem menuItem = new MenuItem(drawableVector.getName());
                        menuItem.setUserData(drawableVector);
                        menu.getItems().add(menuItem);
                    });
                }
            });
            perpendicularVectorMenu.getItems().clear();
            perpendicularVectorMenu.getItems().add(new MenuItem(Constants.Labels.LEFT));
            perpendicularVectorMenu.getItems().add(new MenuItem(Constants.Labels.RIGHT));

            addMenu.getItems().forEach(item -> item.setOnAction(this.handleAdd));
            subtractMenu.getItems().forEach(item -> item.setOnAction(this.handleSubtract));
            dotProductMenu.getItems().forEach(item -> item.setOnAction(this.handleDotProduct));
            perpendicularVectorMenu.getItems().forEach(item -> item.setOnAction(this.handlePerpendicularVector));
            perpendicularProductMenu.getItems().forEach(item -> item.setOnAction(this.handlePerpendicularProduct));
            projectionTimeMenu.getItems().forEach(item -> item.setOnAction(this.handleProjectionTime));
            angleBetweenMenu.getItems().forEach(item -> item.setOnAction(this.handleAngleBetween));
            reflectMenu.getItems().forEach(item -> item.setOnAction(this.handleReflect));
            deleteItem.setOnAction(this.handleDelete);
            renameItem.setOnAction(this.handleRename);
            this.show((Node) e.getSource(), Side.RIGHT, 0, 0);
            e.consume();
        };
    }

    public void setOnAdd(EventHandler<ActionEvent> eventHandler) {
        this.handleAdd = eventHandler;
    }


    public void setHandleSubtract(EventHandler<ActionEvent> eventHandler) {
        this.handleSubtract = eventHandler;
    }


    public void setOnDotProduct(EventHandler<ActionEvent> eventHandler) {
        this.handleDotProduct = eventHandler;
    }


    public void setOnPerpendicularVector(EventHandler<ActionEvent> eventHandler) {
        this.handlePerpendicularVector = eventHandler;
    }

    public void setOnPerpendicularProduct(EventHandler<ActionEvent> eventHandler) {
        this.handlePerpendicularProduct = eventHandler;
    }

    public void setOnProjectionTime(EventHandler<ActionEvent> eventHandler) {
        this.handleProjectionTime = eventHandler;
    }

    public void setOnAngleBetween(EventHandler<ActionEvent> eventHandler) {
        this.handleAngleBetween = eventHandler;
    }
    public void setOnReflect(EventHandler<ActionEvent> eventHandler) {
        this.handleReflect = eventHandler;
    }

    public void setOnRename(EventHandler<ActionEvent> eventHandler) {
        this.handleRename = eventHandler;
    }

    public void setOnDeleteItem(EventHandler<ActionEvent> eventHandler) {
        this.handleDelete = eventHandler;
    }

}
