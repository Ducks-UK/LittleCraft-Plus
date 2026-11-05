package com.littlecraftplus.block;

import com.littlecraftplus.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class DiaperBagBlockEntity extends BlockEntity {
    public static final int CAPACITY = 10;
    private int diapers;

    public DiaperBagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.DIAPER_BAG_ENTITY.get(), pos, state);
    }

    public int getDiapers() { return diapers; }
    public boolean takeOne() {
        if (diapers <= 0) return false;
        diapers--;
        setChanged();
        return true;
    }

    @Override protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("Diapers", diapers);
    }
    @Override protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        diapers = Math.max(0, Math.min(CAPACITY, input.getIntOr("Diapers", 0)));
    }
}
