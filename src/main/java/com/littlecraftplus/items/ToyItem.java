package com.littlecraftplus.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ToyItem extends Item {
    private final int cooldownTicks;
    private final boolean rubberDuck;

    public ToyItem(Properties properties, int cooldownTicks, boolean rubberDuck) {
        super(properties);
        this.cooldownTicks = Math.max(0, cooldownTicks);
        this.rubberDuck = rubberDuck;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.WOOL_HIT,
                SoundSource.PLAYERS,
                0.8F,
                rubberDuck ? 1.5F : 1.0F
            );

            if (cooldownTicks > 0) {
                player.getCooldowns().addCooldown(context.getItemInHand(), cooldownTicks);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.NONE;
    }
}
