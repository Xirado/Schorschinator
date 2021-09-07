package at.xirado.schorschinator;

import at.xirado.schorschinator.commands.HelpCommand;
import at.xirado.schorschinator.commands.ZitatCommand;
import at.xirado.schorschinator.data.DataObject;
import at.xirado.schorschinator.data.Util;
import at.xirado.schorschinator.handler.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static final ScheduledExecutorService EXECUTOR =Executors.newSingleThreadScheduledExecutor();
    public static final CommandHandler COMMAND_HANDLER = new CommandHandler();
    public static final DataObject CONFIG_FILE = loadJSONFile("config.json");

    public static void main(String[] args)
    {
        Shell.startShell();
        COMMAND_HANDLER.registerCommands(new HelpCommand(), new ZitatCommand());
        while (true) {
           Thread.onSpinWait();
        }
    }

    public static DataObject loadJSONFile(String fileName)
    {
        File configFile = new File(fileName);
        if (!configFile.exists())
        {
            InputStream inputStream = Main.class.getResourceAsStream("/"+fileName);
            if (inputStream == null)
            {
                LOGGER.error("Could not copy config from resources folder!");
                return DataObject.empty();
            }
            Path path = Paths.get(Util.getJarPath() + "/"+fileName);
            System.out.println(path.toAbsolutePath());
            try
            {
                Files.copy(inputStream, path);
            } catch (IOException e)
            {
                LOGGER.error("Could not copy file!", e);
            }
        }
        return DataObject.parse(configFile);
    }
}
