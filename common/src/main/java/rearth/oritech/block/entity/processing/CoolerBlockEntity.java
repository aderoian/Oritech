package rearth.oritech.block.entity.processing;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.Oritech;
import rearth.oritech.block.base.entity.MachineBlockEntity;
import rearth.oritech.block.base.entity.MultiblockMachineEntity;
import rearth.oritech.client.init.ModScreens;
import rearth.oritech.client.init.ParticleContent;
import rearth.oritech.init.BlockEntitiesContent;
import rearth.oritech.init.recipes.OritechRecipe;
import rearth.oritech.init.recipes.OritechRecipeType;
import rearth.oritech.init.recipes.RecipeContent;
import rearth.oritech.network.NetworkContent;
import rearth.oritech.util.FluidProvider;
import rearth.oritech.util.InventorySlotAssignment;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CoolerBlockEntity extends MultiblockMachineEntity implements FluidProvider {
    
    private boolean inColdArea;
    private boolean initialized = false;
    
    public final SingleVariantStorage<FluidVariant> inputTank = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }
        
        @Override
        protected long getCapacity(FluidVariant variant) {
            return (4 * FluidConstants.BUCKET);
        }
        
        @Override
        public boolean supportsExtraction() {
            return false;
        }
        
        @Override
        protected void onFinalCommit() {
            super.onFinalCommit();
            CoolerBlockEntity.this.markDirty();
        }
    };
    
    public CoolerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntitiesContent.COOLER_ENTITY, pos, state, Oritech.CONFIG.processingMachines.coolerData.energyPerTick());
    }
    
    @Override
    public void tick(World world, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
        
        if (!world.isClient && !initialized) {
            initialized = true;
            var biome = world.getBiome(pos);
            inColdArea = biome.isIn(ConventionalBiomeTags.IS_COLD);
        }
        
    }
    
    @Override
    public AddonUiData getUiData() {
        var base = super.getUiData();
        if (!inColdArea) return base;
        
        return new AddonUiData(base.positions(), base.openSlots(), base.efficiency() * 0.5f, base.speed() * 0.5f, base.ownPosition());
    }
    
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        
        var inNbt = new NbtCompound();
        SingleVariantStorage.writeNbt(inputTank, FluidVariant.CODEC, inNbt, registryLookup);
        nbt.put("inputStorage", inNbt);
    }
    
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        
        var inNbt = nbt.getCompound("inputStorage");
        SingleVariantStorage.readNbt(inputTank, FluidVariant.CODEC, FluidVariant::blank, inNbt, registryLookup);
    }
    
    @Override
    protected void sendNetworkEntry() {
        super.sendNetworkEntry();
        NetworkContent.MACHINE_CHANNEL.serverHandle(this).send(new NetworkContent.SingleVariantFluidSyncPacket(pos, Registries.FLUID.getId(inputTank.variant.getFluid()).toString(), inputTank.amount));
    }
    
    @Override
    protected void useEnergy() {
        super.useEnergy();
        
        var progress = getProgress();
        if (progress < 0.35 || progress > 0.65) return;
        
        if (world.random.nextFloat() > 0.4) return;
        // emit particles
        var emitPosition = Vec3d.ofCenter(pos);
        
        ParticleContent.COOLER_WORKING.spawn(world, emitPosition, 2);
        
    }
    
    @Override
    protected Optional<RecipeEntry<OritechRecipe>> getRecipe() {
        
        // get recipes matching input items
        var candidates = Objects.requireNonNull(world).getRecipeManager().getAllMatches(getOwnRecipeType(), getInputInventory(), world);
        // filter out recipes based on input tank
        var fluidRecipe = candidates.stream().filter(candidate -> CentrifugeBlockEntity.recipeMatchesTank(inputTank, candidate.value())).findAny();
        if (fluidRecipe.isPresent()) {
            return fluidRecipe;
        }
        
        return super.getRecipe();
    }
    
    @Override
    protected boolean canProceed(OritechRecipe value) {
        return super.canProceed(value) && CentrifugeBlockEntity.recipeMatchesTank(inputTank, value);
    }
    
    @Override
    protected void craftItem(OritechRecipe activeRecipe, List<ItemStack> outputInventory, List<ItemStack> inputInventory) {
        super.craftItem(activeRecipe, outputInventory, inputInventory);
        
        var input = activeRecipe.getFluidInput();
        
        try (var tx = Transaction.openOuter()) {
            if (input != null && input.getAmount() > 0)
                inputTank.extract(FluidVariant.of(input.getFluid()), input.getAmount(), tx);
            tx.commit();
        }
        
    }
    
    @Override
    public float getSpeedMultiplier() {
        var biomeBonus = inColdArea ? 0.5f : 1f;
        return super.getSpeedMultiplier() * biomeBonus;
    }
    
    @Override
    public float getEfficiencyMultiplier() {
        var biomeBonus = inColdArea ? 0.5f : 1f;
        return super.getEfficiencyMultiplier() * biomeBonus;
    }
    
    @Override
    public long getDefaultCapacity() {
        return Oritech.CONFIG.processingMachines.coolerData.energyCapacity();
    }
    
    @Override
    public long getDefaultInsertRate() {
        return Oritech.CONFIG.processingMachines.coolerData.maxEnergyInsertion();
    }
    
    @Override
    protected OritechRecipeType getOwnRecipeType() {
        return RecipeContent.COOLER;
    }
    
    @Override
    public InventorySlotAssignment getSlots() {
        return new InventorySlotAssignment(0, 0, 0, 1);
    }
    
    @Override
    public List<GuiSlot> getGuiSlots() {
        return List.of(
          new GuiSlot(0, 117, 36, true));
    }
    
    @Override
    public ScreenHandlerType<?> getScreenHandlerType() {
        return ModScreens.COOLER_SCREEN;
    }
    
    @Override
    public int getInventorySize() {
        return 1;
    }
    
    @Override
    public List<Vec3i> getCorePositions() {
        return List.of(
          new Vec3i(0, 0,-1)
        );
    }
    
    @Override
    public List<Vec3i> getAddonSlots() {
        
        return List.of(
          new Vec3i(0, 0,-2)
        );
    }
    
    @Override
    public Storage<FluidVariant> getFluidStorage(Direction direction) {
        return inputTank;
    }
    
    @Override
    public @Nullable SingleVariantStorage<FluidVariant> getForDirectFluidAccess() {
        return inputTank;
    }
}
