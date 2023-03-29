package com.thecrownstudios.minestomlauncher.util;

import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class InstanceGenUtil {

    private InstanceGenUtil() {}

    public static void loadInstance(@NotNull String typeName, @NotNull InstanceContainer instanceContainer) {
        if (typeName.equalsIgnoreCase("flat")) {
            loadFlatInstance(instanceContainer);
        } else {
            loadVoidInstance(instanceContainer);
        }
    }


    private static void loadVoidInstance(@NotNull InstanceContainer instance) {
        instance.setGenerator(null);

        for (int x = -16; x < 16; x++) {
            for (int z = -16; z < 16; z++) {
                instance.setBlock(x, 60, z, Block.GRAY_CONCRETE);
            }
        }

        instance.setBlock(+0, 60, +0, Block.RED_CONCRETE);
        instance.setBlock(-1, 60, +0, Block.RED_CONCRETE);
        instance.setBlock(-1, 60, -1, Block.RED_CONCRETE);
        instance.setBlock(+0, 60, -1, Block.RED_CONCRETE);
    }

    private static void loadFlatInstance(@NotNull InstanceContainer instance) {
        instance.setGenerator(unit -> unit.modifier()
                .fillHeight(0, 61, Block.STONE)
        );

        instance.setBlock(+0, 60, +0, Block.RED_CONCRETE);
        instance.setBlock(-1, 60, +0, Block.RED_CONCRETE);
        instance.setBlock(+0, 60, -1, Block.RED_CONCRETE);
        instance.setBlock(-1, 60, -1, Block.RED_CONCRETE);
    }

}