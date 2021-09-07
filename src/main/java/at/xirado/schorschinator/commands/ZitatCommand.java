package at.xirado.schorschinator.commands;

import at.xirado.schorschinator.Main;
import at.xirado.schorschinator.data.Util;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.jline.terminal.Terminal;

public class ZitatCommand extends Command
{
    public static final String LOGO_URL = "https://i.imgur.com/gO4qbaz.gif";

    public ZitatCommand()
    {
        super("zitat", "Schickt ein Zitat in den Zitate-Channel");
    }

    @Override
    public void executeCommand(Terminal terminal, String[] args)
    {
        String url = Main.CONFIG_FILE.getString("webhook-url");
        if (url == null || url.isEmpty())
        {
            System.out.println(Util.error("Du hast keine Webhook-URL in der config.json gesetzt!"));
            return;
        }
        if (args.length == 0)
        {
            System.out.println(Util.error("Benutzung: zitat [Text]"));
            return;
        }
        WebhookClient client = new WebhookClientBuilder(url).build();
        StringBuilder builder = new StringBuilder();
        for(String string : args)
            builder.append(string).append(" ");
        String string = builder.toString().trim();
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xff00ff)
                .setDescription(string)
                .build();
        WebhookMessage message = new WebhookMessageBuilder()
                .addEmbeds(embed)
                .setAvatarUrl(LOGO_URL)
                .setUsername("HTL Zitate")
                .build();
        System.out.println(Util.primary("Nachricht wird versendet..."));
        try
        {
            client.send(message)
                    .handle((msg, error) -> {
                        if (error != null)
                        {
                            System.out.println(Util.error("Ein Fehler ist aufgetreten: "+error.getMessage()));
                        } else {
                            System.out.println(Util.success("Die Nachricht wurde versendet!"));
                        }
                        return null;
                    });
        }catch (Exception ignored) {}
    }
}
