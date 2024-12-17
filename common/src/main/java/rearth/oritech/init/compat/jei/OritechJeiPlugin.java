package rearth.oritech.init.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import rearth.oritech.Oritech;
import rearth.oritech.block.base.entity.MachineBlockEntity;
import rearth.oritech.block.entity.generators.BioGeneratorEntity;
import rearth.oritech.block.entity.generators.FuelGeneratorEntity;
import rearth.oritech.block.entity.generators.LavaGeneratorEntity;
import rearth.oritech.block.entity.generators.SteamEngineEntity;
import rearth.oritech.block.entity.processing.*;
import rearth.oritech.init.BlockContent;
import rearth.oritech.init.recipes.OritechRecipe;
import rearth.oritech.init.recipes.OritechRecipeType;
import rearth.oritech.init.recipes.RecipeContent;

@JeiPlugin
public class OritechJeiPlugin implements IModPlugin {
    
    @Override
    public @NotNull Identifier getPluginUid() {
        return Oritech.id("jei_plugin");
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        
        registerOritechCategory(registration, RecipeContent.PULVERIZER, BlockContent.PULVERIZER_BLOCK, PulverizerBlockEntity.class);
        registerOritechCategory(registration, RecipeContent.GRINDER, BlockContent.FRAGMENT_FORGE_BLOCK, FragmentForgeBlockEntity.class);
        registerOritechCategory(registration, RecipeContent.ASSEMBLER, BlockContent.ASSEMBLER_BLOCK, AssemblerBlockEntity.class);
        registerOritechCategory(registration, RecipeContent.FOUNDRY, BlockContent.FOUNDRY_BLOCK, FoundryBlockEntity.class);
        registerOritechCategory(registration, RecipeContent.COOLER, BlockContent.COOLER_BLOCK, CoolerBlockEntity.class);
        registerOritechCategory(registration, RecipeContent.CENTRIFUGE, BlockContent.CENTRIFUGE_BLOCK, CentrifugeBlockEntity.class);
        registerOritechCategory(registration, RecipeContent.CENTRIFUGE_FLUID, BlockContent.CENTRIFUGE_BLOCK, CentrifugeBlockEntity.class);
        registerOritechCategory(registration, RecipeContent.ATOMIC_FORGE, BlockContent.ATOMIC_FORGE_BLOCK, AtomicForgeBlockEntity.class);
        
        // generators
        registerOritechCategory(registration, RecipeContent.BIO_GENERATOR, BlockContent.BIO_GENERATOR_BLOCK, BioGeneratorEntity.class);
        registerOritechCategory(registration, RecipeContent.FUEL_GENERATOR, BlockContent.FUEL_GENERATOR_BLOCK, FuelGeneratorEntity.class);
        registerOritechCategory(registration, RecipeContent.LAVA_GENERATOR, BlockContent.LAVA_GENERATOR_BLOCK, LavaGeneratorEntity.class);
        registerOritechCategory(registration, RecipeContent.STEAM_ENGINE, BlockContent.STEAM_ENGINE_BLOCK, SteamEngineEntity.class);
        
        registration.addRecipeCategories(new OritechJeiParticleCollisionRecipe(registration.getJeiHelpers().getGuiHelper()));
        
    }
    
    private void registerOritechCategory(IRecipeCategoryRegistration registration, OritechRecipeType type, Block block, Class<? extends MachineBlockEntity> machineClass) {
        registration.addRecipeCategories(
          new OritechRecipeCategory(type, machineClass, block, registration.getJeiHelpers().getGuiHelper()));
    }
    
    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        
        registerRecipe(registration, RecipeContent.PULVERIZER);
        registerRecipe(registration, RecipeContent.GRINDER);
        registerRecipe(registration, RecipeContent.ASSEMBLER);
        registerRecipe(registration, RecipeContent.FOUNDRY);
        registerRecipe(registration, RecipeContent.COOLER);
        registerRecipe(registration, RecipeContent.CENTRIFUGE);
        registerRecipe(registration, RecipeContent.CENTRIFUGE_FLUID);
        registerRecipe(registration, RecipeContent.ATOMIC_FORGE);
        
        // generators
        registerRecipe(registration, RecipeContent.BIO_GENERATOR);
        registerRecipe(registration, RecipeContent.FUEL_GENERATOR);
        registerRecipe(registration, RecipeContent.LAVA_GENERATOR);
        registerRecipe(registration, RecipeContent.STEAM_ENGINE);
        registerRecipe(registration, RecipeContent.PARTICLE_COLLISION);
        
    }
    
    public void registerRecipe(IRecipeRegistration registration, OritechRecipeType type) {
        // this feels incredibly hacky, but seems to be the way to go?
        var world = MinecraftClient.getInstance().world;
        var data = world.getRecipeManager().listAllOfType(type).stream().map(RecipeEntry::value).toList();
        registration.addRecipes(RecipeType.create(type.getIdentifier().getNamespace(), type.getIdentifier().getPath(), OritechRecipe.class), data);
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        
        registerCatalyst(registration, RecipeContent.PULVERIZER, BlockContent.PULVERIZER_BLOCK);
        registerCatalyst(registration, RecipeContent.GRINDER, BlockContent.FRAGMENT_FORGE_BLOCK);
        registerCatalyst(registration, RecipeContent.ASSEMBLER, BlockContent.ASSEMBLER_BLOCK);
        registerCatalyst(registration, RecipeContent.FOUNDRY, BlockContent.FOUNDRY_BLOCK);
        registerCatalyst(registration, RecipeContent.COOLER, BlockContent.COOLER_BLOCK);
        registerCatalyst(registration, RecipeContent.CENTRIFUGE, BlockContent.CENTRIFUGE_BLOCK);
        registerCatalyst(registration, RecipeContent.CENTRIFUGE_FLUID, BlockContent.CENTRIFUGE_BLOCK);
        registerCatalyst(registration, RecipeContent.ATOMIC_FORGE, BlockContent.ATOMIC_FORGE_BLOCK);
        
        // generators
        registerCatalyst(registration, RecipeContent.BIO_GENERATOR, BlockContent.BIO_GENERATOR_BLOCK);
        registerCatalyst(registration, RecipeContent.FUEL_GENERATOR, BlockContent.FUEL_GENERATOR_BLOCK);
        registerCatalyst(registration, RecipeContent.LAVA_GENERATOR, BlockContent.LAVA_GENERATOR_BLOCK);
        registerCatalyst(registration, RecipeContent.STEAM_ENGINE, BlockContent.STEAM_ENGINE_BLOCK);
        registerCatalyst(registration, RecipeContent.PARTICLE_COLLISION, BlockContent.ACCELERATOR_CONTROLLER);
    }
    
    private void registerCatalyst(IRecipeCatalystRegistration registration, OritechRecipeType type, Block block) {
        registration.addRecipeCatalyst(block, RecipeType.create(type.getIdentifier().getNamespace(), type.getIdentifier().getPath(), OritechRecipe.class));
    }
}
