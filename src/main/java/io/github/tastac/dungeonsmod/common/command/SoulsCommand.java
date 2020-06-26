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
    
    private static final int MAX_SOULS = 20;

    private static final String ARG_TARGETS = "targets";
    private static final String ARG_AMOUNT = "amount";

    @SuppressWarnings("ConstantConditions")
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("souls").requires(src -> src.hasPermissionLevel(src.getServer().getOpPermissionLevel()))
                .then(Commands.argument(ARG_TARGETS, EntityArgument.players())
                        .then(Commands.literal("add").then(Commands.argument(ARG_AMOUNT, IntegerArgumentType.integer(0, MAX_SOULS)).executes(ctx -> addOrRemoveSouls(ctx, false))))
                        .then(Commands.literal("remove").then(Commands.argument(ARG_AMOUNT, IntegerArgumentType.integer(0, MAX_SOULS)).executes(ctx -> addOrRemoveSouls(ctx, true))))
                        .then(Commands.literal("get").executes(ctx -> {
                            List<ServerPlayerEntity> players = new ArrayList<>(EntityArgument.getPlayers(ctx, ARG_TARGETS));
                            StringBuilder builder = new StringBuilder();
                            players.forEach(player -> player.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> {
                                builder.append(player.getName().getString()).append("'s souls: ").append(handler.getSouls());
                            }));
                            ctx.getSource().asPlayer().sendMessage(new StringTextComponent(builder.toString()));
                            return 1;
                        }))
                        .then(Commands.literal("set").then(Commands.argument(ARG_AMOUNT, IntegerArgumentType.integer(0, MAX_SOULS)).executes(ctx -> {
                            int souls = IntegerArgumentType.getInteger(ctx, ARG_AMOUNT);
                            List<ServerPlayerEntity> players = new ArrayList<>(EntityArgument.getPlayers(ctx, ARG_TARGETS));

                            players.forEach(player -> player.getCapability(SoulsCapibility.SOULS_CAP).ifPresent(handler -> handler.setSouls(souls)));

                            String playersMsg = players.size() + " players";
                            if (players.size() == 1)
                                playersMsg = players.get(0).getName().getString();
                            ctx.getSource().asPlayer().sendMessage(new StringTextComponent("Set " + playersMsg + " souls(s) to " + souls));
                            return 1;
                        })))));
    }

    @SuppressWarnings("ConstantConditions")
    private static int addOrRemoveSouls(CommandContext<CommandSource> ctx, boolean remove) throws CommandSyntaxException {
        List<ServerPlayerEntity> players = new ArrayList<>(EntityArgument.getPlayers(ctx, ARG_TARGETS));
        int amount = IntegerArgumentType.getInteger(ctx, ARG_AMOUNT);
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
