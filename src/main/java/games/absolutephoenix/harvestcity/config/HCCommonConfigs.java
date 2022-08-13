package games.absolutephoenix.harvestcity.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class HCCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> DAYS_IN_MONTH;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SKIP_PASSOUT;

    static {
        BUILDER.push("Server Configs for Harvest City");

        DAYS_IN_MONTH = BUILDER.comment("Total number of days in a season.").define("Days In Season", 28);
        SKIP_PASSOUT = BUILDER.comment("Skip the pass-out sequence.").define("Skip pass-out", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
