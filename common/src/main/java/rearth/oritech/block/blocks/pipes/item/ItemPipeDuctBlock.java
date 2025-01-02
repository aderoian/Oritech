package rearth.oritech.block.blocks.pipes.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import rearth.oritech.block.blocks.pipes.GenericPipeDuctBlock;
import rearth.oritech.block.entity.pipes.GenericPipeInterfaceEntity;
import rearth.oritech.init.BlockContent;

import static rearth.oritech.block.blocks.pipes.item.ItemPipeBlock.ITEM_PIPE_DATA;

public class ItemPipeDuctBlock extends GenericPipeDuctBlock {
	public ItemPipeDuctBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockState getConnectionBlock() {
		return getNormalBlock();
	}

	@Override
	public BlockState getNormalBlock() {
		return BlockContent.ITEM_PIPE_DUCT_BLOCK.getDefaultState();
	}

	@Override
	public String getPipeTypeName() {
		return "item";
	}

	@Override
	public boolean connectToOwnBlockType(Block block) {
		return block instanceof ItemPipeDuctBlock || block instanceof ItemPipeBlock || block instanceof ItemPipeConnectionBlock;
	}

	@Override
	public GenericPipeInterfaceEntity.PipeNetworkData getNetworkData(World world) {
		return ITEM_PIPE_DATA.computeIfAbsent(world.getRegistryKey().getValue(), data -> new GenericPipeInterfaceEntity.PipeNetworkData());
	}
}
