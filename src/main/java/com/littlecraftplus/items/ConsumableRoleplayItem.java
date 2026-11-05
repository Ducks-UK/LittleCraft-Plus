package com.littlecraftplus.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class ConsumableRoleplayItem extends Item {
    private final ItemUseAnimation animation;
    private final int durationTicks;
    private final boolean singleUse;

    public ConsumableRoleplayItem(Properties properties, ItemUseAnimation animation,
                                  int durationTicks, boolean singleUse) {
        super(properties);
        this.animation = animation;
        this.durationTicks = durationTicks;
        this.singleUse = singleUse;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return animation;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return durationTicks;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide()) {
            level.playSound(
                    null,
                    entity.blockPosition(),
                    (animation == ItemUseAnimation.DRINK
                            ? SoundEvents.GENERIC_DRINK
                            : SoundEvents.GENERIC_EAT),
                    SoundSource.PLAYERS,
                    0.75F,
                    1.0F
            );

            if (singleUse && entity instanceof Player player && !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return stack;
    }
}
