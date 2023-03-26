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

    public VectorContextMenu() {
        super();
        addMenu = new Menu(Constants.Labels.ADD);
        subtractMenu = new Menu(Constants.Labels.SUBTRACT);
        dotProductMenu = new Menu(Constants.Labels.DOT_PRODUCT);
        perpendicularVectorMenu = new Menu(Constants.Labels.PERPENDICULAR_VECTOR);
        perpendicularProductMenu = new Menu(Constants.Labels.PERPENDICULAR_PRODUCT);
        projectionTimeMenu = new Menu(Constants.Labels.PROJECTION_TIME);
        angleBetweenMenu = new Menu(Constants.Labels.ANGLE_BETWEEN);
        this.getItems().addAll(
                addMenu,
                subtractMenu,
                dotProductMenu,
                perpendicularVectorMenu,
                perpendicularProductMenu,
                projectionTimeMenu,
                angleBetweenMenu);

        deleteItem = new MenuItem(Constants.Labels.DELETE);
        this.getItems().addAll(new SeparatorMenuItem(), deleteItem);
    }

    public EventHandler<ContextMenuEvent> getEventHandler() {
        return e -> {
            var vectors = Overlay.vectors.keySet();
            this.getItems().forEach( m -> {
                if(m instanceof Menu){
                    var menu = (Menu) m;
                    menu.getItems().clear();
                    vectors.forEach(v -> {
                        var e1 = new MenuItem(v);
                        menu.getItems().add(e1);
                    });
                }
            });
            perpendicularVectorMenu.getItems().clear();
            perpendicularVectorMenu.getItems().add(new MenuItem(Constants.Labels.LEFT));
            perpendicularVectorMenu.getItems().add( new MenuItem(Constants.Labels.RIGHT));

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
