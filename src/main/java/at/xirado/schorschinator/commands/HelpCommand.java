package at.xirado.schorschinator.commands;

import at.xirado.schorschinator.Main;
import at.xirado.schorschinator.handler.CommandHandler;
import org.jline.terminal.Terminal;

public class HelpCommand extends Command
{
    public HelpCommand()
    {
        super("help", "Listet die Hilfe-Seite", "?");
    }

    @Override
    public void executeCommand(Terminal terminal, String[] args)
    {
        StringBuilder builder = new StringBuilder("\nAlle Befehle:\n");
        int neededspaces = 0;
        for (Command ccmd : Main.COMMAND_HANDLER.getEnabledCommands())
        {
            if (neededspaces < ccmd.getInvoke().length() + 2)
            {
                neededspaces = ccmd.getInvoke().length() + 2;
            }
        }
        builder.append("\n");
        for (Command ccmd : Main.COMMAND_HANDLER.getEnabledCommands())
        {
            builder.append("|» ").append(ccmd.getInvoke());
            int remainingSpaces = (neededspaces) - (ccmd.getInvoke().length());
            builder.append(" ".repeat(Math.max(0, remainingSpaces)));
            builder.append("<->");
            builder.append("  ");
            builder.append(ccmd.getDescription());
            builder.append("\n");
        }
        System.out.println(builder.toString().trim() + "\n");
    }
}
