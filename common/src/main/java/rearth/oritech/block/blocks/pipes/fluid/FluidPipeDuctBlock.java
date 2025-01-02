package rearth.oritech.block.blocks.pipes.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import rearth.oritech.block.blocks.pipes.GenericPipeDuctBlock;
import rearth.oritech.block.entity.pipes.GenericPipeInterfaceEntity;
import rearth.oritech.init.BlockContent;

import static rearth.oritech.block.blocks.pipes.fluid.FluidPipeBlock.FLUID_PIPE_DATA;

public class FluidPipeDuctBlock extends GenericPipeDuctBlock {
	public FluidPipeDuctBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockState getConnectionBlock() {
		return getNormalBlock();
	}

	@Override
	public BlockState getNormalBlock() {
		return BlockContent.FLUID_PIPE_DUCT_BLOCK.getDefaultState();
	}

	@Override
	public String getPipeTypeName() {
		return "fluid";
	}

	@Override
	public boolean connectToOwnBlockType(Block block) {
		return block instanceof FluidPipeDuctBlock || block instanceof FluidPipeBlock || block instanceof FluidPipeConnectionBlock;
	}

	@Override
	public GenericPipeInterfaceEntity.PipeNetworkData getNetworkData(World world) {
		return FLUID_PIPE_DATA.computeIfAbsent(world.getRegistryKey().getValue(), data -> new GenericPipeInterfaceEntity.PipeNetworkData());
	}
}
