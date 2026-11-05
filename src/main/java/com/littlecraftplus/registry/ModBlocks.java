package com.littlecraftplus.registry;

import com.littlecraftplus.LittleCraftPlus;
import com.littlecraftplus.block.DiaperBagBlock;
import com.littlecraftplus.block.DiaperBagBlockEntity;
import com.littlecraftplus.block.HighChairBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(LittleCraftPlus.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, LittleCraftPlus.MOD_ID);

    public static final var HIGH_CHAIR = BLOCKS.registerBlock(
            "high_chair", HighChairBlock::new,
            properties -> properties.strength(1.0F).noOcclusion());
    public static final var DIAPER_BAG = BLOCKS.registerBlock(
            "diaper_bag", DiaperBagBlock::new,
            properties -> properties.strength(1.0F).noOcclusion());

    public static final var DIAPER_BAG_ENTITY = BLOCK_ENTITY_TYPES.register(
            "diaper_bag", () -> new BlockEntityType<>(DiaperBagBlockEntity::new, DIAPER_BAG.get()));

    private ModBlocks() {}
}
