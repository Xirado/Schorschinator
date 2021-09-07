package at.xirado.schorschinator.commands;

import org.jline.terminal.Terminal;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class Command
{
    private String invoke;
    private String description;
    private Set<String> aliases;

    public Command(String invoke, String description)
    {
        this(invoke, description, null);
    }

    public Command(String invoke, String description, String... aliases)
    {
        this.invoke = invoke;
        this.description = description;
        this.aliases = aliases == null ? Collections.emptySet() : Set.of(aliases);
    }

    public String getInvoke()
    {
        return this.invoke;
    }

    public String getDescription()
    {
        return description;
    }

    public Set<String> getAliases()
    {
        return aliases;
    }

    public abstract void executeCommand(Terminal terminal, String[] args);
}
