package com.mtanevski.math2d.gui.canvas;

import com.mtanevski.math2d.gui.Constants;
import com.mtanevski.math2d.math.Point2D;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Affine;

public class CanvasWrapper {

    public static final String ID = "canvas";
    private final Canvas canvas;
    private Point2D selectionStart;
    private Point2D selectionEnd;

    public CanvasWrapper(Canvas canvas) {

        this.canvas = canvas;
        this.canvas.setId(ID);

        //  add overlay to canvas
        Overlay.buildPane();
        var children = ((AnchorPane) this.canvas.getParent()).getChildren();
        children.add(Overlay.pane);

        this.proxyDragEvents();
    }

    private static void handleOnDragOver(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    public void setOnDragDropped(EventHandler<? super DragEvent> value) {
        canvas.setOnDragDropped(value);
        Overlay.pane.setOnDragDropped(value);
    }

    public void setOnMouseEnteredOrMoved(EventHandler<? super MouseEvent> value) {
        canvas.setOnMouseEntered(value);
        canvas.setOnMouseMoved(value);
        Overlay.pane.setOnMouseEntered(value);
        Overlay.pane.setOnMouseMoved(value);
    }

    public void setOnMouseClicked(EventHandler<? super MouseEvent> value) {
        canvas.setOnMouseClicked(value);
        Overlay.pane.setOnMouseClicked(value);
    }

    public void draw(Constants.Origin origin) {
        this.clear();
        Overlay.setOrigin(origin, canvas.getWidth(), canvas.getHeight());
    }

    private void clear() {
        Affine affine = new Affine();
        affine.appendTranslation(0, 0);
        canvas.getGraphicsContext2D().setTransform(affine);
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void proxyDragEvents() {
        canvas.setOnDragOver(CanvasWrapper::handleOnDragOver);
        Overlay.pane.setOnDragOver(CanvasWrapper::handleOnDragOver);

        canvas.setOnMousePressed((MouseEvent event) -> {
            this.selectionStart = Point2D.of(event.getX(), event.getY());
        });
        canvas.setOnMouseDragged((MouseEvent event) -> {
            this.selectionEnd = Point2D.of(event.getX(), event.getY());
            Overlay.redrawDragSquare(this.selectionStart, this.selectionEnd);
        });
        canvas.setOnMouseReleased((MouseEvent event) -> {
            Overlay.removeDragSquare();
        });
    }


}
