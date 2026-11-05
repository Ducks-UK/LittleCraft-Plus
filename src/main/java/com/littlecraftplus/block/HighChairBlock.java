package com.littlecraftplus.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/** A placeable baby high chair placeholder block. */
public class HighChairBlock extends Block {
    public HighChairBlock(BlockBehaviour.Properties properties) { super(properties); }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hit) {
        if (!level.isClientSide()) {
            player.sendSystemMessage(Component.literal("The high chair is ready for feeding."));
        }
        return InteractionResult.SUCCESS;
    }
}
