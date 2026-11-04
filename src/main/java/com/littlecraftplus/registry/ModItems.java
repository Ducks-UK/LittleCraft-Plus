package com.littlecraftplus.registry;

import com.littlecraftplus.LittleCraftPlus;
import com.littlecraftplus.items.ConsumableRoleplayItem;
import com.littlecraftplus.items.ToyItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;

/** Registrations for LittleCraft Plus food, toys, and roleplay equipment. */
public final class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(LittleCraftPlus.MOD_ID);

    /** All registered items in registration order. */
    public static final Map<String, DeferredItem<Item>> ALL = new LinkedHashMap<>();

    /** Direct handle for code that needs the rubber duck item. */
    public static final DeferredItem<Item> RUBBER_DUCK;

    private ModItems() {}

    private static DeferredItem<Item> put(String id, DeferredItem<Item> registered) {
        ALL.put(id, registered);
        return registered;
    }

    private static DeferredItem<Item> plain(String id) {
        return put(id, ITEMS.registerItem(id, Item::new));
    }

    private static DeferredItem<Item> food(String id, ItemUseAnimation animation) {
        return put(id, ITEMS.registerItem(id, properties ->
                new ConsumableRoleplayItem(
                        properties,
                        animation,
                        animation == ItemUseAnimation.DRINK ? 32 : 24,
                        true
                )));
    }

    private static DeferredItem<Item> toy(String id, int cooldownTicks, boolean duck) {
        return put(id, ITEMS.registerItem(id, properties ->
                new ToyItem(properties, cooldownTicks, duck)));
    }

    static {
        // LittleCraft 3.2.5 checks the completed item's EAT/DRINK animation,
        // looks up its item id, and loads the matching food_effects JSON.
        food("coffee", ItemUseAnimation.DRINK);
        food("iced_coffee", ItemUseAnimation.DRINK);
        food("hot_chocolate", ItemUseAnimation.DRINK);
        food("vanilla_latte", ItemUseAnimation.DRINK);
        food("strawberry_milk", ItemUseAnimation.DRINK);
        food("babyccino", ItemUseAnimation.DRINK);
        food("chocolate_milk", ItemUseAnimation.DRINK);
        food("apple_juice", ItemUseAnimation.DRINK);
        food("orange_juice", ItemUseAnimation.DRINK);
        food("warm_milk", ItemUseAnimation.DRINK);

        food("baby_food_jar", ItemUseAnimation.EAT);
        food("applesauce", ItemUseAnimation.EAT);
        food("baby_biscuits", ItemUseAnimation.EAT);
        food("cookies", ItemUseAnimation.EAT);
        food("cupcake", ItemUseAnimation.EAT);
        food("pancakes", ItemUseAnimation.EAT);
        food("cereal_bowl", ItemUseAnimation.EAT);

        // Keep the DeferredItem returned by registerItem directly.
        RUBBER_DUCK = toy("rubber_duck", 12, true);

        toy("teddy_bear", 12, false);
        toy("bunny_plushie", 12, false);
        toy("cat_plushie", 12, false);
        toy("elephant_plushie", 12, false);
        toy("dinosaur_plushie", 12, false);
        toy("frog_plushie", 12, false);
        toy("puppy_plushie", 12, false);
        toy("rattle", 8, false);
        toy("building_blocks", 10, false);
        toy("toy_train", 10, false);
        toy("toy_ball", 10, false);
        toy("musical_toy", 20, false);
        toy("stuffed_star", 12, false);
        toy("toy_mobile", 20, false);
        toy("teething_ring", 10, false);
        toy("baby_rattle", 8, false);

        // These remain registered as ordinary items until their dedicated
        // container, wearable, or interaction implementations are added.
        plain("baby_blanket");
        plain("storybook");
        plain("pacifier_clip");
        plain("baby_wipes");
        plain("diaper_bag");
        plain("sippy_cup");
    }
}
