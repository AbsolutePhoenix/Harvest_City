package games.absolutephoenix.harvestcity.items;
import games.absolutephoenix.harvestcity.blocks.FarmlandBlock;
import games.absolutephoenix.harvestcity.registry.HCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
@SuppressWarnings("DuplicatedCode")
public class ModHoeItem extends HoeItem{
    boolean hasChangeCharge = false;
    boolean isSneaking = false;

    public ModHoeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public InteractionResult useOn(UseOnContext context) {

        BlockPos pos = context.getClickedPos();
        Block block = context.getLevel().getBlockState(pos).getBlock();
        Block blockAbove = context.getLevel().getBlockState(pos.above()).getBlock();
        Direction playerDirection = context.getPlayer().getDirection();

        if(!context.getLevel().isClientSide) {
            ItemStack itemStack = context.getLevel().getServer().getPlayerList().getPlayer(context.getPlayer().getUUID()).getItemInHand(context.getLevel().getServer().getPlayerList().getPlayer(context.getPlayer().getUUID()).getUsedItemHand());

            if(!itemStack.getTag().contains("charge")){
                itemStack.getTag().putInt("charge", 4);
                hasChangeCharge = true;
            }

            if (context.getLevel().getServer().getPlayerList().getPlayer(context.getPlayer().getUUID()).isShiftKeyDown()) {
                if (getTier() == Tiers.STONE) {
                    if(itemStack.getTag().getInt("charge") == 4) itemStack.getTag().putInt("charge", 3);
                    else if(itemStack.getTag().getInt("charge") == 3) itemStack.getTag().putInt("charge", 4);
                    hasChangeCharge = true;
                } else if (getTier() == Tiers.IRON) {
                    if(itemStack.getTag().getInt("charge") == 4) itemStack.getTag().putInt("charge", 3);
                    else if(itemStack.getTag().getInt("charge") == 3) itemStack.getTag().putInt("charge", 2);
                    else if(itemStack.getTag().getInt("charge") == 2) itemStack.getTag().putInt("charge", 4);
                    hasChangeCharge = true;
                } else if (getTier() == Tiers.DIAMOND) {
                    if(itemStack.getTag().getInt("charge") == 4) itemStack.getTag().putInt("charge", 3);
                    else if(itemStack.getTag().getInt("charge") == 3) itemStack.getTag().putInt("charge", 2);
                    else if(itemStack.getTag().getInt("charge") == 2) itemStack.getTag().putInt("charge", 1);
                    else if(itemStack.getTag().getInt("charge") == 1) itemStack.getTag().putInt("charge", 4);
                    hasChangeCharge = true;
                } else if (getTier() == Tiers.NETHERITE) {
                    if(itemStack.getTag().getInt("charge") == 4) itemStack.getTag().putInt("charge", 3);
                    else if(itemStack.getTag().getInt("charge") == 3) itemStack.getTag().putInt("charge", 2);
                    else if(itemStack.getTag().getInt("charge") == 2) itemStack.getTag().putInt("charge", 1);
                    else if(itemStack.getTag().getInt("charge") == 1) itemStack.getTag().putInt("charge", 0);
                    else if(itemStack.getTag().getInt("charge") == 0) itemStack.getTag().putInt("charge", 4);
                    hasChangeCharge = true;
                }
                isSneaking = true;
            } else if (!context.getLevel().getServer().getPlayerList().getPlayer(context.getPlayer().getUUID()).isShiftKeyDown() && isSneaking) {
                isSneaking = false;
            }
            if (hasChangeCharge) {
                if(itemStack.getTag().getInt("charge") == 4)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (1x1)"));
                if(itemStack.getTag().getInt("charge") == 3)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (1x3)"));
                if(itemStack.getTag().getInt("charge") == 2)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (1x5)"));
                if(itemStack.getTag().getInt("charge") == 1)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (3x3)"));
                if(itemStack.getTag().getInt("charge") == 0)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (3x6)"));
                hasChangeCharge = false;
            }

            if (canPreformAction(block, blockAbove) && !isSneaking) {
                if(itemStack.getTag().getInt("charge") == 4) {
                    context.getLevel().setBlock(pos, HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 0), 2);
                }
                else if(itemStack.getTag().getInt("charge") == 3) {
                    for (int x = 0; x < 3; x++){
                        if(canPreformAction(
                                context.getLevel().getBlockState(pos.relative(playerDirection, x)).getBlock(),
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).above()).getBlock()))
                            context.getLevel().setBlock(pos.relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 0), 2);
                    }
                }
                else if(itemStack.getTag().getInt("charge") == 2) {
                    for (int x = 0; x < 5; x++){
                        if(canPreformAction(
                                context.getLevel().getBlockState(pos.relative(playerDirection, x)).getBlock(),
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).above()).getBlock()))
                            context.getLevel().setBlock(pos.relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 0), 2);
                    }
                }
                else if(itemStack.getTag().getInt("charge") == 1) {
                    for (int x = 0; x < 3; x++){
                        if(canPreformAction(
                                context.getLevel().getBlockState(pos.relative(playerDirection, x)).getBlock(),
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).above()).getBlock()))
                            context.getLevel().setBlock(pos.relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 0), 2);//action
                        if(canPreformAction(
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getClockWise())).getBlock(),
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getClockWise()).above()).getBlock()))
                            context.getLevel().setBlock(pos.relative(playerDirection, x).relative(playerDirection.getClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 0), 2);//action
                        if(canPreformAction(
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getBlock(),
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise()).above()).getBlock()))
                            context.getLevel().setBlock(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 0), 2);//action

                    }
                }
                else if(itemStack.getTag().getInt("charge") == 0) {
                    for (int x = 0; x < 6; x++){
                        if(canPreformAction(
                                context.getLevel().getBlockState(pos.relative(playerDirection, x)).getBlock(),
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).above()).getBlock()))
                            context.getLevel().setBlock(pos.relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 0), 2);//action
                        if(canPreformAction(
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getClockWise())).getBlock(),
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getClockWise()).above()).getBlock()))
                            context.getLevel().setBlock(pos.relative(playerDirection, x).relative(playerDirection.getClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 0), 2);//action
                        if(canPreformAction(
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getBlock(),
                                context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise()).above()).getBlock()))
                            context.getLevel().setBlock(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 0), 2);//action

                    }
                }
                return InteractionResult.SUCCESS;
            }else{
                return InteractionResult.FAIL;
            }

        }
        else{
            if (context.getPlayer().isShiftKeyDown()){isSneaking = true;}
            else if (!context.getPlayer().isShiftKeyDown() && isSneaking){
                isSneaking = false;
            }
            if (canPreformAction(block, blockAbove) && !isSneaking) {
                context.getLevel().playSound(context.getPlayer(), pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }else{
                return InteractionResult.FAIL;
            }
        }
    }

    private boolean canPreformAction(Block block, Block blockAbove){
        boolean canTill =
                Blocks.DIRT.equals(block) || Blocks.GRASS_BLOCK.equals(block) || Blocks.MOSS_BLOCK.equals(block);

        boolean notBlockedBy =
                Blocks.AIR.equals(blockAbove) || Blocks.DANDELION.equals(blockAbove) || Blocks.POPPY.equals(blockAbove) ||
                        Blocks.BLUE_ORCHID.equals(blockAbove) || Blocks.ALLIUM.equals(blockAbove) || Blocks.AZURE_BLUET.equals(blockAbove) ||
                        Blocks.RED_TULIP.equals(blockAbove) || Blocks.ORANGE_TULIP.equals(blockAbove) || Blocks.WHITE_TULIP.equals(blockAbove) ||
                        Blocks.PINK_TULIP.equals(blockAbove) || Blocks.OXEYE_DAISY.equals(blockAbove) || Blocks.CORNFLOWER.equals(blockAbove) ||
                        Blocks.LILY_OF_THE_VALLEY.equals(blockAbove) || Blocks.WITHER_ROSE.equals(blockAbove) || Blocks.LILAC.equals(blockAbove) ||
                        Blocks.ROSE_BUSH.equals(blockAbove) || Blocks.PEONY.equals(blockAbove) || Blocks.FERN.equals(blockAbove) ||
                        Blocks.LARGE_FERN.equals(blockAbove) || Blocks.GRASS.equals(blockAbove) || Blocks.TALL_GRASS.equals(blockAbove) ||
                        Blocks.DEAD_BUSH.equals(blockAbove);
        return canTill && notBlockedBy;
    }
}
