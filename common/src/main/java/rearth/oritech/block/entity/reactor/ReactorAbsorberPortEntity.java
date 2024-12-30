package rearth.oritech.block.entity.reactor;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import rearth.oritech.client.init.ModScreens;
import rearth.oritech.client.init.ParticleContent;
import rearth.oritech.client.ui.BasicMachineScreenHandler;
import rearth.oritech.init.BlockEntitiesContent;
import rearth.oritech.init.TagContent;
import rearth.oritech.network.NetworkContent;
import rearth.oritech.util.*;

import java.util.List;

public class ReactorAbsorberPortEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ScreenProvider, InventoryProvider {
    
    private final SimpleSidedInventory inventory = new SimpleSidedInventory(1, new InventorySlotAssignment(0, 1, 1, 0));
    private final InventoryStorage inventoryStorage = InventoryStorage.of(inventory, null);
    
    public int availableFuel;
    public int currentFuelOriginalCapacity;
    
    public ReactorAbsorberPortEntity(BlockPos pos, BlockState state) {
        super(BlockEntitiesContent.REACTOR_ABSORBER_PORT_BLOCK_ENTITY, pos, state);
    }
    
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        
        nbt.putInt("available", availableFuel);
        nbt.putInt("capacity", currentFuelOriginalCapacity);
        
        Inventories.writeNbt(nbt, inventory.heldStacks, false, registryLookup);
    }
    
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        
        availableFuel = nbt.getInt("available");
        currentFuelOriginalCapacity = nbt.getInt("capacity");
        
        Inventories.readNbt(nbt, inventory.heldStacks, registryLookup);
    }
    
    public int getAvailableFuel() {
        if (availableFuel > 0) {
            return availableFuel;
        }
        
        // try consume item
        var inputStack = inventory.getStack(0);
        if (inputStack.isEmpty()) return 0;
        
        if (inputStack.isIn(TagContent.REACTOR_COOLANT)) {
            var capacity = 1000;
            currentFuelOriginalCapacity = capacity;
            availableFuel = capacity;
            inputStack.decrement(1);
            onFuelConsumed();
        }
        
        return availableFuel;
    }
    
    public void consumeFuel(int amount) {
        if (availableFuel >= amount) {
            availableFuel -= amount;
            
            if (world.getTime() % 5 == 0)
                ParticleContent.COOLER_WORKING.spawn(world, pos.toCenterPos().add(0, 0.5, 0), 1);
        }
        
    }
    
    private void onFuelConsumed() {
        ParticleContent.COOLER_WORKING.spawn(world, pos.toCenterPos().add(0, 0.5, 0), 15);
    }
    
    public void updateNetwork() {
        NetworkContent.MACHINE_CHANNEL.serverHandle(this).send(new NetworkContent.ReactorPortDataPacket(pos, currentFuelOriginalCapacity, availableFuel));
    }
    
    @Override
    public Object getScreenOpeningData(ServerPlayerEntity player) {
        return new ModScreens.BasicData(pos);
    }
    
    @Override
    public Text getDisplayName() {
        return Text.of("");
    }
    
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BasicMachineScreenHandler(syncId, playerInventory, this);
    }
    
    @Override
    public List<ScreenProvider.GuiSlot> getGuiSlots() {
        return List.of(new ScreenProvider.GuiSlot(0, 80, 35));
    }
    
    @Override
    public boolean showEnergy() {
        return false;
    }
    
    @Override
    public float getDisplayedEnergyUsage() {
        return 0;
    }
    
    @Override
    public float getProgress() {
        return 0;
    }
    
    @Override
    public boolean showProgress() {
        return false;
    }
    
    @Override
    public InventoryInputMode getInventoryInputMode() {
        return InventoryInputMode.FILL_LEFT_TO_RIGHT;
    }
    
    @Override
    public Inventory getDisplayedInventory() {
        return inventory;
    }
    
    @Override
    public ScreenHandlerType<?> getScreenHandlerType() {
        return ModScreens.FUEL_PORT_SCREEN;
    }
    
    @Override
    public boolean inputOptionsEnabled() {
        return false;
    }
    
    @Override
    public boolean showExpansionPanel() {
        return false;
    }
    
    @Override
    public InventoryStorage getInventory(Direction direction) {
        return inventoryStorage;
    }
}
