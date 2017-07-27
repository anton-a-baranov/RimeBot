package RimeBotPack.Rime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author anton.a.baranov
 *         created on 17.07.2017.
 */
final class Dictionary {
    private final Hashtable<String, LinkedHashSet<String>> dictionary = new Hashtable<>(220000, .75f);

    public int size() {
        return dictionary.size();
    }

    public void add(ParsedRimeWord word) {
        for(int i = 0; i < word.syllablesCount(); i++) {
            LinkedHashSet<String> wordArray = getOrCreateRimes(word.getSyllable(i));
            // если заданное окончание слова еще не было добавлено в словарь, добавляем его
            if(wordArray == null) {
                wordArray = new LinkedHashSet<String>();
                dictionary.put(word.getSyllable(i), wordArray);
            }
            // добавляем слово к окончанию
            wordArray.add(word.getWord());
        }
    }

    private LinkedHashSet<String> getOrCreateRimes(String syllable) {
        LinkedHashSet<String> wordArray = getRimes(word.getSyllable(i));
    }

    public LinkedHashSet<String> getRimes(String syllable) {
        return dictionary.get(syllable);
    }


}
