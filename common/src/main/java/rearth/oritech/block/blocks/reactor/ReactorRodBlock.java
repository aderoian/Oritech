package rearth.oritech.block.blocks.reactor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import rearth.oritech.init.BlockContent;

public class ReactorRodBlock extends BaseReactorBlock {
    
    private final int rodCount;
    private final int internalPulseCount;
    
    private static final VoxelShape SOLO_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 16, 11);
    private static final VoxelShape DUO_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
    private static final VoxelShape QUAD_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);
    
    public ReactorRodBlock(Settings settings, int rodCount, int internalPulseCount) {
        super(settings);
        this.rodCount = rodCount;
        this.internalPulseCount = internalPulseCount;
        this.setDefaultState(getDefaultState().with(Properties.LIT, false));
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.LIT);
    }
    
    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (rodCount) {
            case 1 -> SOLO_SHAPE;
            case 2 -> DUO_SHAPE;
            case 4 -> QUAD_SHAPE;
            default -> SOLO_SHAPE;
        };
    }
    
    public int getRodCount() {
        return rodCount;
    }
    
    public int getInternalPulseCount() {
        return internalPulseCount;
    }
    
    @Override
    public Block requiredStackCeiling() {
        return BlockContent.REACTOR_FUEL_PORT;
    }
}
