package games.absolutephoenix.harvestcity.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class HCClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Client Configs for Harvest City");

        //define configs

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
