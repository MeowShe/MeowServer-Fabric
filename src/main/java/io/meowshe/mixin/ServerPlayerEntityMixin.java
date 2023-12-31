package io.meowshe.mixin;

import io.meowshe.helper.Science;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Unique
    public ServerPlayerEntity serverPlayerEntity;

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource damageSource, CallbackInfo callbackInfo) {
    }

    @Inject(method = "copyFrom", at = @At("HEAD"))
    public void copyForm(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        this.serverPlayerEntity = oldPlayer;
    }

    @Redirect(method = "copyFrom", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"
    ))
    public boolean copyFrom(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        if (rule.getName().equals("keepInventory")) {
            return new Science().useScience(this.serverPlayerEntity.getInventory(), true);
        }
        return instance.getBoolean(rule);
    }
}
