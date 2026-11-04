package com.littlecraftplus.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

/** Reusable, server-authoritative toy interaction with a short cooldown. */
public class ToyItem extends Item {
    private final int cooldown;
    private final boolean duck;

    public ToyItem(Properties properties, int cooldown, boolean duck) {
        super(properties);
        this.cooldown = cooldown;
        this.duck = duck;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (!level.isClientSide) {
            level.playSound(null, player.blockPosition(), duck ? SoundEvents.CHICKEN_AMBIENT : SoundEvents.WOOL_HIT,
                    SoundSource.PLAYERS, 0.8F, duck ? 1.35F : 1.0F);
            player.getCooldowns().addCooldown(this, cooldown);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) { return 12; }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) { return ItemUseAnimation.TOUCH; }
}
