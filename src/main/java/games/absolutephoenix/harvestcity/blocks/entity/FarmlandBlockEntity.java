package games.absolutephoenix.harvestcity.blocks.entity;

import games.absolutephoenix.harvestcity.blocks.FarmlandBlock;
import games.absolutephoenix.harvestcity.blocks.crops.utils.BaseCropBlock;
import games.absolutephoenix.harvestcity.reference.ModReference;
import games.absolutephoenix.harvestcity.registry.HCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Random;

import static net.minecraft.world.level.block.Block.pushEntitiesUp;

public class FarmlandBlockEntity extends BlockEntity {

    public int days;
    public FarmlandBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(HCBlockEntities.FARMLAND.get(), pWorldPosition, pBlockState);
        days = ModReference.calendarSaveData.days;
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("days", this.days);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.days = pTag.getInt("days");
    }
    public void tick(Level level, BlockPos pos, BlockState state){
        if(Objects.requireNonNull(level.getServer()).overworld().isLoaded(pos)) {
            if (days != ModReference.calendarSaveData.days && level.getServer().overworld().getTimeOfDay(0) > 0.78460425F) {
                level.setBlock(pos, state.setValue(FarmlandBlock.MOISTURE, 0), 2);
                days = ModReference.calendarSaveData.days;
                if (!isUnderCrops(level, pos)) {
                    Random random = new Random();
                    if ((random.nextInt((4 - 1) + 1) + 1) == 4) {
                        turnToDirt(state, level, pos);
                    }
                }
            }
        }
        if (level.getServer().overworld().isRainingAt(pos.above()))
            level.setBlock(pos, state.setValue(FarmlandBlock.MOISTURE, 7), 2);

        if (!(level.getBlockState(pos.above()).getBlock() instanceof BaseCropBlock) && !level.getBlockState(pos.above()).getBlock().equals(Blocks.AIR))
            turnToDirt(state, level, pos);
    }

    public static void turnToDirt(BlockState pState, Level pLevel, BlockPos pPos) {
        pLevel.setBlockAndUpdate(pPos, pushEntitiesUp(pState, Blocks.DIRT.defaultBlockState(), pLevel, pPos));
    }
    private static boolean isUnderCrops(BlockGetter pLevel, BlockPos pPos) {
        BlockState plant = pLevel.getBlockState(pPos.above());
        BlockState state = pLevel.getBlockState(pPos);
        return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(pLevel, pPos, Direction.UP, (net.minecraftforge.common.IPlantable)plant.getBlock());
    }
}
