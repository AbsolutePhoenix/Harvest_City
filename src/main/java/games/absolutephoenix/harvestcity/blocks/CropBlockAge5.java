package games.absolutephoenix.harvestcity.blocks;

import games.absolutephoenix.harvestcity.blocks.crops.utils.BaseCropBlock;
import games.absolutephoenix.harvestcity.blocks.entity.CropEntity;
import games.absolutephoenix.harvestcity.reference.HCCropReference;
import games.absolutephoenix.harvestcity.registry.HCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("deprecation")
public class CropBlockAge5 extends BaseCropBlock implements EntityBlock {
    private final RegistryObject<Item> product;

    public static int MAX_AGE = 5;
    public static IntegerProperty AGE = IntegerProperty.create("age", 0, 5);
    private final VoxelShape[] SHAPE_BY_AGE = HCCropReference.SHAPE_BY_AGE_5;

    private final int[] stages = new int[5];
    private final String[] seasons;
    private int totalDaysToGrow;

    private final boolean hasRegrowth;
    int regrowthDays;

    private CropBlockAge5(RegistryObject<Item> productItem, int[] stages, String[] seasons, int regrowthDays){
        super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.CROP).noOcclusion());
        for (int x = 0; x < MAX_AGE; x++) this.stages[x] = stages[x] - 1;
        for (int stage: stages){totalDaysToGrow = totalDaysToGrow + (stage);}
        hasRegrowth = regrowthDays != 0;
        this.regrowthDays = regrowthDays - 1;
        this.seasons = seasons;
        this.product = productItem;
    }
    public CropBlockAge5(RegistryObject<Item> productItem,  int[] stages, String[] seasons){
        this(productItem,  stages, seasons, 0);
    }
    public IntegerProperty getAgeProperty() {return AGE;}
    public int getMaxAge(){
        return MAX_AGE;
    }
    public boolean isHasRegrowth(){
        return hasRegrowth;
    }
    public int getRegrowthDays(){
        return regrowthDays;
    }
    public boolean isRandomlyTicking(BlockState pState) {return false;}
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[pState.getValue(this.getAgeProperty())];
    }
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(product.get());
    }
    public String[] getSeasons() {
        return seasons;
    }
    public int getTotalDaysToGrow() {
        return totalDaysToGrow;
    }
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState,BlockEntity pBlockEntity, ItemStack pTool) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return HCBlockEntities.CROP_ENTITY.get().create(pPos, pState);
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : (level, pos, state, blockEntity) -> ((CropEntity)blockEntity).tick(level, pos, state, stages, getSeasons(), regrowthDays, MAX_AGE, getAgeProperty());
    }

}
