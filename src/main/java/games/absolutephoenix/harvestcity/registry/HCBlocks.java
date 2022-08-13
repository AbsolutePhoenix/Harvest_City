package games.absolutephoenix.harvestcity.registry;

import games.absolutephoenix.harvestcity.blocks.FarmlandBlock;
import games.absolutephoenix.harvestcity.blocks.crops.utils.BaseCropBlock;
import games.absolutephoenix.harvestcity.blocks.entity.WitheredCrop;
import games.absolutephoenix.harvestcity.reference.ModReference;
import games.absolutephoenix.harvestcity.tabs.HCCCreativeModeTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
public class HCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModReference.MOD_ID);

    public static final RegistryObject<Block> FARMLAND = registerCropBlocks("farmland", ()->
            new FarmlandBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND)));
    public static final RegistryObject<Block> GREENHOUSE_GLASS = registerBlock("greenhouse_glass", () ->
            new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)), HCCCreativeModeTabs.HC_BLOCKS);


    public static final HCCrops CROPS = new HCCrops();
    public static final RegistryObject<BaseCropBlock> WITHERED_CROP = registerCropBlocks("withered_crop", WitheredCrop::new);

    public static <T extends Block> RegistryObject<T> registerCropBlocks(String name, Supplier<T> block){
        return BLOCKS.register(name, block);
    }
    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    public static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        HCItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus event){
        BLOCKS.register(event);
    }
}
