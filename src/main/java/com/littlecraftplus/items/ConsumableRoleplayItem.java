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

/** A roleplay food/drink item. LittleCraft reads the use animation and this item's
 * food-effect JSON on use completion, so digestion remains owned by LittleCraft. */
public class ConsumableRoleplayItem extends Item {
    private final ItemUseAnimation animation;
    private final int duration;
    private final boolean singleUse;

    public ConsumableRoleplayItem(Properties properties, ItemUseAnimation animation, int duration, boolean singleUse) {
        super(properties);
        this.animation = animation;
        this.duration = duration;
        this.singleUse = singleUse;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) { return duration; }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) { return animation; }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, net.minecraft.world.entity.LivingEntity entity) {
        if (!level.isClientSide && singleUse && entity instanceof Player player && !player.getAbilities().instabuild)
            stack.shrink(1);
        if (!level.isClientSide)
            level.playSound(null, entity.blockPosition(), animation == ItemUseAnimation.DRINK ? SoundEvents.GENERIC_DRINK : SoundEvents.GENERIC_EAT,
                    SoundSource.PLAYERS, 0.75F, 1.0F);
        return stack;
    }
}
