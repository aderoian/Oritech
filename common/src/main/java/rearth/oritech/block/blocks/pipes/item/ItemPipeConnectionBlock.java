package rearth.oritech.block.blocks.pipes.item;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.block.blocks.pipes.ExtractablePipeConnectionBlock;
import rearth.oritech.block.entity.pipes.GenericPipeInterfaceEntity;
import rearth.oritech.block.entity.pipes.ItemPipeInterfaceEntity;
import rearth.oritech.init.BlockContent;

import static rearth.oritech.block.blocks.pipes.item.ItemPipeBlock.ITEM_PIPE_DATA;

public class ItemPipeConnectionBlock extends ExtractablePipeConnectionBlock {

    public ItemPipeConnectionBlock(Settings settings) {
        super(settings);
    }


    @Override
    public TriFunction<World, BlockPos, Direction, Boolean> apiValidationFunction() {
        return ((world, pos, direction) -> ItemStorage.SIDED.find(world, pos, direction) != null);
    }
    
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemPipeInterfaceEntity(pos, state);
    }
    
    @Override
    public BlockState getConnectionBlock() {
        return BlockContent.ITEM_PIPE_CONNECTION.getDefaultState();
    }
    
    @Override
    public BlockState getNormalBlock() {
        return BlockContent.ITEM_PIPE.getDefaultState();
    }
    
    @Override
    public String getPipeTypeName() {
        return "item";
    }
    
    @Override
    public boolean connectToOwnBlockType(Block block) {
        return block instanceof ItemPipeBlock || block instanceof ItemPipeConnectionBlock || block instanceof ItemPipeDuctBlock;
    }
    
    @Override
    public GenericPipeInterfaceEntity.PipeNetworkData getNetworkData(World world) {
        return ITEM_PIPE_DATA.computeIfAbsent(world.getRegistryKey().getValue(), data -> new GenericPipeInterfaceEntity.PipeNetworkData());
    }

    public static class FramedItemPipeConnectionBlock extends ItemPipeConnectionBlock {

        public FramedItemPipeConnectionBlock(Settings settings) {
            super(settings);
        }

        @Override
        public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return VoxelShapes.fullCube();
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return state.getOutlineShape(world, pos);
        }

        @Override
        public BlockState getNormalBlock() {
            return BlockContent.FRAMED_ITEM_PIPE.getDefaultState();
        }

        @Override
        public BlockState getConnectionBlock() {
            return BlockContent.FRAMED_ITEM_PIPE_CONNECTION.getDefaultState();
        }
    }
}
