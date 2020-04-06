package io.github.tastac.dungeonsmod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.ILightReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2020
 */
public class TableBlock extends ModBlock {

    public static final BooleanProperty HASCLOTH = BooleanProperty.create("hascloth");
    public static final EnumProperty<DyeColor> CLOTHCOLOR = EnumProperty.create("clothcolor", DyeColor.class);
    public static final EnumProperty<PlaceState> PLACESTATE = EnumProperty.create("placestate", PlaceState.class);

    public TableBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(HORIZONTAL_FACING, Direction.NORTH)
                .with(WATERLOGGED, false)
                .with(HASCLOTH, false)
                .with(CLOTHCOLOR, DyeColor.WHITE)
                .with(PLACESTATE, PlaceState.SIDE));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            if (!stack.isEmpty()) {
                if (Block.getBlockFromItem(stack.getItem()) instanceof CarpetBlock && !state.get(HASCLOTH)) {
                    CarpetBlock carpet = (CarpetBlock) Block.getBlockFromItem(stack.getItem());
                    world.setBlockState(pos, state.with(HASCLOTH, true).with(CLOTHCOLOR, carpet.getColor()));
                    if (!player.isCreative())
                        stack.shrink(1);
                } else if (stack.getItem() == Items.SHEARS && state.get(HASCLOTH)) {
                    world.setBlockState(pos, state.with(HASCLOTH, false));
                    if (!player.isCreative()) {
                        ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), getCarpet(state.get(CLOTHCOLOR)));
                        itementity.setDefaultPickupDelay();
                        world.addEntity(itementity);
                    }
                }
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateContainer) {
        stateContainer.add(HORIZONTAL_FACING, WATERLOGGED, HASCLOTH, CLOTHCOLOR, PLACESTATE);
    }

    public static DyeColor getColthColor(ILightReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof TableBlock)
            return state.get(CLOTHCOLOR);
        return DyeColor.WHITE;
    }

    private ItemStack getCarpet(DyeColor dyeColor) {
        switch (dyeColor) {
            default:
            case WHITE:
                return new ItemStack(Blocks.WHITE_CARPET);
            case ORANGE:
                return new ItemStack(Blocks.ORANGE_CARPET);
            case MAGENTA:
                return new ItemStack(Blocks.MAGENTA_CARPET);
            case LIGHT_BLUE:
                return new ItemStack(Blocks.LIGHT_BLUE_CARPET);
            case YELLOW:
                return new ItemStack(Blocks.YELLOW_CARPET);
            case LIME:
                return new ItemStack(Blocks.LIME_CARPET);
            case PINK:
                return new ItemStack(Blocks.PINK_CARPET);
            case GRAY:
                return new ItemStack(Blocks.GRAY_CARPET);
            case LIGHT_GRAY:
                return new ItemStack(Blocks.LIGHT_GRAY_CARPET);
            case CYAN:
                return new ItemStack(Blocks.CYAN_CARPET);
            case PURPLE:
                return new ItemStack(Blocks.PURPLE_CARPET);
            case BLUE:
                return new ItemStack(Blocks.BLUE_CARPET);
            case BROWN:
                return new ItemStack(Blocks.BROWN_CARPET);
            case GREEN:
                return new ItemStack(Blocks.GREEN_CARPET);
            case RED:
                return new ItemStack(Blocks.RED_CARPET);
            case BLACK:
                return new ItemStack(Blocks.BLACK_CARPET);
        }
    }

    public enum PlaceState implements IStringSerializable {
        CORNER_SHOW("corner_show"),
        CORNER_HIDE("corner_hide"),
        SIDE("side");

        String name;

        PlaceState(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
