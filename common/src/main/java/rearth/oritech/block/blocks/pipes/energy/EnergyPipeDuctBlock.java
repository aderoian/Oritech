package rearth.oritech.block.blocks.pipes.energy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import rearth.oritech.block.blocks.pipes.GenericPipeDuctBlock;
import rearth.oritech.block.entity.pipes.GenericPipeInterfaceEntity;
import rearth.oritech.init.BlockContent;

import static rearth.oritech.block.blocks.pipes.energy.EnergyPipeBlock.ENERGY_PIPE_DATA;

public class EnergyPipeDuctBlock extends GenericPipeDuctBlock {
	public EnergyPipeDuctBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockState getConnectionBlock() {
		return getNormalBlock();
	}

	@Override
	public BlockState getNormalBlock() {
		return BlockContent.ENERGY_PIPE_DUCT_BLOCK.getDefaultState();
	}

	@Override
	public String getPipeTypeName() {
		return "energy";
	}

	@Override
	public boolean connectToOwnBlockType(Block block) {
		return block instanceof EnergyPipeDuctBlock || block instanceof EnergyPipeBlock ||block instanceof EnergyPipeConnectionBlock;
	}

	@Override
	public GenericPipeInterfaceEntity.PipeNetworkData getNetworkData(World world) {
		return ENERGY_PIPE_DATA.computeIfAbsent(world.getRegistryKey().getValue(), data -> new GenericPipeInterfaceEntity.PipeNetworkData());
	}
}
