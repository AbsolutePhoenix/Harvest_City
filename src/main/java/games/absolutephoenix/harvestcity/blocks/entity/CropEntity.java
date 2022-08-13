package games.absolutephoenix.harvestcity.blocks.entity;

import games.absolutephoenix.harvestcity.blocks.FarmlandBlock;
import games.absolutephoenix.harvestcity.reference.ModReference;
import games.absolutephoenix.harvestcity.registry.HCBlockEntities;
import games.absolutephoenix.harvestcity.registry.HCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Objects;

public class CropEntity extends BlockEntity {

    int days;
    int ageDays;
    int regrowthDays;

    public CropEntity(BlockPos pos, BlockState state) {
        super(HCBlockEntities.CROP_ENTITY.get(), pos, state);
        days = ModReference.calendarSaveData.days;
        ageDays = 0;
        regrowthDays = 0;
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("days", this.days);
        pTag.putInt("age_days", this.ageDays);
        pTag.putInt("regrowth_days", this.regrowthDays);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.days = pTag.getInt("days");
        this.ageDays = pTag.getInt("age_days");
        this.regrowthDays = pTag.getInt("regrowth_days");
    }

    int ticks = 0;

    public void tick(Level level, BlockPos pos, BlockState state, int[] stages, String[] seasons, int regrowthDays, int maxAge, IntegerProperty age) {

        if (Objects.requireNonNull(level.getServer()).overworld().isLoaded(pos)) {
            boolean canBePlanted = false;

            if (ticks == 20) {
                for (int x = 0; x < 15; x++) {
                    if (level.getBlockState(pos.above(x)).getBlock().equals(HCBlocks.GREENHOUSE_GLASS.get())) {
                        canBePlanted = true;
                        break;
                    }
                }
                for (String season : seasons) {
                    if (season.equals(ModReference.calendarSaveData.season)) {
                        canBePlanted = true;
                        break;
                    }
                }
                if (!canBePlanted) {
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    level.setBlock(pos, HCBlocks.WITHERED_CROP.get().defaultBlockState(), 2);
                }
                ticks = 0;
            } else {
                ticks++;
            }
            if (days != ModReference.calendarSaveData.days) {
                if (level.getBlockState(pos.below()).getValue(FarmlandBlock.MOISTURE) == 7) {
                    if (state.getValue(age) != maxAge) {
                        if (state.getValue(age) > maxAge) {
                            if (this.regrowthDays == regrowthDays) {
                                level.setBlock(pos, state.setValue(age, state.getValue(age) - 1), 2);
                                this.regrowthDays = 0;
                            } else {
                                this.regrowthDays = this.regrowthDays + 1;
                            }
                            days = ModReference.calendarSaveData.days;
                        } else if (ageDays == stages[state.getValue(age)]) {
                            level.setBlock(pos, state.setValue(age, state.getValue(age) + 1), 2);
                            ageDays = 0;
                            days = ModReference.calendarSaveData.days;
                        } else {
                            ageDays = ageDays + 1;
                            days = ModReference.calendarSaveData.days;
                        }
                    }
                } else {
                    days = ModReference.calendarSaveData.days;
                }
            }

        }
    }
}