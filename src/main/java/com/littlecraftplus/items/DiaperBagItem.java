package com.littlecraftplus.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;

/** Creative-tab diaper bag can start full; crafted bags intentionally start empty. */
public class DiaperBagItem extends Item {
    public static final int CAPACITY = 10;
    public DiaperBagItem(Properties properties) { super(properties); }

    public static ItemStack withDiaperCount(Item item, int count) {
        ItemStack stack = new ItemStack(item);
        CompoundTag tag = new CompoundTag();
        tag.putInt("Diapers", Math.max(0, Math.min(CAPACITY, count)));
        stack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return stack;
    }
}
