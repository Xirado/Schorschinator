package at.xirado.schorschinator.handler;

import at.xirado.schorschinator.Main;
import at.xirado.schorschinator.commands.Command;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandHandler.class);

    private Set<Command> enabledCommands = new HashSet<>();

    public CommandHandler registerCommands(Command command, Command... commands)
    {
        enabledCommands.add(command);
        enabledCommands.addAll(List.of(commands));
        return this;
    }

    public void unregisterCommand(Command command)
    {
        enabledCommands.removeIf(cmd -> cmd.getInvoke().equals(command.getInvoke()));
    }

    public void handleCommand(Terminal terminal, String invoke, String[] args)
    {
        Runnable r  = () -> {
            try
            {
                boolean foundCommand = false;
                Command command = enabledCommands.stream()
                        .filter(cmd -> cmd.getInvoke().equalsIgnoreCase(invoke) || cmd.getAliases().stream().anyMatch(invoke::equalsIgnoreCase))
                        .findFirst()
                        .orElse(null);
                if (command == null)
                {
                    System.out.println(new AttributedStringBuilder().style(AttributedStyle.DEFAULT.foreground(0xff, 0, 0)).append("Befehl \"").append(invoke).append("\" wurde nicht gefunden!").toAnsi());
                    return;
                }
                command.executeCommand(terminal, args);
            }catch (Exception ex)
            {
                LOGGER.error("Bei der Ausführung ist ein Fehler aufgetreten!", ex);
            }
        };
        Main.EXECUTOR.submit(r);
    }

    public Set<Command> getEnabledCommands()
    {
        return Collections.unmodifiableSet(enabledCommands);
    }
}
