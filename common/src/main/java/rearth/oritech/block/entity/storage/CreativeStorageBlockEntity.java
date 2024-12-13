package rearth.oritech.block.entity.storage;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import rearth.oritech.block.base.entity.ExpandableEnergyStorageBlockEntity;
import rearth.oritech.init.BlockEntitiesContent;
import rearth.oritech.util.ComparatorOutputProvider;

import java.util.List;

public class CreativeStorageBlockEntity extends ExpandableEnergyStorageBlockEntity implements ComparatorOutputProvider {

    public CreativeStorageBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntitiesContent.CREATIVE_STORAGE_ENTITY, pos, state);
    }

    @Override
    public List<Vec3i> getAddonSlots() {
        return List.of();
    }

    @Override
    public long getDefaultCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public long getDefaultInsertRate() {
        return Integer.MAX_VALUE;
    }

    @Override
    public long getDefaultExtractionRate() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getComparatorOutput() {
        if (energyStorage.amount == 0) return 0;
        return (int) (1 + ((energyStorage.amount / (float) energyStorage.capacity) * 14));
    }

    @Override
    public float getCoreQuality() {
        return 0;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, ExpandableEnergyStorageBlockEntity blockEntity) {
        energyStorage.amount = Integer.MAX_VALUE;
        super.tick(world, pos, state, blockEntity);
    }
}
