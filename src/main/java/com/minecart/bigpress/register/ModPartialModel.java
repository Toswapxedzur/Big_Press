package com.minecart.bigpress.register;

import com.minecart.bigpress.BigPress;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class ModPartialModel {
    public static final PartialModel BIG_PRESS_HEAD = block("big_mechanical_press/head");

    private static PartialModel block(String path) {
        return PartialModel.of(BigPress.modLoc("block/" + path));
    }

    public static void init(){}
}
