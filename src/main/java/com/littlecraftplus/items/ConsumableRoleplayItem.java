
package com.littlecraftplus.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class ConsumableRoleplayItem extends Item {

    private final ItemUseAnimation animation;
    private final int duration;
    private final boolean singleUse;

    public ConsumableRoleplayItem(
            Properties properties,
            ItemUseAnimation animation,
            int duration,
            boolean singleUse) {
        super(properties);
        this.animation = animation;
        this.duration = duration;
        this.singleUse = singleUse;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return animation;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return duration;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level level,
            Player player,
            InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(
            ItemStack stack,
            Level level,
            LivingEntity entity) {

        if (!level.isClientSide()) {
            if (animation == ItemUseAnimation.DRINK) {
                level.playSound(
                    null,
                    entity.blockPosition(),
                    SoundEvents.GENERIC_DRINK.value(),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
                );
            } else {
                level.playSound(
                    null,
                    entity.blockPosition(),
                    SoundEvents.GENERIC_EAT.value(),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
                );
            }

            if (singleUse && entity instanceof Player player
                    && !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return stack;
    }
}
