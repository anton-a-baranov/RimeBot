package RimeBotPack.Rime;

import java.util.*;

/**
 * @author anton.a.baranov
 *         created on 17.07.2017.
 */
final class RimesDictionary {
    private final Hashtable<String, LinkedHashSet<String>> dictionary = new Hashtable<>(25000, .75f);

    public int size() {
        return dictionary.size();
    }

    public void addWord(ParsedRimeWord word) {
        for(int i = 0; i < word.suffixesCount(); i++) {
            LinkedHashSet<String> rimes = getOrCreateRimes(word.getSuffix(i));
            rimes.add(word.getWord());
        }
    }

    private LinkedHashSet<String> getOrCreateRimes(String suffix) {
        LinkedHashSet<String> rimes = getRimes(suffix);
        if (rimes == null)
            rimes = createRimes(suffix);
        return rimes;
    }

    public LinkedHashSet<String> getRimes(String suffix) {
        return dictionary.get(suffix);
    }

    private LinkedHashSet<String> createRimes(String suffix) {
        LinkedHashSet<String> rimes = new LinkedHashSet<>();
        dictionary.put(suffix, rimes);
        return rimes;
    }
}
