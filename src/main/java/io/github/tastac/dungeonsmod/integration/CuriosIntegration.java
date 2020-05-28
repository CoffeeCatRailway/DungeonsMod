package io.github.tastac.dungeonsmod.integration;

import io.github.tastac.dungeonsmod.DungeonsMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author CoffeeCatRailway
 * Created: 28/05/2020
 */
public class CuriosIntegration {

    public static void sendImc(InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosAPI.MODID, CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("charm").setSize(3));
        DungeonsMod.LOGGER.info("Common Event: Register curios types");
    }

    public static List<ItemStack> getArtifactStacks(PlayerEntity player) {
        return getArtifactStacks(player, -1);
    }

    public static List<ItemStack> getArtifactStacks(PlayerEntity player, int slot) {
        List<AtomicReference<ItemStack>> artifacts = new ArrayList<>();
        artifacts.add(new AtomicReference<>(ItemStack.EMPTY));

        if (CuriosAPI.getType("charm").isPresent()) {
            artifacts.clear();
            for (int i = 0; i < (slot == -1 ? CuriosAPI.getType("charm").get().getSize() : 1); i++)
                artifacts.add(new AtomicReference<>(ItemStack.EMPTY));

            LazyOptional<ICurioItemHandler> optional = CuriosAPI.getCuriosHandler(player);
            optional.ifPresent(handler -> {
                if (slot == -1) {
                    for (int i = 0; i < CuriosAPI.getType("charm").get().getSize(); i++)
                        if (handler.getStackInSlot("charm", i).getItem() instanceof Item) // TODO: Change instanceof
                            artifacts.get(i).set(handler.getStackInSlot("charm", i));
                } else {
                    if (handler.getStackInSlot("charm", slot).getItem() instanceof Item) // TODO: Change instanceof
                        artifacts.get(slot).set(handler.getStackInSlot("charm", slot));
                }
            });
        }

        return artifacts.stream().map(AtomicReference::get).collect(Collectors.toList());
    }
}
