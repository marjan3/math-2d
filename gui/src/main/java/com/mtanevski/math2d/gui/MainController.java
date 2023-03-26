package com.mtanevski.math2d.gui;

import com.mtanevski.math2d.gui.canvas.Kanvas;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.canvas.coordinatesystem.CoordinateSystem;
import com.mtanevski.math2d.gui.commands.CommandsManager;
import com.mtanevski.math2d.gui.commands.CreatePointCommand;
import com.mtanevski.math2d.gui.commands.CreateVectorCommand;
import com.mtanevski.math2d.gui.utils.FxUtil;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
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
    @FXML
    private MenuItem undoMenuItem;
    @FXML
    private MenuItem redoMenuItem;
    private Kanvas kanvas;

    public void initialize() {
        initializeKanvas();
        // initialize coordinate system
        this.coordinateSystem = new CoordinateSystem(canvas, this::draw);

        initializeObjectsList();
        this.draw();
        undoMenuItem.disableProperty().bind(CommandsManager.canUndoProperty);
        redoMenuItem.disableProperty().bind(CommandsManager.canRedoProperty);
    }

    public void exit(){
        Platform.exit();
    }

    public void about(){
        FxUtil.loadUtilWindow(Constants.Resources.ABOUT_FXML, null, canvas.getScene().getWindow());
    }
    public void undo(){
        CommandsManager.undo();
    }

    public void redo(){
        CommandsManager.redo();
    }

    private void initializeKanvas() {
        kanvas = new Kanvas(canvas);
        kanvas.setOnMouseEnteredOrMoved(this::updateMousePositions);
        kanvas.setOnMouseClicked(event -> {
            this.updateMousePositions(event);
            this.switchPropertiesPane(coordinateSystem.getEditPropertiesPane());
            Overlay.deselectAll();
        });
        kanvas.setOnDragDropped(getDragDroppedHandler());
    }

    private EventHandler<DragEvent> getDragDroppedHandler() {
        return (DragEvent event) ->
        {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String draggedItem = db.getString();
                if (Constants.VECTOR.equals(draggedItem)) {
                    CommandsManager.execute(new CreateVectorCommand(this, null));
                } else if (Constants.POINT.equals(draggedItem)) {
                    CommandsManager.execute(new CreatePointCommand(this, null));
                }

                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        };
    }

    private void initializeObjectsList() {
        objectsList.getItems().addAll(Constants.OBJECTS);
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

    public void switchPropertiesPane(Node editPropertiesPane) {
        propertiesPane.getChildren().clear();
        propertiesPane.getChildren().addAll(editPropertiesPane);
    }

    private void updateMousePositions(MouseEvent mouseEvent) {
        var pickResult = mouseEvent.getPickResult();
        double x = pickResult.getIntersectedPoint().getX();
        double y = pickResult.getIntersectedPoint().getY();
        var intersectedNode = pickResult.getIntersectedNode();
        boolean isCanvasIntersected = intersectedNode instanceof Canvas || "overlay".equals(intersectedNode.getId());
        if (isCanvasIntersected && this.coordinateSystem.isOriginCenter()) {
            x = (x - canvas.getWidth() / 2);
            y = (y - canvas.getHeight() / 2);
        }
        this.mouseCoordinates.setText(String.format("%d,%d", (int)x, (int)y));
    }

    private void draw() {
        Constants.OriginType selectedItem = this.coordinateSystem.getOriginType();
        this.kanvas.drawWithOrigin(selectedItem);
        this.coordinateSystem.draw();
    }
}
