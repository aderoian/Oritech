package rearth.oritech.block.blocks.machines.worldInteraction;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.block.base.block.FrameInteractionBlock;
import rearth.oritech.block.entity.machines.interaction.DestroyerBlockEntity;

public class DestroyerBlock extends FrameInteractionBlock {
    public DestroyerBlock(Settings settings) {
        super(settings);
    }
    
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DestroyerBlockEntity(pos, state);
    }
}
