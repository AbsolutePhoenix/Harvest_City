package games.absolutephoenix.harvestcity.registry.utility;

import games.absolutephoenix.harvestcity.blocks.*;
import games.absolutephoenix.harvestcity.blocks.crops.utils.BaseCropBlock;
import games.absolutephoenix.harvestcity.items.SeedItem;
import games.absolutephoenix.harvestcity.tabs.HCCCreativeModeTabs;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import static games.absolutephoenix.harvestcity.registry.HCBlocks.registerCropBlocks;
import static games.absolutephoenix.harvestcity.registry.HCItems.*;

@SuppressWarnings("unused")
public class CropRegister {

    public RegistryObject<BaseCropBlock> plant;
    public RegistryObject<Item> seed;
    public RegistryObject<Item> product;

    public CropRegister(String name, String seedType, int[] stages, int regrowthDays, String[] season, int hunger, float saturation, String productOverride) {
        switch (stages.length) {
            case 3:
                if (regrowthDays <= 0)
                    plant = registerCropBlocks(name + "_plant", () -> new CropBlockAge3(product, stages, season));
                else
                    plant = registerCropBlocks(name + "_plant", () -> new CropBlockAge3Regrow(product, stages, season, regrowthDays));
                break;
            case 4:
                if (regrowthDays <= 0)
                    plant = registerCropBlocks(name + "_plant", () -> new CropBlockAge4(product, stages, season));
                else
                    plant = registerCropBlocks(name + "_plant", () -> new CropBlockAge4Regrow(product, stages, season, regrowthDays));
                break;
            case 5:
                if (regrowthDays <= 0)
                    plant = registerCropBlocks(name + "_plant", () -> new CropBlockAge5(product, stages, season));
                else
                    plant = registerCropBlocks(name + "_plant", () -> new CropBlockAge5Regrow(product, stages, season, regrowthDays));
                break;
            default:
                try {
                    throw new Exception();
                } catch (Exception e) {
                    System.err.println("invalid crop registry data. for " + name + ". Crashed due to invalid age data.");
                    System.exit(0);
                }
        }

        String productName = name;
        if(!productOverride.equals("none"))
            productName = productOverride;

        if (seedType.equals("none")) {
            if (hunger == 0)
                product = ITEMS.register(productName, () -> new SeedItem(plant.get(), new Item.Properties().tab(HCCCreativeModeTabs.HC_ITEMS)));
            else
                product = ITEMS.register(productName, () -> new SeedItem(plant.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod(saturation).build()).tab(HCCCreativeModeTabs.HC_FOODS)));

        } else {
            seed = ITEMS.register(name + "_" + seedType, () -> new SeedItem(plant.get(), new Item.Properties().tab(HCCCreativeModeTabs.HC_SEEDS)));
            if (hunger == 0)
                product = ITEMS.register(productName, () -> new Item(new Item.Properties().tab(HCCCreativeModeTabs.HC_ITEMS)));
            else
                product = ITEMS.register(productName, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationMod(saturation).build()).tab(HCCCreativeModeTabs.HC_FOODS)));
        }
    }

    public CropRegister(String name, String seedType, int[] stages, int regrowthDays, String[] season, String productOverride) {
        this(name, seedType, stages, regrowthDays, season, 0, 0f, productOverride);
    }

    public CropRegister(String name, String seedType, int[] stages, int regrowthDays, String[] season) {
        this(name, seedType, stages, regrowthDays, season, 0, 0f, "none");
    }
    public CropRegister(String name, int[] stages, int regrowthDays, String[] season, String productOverride) {
        this(name, "none", stages, regrowthDays, season, 0, 0f, productOverride);
    }
    public CropRegister(String name, int[] stages, int regrowthDays, String[] season) {
        this(name, "none", stages, regrowthDays, season, 0, 0f, "none");
    }

    public CropRegister(String name, String seedType, int[] stages, String[] season, int hunger, float saturation, String productOverride) {
        this(name, seedType, stages, 0, season, hunger, saturation, productOverride);
    }
    public CropRegister(String name, String seedType, int[] stages, String[] season, int hunger, float saturation) {
        this(name, seedType, stages, 0, season, hunger, saturation, "none");
    }

    public CropRegister(String name, int[] stages, String[] season, int hunger, float saturation, String productOverride) {
        this(name, "none", stages, 0, season, hunger, saturation, productOverride);
    }
    public CropRegister(String name, int[] stages, String[] season, int hunger, float saturation) {
        this(name, "none", stages, 0, season, hunger, saturation, "none");
    }
    public CropRegister(String name, String seedType, int[] stages, String[] season, String productOverride) {
        this(name, seedType, stages, 0, season, 0, 0f, productOverride);
    }
    public CropRegister(String name, String seedType, int[] stages, String[] season) {
        this(name, seedType, stages, 0, season, 0, 0f, "none");
    }
    public CropRegister(String name, int[] stages, String[] season, String productOverride) {
        this(name, "none", stages, 0, season, 0, 0f, productOverride);
    }
    public CropRegister(String name, int[] stages, String[] season) {
        this(name, "none", stages, 0, season, 0, 0f, "none");
    }
}
