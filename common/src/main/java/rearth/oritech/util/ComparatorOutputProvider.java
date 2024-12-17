package rearth.oritech.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.item.ItemStack;

public interface ComparatorOutputProvider {

	static int getItemStackComparatorOutput(ItemStack stack) {
		return (int) ((stack.getCount() / (float) stack.getMaxCount()) * 15);
	}

	static int getFluidStorageComparatorOutput(SingleVariantStorage<FluidVariant> storage) {
		return (int) ((storage.getAmount() / (float) storage.getCapacity()) * 15);
	}

	int getComparatorOutput();
}
