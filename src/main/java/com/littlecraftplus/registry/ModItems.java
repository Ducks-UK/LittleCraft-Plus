package com.littlecraftplus.registry;

import com.littlecraftplus.LittleCraftPlus;
import com.littlecraftplus.items.ConsumableRoleplayItem;
import com.littlecraftplus.items.ToyItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LittleCraftPlus.MOD_ID);
    public static final Map<String, DeferredItem<Item>> ALL = new LinkedHashMap<>();

    private ModItems() {}

    private static DeferredItem<Item> item(String id) {
        DeferredItem<Item> registered = ITEMS.registerItem(id, Item::new);
        ALL.put(id, registered);
        return registered;
    }

    private static DeferredItem<Item> food(String id, boolean drink) {
        DeferredItem<Item> registered = ITEMS.registerItem(id, p -> new ConsumableRoleplayItem(
                p, drink ? ItemUseAnimation.DRINK : ItemUseAnimation.EAT, drink ? 32 : 24, true));
        ALL.put(id, registered);
        return registered;
    }

    static {
        food("coffee", true); food("iced_coffee", true); food("hot_chocolate", true);
        food("vanilla_latte", true); food("strawberry_milk", true); food("babyccino", true);
        food("chocolate_milk", true); food("apple_juice", true); food("orange_juice", true);
        food("warm_milk", true); food("baby_food_jar", false); food("applesauce", false);
        food("baby_biscuits", false); food("cookies", false); food("cupcake", false);
        food("pancakes", false); food("cereal_bowl", false);

        ITEMS.registerItem("rubber_duck", p -> new ToyItem(p, 12, true));
        ALL.put("rubber_duck", ITEMS.getEntries().stream().filter(e -> e.getId().getPath().equals("rubber_duck")).findFirst().orElseThrow());
        for (String id : new String[]{"teddy_bear", "bunny_plushie", "cat_plushie", "elephant_plushie",
                "dinosaur_plushie", "frog_plushie", "puppy_plushie", "rattle", "building_blocks",
                "toy_train", "toy_ball", "musical_toy", "stuffed_star", "toy_mobile", "teething_ring",
                "baby_rattle", "baby_blanket", "storybook", "pacifier_clip", "baby_wipes", "diaper_bag", "sippy_cup"}) {
            if (id.equals("rattle") || id.equals("baby_rattle")) {
                DeferredItem<Item> toy = ITEMS.registerItem(id, p -> new ToyItem(p, 8, false)); ALL.put(id, toy);
            } else item(id);
        }
    }
}
