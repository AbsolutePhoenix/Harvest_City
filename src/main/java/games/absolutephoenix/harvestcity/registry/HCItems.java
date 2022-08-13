package games.absolutephoenix.harvestcity.registry;

import games.absolutephoenix.harvestcity.items.ModHoeItem;
import games.absolutephoenix.harvestcity.items.ModWateringCanItem;
import games.absolutephoenix.harvestcity.reference.ModReference;
import games.absolutephoenix.harvestcity.tabs.HCCCreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
@SuppressWarnings("ALL")
public class HCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModReference.MOD_ID);

    //region Tools
    public static final RegistryObject<Item> TIER_1_HOE = ITEMS.register("tier1hoe", ()->
            new ModHoeItem(Tiers.WOOD, 0, -3.0F, (new Item.Properties()).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> TIER_2_HOE = ITEMS.register("tier2hoe", ()->
            new ModHoeItem(Tiers.STONE, 0, -3.0F, (new Item.Properties()).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> TIER_3_HOE = ITEMS.register("tier3hoe", ()->
            new ModHoeItem(Tiers.IRON, 0, -3.0F, (new Item.Properties()).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> TIER_4_HOE = ITEMS.register("tier4hoe", ()->
            new ModHoeItem(Tiers.DIAMOND, 0, -3.0F, (new Item.Properties()).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> TIER_5_HOE = ITEMS.register("tier5hoe", ()->
            new ModHoeItem(Tiers.NETHERITE, 0, -3.0F, (new Item.Properties()).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> TIER_1_WATERING_CAN = ITEMS.register("tier1wateringcan", ()->
            new ModWateringCanItem(Tiers.WOOD, 0, -3.0F, (new Item.Properties()).durability(54).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> TIER_2_WATERING_CAN = ITEMS.register("tier2wateringcan", ()->
            new ModWateringCanItem(Tiers.STONE, 0, -3.0F, (new Item.Properties()).durability(243).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> TIER_3_WATERING_CAN = ITEMS.register("tier3wateringcan", ()->
            new ModWateringCanItem(Tiers.IRON, 0, -3.0F, (new Item.Properties()).durability(405).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> TIER_4_WATERING_CAN = ITEMS.register("tier4wateringcan", ()->
            new ModWateringCanItem(Tiers.DIAMOND, 0, -3.0F, (new Item.Properties()).durability(729).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> TIER_5_WATERING_CAN = ITEMS.register("tier5wateringcan", ()->
            new ModWateringCanItem(Tiers.NETHERITE, 0, -3.0F, (new Item.Properties()).durability(1458).tab(HCCCreativeModeTabs.HC_TOOLS)));
    public static final RegistryObject<Item> SEASONS_CLOCK = ITEMS.register("seasons_clock", () ->
            new Item(new Item.Properties().stacksTo(1).tab(HCCCreativeModeTabs.HC_TOOLS)));
    //endregion

    public static void register(IEventBus event){
        ITEMS.register(event);
    }

}
