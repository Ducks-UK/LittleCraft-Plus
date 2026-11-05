package com.littlecraftplus.events;

import com.littlecraftplus.items.ConsumableRoleplayItem;
import com.littlecraftplus.items.ToyItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/** Server-authoritative caregiver interactions: offer a held consumable or rock a toy. */
@EventBusSubscriber(modid = "littlecraftplus")
public final class CaregiverEvents {
    private CaregiverEvents() {}

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getLevel().isClientSide() || event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!(event.getTarget() instanceof Player recipient)) return;

        Player caregiver = event.getEntity();
        ItemStack held = caregiver.getItemInHand(event.getHand());
        if (held.isEmpty()) return;

        if (held.getItem() instanceof ConsumableRoleplayItem) {
            event.setCanceled(true);
            ItemStack offered = held.copyWithCount(1);
            held.shrink(1);
            if (!recipient.getInventory().add(offered)) {
                ItemEntity drop = new ItemEntity(recipient.level(), recipient.getX(), recipient.getY(), recipient.getZ(), offered);
                recipient.level().addFreshEntity(drop);
            }
            caregiver.sendSystemMessage(Component.literal("You offer " + recipient.getScoreboardName() + " a treat."));
            recipient.sendSystemMessage(Component.literal(caregiver.getScoreboardName() + " offers you a treat."));
        } else if (held.getItem() instanceof ToyItem) {
            event.setCanceled(true);
            recipient.level().playSound(null, recipient.blockPosition(), net.minecraft.sounds.SoundEvents.WOOL_HIT,
                    net.minecraft.sounds.SoundSource.PLAYERS, 0.8F, 1.1F);
            caregiver.sendSystemMessage(Component.literal("You gently rock " + recipient.getScoreboardName() + "."));
            recipient.sendSystemMessage(Component.literal(caregiver.getScoreboardName() + " gently rocks you."));
        }
    }
}
