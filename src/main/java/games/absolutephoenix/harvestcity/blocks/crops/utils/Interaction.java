package games.absolutephoenix.harvestcity.blocks.crops.utils;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class Interaction {
    public static void onInteract(PlayerInteractEvent.RightClickBlock event){
            if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BaseCropBlock){
                ((BaseCropBlock) event.getWorld().getBlockState(event.getPos()).getBlock()).onRightClick(event);
            }
    }
}