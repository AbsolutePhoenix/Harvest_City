package games.absolutephoenix.harvestcity.items;

import games.absolutephoenix.harvestcity.blocks.crops.utils.BaseCropBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeedItem extends ItemNameBlockItem {
    private final String[] seasons;
    private final int daysToGrow;
    private final int regrowthDays;
    public SeedItem(BaseCropBlock block, Properties properties) {
        super(block, properties);
        seasons = block.getSeasons();
        daysToGrow = block.getTotalDaysToGrow();
        regrowthDays = block.getRegrowthDays();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(new TranslatableComponent("tooltip.harvestcity.plantedIn"));
        for(String season : seasons) {
            if (season.equals("spring"))
                pTooltip.add(new TranslatableComponent("tooltip.harvestcity.spring"));
            if (season.equals("summer"))
                pTooltip.add(new TranslatableComponent("tooltip.harvestcity.summer"));
            if (season.equals("fall"))
                pTooltip.add(new TranslatableComponent("tooltip.harvestcity.fall"));
            if (season.equals("winter"))
                pTooltip.add(new TranslatableComponent("tooltip.harvestcity.winter"));
        }
        pTooltip.add(new TranslatableComponent("tooltip.harvestcity.startTotalDays").append(new TextComponent(daysToGrow + "").append(new TranslatableComponent("tooltip.harvestcity.endTotalDays"))));
        if(regrowthDays > 0){
            pTooltip.add(new TranslatableComponent("tooltip.harvestcity.startRegrowthDays").append(new TextComponent(regrowthDays + "").append(new TranslatableComponent("tooltip.harvestcity.endRegrowthDays"))));
        }
    }
}
