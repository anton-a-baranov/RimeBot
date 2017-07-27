package RimeBotPack;

import RimeBotPack.Rime.*;
import java.util.ArrayList;

/**
 * Created by anton.a.baranov on 10.07.2017.
 */
public class Test {
    public static void main(String[] args) {
        try {
            RimeBot bot = new RimeBot();
            System.out.println(bot.reply("Утка"));
            System.out.println(bot.reply("/dumn"));
            System.out.println(bot.reply("Утка"));
            System.out.println(bot.reply("/normal"));
            System.out.println(bot.reply("Утка"));
            System.out.println(bot.reply("/Утка"));
        }
        catch (Exception exc) {
            System.out.println(exc);
        }
    }
}
