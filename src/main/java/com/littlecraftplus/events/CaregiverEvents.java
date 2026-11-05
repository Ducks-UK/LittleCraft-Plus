package com.littlecraftplus.events;

import com.littlecraftplus.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/** Simple server-authoritative caregiver interactions: offer a held consumable or rock a toy. */
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

        if (ModItems.CONSUMABLE_IDS.contains(held.getItem())) {
            event.setCanceled(true);
            ItemStack offered = held.copyWithCount(1);
            held.shrink(1);
            if (!recipient.getInventory().add(offered)) recipient.drop(offered, false);
            caregiver.sendSystemMessage(Component.literal("You offer " + recipient.getScoreboardName() + " a treat."));
            recipient.sendSystemMessage(Component.literal(caregiver.getScoreboardName() + " offers you a treat."));
        } else if (ModItems.TOY_IDS.contains(held.getItem())) {
            event.setCanceled(true);
            recipient.level().playSound(null, recipient.blockPosition(), SoundEvents.WOOL_HIT,
                    SoundSource.PLAYERS, 0.8F, 1.1F);
            caregiver.sendSystemMessage(Component.literal("You gently rock " + recipient.getScoreboardName() + "."));
            recipient.sendSystemMessage(Component.literal(caregiver.getScoreboardName() + " gently rocks you."));
        }
    }
}
