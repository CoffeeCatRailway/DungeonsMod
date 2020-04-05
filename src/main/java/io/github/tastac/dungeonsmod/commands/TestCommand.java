package io.github.tastac.dungeonsmod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class TestCommand
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("debugGenerateDungeon").requires((source) -> source.hasPermissionLevel(2)).executes((ctx) -> execute(ctx.getSource())));
    }

    private static int execute(CommandSource source)
    {
        long startTime = System.currentTimeMillis();
        source.sendFeedback(new StringTextComponent("Generated dungeon with size " + 5 + " in " + (System.currentTimeMillis() - startTime) + "ms"), true);
        return Command.SINGLE_SUCCESS;
    }
}
