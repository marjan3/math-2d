package com.mtanevski.collisions.point.javafx.canvas;

import com.mtanevski.collisions.point.javafx.Constants;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Affine;

public class Kanvas {

    private final Canvas canvas;

    public Kanvas(Canvas canvas) {

        this.canvas = canvas;

        //  add overlay to canvas
        Overlay.buildPane();
        ((AnchorPane) this.canvas.getParent()).getChildren().add(Overlay.pane);

        this.setOnDragDetected();
        this.setOnDragOver();
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

    private void setOnDragOver() {
        canvas.setOnDragOver(Kanvas::handleOnDrag);
        Overlay.pane.setOnDragOver(Kanvas::handleOnDrag);
    }


    private void setOnDragDetected() {
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
