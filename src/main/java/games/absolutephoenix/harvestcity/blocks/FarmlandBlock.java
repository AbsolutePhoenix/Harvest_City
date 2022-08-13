package games.absolutephoenix.harvestcity.blocks;

import games.absolutephoenix.harvestcity.blocks.crops.utils.BaseCropBlock;
import games.absolutephoenix.harvestcity.blocks.entity.FarmlandBlockEntity;
import games.absolutephoenix.harvestcity.registry.HCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FarmlandBlock extends FarmBlock implements EntityBlock {
    public FarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MOISTURE);
    }
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRand) {}
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {}
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float p_153231_) {
        if (!pLevel.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(pLevel, pPos, Blocks.DIRT.defaultBlockState(), p_153231_, pEntity)) {
            if(!(pLevel.getBlockState(pPos.above()).getBlock() instanceof BaseCropBlock)) {
                turnToDirt(pState, pLevel, pPos);
            }
        }

    }
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(Blocks.DIRT);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return HCBlockEntities.FARMLAND.get().create(pPos, pState);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {

        return pLevel.isClientSide ? null : (level, pos, state, blockEntity) -> ((FarmlandBlockEntity)blockEntity).tick(level, pos, state);
    }
}
