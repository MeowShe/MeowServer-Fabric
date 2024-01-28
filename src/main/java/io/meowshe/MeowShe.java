package io.meowshe;

import io.meowshe.item.returnHomeScienceItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.meowshe.item.returnHomeScienceItem.SCIENCE_RETURN_HOME;

public class MeowShe implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("meowshe/LS3");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("MeowShe & Lampese Minecraft Server Mod.");
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (hand.equals(Hand.MAIN_HAND) && ItemStack.canCombine(player.getMainHandStack(), SCIENCE_RETURN_HOME)) {
                if (world.getDimensionKey().equals(DimensionTypes.OVERWORLD)) {
                    returnHomeScienceItem.useScience(world, (ServerPlayerEntity) player);
                }
            }
            return TypedActionResult.pass(SCIENCE_RETURN_HOME);
        });
    }
}