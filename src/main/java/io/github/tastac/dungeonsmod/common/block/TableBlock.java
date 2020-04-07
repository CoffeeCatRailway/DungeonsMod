package io.github.tastac.dungeonsmod.common.block;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ILightReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2020
 */
public class TableBlock extends ModBlock implements IWaterLoggable {

    public static final BooleanProperty HASCLOTH = BooleanProperty.create("hascloth");
    public static final EnumProperty<DyeColor> CLOTHCOLOR = EnumProperty.create("clothcolor", DyeColor.class);
    public static final EnumProperty<TablePart> PART = EnumProperty.create("part", TablePart.class);

    private static final VoxelShape TOP = VoxelShapes.create(0.0d, 13.0d / 16.0d, 0.0d, 1.0d, 1.0d, 1.0d);

    public TableBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(HORIZONTAL_FACING, Direction.NORTH)
                .with(WATERLOGGED, false)
                .with(HASCLOTH, false)
                .with(CLOTHCOLOR, DyeColor.WHITE)
                .with(PART, TablePart.SIDE_FRONT));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return TOP;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
        if (facing == getDirectionToOther(state.get(PART), state.get(HORIZONTAL_FACING))) {
            return facingState.getBlock() == this && facingState.get(PART) != state.get(PART) ? state.with(WATERLOGGED, facingState.get(WATERLOGGED)).with(HASCLOTH, facingState.get(HASCLOTH)).with(CLOTHCOLOR, facingState.get(CLOTHCOLOR)) : Blocks.AIR.getDefaultState();
        } else {
            return super.updatePostPlacement(state, facing, facingState, world, pos, facingPos);
        }
    }

    private static Direction getDirectionToOther(TablePart part, Direction direction) {
        return part != TablePart.SIDE_FRONT ? direction : direction.getOpposite();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos[] poses = getBlockPoses(context.getPlacementHorizontalFacing(), context.getPos());
        for (BlockPos pos : poses)
            if (!context.getWorld().getBlockState(pos).isReplaceable(context))
                return null;
        return super.getStateForPlacement(context);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockPos[] poses = getBlockPoses(placer.getHorizontalFacing(), pos);
        setBlockStateAtWorldPos(world, poses[0], state, placer, TablePart.SIDE_BACK);
        setBlockStateAtWorldPos(world, poses[1], state, placer, TablePart.CORNER_BACK_RIGHT);
        setBlockStateAtWorldPos(world, poses[2], state, placer, TablePart.CORNER_BACK_LEFT);
        setBlockStateAtWorldPos(world, poses[3], state, placer, TablePart.CORNER_FRONT_RIGHT);
        setBlockStateAtWorldPos(world, poses[4], state, placer, TablePart.CORNER_FRONT_LEFT);
    }

    private void setBlockStateAtWorldPos(World world, BlockPos pos, BlockState state, LivingEntity placer, TablePart part) {
        world.setBlockState(pos, this.getDefaultState()
                .with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite())
                .with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER)
                .with(HASCLOTH, state.get(HASCLOTH))
                .with(CLOTHCOLOR, state.get(CLOTHCOLOR))
                .with(PART, part), 3);
    }

    private BlockPos[] getBlockPoses(Direction facing, BlockPos pos) {
        BlockPos backPos = pos.offset(facing);
        BlockPos backRightPos = backPos.offset(facing.rotateY());
        BlockPos backLeftPos = backPos.offset(facing.rotateY().getOpposite());
        BlockPos frontRightPos = pos.offset(facing.rotateY());
        BlockPos frontLeftPos = pos.offset(facing.rotateY().getOpposite());
        return new BlockPos[]{backPos, backRightPos, backLeftPos, frontRightPos, frontLeftPos};
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            if (Block.getBlockFromItem(stack.getItem()) instanceof CarpetBlock && !state.get(HASCLOTH)) {
                CarpetBlock carpet = (CarpetBlock) Block.getBlockFromItem(stack.getItem());
                world.setBlockState(pos, state.with(HASCLOTH, true).with(CLOTHCOLOR, carpet.getColor()));
                if (!player.isCreative())
                    stack.shrink(1);
                return ActionResultType.CONSUME;
            }
            if (stack.getItem() == Items.SHEARS && state.get(HASCLOTH)) {
                world.setBlockState(pos, state.with(HASCLOTH, false));
                if (!player.isCreative()) {
                    ItemEntity itementity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), getCarpet(state.get(CLOTHCOLOR)));
                    itementity.setDefaultPickupDelay();
                    world.addEntity(itementity);
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateContainer) {
        stateContainer.add(HORIZONTAL_FACING, WATERLOGGED, HASCLOTH, CLOTHCOLOR, PART);
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

    public enum TablePart implements IStringSerializable {
        SIDE_FRONT("side_front"),
        SIDE_BACK("side_back"),
        CORNER_FRONT_LEFT("corner_front_left"),
        CORNER_FRONT_RIGHT("corner_front_right"),
        CORNER_BACK_LEFT("corner_back_left"),
        CORNER_BACK_RIGHT("corner_back_right");

        String name;

        TablePart(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
