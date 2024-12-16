package rearth.oritech.block.blocks.reactor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.block.entity.reactor.ReactorEnergyPortEntity;

import java.util.Objects;

public class ReactorEnergyPortBlock extends BaseReactorBlock implements BlockEntityProvider {
    public ReactorEnergyPortBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(Properties.FACING, Direction.NORTH));
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.FACING);
    }
    
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(Properties.FACING, ctx.getPlayerLookDirection().getOpposite());
    }
    
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReactorEnergyPortEntity(pos, state);
    }
    
    @Override
    public boolean validForWalls() {
        return true;
    }
}
