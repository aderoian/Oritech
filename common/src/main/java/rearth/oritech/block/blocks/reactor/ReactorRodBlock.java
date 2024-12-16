package rearth.oritech.block.blocks.reactor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import rearth.oritech.init.BlockContent;

public class ReactorRodBlock extends BaseReactorBlock {
    
    private final int rodCount;
    private final int internalPulseCount;
    
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
