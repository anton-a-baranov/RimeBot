package RimeBotPack.Rime;

import java.util.LinkedHashSet;

/**
 * @author anton.a.baranov
 *         created on 18.07.2017.
 */
final class SuffixParser {
    private String word;
    private String suffix;

    public SuffixParser(String word) {
        this.word = word.toLowerCase();
        suffix = getSuffix();
    }

    private String getSuffix() {
        if(word.length() <= 3)
            return word;

        int idx = 0;
        for (int i = 0; i < word.length()-2; i++)
            // в качестве последнего слога берем индекс последней гласной до окончания (-2 символа).
            // Если ей предшествует ударная гласная, то возвращаем часть слова, начиная с ударения.
            if (i > 1 && word.charAt(i-2) == '`')
                idx = i-2;
            else if( Wovels.isVowel(word.charAt(i)) )
                idx = i;
        return word.substring(idx);
    }

    public String[] suffixes() {
        LinkedHashSet<String> variants = new LinkedHashSet<>(); // для обеспечения уникальности используем LinkedHashSet
        genSimilarWord(suffix.toLowerCase(), variants, 0);
        return hashsetToArray(variants);
    }

    private void genSimilarWord(String syllable, LinkedHashSet<String> arr, int firstCharIdx) {
        if (firstCharIdx == syllable.length())
            return;

        char let = syllable.charAt(firstCharIdx);
        char replaces[] = SymbolReplace.getReplaces(let);

        // получаем все перестановки текущего элемента и вызываем перестановки последующих
        for (int i = 0; i < replaces.length; i++) {
            if(replaces[i] == '\0')
                break;

            String replaceStr = replaceChar(syllable, firstCharIdx, replaces[i]);
            arr.add(replaceStr);
            genSimilarWord(replaceStr, arr, firstCharIdx + 1);
        }
    }

    private static String replaceChar(String string, int index, char charToReplace) {
        String beginStr = index == 0? "": string.substring(0, index);
        String endStr = index == string.length() - 1? "": string.substring(index + 1);
        return beginStr + charToReplace + endStr;
    }

    private static String[] hashsetToArray(LinkedHashSet<String> variants) {
        String[] array = new String[variants.size()];
        variants.toArray(array);
        return array;
    }

    @Override
    public String toString() {
        String[] syllables = suffixes();
        return String.join(", ", syllables);
    }
}
