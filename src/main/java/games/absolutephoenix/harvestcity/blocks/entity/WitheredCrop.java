package games.absolutephoenix.harvestcity.blocks.entity;

import games.absolutephoenix.harvestcity.blocks.crops.utils.BaseCropBlock;
import games.absolutephoenix.harvestcity.registry.HCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class WitheredCrop extends BaseCropBlock {
    public WitheredCrop(){
        super(Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.CROP).noOcclusion());

    }
    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
            return pState.is(HCBlocks.FARMLAND.get());
    }
}
