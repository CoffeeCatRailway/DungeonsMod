package io.github.tastac.dungeonsmod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

/**
 * @author CoffeeCatRailway
 * Created: 6/04/2020
 */
public class SideTableBlock extends ModBlock implements IWaterLoggable {

    private static final VoxelShape SHAPE = createShape();

    public SideTableBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateContainer) {
        stateContainer.add(WATERLOGGED);
    }

    private static VoxelShape createShape() {
        VoxelShape top = VoxelShapes.create(0.0d, 10.0d / 16.0d, 0.0d, 16.0d / 16.0d, 14.0d / 16.0d, 16.0d / 16.0d);
        VoxelShape leg_1 = VoxelShapes.combineAndSimplify(top, VoxelShapes.create(1.0d / 16.0d, 0.0d, 1.0d / 16.0d, 4.0d / 16.0d, 10.0d / 16.0d, 4.0d / 16.0d), IBooleanFunction.OR);
        VoxelShape leg_2 = VoxelShapes.combineAndSimplify(leg_1, VoxelShapes.create(15.0d / 16.0d, 0.0d, 1.0d / 16.0d, 12.0d / 16.0d, 10.0d / 16.0d, 4.0d / 16.0d), IBooleanFunction.OR);
        VoxelShape leg_3 = VoxelShapes.combineAndSimplify(leg_2, VoxelShapes.create(15.0d / 16.0d, 0.0d, 15.0d / 16.0d, 12.0d / 16.0d, 10.0d / 16.0d, 12.0d / 16.0d), IBooleanFunction.OR);
        return VoxelShapes.combineAndSimplify(leg_3, VoxelShapes.create(1.0d / 16.0d, 0.0d, 15.0d / 16.0d, 4.0d / 16.0d, 10.0d / 16.0d, 12.0d / 16.0d), IBooleanFunction.OR);
    }
}
