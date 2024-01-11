package io.meowshe.item;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

public class Items {
    public static final ItemStack SCIENCE_KEEP_INVENTORY;

    static {
        // Definition of Science: keepInventory
        // Item: paper{Tag:["keepInventory"],display:{Lore:['{"text": "死亡时保留物品栏", "italic": false}'],Name:'{"text": "秘法古卷", "italic": false, "color": "gold"}'}}
        ItemStack science = new ItemStack(net.minecraft.item.Items.PAPER);
        NbtList tags = new NbtList();
        tags.add(NbtString.of("keepInventory"));
        science.getOrCreateNbt().put("Tag", tags);
        NbtCompound display = science.getOrCreateSubNbt("display");
        display.putString("Name", "{\"text\": \"秘法古卷\", \"italic\": false, \"color\": \"gold\"}");
        NbtList lore = new NbtList();
        lore.add(NbtString.of("{\"text\": \"死亡时保留物品栏\", \"italic\": false}"));
        display.put("Lore", lore);
        SCIENCE_KEEP_INVENTORY = science;
    }

    public static boolean useScience(PlayerInventory playerInventory) {
        if (playerInventory.contains(SCIENCE_KEEP_INVENTORY)) {
            playerInventory.removeStack(playerInventory.getSlotWithStack(SCIENCE_KEEP_INVENTORY), 1);
            return true;
        }
        return false;
    }
}
