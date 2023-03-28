package com.mtanevski.math2d.gui;

import com.mtanevski.math2d.gui.canvas.CanvasWrapper;
import com.mtanevski.math2d.gui.canvas.Overlay;
import com.mtanevski.math2d.gui.commands.CommandsManager;
import com.mtanevski.math2d.gui.commands.CreatePointCommand;
import com.mtanevski.math2d.gui.commands.CreateRequest;
import com.mtanevski.math2d.gui.commands.CreateVectorCommand;
import com.mtanevski.math2d.gui.utils.CoordinateSystem;
import com.mtanevski.math2d.gui.utils.FxUtil;
import com.mtanevski.math2d.math.Point2D;
import javafx.application.Platform;
import javafx.event.EventTarget;
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
        canvasWrapper.setOnMouseEnteredOrMoved((event) -> {
            var coordinate = this.getCanvasCoordinate(event.getTarget(), event.getX(), event.getY());
            String text = String.format(Constants.Formats.CANVAS_COORDINATES_FORMAT, (int) coordinate.x, (int) coordinate.y);
            this.mouseCoordinates.setText(text);
        });
        canvasWrapper.setOnMouseClicked(event -> {
            var coordinate = this.getCanvasCoordinate(event.getTarget(), event.getX(), event.getY());
            String text = String.format(Constants.Formats.CANVAS_COORDINATES_FORMAT, (int) coordinate.x, (int) coordinate.y);
            this.mouseCoordinates.setText(text);
            Overlay.deselectAll();
            propertiesPane.getChildren().clear();
        });
        canvasWrapper.setOnDragDropped(this::getDragDroppedHandler);
        this.canvasWrapper.draw(origin);

        // objects
        initializeObjectsList();

        // coordinate
        CoordinateSystem.draw(canvas, origin);
        undoMenuItem.setDisable(true);
        redoMenuItem.setDisable(true);
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
        if (canvasWrapper != null) {
            this.canvasWrapper.draw(selectedOrigin);
            CoordinateSystem.draw(canvas, selectedOrigin);
        }
    }

    private void getDragDroppedHandler(DragEvent event) {
        var dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasString()) {
            String draggedItem = dragboard.getString();
            var createRequest = CreateRequest.from(getCanvasCoordinate(event.getTarget(), event.getX(), event.getY()));
            if (Constants.VECTOR.equals(draggedItem)) {
                CommandsManager.execute(new CreateVectorCommand(createRequest));
            } else if (Constants.POINT.equals(draggedItem)) {
                CommandsManager.execute(new CreatePointCommand(createRequest));
            }
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
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
                    Dragboard dragboard = listCell.startDragAndDrop(TransferMode.COPY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(listCell.getItem());
                    dragboard.setContent(content);
                    event.consume();
                });

                return listCell;
            }
        });
    }

    private Point2D getCanvasCoordinate(EventTarget target, double x, double y) {
        boolean isCanvasIntersected = target instanceof Canvas;
        double newX = x;
        double newY = y;
        if (isCanvasIntersected && isOriginCenter()) {
            newX = (x - canvas.getWidth() / 2);
            newY = (y - canvas.getHeight() / 2);
        }
        return Point2D.of(newX, newY);
    }

    private boolean isOriginCenter() {
        return originChoiceBox.getSelectionModel().getSelectedItem() == Constants.Origin.CENTER;
    }

    private Constants.Origin getSelectedOrigin() {
        return originChoiceBox.getSelectionModel().getSelectedItem();
    }

}
