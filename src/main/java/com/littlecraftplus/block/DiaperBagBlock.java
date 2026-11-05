package com.littlecraftplus.block;

import com.littlecraftplus.registry.ModBlocks;
import com.littlecraftplus.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.phys.BlockHitResult;

public class DiaperBagBlock extends BaseEntityBlock {
    private static final ResourceLocation LITTLECRAFT_DIAPER_ID = ResourceLocation.parse("littlecraft:diaper");

    public DiaperBagBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DiaperBagBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (!(level.getBlockEntity(pos) instanceof DiaperBagBlockEntity bag)) return InteractionResult.PASS;

        if (player.isShiftKeyDown() && level instanceof ServerLevel serverLevel
                && player instanceof ServerPlayer) {
            ItemStack packedBag = bagStack(bag.getDiapers());
            level.removeBlock(pos, false);
            serverLevel.addFreshEntity(new ItemEntity(serverLevel, pos.getX() + 0.5D,
                    pos.getY() + 0.5D, pos.getZ() + 0.5D, packedBag));
            return InteractionResult.CONSUME;
        }

        Item diaperItem = BuiltInRegistries.ITEM.get(LITTLECRAFT_DIAPER_ID);
        if (diaperItem == Items.AIR) {
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                    "LittleCraft diaper item is unavailable. Check that LittleCraft is installed."));
            return InteractionResult.CONSUME;
        }
        if (!bag.takeOne()) {
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("The diaper bag is empty."));
            return InteractionResult.CONSUME;
        }

        ItemStack diaper = new ItemStack(diaperItem);
        if (!player.getInventory().add(diaper)) {
            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), diaper));
        }
        player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                "Diapers in bag: " + bag.getDiapers() + "/" + DiaperBagBlockEntity.CAPACITY));
        return InteractionResult.CONSUME;
    }

    @Override
    public void playerDestroy(ServerLevel level, ServerPlayer player, BlockPos pos, BlockState state,
                              BlockEntity blockEntity, ItemStack tool) {
        if (blockEntity instanceof DiaperBagBlockEntity bag) {
            level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D,
                    pos.getZ() + 0.5D, bagStack(bag.getDiapers())));
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    public static ItemStack bagStack(int diaperCount) {
        ItemStack stack = new ItemStack(ModItems.DIAPER_BAG.get());
        TagValueOutput data = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);
        data.putInt("Diapers", Math.max(0, Math.min(DiaperBagBlockEntity.CAPACITY, diaperCount)));
        BlockItem.setBlockEntityData(stack, (BlockEntityType<?>) ModBlocks.DIAPER_BAG_ENTITY.get(), data);
        return stack;
    }
}
