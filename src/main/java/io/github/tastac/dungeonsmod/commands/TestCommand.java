package io.github.tastac.dungeonsmod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class TestCommand
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("test").requires((source) -> source.hasPermissionLevel(2)).executes((ctx) -> execute(ctx.getSource())));
    }

    private static int execute(CommandSource source)
    {
        return Command.SINGLE_SUCCESS;
    }
}
