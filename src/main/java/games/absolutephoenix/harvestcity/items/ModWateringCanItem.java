package games.absolutephoenix.harvestcity.items;

import games.absolutephoenix.harvestcity.blocks.FarmlandBlock;
import games.absolutephoenix.harvestcity.registry.HCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("DuplicatedCode")
public class ModWateringCanItem extends HoeItem {

    boolean hasChangeCharge = false;
    boolean isSneaking = false;

    public ModWateringCanItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Block block = context.getLevel().getBlockState(pos).getBlock();
        Block adjacentBlock = null;
        Direction playerDirection = context.getPlayer().getDirection();
        
        if (!context.getLevel().isClientSide) {
            ItemStack itemStack = context.getLevel().getServer().getPlayerList().getPlayer(context.getPlayer().getUUID()).getItemInHand(
                    context.getLevel().getServer().getPlayerList().getPlayer(context.getPlayer().getUUID()).getUsedItemHand());
            ServerPlayerGameMode gameMode = context.getLevel().getServer().getPlayerList().getPlayer(context.getPlayer().getUUID()).gameMode;
            if (!itemStack.getTag().contains("charge")) {
                itemStack.getTag().putInt("charge", 4);
                hasChangeCharge = true;
            }
            if (context.getLevel().getServer().getPlayerList().getPlayer(context.getPlayer().getUUID()).isShiftKeyDown()) {
                if (getTier() == Tiers.STONE) {
                    if (itemStack.getTag().getInt("charge") == 4) itemStack.getTag().putInt("charge", 3);
                    else if (itemStack.getTag().getInt("charge") == 3) itemStack.getTag().putInt("charge", 4);
                    hasChangeCharge = true;
                } else if (getTier() == Tiers.IRON) {
                    if (itemStack.getTag().getInt("charge") == 4) itemStack.getTag().putInt("charge", 3);
                    else if (itemStack.getTag().getInt("charge") == 3) itemStack.getTag().putInt("charge", 2);
                    else if (itemStack.getTag().getInt("charge") == 2) itemStack.getTag().putInt("charge", 4);
                    hasChangeCharge = true;
                } else if (getTier() == Tiers.DIAMOND) {
                    if (itemStack.getTag().getInt("charge") == 4) itemStack.getTag().putInt("charge", 3);
                    else if (itemStack.getTag().getInt("charge") == 3) itemStack.getTag().putInt("charge", 2);
                    else if (itemStack.getTag().getInt("charge") == 2) itemStack.getTag().putInt("charge", 1);
                    else if (itemStack.getTag().getInt("charge") == 1) itemStack.getTag().putInt("charge", 4);
                    hasChangeCharge = true;
                } else if (getTier() == Tiers.NETHERITE) {
                    if (itemStack.getTag().getInt("charge") == 4) itemStack.getTag().putInt("charge", 3);
                    else if (itemStack.getTag().getInt("charge") == 3) itemStack.getTag().putInt("charge", 2);
                    else if (itemStack.getTag().getInt("charge") == 2) itemStack.getTag().putInt("charge", 1);
                    else if (itemStack.getTag().getInt("charge") == 1) itemStack.getTag().putInt("charge", 0);
                    else if (itemStack.getTag().getInt("charge") == 0) itemStack.getTag().putInt("charge", 4);
                    hasChangeCharge = true;
                }
                isSneaking = true;
            }
            else if (!context.getLevel().getServer().getPlayerList().getPlayer(context.getPlayer().getUUID()).isShiftKeyDown() && isSneaking) {
                isSneaking = false;
            }
            if (hasChangeCharge) {
                if (itemStack.getTag().getInt("charge") == 4)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (1x1)"));
                if (itemStack.getTag().getInt("charge") == 3)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (1x3)"));
                if (itemStack.getTag().getInt("charge") == 2)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (1x5)"));
                if (itemStack.getTag().getInt("charge") == 1)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (3x3)"));
                if (itemStack.getTag().getInt("charge") == 0)
                    itemStack.setHoverName(new TextComponent(new TranslatableComponent(itemStack.getDescriptionId()).getString() + " (3x6)"));
                hasChangeCharge = false;
            }


            if (itemStack.getTag().getInt("charge") == 4 && !isSneaking) {
                if(context.getLevel().getBlockState(pos).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    if (
                            (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                    && context.getLevel().getBlockState(pos).getBlock().equals(HCBlocks.FARMLAND.get())
                                    && context.getLevel().getBlockState(pos).getValue(FarmlandBlock.MOISTURE) != 7) {
                        context.getLevel().setBlock(pos, HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                        if (!gameMode.isCreative())
                            itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                    }
                }
                else if(context.getLevel().getBlockState(pos.below()).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    if (
                            (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                    && context.getLevel().getBlockState(pos.below()).getBlock().equals(HCBlocks.FARMLAND.get())
                                    && context.getLevel().getBlockState(pos.below()).getValue(FarmlandBlock.MOISTURE) != 7) {
                        context.getLevel().setBlock(pos.below(), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                        if (!gameMode.isCreative())
                            itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                    }
                }
            }
            else if (itemStack.getTag().getInt("charge") == 3 && !isSneaking) {
                if(context.getLevel().getBlockState(pos).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    for(int x = 0; x < 3; x++)
                    {
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x)).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x)).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                    }
                }
                else if(context.getLevel().getBlockState(pos.below()).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    for(int x = 0; x < 3; x++)
                    {
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x)).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x)).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.below().relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                    }
                }
            }
            else if (itemStack.getTag().getInt("charge") == 2 && !isSneaking) {
                if(context.getLevel().getBlockState(pos).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    for(int x = 0; x < 5; x++)
                    {
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x)).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x)).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                    }
                }
                else if(context.getLevel().getBlockState(pos.below()).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    for(int x = 0; x < 5; x++)
                    {
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x)).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x)).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.below().relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                    }
                }

            }
            else if (itemStack.getTag().getInt("charge") == 1 && !isSneaking) {
                if(context.getLevel().getBlockState(pos).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    for(int x = 0; x < 3; x++) {

                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x)).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x)).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getClockWise())).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getClockWise())).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.relative(playerDirection, x).relative(playerDirection.getClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                    }
                }
                else if(context.getLevel().getBlockState(pos.below()).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    for(int x = 0; x < 3; x++) {

                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x)).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x)).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.below().relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x).relative(playerDirection.getClockWise())).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x).relative(playerDirection.getClockWise())).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.below().relative(playerDirection, x).relative(playerDirection.getClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.below().relative(playerDirection, x).relative(playerDirection.getCounterClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                    }
                }
            }
            else if (itemStack.getTag().getInt("charge") == 0 && !isSneaking) {
                if(context.getLevel().getBlockState(pos).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    for(int x = 0; x < 6; x++) {

                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x)).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x)).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getClockWise())).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getClockWise())).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.relative(playerDirection, x).relative(playerDirection.getClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.relative(playerDirection, x).relative(playerDirection.getCounterClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                    }
                }
                else if(context.getLevel().getBlockState(pos.below()).getBlock().equals(HCBlocks.FARMLAND.get())) {
                    for(int x = 0; x < 6; x++) {

                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x)).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x)).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.below().relative(playerDirection, x), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x).relative(playerDirection.getClockWise())).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x).relative(playerDirection.getClockWise())).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.below().relative(playerDirection, x).relative(playerDirection.getClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                        if (
                                (itemStack.getDamageValue() != itemStack.getMaxDamage())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getBlock().equals(HCBlocks.FARMLAND.get())
                                        && context.getLevel().getBlockState(pos.below().relative(playerDirection, x).relative(playerDirection.getCounterClockWise())).getValue(FarmlandBlock.MOISTURE) != 7) {
                            context.getLevel().setBlock(pos.below().relative(playerDirection, x).relative(playerDirection.getCounterClockWise()), HCBlocks.FARMLAND.get().defaultBlockState().setValue(FarmlandBlock.MOISTURE, 7), 2);
                            if (!gameMode.isCreative())
                                itemStack.setDamageValue(context.getItemInHand().getDamageValue() + 1);
                        }
                    }
                }
            }


            if (context.getClickedFace().getName().equals("north")) {
                adjacentBlock = context.getLevel().getBlockState(pos.north()).getBlock();
            } else if (context.getClickedFace().getName().equals("south")) {
                adjacentBlock = context.getLevel().getBlockState(pos.south()).getBlock();
            } else if (context.getClickedFace().getName().equals("east")) {
                adjacentBlock = context.getLevel().getBlockState(pos.east()).getBlock();
            } else if (context.getClickedFace().getName().equals("west")) {
                adjacentBlock = context.getLevel().getBlockState(pos.west()).getBlock();
            } else if (context.getClickedFace().getName().equals("up")) {
                adjacentBlock = context.getLevel().getBlockState(pos.above()).getBlock();
            } else if (context.getClickedFace().getName().equals("down")) {
                adjacentBlock = context.getLevel().getBlockState(pos.below()).getBlock();
            }
            if (Blocks.WATER.equals(adjacentBlock)) {
                if (!gameMode.isCreative())
                    itemStack.setDamageValue(0);
            }
            return InteractionResult.SUCCESS;

        } else {
            ItemStack itemStack = context.getPlayer().getItemInHand(context.getPlayer().getUsedItemHand());
            if (context.getPlayer().isShiftKeyDown()) {
                isSneaking = true;
            } else if (!context.getPlayer().isShiftKeyDown() && isSneaking) {
                isSneaking = false;
            }

            if ((itemStack.getDamageValue() != itemStack.getMaxDamage()) && !isSneaking) {
                if (HCBlocks.FARMLAND.get().equals(block)) {
                    context.getLevel().playSound(context.getPlayer(), pos, SoundEvents.AXOLOTL_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.SUCCESS;

            } else {
                return InteractionResult.FAIL;
            }
        }
    }
}
