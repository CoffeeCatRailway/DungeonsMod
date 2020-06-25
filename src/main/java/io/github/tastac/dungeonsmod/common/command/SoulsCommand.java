package io.github.tastac.dungeonsmod.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.tastac.dungeonsmod.common.capability.SoulsCapibility;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 23/06/2020
 */
public class SoulsCommand {

    @SuppressWarnings("ConstantConditions")
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("souls").requires(src -> src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                .then(Commands.argument("targets", EntityArgument.players())
                        .then(Commands.literal("add").then(Commands.argument("amount", IntegerArgumentType.integer(0, 10)).executes(ctx -> addOrRemoveSouls(ctx, false))))
                        .then(Commands.literal("remove").then(Commands.argument("amount", IntegerArgumentType.integer(0, 10)).executes(ctx -> addOrRemoveSouls(ctx, true))))
                        .then(Commands.literal("get").executes(ctx -> {
                            ServerPlayerEntity player = ctx.getSource().asPlayer();
                            ServerPlayerEntity targets = EntityArgument.getPlayer(ctx, "targets");
                            targets.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> {
                                player.sendMessage(new StringTextComponent("Souls: " + handler.getSouls()));
                            });
                            return 1;
                        }))));
    }

    @SuppressWarnings("ConstantConditions")
    private static int addOrRemoveSouls(CommandContext<CommandSource> ctx, boolean remove) throws CommandSyntaxException {
        List<ServerPlayerEntity> players = new ArrayList<>(EntityArgument.getPlayers(ctx, "targets"));
        int amount = IntegerArgumentType.getInteger(ctx, "amount");
        players.forEach(player -> player.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> {
            if (remove)
                handler.removeSouls(amount);
            else
                handler.addSouls(amount);
        }));

        String playersMsg = players.size() + " players";
        if (players.size() == 1)
            playersMsg = players.get(0).getDisplayName().getString();
        ctx.getSource().asPlayer().sendMessage(new StringTextComponent((remove ? "Removed" : "Gave") + " " + amount + " souls(s) to " + playersMsg));
        return 1;
    }
}
