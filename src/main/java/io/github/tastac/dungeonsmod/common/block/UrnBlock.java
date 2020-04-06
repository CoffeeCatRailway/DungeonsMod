package io.github.tastac.dungeonsmod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ocelot
 */
public class UrnBlock extends ModBlock implements IWaterLoggable
{
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape TOP_SHAPE = generateShape(-16, true, true);
    private static final VoxelShape BOTTOM_SHAPE = generateShape(0, true, true);
    private static final VoxelShape TOP_COLLISION_SHAPE = generateShape(-16, true, false);
    private static final VoxelShape BOTTOM_COLLISION_SHAPE = generateShape(0, false, true);

    public UrnBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? BOTTOM_SHAPE : TOP_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? BOTTOM_COLLISION_SHAPE : TOP_COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? BOTTOM_COLLISION_SHAPE : TOP_COLLISION_SHAPE;
    }

    public void placeAt(IWorld world, BlockPos pos, Direction facing, int flags)
    {
        world.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(HORIZONTAL_FACING, facing).with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER), flags);
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(HORIZONTAL_FACING, facing).with(WATERLOGGED, world.getFluidState(pos.up()).getFluid() == Fluids.WATER), flags);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        DoubleBlockHalf doubleblockhalf = stateIn.get(HALF);
        if (facing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.get(HALF) != doubleblockhalf)
        {
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
        else
        {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockPos blockpos = context.getPos();
        return blockpos.getY() < context.getWorld().getDimension().getHeight() - 1 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context) ? super.getStateForPlacement(context) : null;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()).with(WATERLOGGED, world.getFluidState(pos.up()).getFluid() == Fluids.WATER), 3);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
    {
        if (state.get(HALF) != DoubleBlockHalf.UPPER)
        {
            return super.isValidPosition(state, world, pos);
        }
        else
        {
            BlockState blockstate = world.getBlockState(pos.down());
            if (state.getBlock() != this)
                return super.isValidPosition(state, world, pos); // Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return blockstate.getBlock() == this && blockstate.get(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        super.harvestBlock(world, player, pos, Blocks.AIR.getDefaultState(), te, stack);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        DoubleBlockHalf doubleblockhalf = state.get(HALF);
        BlockPos blockpos = doubleblockhalf == DoubleBlockHalf.LOWER ? pos.up() : pos.down();
        BlockState blockstate = world.getBlockState(blockpos);
        if (blockstate.getBlock() == this && blockstate.get(HALF) != doubleblockhalf)
        {
            world.setBlockState(blockpos, blockstate.get(WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState(), 35);
            world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            if (!world.isRemote() && !player.isCreative())
            {
                spawnDrops(state, world, pos, null, player, player.getHeldItemMainhand());
                spawnDrops(blockstate, world, blockpos, null, player, player.getHeldItemMainhand());
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HALF, HORIZONTAL_FACING, WATERLOGGED);
    }

    private static VoxelShape generateShape(float offset, boolean top, boolean bottom)
    {
        List<VoxelShape> shapes = new ArrayList<>();

        if (top)
        {
            shapes.add(makeCuboidShape(0, 16 + offset, 0, 16, offset + 18, 16)); // BASE TOP
            shapes.add(makeCuboidShape(3, 18 + offset, 3, 13, 26 + offset, 13)); // TOP
        }

        if (bottom)
        {
            shapes.add(makeCuboidShape(0, offset, 0, 16, 16 + offset, 16)); // BASE
        }

        //        shapes.add(makeCuboidShape(0, offset, 0, 16, 18 + offset, 16)); // BASE
        //        shapes.add(makeCuboidShape(3, 18 + offset, 3, 13, 26 + offset, 13)); // TOP

        //        shapes.add(VoxelShapes.create(0.188, 1.125 + offset/16f, 0.188, 0.812, 1.625 + offset/16f, 0.812)); // TOP_1

        //        shapes.add(VoxelShapes.create(0.188, 1.125 + offset, 0.188, 0.312, 1.625 + offset, 0.812)); // TOP_1
        //        shapes.add(VoxelShapes.create(0.312, 1.125 + offset, 0.688, 0.688, 1.625 + offset, 0.812)); // TOP_2
        //        shapes.add(VoxelShapes.create(0.688, 1.125 + offset, 0.188, 0.812, 1.625 + offset, 0.812)); // TOP_3
        //        shapes.add(VoxelShapes.create(0.312, 1.125 + offset, 0.188, 0.688, 1.625 + offset, 0.312)); // TOP_4

        VoxelShape result = VoxelShapes.empty();
        for (VoxelShape shape : shapes)
        {
            result = VoxelShapes.combine(result, shape, IBooleanFunction.OR);
        }
        return result.simplify();
    }
}
