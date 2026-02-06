package com.minecart.bigpress.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class ModKinetics extends ConfigBase {
    public final ModStress stress = nested(1, ModStress::new, Comments.stress);

    @Override
    public String getName() {
        return "kinetics";
    }

    private static class Comments{
        static String stress = "Fine tune the kinetic stats of individual components";
    }
}
