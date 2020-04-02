package com.mtanevski.collisions.point.gui.canvas;

import com.mtanevski.collisions.point.gui.Constants;
import com.mtanevski.collisions.point.math.Point2D;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Affine;

public class Kanvas {

    public static final String ID = "canvas";
    private final Canvas canvas;
    private Point2D selectionStart;
    private Point2D selectionEnd;

    public Kanvas(Canvas canvas) {

        this.canvas = canvas;
        this.canvas.setId(ID);

        //  add overlay to canvas
        Overlay.buildPane();
        ((AnchorPane) this.canvas.getParent()).getChildren().add(Overlay.pane);

        this.proxyDragEvents();
    }

    public void setOnDragDropped(EventHandler<? super DragEvent> value) {
        canvas.setOnDragDropped(value);
        Overlay.pane.setOnDragDropped(value);
    }


    public void setOnMouseEnteredOrMoved(EventHandler<? super MouseEvent> value){
        canvas.setOnMouseEntered(value);
        canvas.setOnMouseMoved(value);
        Overlay.pane.setOnMouseEntered(value);
        Overlay.pane.setOnMouseMoved(value);
    }

    public void setOnMouseClicked(EventHandler<? super MouseEvent> value){
        canvas.setOnMouseClicked(value);
//        overlay.setOnMouseClicked(value);
    }

    private static void handleOnDrag(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    public void drawWithOrigin(Constants.OriginType originType) {
        this.clear();
        Overlay.setOrigin(originType, canvas.getWidth(), canvas.getHeight());
    }

    private void clear() {
        Affine affine = new Affine();
        affine.appendTranslation(0, 0);
        canvas.getGraphicsContext2D().setTransform(affine);
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void proxyDragEvents() {
        canvas.setOnDragOver(Kanvas::handleOnDrag);
        Overlay.pane.setOnDragOver(Kanvas::handleOnDrag);

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

        canvas.setOnDragDetected((MouseEvent event) ->
        {
            Dragboard db = canvas.startDragAndDrop(TransferMode.COPY_OR_MOVE);
            ClipboardContent content = new ClipboardContent();
//            content.putString( listCell.getItem() );
            db.setContent(content);
            event.consume();
        });

    }


}
