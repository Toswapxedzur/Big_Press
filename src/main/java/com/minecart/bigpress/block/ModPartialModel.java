package com.minecart.bigpress.block;

import com.minecart.bigpress.BigPress;
import com.simibubi.create.Create;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class ModPartialModel {
    public static final PartialModel BIG_PRESS_HEAD = block("big_mechanical_press/head");

    private static PartialModel block(String path) {
        return PartialModel.of(BigPress.modLoc("block/" + path));
    }

    public static void init(){}
}
