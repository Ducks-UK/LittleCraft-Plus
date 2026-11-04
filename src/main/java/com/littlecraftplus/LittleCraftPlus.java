package com.littlecraftplus;

import com.littlecraftplus.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(LittleCraftPlus.MOD_ID)
public final class LittleCraftPlus {
    public static final String MOD_ID = "littlecraftplus";

    public LittleCraftPlus(IEventBus modBus) {
        ModItems.ITEMS.register(modBus);
    }
}
