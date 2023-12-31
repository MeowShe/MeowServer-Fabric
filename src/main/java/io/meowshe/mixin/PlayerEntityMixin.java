package io.meowshe.mixin;

import io.meowshe.helper.Science;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract PlayerInventory getInventory();

    @Redirect(method = "dropInventory", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"
    ))
    public boolean dropInventory(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        if (rule.getName().equals("keepInventory")) {
            return this.getInventory().contains(Science.SCIENCE_KEEP_INVENTORY);
        }
        return instance.getBoolean(rule);
    }

    @Redirect(method = "getXpToDrop", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"
    ))
    public boolean getXpToDrop(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        if (rule.getName().equals("keepInventory")) {
            return this.getInventory().contains(Science.SCIENCE_KEEP_INVENTORY);
        }
        return instance.getBoolean(rule);
    }
}
