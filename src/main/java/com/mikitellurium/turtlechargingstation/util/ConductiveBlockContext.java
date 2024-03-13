package com.mikitellurium.turtlechargingstation.util;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConductiveBlockContext {

    private static final Map<Property<?>, DirectionFunction> PROPERTY_FUNCTIONS = Util.make(new HashMap<>(), (map) -> {
        map.put(Properties.FACING, new DirectionFunction(
                (state) -> new Direction[]{state.get(Properties.FACING)},
                (state) -> state.get(Properties.FACING).getAxis()
        ));
        map.put(Properties.HORIZONTAL_FACING, new DirectionFunction(
                (state) -> new Direction[]{state.get(Properties.HORIZONTAL_FACING)},
                (state) -> state.get(Properties.HORIZONTAL_FACING).getAxis()
        ));
        map.put(Properties.VERTICAL_DIRECTION, new DirectionFunction(
                (state) -> new Direction[]{state.get(Properties.VERTICAL_DIRECTION)},
                (state) -> state.get(Properties.VERTICAL_DIRECTION).getAxis()
        ));
        map.put(Properties.AXIS, new DirectionFunction(
                (state) -> {
                    Direction.Axis axis = state.get(Properties.AXIS);
                    return new Direction[]{
                            Direction.from(axis, Direction.AxisDirection.POSITIVE),
                            Direction.from(axis, Direction.AxisDirection.NEGATIVE)};
                },
                (state) -> state.get(Properties.AXIS)
        ));
        map.put(Properties.HORIZONTAL_AXIS, new DirectionFunction(
                (state) -> {
                    Direction.Axis axis = state.get(Properties.HORIZONTAL_AXIS);
                    return new Direction[]{
                            Direction.from(axis, Direction.AxisDirection.POSITIVE),
                            Direction.from(axis, Direction.AxisDirection.NEGATIVE)};
                },
                (state) -> state.get(Properties.HORIZONTAL_AXIS)));
    });

    private final BlockState blockState;
    private final Direction[] directions;
    private final Direction.Axis axis;

    public ConductiveBlockContext(BlockState blockState) {
        this.blockState = blockState;
        DirectionFunction function = DirectionFunction.getFunction(blockState);
        this.directions = function.getDirections(blockState);
        this.axis = function.getAxis(blockState);
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public Direction[] getDirections() {
        return directions;
    }

    public Direction.Axis getAxis() {
        return axis;
    }

    // If axis is null the block can conduct to any directions
    public boolean canConductTo(BlockState blockState, Direction direction) {
        ConductiveBlockContext context = new ConductiveBlockContext(blockState);
        return (this.axis == null && context.axis != null) ? context.axis.test(direction) :
                (this.axis != null && context.axis == null) ? this.axis.test(direction) :
                        this.axis == context.axis;
    }

    private record DirectionFunction(Function<BlockState, Direction[]> directionFunction,
                                     Function<BlockState, Direction.Axis> axisFunction) {
        private static final DirectionFunction DEFAULT = new DirectionFunction(
                (state) -> Direction.values(),
                (state) -> null);

        Direction[] getDirections(BlockState blockState) {
            return this.directionFunction.apply(blockState);
        }

        Direction.Axis getAxis(BlockState blockState) {
            return this.axisFunction.apply(blockState);
        }

        static DirectionFunction getFunction(BlockState blockState) {
            for (Map.Entry<Property<?>, DirectionFunction> entry : PROPERTY_FUNCTIONS.entrySet()) {
                Property<?> property = entry.getKey();
                if (blockState.contains(property)) {
                    return entry.getValue();
                }
            }
            return DirectionFunction.DEFAULT;
        }
    }

}
