package com.mtanevski.math2d.gui;

import com.mtanevski.math2d.gui.canvas.CanvasWrapper;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.commands.CommandsManager;
import com.mtanevski.math2d.gui.commands.CreatePointCommand;
import com.mtanevski.math2d.gui.commands.CreateVectorCommand;
import com.mtanevski.math2d.gui.utils.CoordinateSystem;
import com.mtanevski.math2d.gui.utils.FxUtil;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class MainController extends VBox {

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
    @FXML
    public ChoiceBox<Constants.Origin> originChoiceBox;

    private CanvasWrapper canvasWrapper;

    public void initialize() {

        // initialize choicebox
        originChoiceBox.getItems().addAll(Constants.Origin.values());
        originChoiceBox.getSelectionModel().select(Constants.Origin.CENTER);
        var origin = getSelectedOrigin();

        // canvas wrapper
        canvasWrapper = new CanvasWrapper(canvas);
        canvasWrapper.setOnMouseEnteredOrMoved(this::updateMousePositions);
        canvasWrapper.setOnMouseClicked(event -> {
            this.updateMousePositions(event);
            Overlay.deselectAll();
        });
        canvasWrapper.setOnDragDropped(getDragDroppedHandler());
        this.canvasWrapper.draw(origin);

        // objects
        initializeObjectsList();

        // coordinate
        CoordinateSystem.draw(canvas, origin);
        CommandsManager.canUndoProperty.addListener((observableValue, oldValue, newValue) -> undoMenuItem.setDisable(!newValue));
        CommandsManager.canRedoProperty.addListener((observableValue, oldValue, newValue) -> redoMenuItem.setDisable(!newValue));
    }

    public void newPoint() {
        CommandsManager.execute(new CreatePointCommand(null));
    }

    public void newVector() {
        CommandsManager.execute(new CreateVectorCommand(null));
    }

    public void exit() {
        Platform.exit();
    }

    public void about() {
        FxUtil.loadUtilWindow(Constants.Resources.ABOUT_FXML, null, canvas.getScene().getWindow());
    }

    public void undo() {
        CommandsManager.undo();
    }

    public void redo() {
        CommandsManager.redo();
    }

    public void originChange() {
        var selectedOrigin = getSelectedOrigin();
        if(canvasWrapper!= null) {
            this.canvasWrapper.draw(selectedOrigin);
        CoordinateSystem.draw(canvas, selectedOrigin);
        }
    }

    private EventHandler<DragEvent> getDragDroppedHandler() {
        return (DragEvent event) ->
        {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String draggedItem = db.getString();
                if (Constants.VECTOR.equals(draggedItem)) {
                    CommandsManager.execute(new CreateVectorCommand(null));
                } else if (Constants.POINT.equals(draggedItem)) {
                    CommandsManager.execute(new CreatePointCommand(null));
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
            private final ImageView imageView1 = new ImageView();
            private final ImageView imageView2 = new ImageView();
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> listCell = new ListCell<>() {
                    @Override
                    protected void updateItem(String name, boolean empty) {
                        super.updateItem(name, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            if (name.equals(Constants.POINT)) {
                                imageView1.setImage(FxUtil.createImage(imageView1, Constants.Resources.SMALL_CIRCLE_PNG));
                                setGraphic(imageView1);
                            } else if (name.equals(Constants.VECTOR)) {
                                imageView2.setImage(FxUtil.createImage(imageView2, Constants.Resources.ARROW_DOWN_RIGHT_PNG));
                                setGraphic(imageView2);
                            }
                            setText(name);
                        }
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

    private void updateMousePositions(MouseEvent mouseEvent) {
        var pickResult = mouseEvent.getPickResult();
        double x = pickResult.getIntersectedPoint().getX();
        double y = pickResult.getIntersectedPoint().getY();
        var intersectedNode = pickResult.getIntersectedNode();
        boolean isCanvasIntersected = intersectedNode instanceof Canvas
                || Constants.Ids.OVERLAY.equals(intersectedNode.getId());
        if (isCanvasIntersected && isOriginCenter()) {
            x = (x - canvas.getWidth() / 2);
            y = (y - canvas.getHeight() / 2);
        }
        if(isCanvasIntersected && mouseEvent.getClickCount() > 0) {
            propertiesPane.getChildren().clear();
        }
        this.mouseCoordinates.setText(String.format("%d,%d", (int) x, (int) y));
    }

    private boolean isOriginCenter() {
        return originChoiceBox.getSelectionModel().getSelectedItem() == Constants.Origin.CENTER;
    }

    private Constants.Origin getSelectedOrigin() {
        return originChoiceBox.getSelectionModel().getSelectedItem();
    }


}
