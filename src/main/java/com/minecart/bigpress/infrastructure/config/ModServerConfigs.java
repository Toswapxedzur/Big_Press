package com.minecart.bigpress.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class ModServerConfigs extends ConfigBase {
    public final ModKinetics kinetics = nested(0, ModKinetics::new, Comments.kinetics);
    @Override
    public String getName() {
        return "server";
    }

    private static class Comments{
        static String kinetics = "Parameters and abilities of Create's kinetic mechanisms";
    }
}
