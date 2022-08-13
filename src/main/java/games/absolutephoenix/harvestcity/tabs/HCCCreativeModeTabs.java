package games.absolutephoenix.harvestcity.tabs;

import games.absolutephoenix.harvestcity.registry.HCBlocks;
import games.absolutephoenix.harvestcity.registry.HCCrops;
import games.absolutephoenix.harvestcity.registry.HCItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class HCCCreativeModeTabs {
    public static final CreativeModeTab HC_BLOCKS = new CreativeModeTab("hc_blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(HCBlocks.GREENHOUSE_GLASS.get().asItem());
        }
    };
    public static final CreativeModeTab HC_ITEMS = new CreativeModeTab("hc_items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(HCCrops.COFFEE_BEAN.product.get());
        }
    };
    public static final CreativeModeTab HC_TOOLS = new CreativeModeTab("hc_tools") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(HCItems.TIER_1_HOE.get());
        }
    };
    public static final CreativeModeTab HC_SEEDS = new CreativeModeTab("hc_seeds") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(HCCrops.STRAWBERRY.seed.get().asItem());
        }
    };
    public static final CreativeModeTab HC_FOODS = new CreativeModeTab("hc_foods") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(HCCrops.STRAWBERRY.product.get());
        }
    };
}
