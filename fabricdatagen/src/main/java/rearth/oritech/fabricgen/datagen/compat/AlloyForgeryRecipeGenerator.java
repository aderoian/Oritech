package rearth.oritech.fabricgen.datagen.compat;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.TagKey;
import rearth.oritech.fabricgen.datagen.RecipeGenerator;
import rearth.oritech.init.ItemContent;
import rearth.oritech.init.TagContent;
import wraith.alloyforgery.data.builders.AlloyForgeryRecipeBuilder;

public class AlloyForgeryRecipeGenerator {
    public static void generateRecipes(RecipeExporter exporter) {
        offerAFAlloyRecipe(exporter, TagContent.NICKEL_INGOTS, Items.DIAMOND, ItemContent.ADAMANT_INGOT, 1, 1, 10, "adamant");
        offerAFAlloyRecipe(exporter, ConventionalItemTags.IRON_INGOTS, ItemContent.RAW_BIOPOLYMER.asItem(), ItemContent.BIOSTEEL_INGOT.asItem(), 1, 1, 10, "biosteel");
        offerAFAlloyRecipe(exporter, TagContent.PLATINUM_INGOTS, Items.NETHERITE_INGOT, ItemContent.DURATIUM_INGOT.asItem(), 1, 1, 20, "duratium");
        offerAFAlloyRecipe(exporter, ConventionalItemTags.GOLD_INGOTS, ConventionalItemTags.REDSTONE_DUSTS, ItemContent.ELECTRUM_INGOT.asItem(), 1, 1, 10, "electrum");
        offerAFAlloyRecipe(exporter, TagContent.NICKEL_INGOTS, ItemContent.FLUXITE.asItem(), ItemContent.ENERGITE_INGOT.asItem(), 1, 1, 10, "energite");
        offerAFAlloyRecipe(exporter, ConventionalItemTags.IRON_INGOTS, TagContent.COAL_DUSTS, ItemContent.STEEL_INGOT.asItem(), 1, 1, 5, "steel");

    }

    private static void offerAFAlloyRecipe(RecipeExporter exporter, TagKey<Item> inputA, Item inputB, Item result, int resultCount, int minForgeTier, int fuelPerTick, String suffix) {
        AlloyForgeryRecipeBuilder.create(result)
            .input(inputA, 1)
            .criterion("has_" + inputA.id().toUnderscoreSeparatedString(), RecipeGenerator.conditionsFromTag(inputA))
            .input(inputB, 1)
            .criterion(RecipeGenerator.hasItem(inputB), RecipeGenerator.conditionsFromItem(inputB))
            .setMinimumForgeTier(minForgeTier)
            .setFuelPerTick(fuelPerTick)
            .offerTo(exporter, "compat/alloyforgery/" + suffix);
    }

    private static void offerAFAlloyRecipe(RecipeExporter exporter, TagKey<Item> inputA, TagKey<Item> inputB, Item result, int resultCount, int minForgeTier, int fuelPerTick, String suffix) {
        AlloyForgeryRecipeBuilder.create(result)
            .input(inputA, 1)
            .criterion("has_" + inputA.id().toUnderscoreSeparatedString(), RecipeGenerator.conditionsFromTag(inputA))
            .input(inputB, 1)
            .criterion("has_" + inputB.id().toUnderscoreSeparatedString(), RecipeGenerator.conditionsFromTag(inputB))
            .setMinimumForgeTier(minForgeTier)
            .setFuelPerTick(fuelPerTick)
            .offerTo(exporter, "compat/alloyforgery/" + suffix);
    }
}