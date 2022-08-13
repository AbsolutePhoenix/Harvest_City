package games.absolutephoenix.harvestcity.blocks.crops.utils;

import games.absolutephoenix.harvestcity.blocks.entity.WitheredCrop;
import games.absolutephoenix.harvestcity.reference.ModReference;
import games.absolutephoenix.harvestcity.registry.HCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BaseCropBlock extends BushBlock {

    public BaseCropBlock(Properties properties) {
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        boolean isInSeason = false;
        for(int x = 0; x < 15; x++) {
            if (pLevel.getBlockState(pPos.above(x)).getBlock().equals(HCBlocks.GREENHOUSE_GLASS.get())) {
                isInSeason = true;
                break;
            }
        }
        if(!isInSeason)
            for(String season: getSeasons()) {
                if (season.toLowerCase().equals(ModReference.calendarSaveData.season)) {
                    isInSeason = true;
                    break;
                }
            }
        if(isInSeason)
            return pState.is(HCBlocks.FARMLAND.get());
        else
            return false;
    }
    public IntegerProperty getAgeProperty() {return IntegerProperty.create("age", 0, 1);}
    public boolean isHasRegrowth(){
        return false;
    }
    public int getMaxAge(){return 1;}
    public String[] getSeasons() {
        return null;
    }
    public int getTotalDaysToGrow() {
        return 0;
    }

    public int getRegrowthDays(){
        return 0;
    }
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if(!event.getWorld().isClientSide) {
            if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof WitheredCrop){
                event.getWorld().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
            } else if (getMaxAge() == event.getWorld().getBlockState(event.getPos()).getValue(getAgeProperty()) && isHasRegrowth()) {
                Block.dropResources(event.getWorld().getBlockState(event.getPos()), event.getWorld(), event.getPos());
                event.getWorld().setBlock(event.getPos(), event.getWorld().getBlockState(event.getPos()).setValue(getAgeProperty(), event.getWorld().getBlockState(event.getPos()).getValue(getAgeProperty()) + 1), 2);
            } else if (getMaxAge() == event.getWorld().getBlockState(event.getPos()).getValue(getAgeProperty()) && !isHasRegrowth()) {
                Block.dropResources(event.getWorld().getBlockState(event.getPos()), event.getWorld(), event.getPos());
                event.getWorld().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
            }
        }else{
            if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof WitheredCrop){
                spawnDestroyParticles(event.getWorld(), event.getPlayer(), event.getPos(), event.getWorld().getBlockState(event.getPos()));
                event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            }else if ((getMaxAge() == event.getWorld().getBlockState(event.getPos()).getValue(getAgeProperty()) && !isHasRegrowth())) {
                spawnDestroyParticles(event.getWorld(), event.getPlayer(), event.getPos(), event.getWorld().getBlockState(event.getPos()));
                event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }
}
