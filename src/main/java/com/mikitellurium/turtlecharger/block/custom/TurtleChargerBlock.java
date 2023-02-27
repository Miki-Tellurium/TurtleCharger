package com.mikitellurium.turtlecharger.block.custom;

import com.mikitellurium.turtlecharger.blockentity.ModBlockEntities;
import com.mikitellurium.turtlecharger.blockentity.custom.TurtleChargerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class TurtleChargerBlock extends BaseEntityBlock {

    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public TurtleChargerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ENABLED, Boolean.valueOf(true)));
    }

    // Block entity stuff
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TurtleChargerBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof TurtleChargerBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (TurtleChargerBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Container provider is missing");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock())) {
            checkPoweredState(pLevel, pPos, pState);
        }
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        this.checkPoweredState(pLevel, pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(Level pLevel, BlockState pState,
                                                               BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.TURTLE_CHARGER.get(),
                TurtleChargerBlockEntity::tick);
    }

    private void checkPoweredState(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean flag = !pLevel.hasNeighborSignal(pPos);
        if (flag != pState.getValue(ENABLED)) {
            pLevel.setBlock(pPos, pState.setValue(ENABLED, Boolean.valueOf(flag)), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ENABLED);
    }

}
