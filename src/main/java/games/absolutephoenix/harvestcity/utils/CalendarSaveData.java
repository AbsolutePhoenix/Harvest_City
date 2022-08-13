package games.absolutephoenix.harvestcity.utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;


public class CalendarSaveData extends SavedData{

    public int days = 0;
    public String season = "spring";
    public int year = 0;
    public CalendarSaveData create(){
        return this;
    }

    public CalendarSaveData load(CompoundTag tag){
        CalendarSaveData data = create();
        days = tag.getInt("days");
        season = tag.getString("season");
        year = tag.getInt("year");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("days", days);
        tag.putString("season", season);
        tag.putInt("year", year);
        return tag;
    }

    @Override
    public void setDirty() {
        super.setDirty();
    }
}
