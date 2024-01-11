package io.meowshe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.meowshe.Config;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin {
    @Unique
    private final Set<UUID> notifiedPlayers = new HashSet<>();

    @WrapOperation(method = "onEntityCollision", at = @At(target = "Lnet/minecraft/entity/Entity;canUsePortals()Z", value = "INVOKE"))
    private boolean canUsePortals(Entity instance, Operation<Boolean> original) {
        if (instance.isPlayer() && !Config.allowJoinEnd) {
            UUID playerId = instance.getUuid();
            if (!notifiedPlayers.contains(playerId)) {
                instance.sendMessage(Text.literal("世界之混沌程度不足以开启末地。"));
                notifiedPlayers.add(playerId);
            }
            return false;
        }
        return original.call(instance);
    }
}
