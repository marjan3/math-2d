package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.dialogs.SimpleDialogResult;
import com.mtanevski.math2d.math.Point2D;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CreateRequest {

    private final String name;
    private final double x;
    private final double y;

    public static CreateRequest fromDialogResult(SimpleDialogResult newDialogResult) {
        return CreateRequest.builder()
                .name(newDialogResult.name)
                .x(newDialogResult.x)
                .y(newDialogResult.y).
                build();
    }

    public static CreateRequest from(Point2D point2D) {
        return CreateRequest.builder()
                .x(point2D.x)
                .y(point2D.y)
                .build();
    }
}
