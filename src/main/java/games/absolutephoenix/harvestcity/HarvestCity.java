package games.absolutephoenix.harvestcity;

import com.mojang.logging.LogUtils;
import games.absolutephoenix.harvestcity.blocks.crops.utils.Interaction;
import games.absolutephoenix.harvestcity.config.HCClientConfigs;
import games.absolutephoenix.harvestcity.config.HCCommonConfigs;
import games.absolutephoenix.harvestcity.handlers.DayCycleHandler;
import games.absolutephoenix.harvestcity.reference.ModReference;
import games.absolutephoenix.harvestcity.registry.HCBlockEntities;
import games.absolutephoenix.harvestcity.registry.HCBlocks;
import games.absolutephoenix.harvestcity.registry.HCItems;
import games.absolutephoenix.harvestcity.utils.CalendarSaveData;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ModReference.MOD_ID)
public class HarvestCity {

    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogUtils.getLogger();

    public HarvestCity() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        HCBlocks.register(eventBus);
        HCBlockEntities.register(eventBus);
        HCItems.register(eventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clinetSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HCClientConfigs.SPEC, "Harvest City/" + ModReference.MOD_ID + "-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HCCommonConfigs.SPEC, "Harvest City/" + ModReference.MOD_ID + "-common.toml");
        MinecraftForge.EVENT_BUS.addListener(this::onWorldLoaded);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldClose);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void clinetSetup(final FMLClientSetupEvent event) {
        HCBlocks.BLOCKS.getEntries().forEach(block -> {
            if (block.get().getDescriptionId().contains("_plant"))
                ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(HCBlocks.GREENHOUSE_GLASS.get(), RenderType.translucent());
        });
    }

    public void onWorldLoaded(WorldEvent.Load event) {
        System.out.println("loading");
        if (event.getWorld() instanceof ServerLevel) {
            ModReference.calendarSaveData = event.getWorld().getServer().overworld().getDataStorage().computeIfAbsent(new CalendarSaveData()::load, new CalendarSaveData()::create, ModReference.MOD_ID + "_seasons");
        }
    }

    public void onWorldClose(WorldEvent.Unload event){

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
    @SubscribeEvent
    public void onSleepFinished(SleepFinishedTimeEvent event) {
        DayCycleHandler.onSleepFinished(event);
    }
    @SubscribeEvent
    public void onSleep(SleepingTimeCheckEvent event) {
        event.setResult(Event.Result.ALLOW);
    }
    @SubscribeEvent
    public void onServerTick(TickEvent.WorldTickEvent event) {
        DayCycleHandler.onServerTick(event);
    }
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != event.getPlayer().getUsedItemHand()){
            Interaction.onInteract(event);
        }
    }

}
