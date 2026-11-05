package com.littlecraftplus.events;

import com.littlecraftplus.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = "littlecraftplus", bus = EventBusSubscriber.Bus.MOD)
public final class CreativeTabEvents {
    private CreativeTabEvents() {}

    @SubscribeEvent
    public static void addItems(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().location().getNamespace().equals("littlecraftplus")
                || !event.getTabKey().location().getPath().equals("littlecraftplus")) return;
        for (Item item : ModItems.ALL.values().stream().map(holder -> holder.get()).toList()) {
            if (item == ModItems.DIAPER_BAG.get()) {
                event.accept(com.littlecraftplus.items.DiaperBagItem.withDiaperCount(item, 10));
            } else {
                event.accept(item);
            }
        }
    }
}
