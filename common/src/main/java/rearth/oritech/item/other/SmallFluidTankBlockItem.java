package rearth.oritech.item.other;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class SmallFluidTankBlockItem extends BlockItem {
    public SmallFluidTankBlockItem(Block block, Settings settings) {
        super(block, settings);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        
        if (!stack.contains(DataComponentTypes.CUSTOM_DATA)) return;
        var nbt = stack.get(DataComponentTypes.CUSTOM_DATA).copyNbt();
        if (nbt.isEmpty() || !nbt.contains("variant") || !nbt.contains("amount")) return;
        
        var variant = Registries.FLUID.get(Identifier.of(nbt.getCompound("variant").getString("fluid")));
        var amount = nbt.getLong("amount") * 1000 / FluidConstants.BUCKET;
        tooltip.add(Text.translatable("tooltip.oritech.fluid_content", amount, amount <= 0
            ? Text.translatable("tooltip.oritech.fluid_empty")
            : FluidVariantAttributes.getName(FluidVariant.of(variant)).getString()));
    }
}
