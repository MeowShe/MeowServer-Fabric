package io.meowshe.item;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class returnHomeScienceItem {
    public static final ItemStack SCIENCE_RETURN_HOME;

    static {
        // Definition of Science: returnToHome
        // Item: paper{Tag:["returnToHome"],display:{Lore:['{"text": "返回出生点", "italic": false}'],Name:'{"text": "秘法古卷", "italic": false, "color": "gold"}'}}
        ItemStack science = new ItemStack(net.minecraft.item.Items.PAPER);
        NbtList tags = new NbtList();
        tags.add(NbtString.of("returnToHome"));
        science.getOrCreateNbt().put("Tag", tags);
        NbtCompound display = science.getOrCreateSubNbt("display");
        display.putString("Name", "{\"text\": \"秘法古卷\", \"italic\": false, \"color\": \"gold\"}");
        NbtList lore = new NbtList();
        lore.add(NbtString.of("{\"text\": \"返回出生点\", \"italic\": false}"));
        display.put("Lore", lore);
        SCIENCE_RETURN_HOME = science;
    }

    public static void useScience(World world, ServerPlayerEntity serverPlayerEntity) {
        PlayerInventory playerInventory = serverPlayerEntity.getInventory();
        int slot = playerInventory.getSlotWithStack(SCIENCE_RETURN_HOME);
        // When slot equals -1, it means the item is in player's offhand.
        if (slot == -1) {
            ItemStack itemStack = playerInventory.offHand.get(0);
            itemStack.setCount(itemStack.getCount() - 1);
        } else {
            playerInventory.removeStack(slot, 1);
        }
        BlockPos spawnPointPos = serverPlayerEntity.getSpawnPointPosition();
        if (spawnPointPos != null) {
            serverPlayerEntity.teleport((float) spawnPointPos.getX(), (float) spawnPointPos.getY(), (float) spawnPointPos.getZ());
        } else {
            BlockPos worldSpawnPointPos = world.getSpawnPos();
            serverPlayerEntity.teleport((float) worldSpawnPointPos.getX(), (float) worldSpawnPointPos.getY(), (float) worldSpawnPointPos.getZ());
        }
        OverlayMessageS2CPacket overlayMessageS2CPacket = new OverlayMessageS2CPacket(Text.literal("Welcome Home."));
        serverPlayerEntity.networkHandler.sendPacket(overlayMessageS2CPacket);
    }
}
