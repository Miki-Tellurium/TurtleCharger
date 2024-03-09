package com.mikitellurium.turtlecharginstation.block;

import com.mikitellurium.turtlecharginstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlecharginstation.networking.ModMessages;
import com.mikitellurium.turtlecharginstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlecharginstation.registry.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class TurtleChargingStationBlock extends BaseEntityBlock {

    private static final MapCodec<TurtleChargingStationBlock> CODEC = simpleCodec((properties) -> new TurtleChargingStationBlock());

    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    public static final BooleanProperty CHARGING = BooleanProperty.create("charging");

    public TurtleChargingStationBlock() {
        super(Properties.of()
                .mapColor(MapColor.METAL)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F)
                .sound(SoundType.METAL));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ENABLED, Boolean.TRUE)
                .setValue(CHARGING, Boolean.FALSE));
    }

    // Block entity stuff
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new TurtleChargingStationBlockEntity(pos, blockState);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    // Functionality
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof TurtleChargingStationBlockEntity station) {
                player.openMenu(station, pos);
                ModMessages.sendToAll(new EnergySyncS2CPacket(station.getEnergyStorage().getEnergyStored(), pos));
            } else {
                throw new IllegalStateException("Container provider is missing");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onPlace(BlockState pState, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(pState.getBlock())) {
            checkPoweredState(level, pos, pState);
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos pos, Block neighbor, BlockPos fromPos,
                                boolean isMoving) {
        this.checkPoweredState(level, pos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.TURTLE_CHARGING_STATION.get(),
                (tickLevel, pos, state, station) -> station.tick(tickLevel, pos, state));
    }

    private void checkPoweredState(Level level, BlockPos pos, BlockState state) {
        boolean flag = !level.hasNeighborSignal(pos);
        if (flag != state.getValue(ENABLED)) {
            level.setBlock(pos, state.setValue(ENABLED, Boolean.valueOf(flag)), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ENABLED);
        builder.add(CHARGING);
    }

}
