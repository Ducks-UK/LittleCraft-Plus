package com.littlecraftplus.events;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;

import java.lang.reflect.Method;

/** Optional chat roleplay integration with LittleCraft's /age attachment. */
@EventBusSubscriber(modid = "littlecraftplus")
public final class AgeSpeechEvents {
    private AgeSpeechEvents() {}

    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        Integer age = readLittleCraftAge(player);
        if (age == null || age >= 3) return;

        String message = player.getRandom().nextFloat() < 0.1F
                ? "WAAAAAA WAAAAAA"
                : "Goo goo gaa gaa";
        event.setMessage(Component.literal(message));
    }

    private static Integer readLittleCraftAge(ServerPlayer player) {
        try {
            Class<?> data = Class.forName("fr.dev1lroot.mcmods.littlecraft.common.LittleData");
            Method getAge = data.getMethod("getAge", net.minecraft.world.entity.player.Player.class);
            return (Integer) getAge.invoke(null, player);
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return null;
        }
    }
}
