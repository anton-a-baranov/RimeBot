package RimeBotPack;

import RimeBotPack.Rime.DumnRime;
import RimeBotPack.Rime.NormalRime;
import RimeBotPack.Rime.Rime;
import java.util.ArrayList;

/**
 * @author anton.a.baranov
 * created on 26.07.2017.
 */
public final class RimeBot {
    private RimeType rimeType = RimeType.NORMAL;
    private final String COMMAND_CHAR = "/";

    public enum RimeType {
        NORMAL("стандартный"), DUMN("ругательный");

        private final String name;
        RimeType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public final String reply(String message) {
        try {
            String firstChar = message.substring(0, 1);
            if(firstChar.equals(COMMAND_CHAR))
                return executeCommand(message);
            else
                return getRime(message);
        }
        catch (Exception exc) {
            return exc.getMessage();
        }
    }

    private String executeCommand(String commandText) throws Exception{
        String[] commandParams = commandText.split("\\s+");
        String command = commandParams[0].toUpperCase().replaceAll(COMMAND_CHAR, "");
        switch (command) {
            case "HELP":
                return helpReplay();
            case "NORMAL":
            case "DUMN":
                return switchState(RimeType.valueOf(command));
            default:
                throw new Exception("Данная команда не поддерживается");
        }
    }

    private String helpReplay() {
        return "Команды бота: \n"
                + "/help справка; \n"
                + "/normal изменить тип рифм на стандартный; \n"
                + "/dumn изменить тип рифм на ругательный; \n ";
    }
    private String getRime(String word) throws Exception {
        Rime rime = getRimeInstance(word);
        ArrayList<String> rimesArray = rime.getRime();
        String[] rimes = new String[rimesArray.size()];
        rimes = (String[])rimesArray.toArray(rimes);
        String rimeResult = String.join(", ", rimes);
        return rimeResult;
    }

    private Rime getRimeInstance(String word) throws Exception {
        switch (rimeType) {
            case DUMN:
                return new DumnRime(word);
            case NORMAL:
                return new NormalRime(word);
            default:
                throw new Exception("Данный тип рифм не поддерживается");
        }
    }

    private RimeType getRimeType(String type) throws Exception {
        RimeType result = RimeType.valueOf(type);
        if (result == null)
            throwRimeTypeException();
        return result;
    }

    private static void throwRimeTypeException() throws Exception {
        RimeType[] rimeTypes = RimeType.values();
        String[] rimeTypesArray = new String[rimeTypes.length];
        String rimeTypesString = String.join(", ", rimeTypesArray);

        throw new Exception("Недопустимый тип рифм. Допустимые типы: " + rimeTypesString);
    }


    private String switchState(RimeType type){
        rimeType = type;
        return "Тип рифм изменен на " + rimeType.getName();
    }
}
