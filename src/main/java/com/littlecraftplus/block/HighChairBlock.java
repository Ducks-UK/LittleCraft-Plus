package com.littlecraftplus.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

/** A placeable high chair with a temporary invisible mount seat. */
public class HighChairBlock extends Block {
    public HighChairBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (!(level instanceof ServerLevel serverLevel) || player.getVehicle() != null) {
            return InteractionResult.CONSUME;
        }

        AABB chairArea = new AABB(pos).inflate(0.75);
        serverLevel.getEntitiesOfClass(ArmorStand.class, chairArea, stand -> stand.isInvisible() && stand.isPassenger() == false)
                .stream().findFirst().ifPresent(stand -> stand.discard());

        ArmorStand seat = new ArmorStand(level, pos.getX() + 0.5D, pos.getY() + 0.35D, pos.getZ() + 0.5D);
        seat.setInvisible(true);
        seat.setNoGravity(true);
        seat.setSmall(true);
        if (serverLevel.addFreshEntity(seat) && player.startRiding(seat)) {
            return InteractionResult.CONSUME;
        }
        seat.discard();
        return InteractionResult.CONSUME;
    }
}
