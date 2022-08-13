package games.absolutephoenix.harvestcity.handlers;

import games.absolutephoenix.harvestcity.config.HCCommonConfigs;
import games.absolutephoenix.harvestcity.reference.ModReference;
import games.absolutephoenix.harvestcity.registry.HCItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;

import java.util.List;
import java.util.Objects;


public class DayCycleHandler {

    static boolean dateHasTicked = false;
    static boolean maxNightReached = false;

    public static void onSleepFinished (SleepFinishedTimeEvent event){
        if (event.getWorld() instanceof ServerLevel) {
            List<ServerPlayer> players = event.getWorld().getServer().getPlayerList().getPlayers();
            for (ServerPlayer player : players) {
                player.sendMessage(new TextComponent("Wakey, wakey, rise and shine! Its breakfast time!"), player.getUUID());
            }
        }



    }

    static int passedTicks = 0;
    public static void onServerTick(TickEvent.WorldTickEvent event) {

        if(passedTicks == 20){
            List<ServerPlayer> players = Objects.requireNonNull(event.world.getServer()).getPlayerList().getPlayers();

            for (ServerPlayer player : players) {
                for (int x = 0; x < 28; x++) {
                    if (player.getInventory().getItem(x).getItem().equals(HCItems.SEASONS_CLOCK.get())) {
                        ItemStack seasonsClock = player.getInventory().getItem(x);
                        if (ModReference.calendarSaveData.season.equals("spring")) {
                            CompoundTag compoundTag = new CompoundTag();
                            compoundTag.putInt("CustomModelData", 1);
                            seasonsClock.setTag(compoundTag);
                        }
                        if (ModReference.calendarSaveData.season.equals("summer")) {
                            CompoundTag compoundTag = new CompoundTag();
                            compoundTag.putInt("CustomModelData", 2);
                            seasonsClock.setTag(compoundTag);
                        }
                        if (ModReference.calendarSaveData.season.equals("fall")) {
                            CompoundTag compoundTag = new CompoundTag();
                            compoundTag.putInt("CustomModelData", 3);
                            seasonsClock.setTag(compoundTag);
                        }
                        if (ModReference.calendarSaveData.season.equals("winter")) {
                            CompoundTag compoundTag = new CompoundTag();
                            compoundTag.putInt("CustomModelData", 4);
                            seasonsClock.setTag(compoundTag);
                        }
                    }
                }
            }
            passedTicks = 0;
        }else {
            passedTicks++;
        }

        if (event.world.getServer().overworld().getTimeOfDay(0) == ModReference.Morning && !dateHasTicked) {
            if (event.world instanceof ServerLevel) {
                System.out.println("date changed");
                if (ModReference.calendarSaveData.days == HCCommonConfigs.DAYS_IN_MONTH.get()) {
                    ModReference.calendarSaveData.days = 1;

                    switch (ModReference.calendarSaveData.season) {
                        case "spring" -> ModReference.calendarSaveData.season = "summer";
                        case "summer" -> ModReference.calendarSaveData.season = "fall";
                        case "fall" -> ModReference.calendarSaveData.season = "winter";
                        case "winter" -> {
                            ModReference.calendarSaveData.season = "spring";
                            ModReference.calendarSaveData.year++;
                        }
                        default -> ModReference.calendarSaveData.season = "spring";
                    }
                } else {
                    ModReference.calendarSaveData.days++;
                }
                ModReference.calendarSaveData.setDirty();
                System.out.println(ModReference.calendarSaveData.season + " " + ModReference.calendarSaveData.days + ", " + ModReference.calendarSaveData.year);
                dateHasTicked = true;
            }
        }

        if (event.world.getServer().overworld().getTimeOfDay(0) == 0.78460425F && dateHasTicked) {
            List<ServerPlayer> players = event.world.getServer().getPlayerList().getPlayers();
            if(!HCCommonConfigs.SKIP_PASSOUT.get()) {
                for (ServerPlayer player : players)
                    player.stopSleeping();
            }
            dateHasTicked = false;
        }

        //region PASSOUT
        if (!HCCommonConfigs.SKIP_PASSOUT.get()) {
            if (event.world.getServer().overworld().getTimeOfDay(0) == 0.53162503F) {
                List<ServerPlayer> players = event.world.getServer().getPlayerList().getPlayers();
                for (ServerPlayer player : players)
                    player.addEffect(new MobEffectInstance(MobEffect.byId(MobEffect.getId(MobEffects.BLINDNESS)), 100));
            }
            if (event.world.getServer().overworld().getTimeOfDay(0) == 0.5344967F && !maxNightReached) {
                System.out.println("executing");
                List<ServerPlayer> players = event.world.getServer().getPlayerList().getPlayers();
                for (ServerPlayer player : players)
                    player.startSleeping(player.getRespawnPosition());
                maxNightReached = true;
            }
            if (event.world.getServer().overworld().getTimeOfDay(0) == 0.5345462F && maxNightReached) {
                event.world.getServer().overworld().setDayTime(24000);
                maxNightReached = false;
            }
        }
        //endregion

    }
}
