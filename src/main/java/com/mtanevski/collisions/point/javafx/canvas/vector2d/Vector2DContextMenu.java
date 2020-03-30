package com.mtanevski.collisions.point.javafx.canvas.vector2d;

import com.mtanevski.collisions.point.javafx.canvas.Overlay;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.ContextMenuEvent;

import java.util.Set;

public class Vector2DContextMenu extends ContextMenu {

    private final MenuItem deleteItem;
    private final Menu addMenu;
    private final Menu subtractMenu;
    private final Menu dotProductMenu;
    private final Menu perpendicularVectorMenu;
    private final Menu perpendicularProductMenu;
    private final Menu projectionTimeMenu;
    private final Menu angleBetweenMenu;

    private EventHandler<ActionEvent> onAdd;
    private EventHandler<ActionEvent> onSubtract;
    private EventHandler<ActionEvent> onDotProduct;
    private EventHandler<ActionEvent> onPerpendicularVector;
    private EventHandler<ActionEvent> onPerpendicularProduct;
    private EventHandler<ActionEvent> onProjectionTime;
    private EventHandler<ActionEvent> onAngleBetween;

    public Vector2DContextMenu() {
        super();
        addMenu = new Menu("Add...");
        subtractMenu = new Menu("Subtract...");
        dotProductMenu = new Menu("Dot Product...");
        perpendicularVectorMenu = new Menu("Perpendicular Vector..");
        perpendicularProductMenu = new Menu("Perpendicular Product...");
        projectionTimeMenu = new Menu("Projection Time...");
        angleBetweenMenu = new Menu("Angle Between...");
        this.getItems().addAll(
                addMenu,
                subtractMenu,
                dotProductMenu,
                perpendicularVectorMenu,
                perpendicularProductMenu,
                projectionTimeMenu,
                angleBetweenMenu);

        deleteItem = new MenuItem("Delete");
        this.getItems().addAll(new SeparatorMenuItem(), deleteItem);
    }

    public EventHandler<ContextMenuEvent> getEventHandler() {
        return e -> {
            Set<String> vectors = Overlay.vectors.keySet();
            this.getItems().forEach( m -> {
                if(m instanceof Menu){
                    Menu menu = (Menu) m;
                    menu.getItems().clear();
                    vectors.forEach(v -> {
                        MenuItem e1 = new MenuItem(v);
                        menu.getItems().add(e1);
                    });
                }
            });
            perpendicularVectorMenu.getItems().clear();
            perpendicularVectorMenu.getItems().add(new MenuItem("Left"));
            perpendicularVectorMenu.getItems().add( new MenuItem("Right"));

            addMenu.getItems().forEach( item -> item.setOnAction(this.onAdd));
            subtractMenu.getItems().forEach( item -> item.setOnAction(this.onSubtract));
            dotProductMenu.getItems().forEach(item -> item.setOnAction(this.onDotProduct));
            perpendicularVectorMenu.getItems().forEach(item -> item.setOnAction(this.onPerpendicularVector));
            perpendicularProductMenu.getItems().forEach( item -> item.setOnAction(this.onPerpendicularProduct));
            projectionTimeMenu.getItems().forEach( item -> item.setOnAction(this.onProjectionTime));
            angleBetweenMenu.getItems().forEach( item -> {
                item.setOnAction(this.onAngleBetween);
            });
            this.show((Node) e.getSource(), Side.RIGHT, 0, 0);
            e.consume();
        };
    }

    public void setOnAdd(EventHandler<ActionEvent> eventHandler){
        this.onAdd = eventHandler;
    }


    public void setOnSubtract(EventHandler<ActionEvent> eventHandler){
        this.onSubtract = eventHandler;
    }


    public void setOnDotProduct(EventHandler<ActionEvent> eventHandler){
        this.onDotProduct = eventHandler;
    }


    public void setOnPerpendicularVector(EventHandler<ActionEvent> eventHandler){
        this.onPerpendicularVector = eventHandler;
    }

    public void setOnPerpendicularProduct(EventHandler<ActionEvent> eventHandler){
        this.onPerpendicularProduct = eventHandler;
    }

    public void setOnProjectionTime(EventHandler<ActionEvent> eventHandler){
        this.onProjectionTime = eventHandler;
    }

    public void setOnAngleBetween(EventHandler<ActionEvent> eventHandler){
        this.onAngleBetween = eventHandler;
    }

    public MenuItem getDeleteItem() {
        return deleteItem;
    }
}
