package com.littlecraftplus.registry;

import com.littlecraftplus.LittleCraftPlus;
import com.littlecraftplus.block.DiaperBagBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LittleCraftPlus.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LITTLECRAFT_PLUS =
            TABS.register("littlecraftplus", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.littlecraftplus"))
                    .icon(() -> new ItemStack(ModItems.RUBBER_DUCK.get()))
                    .displayItems((parameters, output) -> {
                        ModItems.ALL.values().forEach(item -> output.accept(item.get()));
                        output.accept(DiaperBagBlock.bagStack(DiaperBagBlockEntity.CAPACITY));
                    })
                    .build());

    private ModCreativeTabs() {}
}
