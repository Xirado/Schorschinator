package at.xirado.schorschinator.data;

import at.xirado.schorschinator.Main;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import java.io.File;
import java.security.CodeSource;

public class Util
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static String format(String text, Object... arguments)
    {
        return MessageFormatter.arrayFormat(text, arguments).getMessage();
    }

    public static String getJarPath()
    {
        try
        {
            CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            return (jarFile.getParentFile().getPath());
        } catch (Exception e)
        {
            LOGGER.error("Could not get path of jar!", e);
            return null;
        }
    }

    public static String error(String string)
    {
        return new AttributedStringBuilder().style(AttributedStyle.DEFAULT.foreground(0xff, 0x00, 0x00)).append("FEHLER: ").append(string).style(AttributedStyle.DEFAULT).toAnsi();
    }

    public static String success(String string)
    {
        return new AttributedStringBuilder().style(AttributedStyle.DEFAULT.foreground(0x00, 0xff, 0x00)).append(string).style(AttributedStyle.DEFAULT).toAnsi();
    }

    public static String primary(String string)
    {
        return new AttributedStringBuilder().style(AttributedStyle.DEFAULT.foreground(0x52, 0xB2, 0xBF)).append(string).style(AttributedStyle.DEFAULT).toAnsi();
    }


}
