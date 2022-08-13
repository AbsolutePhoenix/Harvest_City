package games.absolutephoenix.harvestcity.handlers;

import games.absolutephoenix.harvestcity.registry.HCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("unused")
public abstract class ToolActionHandler implements IForgeBlock {
    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        BlockState toolModifiedState = getToolModifiedState(state, context.getLevel(), context.getClickedPos(),
                context.getPlayer(), context.getItemInHand(), toolAction);

        if (toolModifiedState == null && ToolActions.HOE_TILL == toolAction && context.getItemInHand().canPerformAction(ToolActions.HOE_TILL))
        {
            Block block = state.getBlock();
            if (block == Blocks.ROOTED_DIRT)
            {
                if (!simulate && !context.getLevel().isClientSide)
                {
                    Block.popResourceFromFace(context.getLevel(), context.getClickedPos(), context.getClickedFace(), new ItemStack(Items.HANGING_ROOTS));
                }
                return Blocks.DIRT.defaultBlockState();
            } else if ((block == Blocks.GRASS_BLOCK || block == Blocks.DIRT_PATH || block == Blocks.DIRT || block == Blocks.COARSE_DIRT) &&
                    context.getLevel().getBlockState(context.getClickedPos().above()).isAir())
            {
                return block == Blocks.COARSE_DIRT ? Blocks.DIRT.defaultBlockState() : HCBlocks.FARMLAND.get().defaultBlockState();
            }
        }

        return toolModifiedState;
    }

    @SuppressWarnings("removal")
    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, Level level, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
        if (!stack.canPerformAction(toolAction)) return null;
        if (ToolActions.AXE_STRIP == toolAction) return AxeItem.getAxeStrippingState(state);
        else if(ToolActions.AXE_SCRAPE == toolAction) return WeatheringCopper.getPrevious(state).orElse(null);
        else if(ToolActions.AXE_WAX_OFF == toolAction) return Optional.ofNullable(HoneycombItem.WAX_OFF_BY_BLOCK.get().get(state.getBlock())).map((block) -> block.withPropertiesOf(state)).orElse(null);
        else if (ToolActions.SHOVEL_FLATTEN == toolAction) return ShovelItem.getShovelPathingState(state);
        return null;
    }
}
