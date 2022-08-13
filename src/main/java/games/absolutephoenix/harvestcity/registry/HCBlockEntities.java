package games.absolutephoenix.harvestcity.registry;

import games.absolutephoenix.harvestcity.blocks.entity.CropEntity;
import games.absolutephoenix.harvestcity.blocks.entity.FarmlandBlockEntity;
import games.absolutephoenix.harvestcity.reference.ModReference;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class HCBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ModReference.MOD_ID);
    //region farmland
    public static final RegistryObject<BlockEntityType<FarmlandBlockEntity>> FARMLAND = BLOCK_ENTITIES.register("farmland", () ->
            BlockEntityType.Builder.of(FarmlandBlockEntity::new, HCBlocks.FARMLAND.get()).build(null));
    //endregion
    public static final RegistryObject<BlockEntityType<CropEntity>> CROP_ENTITY = BLOCK_ENTITIES.register("crop_entity", () ->
            BlockEntityType.Builder.of(CropEntity::new, getCropBlocks()).build(null));


    public static void register(IEventBus event){
        BLOCK_ENTITIES.register(event);
    }
    public static Block[] getCropBlocks(){
        List<Block> blocks = new ArrayList<>();

        for(RegistryObject<Block> registryObject : HCBlocks.BLOCKS.getEntries()){
            if(registryObject.get().getRegistryName().toString().contains("_plant")) {
                blocks.add(registryObject.get());
                System.out.println(registryObject.get().getRegistryName());
            }
        }
        return blocks.toArray( Block[]::new);
    }
}
