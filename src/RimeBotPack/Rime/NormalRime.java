package RimeBotPack.Rime;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author anton.a.baranov
 * created on 06.07.2017.
 *
 * Предоставляет список обычных рифм к заданному слову. Слово задается в конструкторе при создании объекта
 */
public final class NormalRime implements Rime {
    private final String word;
    private final String errorMsg = "Мне нужно только одно слово на русском языке, без каких-либо других символов";

    public NormalRime(String word) {
        super();
        this.word = word.toLowerCase();
    }

    public boolean checkWord() {
        boolean result = true;
        Pattern pattern = Pattern.compile("[а-яё`]*");
        // проверяем на соответствие регулярному выражению
        if(!pattern.matcher(word).matches())
            result = false;
        return result;
    }

    @Override
    public ArrayList<String> getRime() throws Exception {
        ArrayList<String> rime;

        if(!checkWord()) {
            throw new Exception(errorMsg);
        }
        else {
            RussianWordRimesDictionary dictionary = RussianWordRimesDictionary.Singleton.getInstance();
            rime = dictionary.getRime(word);
            if (rime.indexOf(word) != -1)
                rime.remove(rime.indexOf(word));
        }
        return rime;
    }
}
