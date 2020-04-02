package com.mtanevski.collisions.point.gui;

import com.mtanevski.collisions.point.gui.canvas.Kanvas;
import com.mtanevski.collisions.point.gui.canvas.Overlay;
import com.mtanevski.collisions.point.gui.canvas.coordinatesystem.CoordinateSystem;
import com.mtanevski.collisions.point.gui.canvas.point2d.DrawablePoint2D;
import com.mtanevski.collisions.point.gui.canvas.vector2d.Vector2DGraphic;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class MainController extends VBox {

    private CoordinateSystem coordinateSystem;

    @FXML
    private Canvas canvas;
    @FXML
    private Label mouseCoordinates;
    @FXML
    private ListView<String> objectsList;
    @FXML
    private VBox propertiesPane;
    private Kanvas kanvas;

    public void initialize() {
        initializeKanvas();
        // initialize coordinate system
        this.coordinateSystem = new CoordinateSystem(canvas, this::draw);

        initializeObjectsList();
        this.draw();
    }

    private void initializeKanvas() {
        kanvas = new Kanvas(canvas);
        kanvas.setOnMouseEnteredOrMoved(this::updateMousePositions);
        kanvas.setOnMouseClicked(event -> {
            this.updateMousePositions(event);
            this.switchPropertiesPane(coordinateSystem.getEditPropertiesPane());
        });

        kanvas.setOnDragDropped(getDragDroppedHandler());
    }

    public void exit(){
        Platform.exit();
    }

    private EventHandler<DragEvent> getDragDroppedHandler() {
        return (DragEvent event) ->
        {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String draggedItem = db.getString();
                if ("Vector2D".equals(draggedItem)) {
                    Platform.runLater(() -> {

                        Vector2DGraphic vector2DGraphic = Overlay.drawVector2D();
                        GridPane editPropertiesPane = vector2DGraphic.getEditPropertiesPane();
                        vector2DGraphic.onDrag(() -> switchPropertiesPane(editPropertiesPane));
                        switchPropertiesPane(editPropertiesPane);

                    });
                } else if ("Point2D".equals(draggedItem)) {
                    Platform.runLater(() -> {

                        DrawablePoint2D drawablePoint2D = Overlay.drawPoint2D();
                        GridPane editPropertiesPane = drawablePoint2D.getEditPropertiesPane();
                        drawablePoint2D.onDrag(() -> switchPropertiesPane(editPropertiesPane));
                        switchPropertiesPane(editPropertiesPane);


                    });
                }

                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        };
    }

    private void initializeObjectsList() {
        objectsList.getItems().addAll("Vector2D", "Point2D");
        objectsList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> listCell = new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };

                listCell.setOnDragDetected((MouseEvent event) ->
                {
                    Dragboard db = listCell.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(listCell.getItem());
                    db.setContent(content);
                    event.consume();
                });

                return listCell;
            }
        });
    }

    private void switchPropertiesPane(Node editPropertiesPane) {
        propertiesPane.getChildren().clear();
        propertiesPane.getChildren().addAll(editPropertiesPane);
    }

    private void updateMousePositions(MouseEvent mouseEvent) {
        PickResult pickResult = mouseEvent.getPickResult();
        double x = pickResult.getIntersectedPoint().getX();
        double y = pickResult.getIntersectedPoint().getY();
        if (pickResult.getIntersectedNode() instanceof Canvas && this.coordinateSystem.isOriginCenter()) {
            x = x - canvas.getWidth() / 2;
            y = y - canvas.getHeight() / 2;
        }
        this.mouseCoordinates.setText(x + "," + y);
    }

    private void draw() {
        Constants.OriginType selectedItem = this.coordinateSystem.getOriginType();
        this.kanvas.drawWithOrigin(selectedItem);
        this.coordinateSystem.draw();
    }
}
