package rearth.oritech.fabricgen.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import rearth.oritech.init.BlockContent;
import rearth.oritech.init.TagContent;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    
    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        
        var pickaxeBuilder = getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE);
        
        for (var block : BlockContent.autoRegisteredDrops) {
            pickaxeBuilder.add(block);
        }
        pickaxeBuilder.add(BlockContent.ENERGY_PIPE_CONNECTION);
        pickaxeBuilder.add(BlockContent.SUPERCONDUCTOR_CONNECTION);
        pickaxeBuilder.add(BlockContent.FLUID_PIPE_CONNECTION);
        pickaxeBuilder.add(BlockContent.ITEM_PIPE_CONNECTION);
        pickaxeBuilder.add(BlockContent.FRAMED_ENERGY_PIPE_CONNECTION);
        pickaxeBuilder.add(BlockContent.FRAMED_SUPERCONDUCTOR_CONNECTION);
        pickaxeBuilder.add(BlockContent.FRAMED_FLUID_PIPE_CONNECTION);
        pickaxeBuilder.add(BlockContent.FRAMED_ITEM_PIPE_CONNECTION);
        pickaxeBuilder.add(BlockContent.SMALL_TANK_BLOCK);
        pickaxeBuilder.add(BlockContent.SMALL_STORAGE_BLOCK);
        pickaxeBuilder.add(BlockContent.PUMP_TRUNK_BLOCK);
        
        pickaxeBuilder
          .add(BlockContent.NICKEL_ORE)
          .add(BlockContent.DEEPSLATE_NICKEL_ORE)
          .add(BlockContent.DEEPSLATE_PLATINUM_ORE)
          .add(BlockContent.DEEPSLATE_URANIUM_ORE)
          .add(BlockContent.URANIUM_CRYSTAL)
          .add(BlockContent.ENDSTONE_PLATINUM_ORE);
        
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
          .add(BlockContent.ITEM_PIPE)
          .add(BlockContent.ITEM_PIPE_CONNECTION);
        
        getOrCreateTagBuilder(ConventionalBlockTags.ORES)
          .add(BlockContent.NICKEL_ORE)
          .add(BlockContent.DEEPSLATE_NICKEL_ORE)
          .add(BlockContent.DEEPSLATE_PLATINUM_ORE)
          .add(BlockContent.DEEPSLATE_URANIUM_ORE)
          .add(BlockContent.ENDSTONE_PLATINUM_ORE);
        
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
          .add(BlockContent.NICKEL_ORE)
          .add(BlockContent.DEEPSLATE_NICKEL_ORE);
        
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
          .add(BlockContent.DEEPSLATE_PLATINUM_ORE)
          .add(BlockContent.DEEPSLATE_URANIUM_ORE)
          .add(BlockContent.ENDSTONE_PLATINUM_ORE);
        
        getOrCreateTagBuilder(TagContent.NICKEL_ORE_BLOCKS)
          .add(BlockContent.NICKEL_ORE, BlockContent.DEEPSLATE_NICKEL_ORE);
        getOrCreateTagBuilder(TagContent.PLATINUM_ORE_BLOCKS)
          .add(BlockContent.DEEPSLATE_PLATINUM_ORE, BlockContent.ENDSTONE_PLATINUM_ORE);
        getOrCreateTagBuilder(TagContent.URANIUM_ORE_BLOCKS)
          .add(BlockContent.DEEPSLATE_URANIUM_ORE);
        
        getOrCreateTagBuilder(TagContent.DRILL_MINEABLE)
          .addOptionalTag(BlockTags.PICKAXE_MINEABLE)
          .addOptionalTag(BlockTags.SHOVEL_MINEABLE);
        
        getOrCreateTagBuilder(TagContent.RESOURCE_NODES)
          .add(BlockContent.RESOURCE_NODE_COPPER)
          .add(BlockContent.RESOURCE_NODE_IRON)
          .add(BlockContent.RESOURCE_NODE_NICKEL)
          .add(BlockContent.RESOURCE_NODE_GOLD)
          .add(BlockContent.RESOURCE_NODE_REDSTONE)
          .add(BlockContent.RESOURCE_NODE_LAPIS)
          .add(BlockContent.RESOURCE_NODE_EMERALD)
          .add(BlockContent.RESOURCE_NODE_DIAMOND)
          .add(BlockContent.RESOURCE_NODE_COAL)
          .add(BlockContent.RESOURCE_NODE_URANIUM)
          .add(BlockContent.RESOURCE_NODE_PLATINUM);
        
        getOrCreateTagBuilder(TagContent.LASER_PASSTHROUGH)
          .forceAddTag(ConventionalBlockTags.GLASS_BLOCKS)
          .forceAddTag(ConventionalBlockTags.GLASS_PANES)
          .forceAddTag(ConventionalBlockTags.BUDS);

        getOrCreateTagBuilder(TagContent.CUTTER_LOGS_MINEABLE)
        // using forceAddTag because the datagen wasn't recognizing the vanilla LOGS, LEAVES, and WART_BLOCKS tags
        // even though they should absolutely be there
          .forceAddTag(BlockTags.LOGS)
          .add(Blocks.MANGROVE_ROOTS)
          .add(Blocks.MUSHROOM_STEM);
        
        getOrCreateTagBuilder(TagContent.CUTTER_LEAVES_MINEABLE)
          .forceAddTag(BlockTags.LEAVES)
          .forceAddTag(BlockTags.WART_BLOCKS)
          .add(Blocks.SHROOMLIGHT)
          .add(Blocks.RED_MUSHROOM_BLOCK)
          .add(Blocks.BROWN_MUSHROOM_BLOCK);
        
        getOrCreateTagBuilder(TagContent.REACTOR_WALL_BLOCKS)
          .add(BlockContent.REACTOR_WALL)
          .add(BlockContent.REACTOR_ABSORBER_PORT)
          .add(BlockContent.REACTOR_ENERGY_PORT)
          .add(BlockContent.REACTOR_FUEL_PORT)
          .add(BlockContent.REACTOR_REDSTONE_PORT)
          .add(BlockContent.REACTOR_CONTROLLER);
    }
}
