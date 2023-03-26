package com.mtanevski.math2d.gui.commands;

import com.mtanevski.math2d.gui.dialogs.NewObjectDialogResult;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateRequest {

    private final String name;
    private final double x;
    private final double y;

    public static CreateRequest fromDialogResult(NewObjectDialogResult newDialogResult) {
        return CreateRequest.builder()
                .name(newDialogResult.label)
                .x(newDialogResult.x)
                .y(newDialogResult.y).
                build();
    }

}
