package RimeBotPack.Rime;

import java.util.LinkedHashSet;

/**
 * @author anton.a.baranov
 *         created on 18.07.2017.
 */
public class WordParser {
    private final static char[] VOWEL = {'а', 'е', 'ё', 'и', 'о', 'у', 'ы', 'э', 'ю', 'я'};

    private final static int MAX_SIMILAR_COUNT = 4;
    private final static char[][] SIMILAR_LETTER_MANUAL = {
            {'к', 'г'},
            {'ч', 'щ'},
            {'б', 'п'},
            {'з', 'с'},
            {'ж', 'ш'},
            {'д', 'т'},
            {'ё', 'о'}
            //{'', ''}
    };
    private final static char[][] SIMILAR_LETTER = new char[SIMILAR_LETTER_MANUAL.length * 2][2];

    private String word;

    static {
        /**
         * получаем SIMILAR_LETTER из вручную заполненного массива с помощью перестаново
         */
        // копируем звуки из ручного массива в результирующий
        System.arraycopy(SIMILAR_LETTER_MANUAL, 0, SIMILAR_LETTER, 0, SIMILAR_LETTER_MANUAL.length);
        // и добавляем их же, но в обратном порядке (к-г -> г-к)
        for(int i = SIMILAR_LETTER_MANUAL.length; i < SIMILAR_LETTER_MANUAL.length * 2; i++) {
            SIMILAR_LETTER[i][0] = SIMILAR_LETTER_MANUAL[i - SIMILAR_LETTER_MANUAL.length][1];
            SIMILAR_LETTER[i][1] = SIMILAR_LETTER_MANUAL[i - SIMILAR_LETTER_MANUAL.length][0];
        }
    }

    public WordParser(String word) {
        this.word = word.toLowerCase();
    }

    public String[] syllables() {
        String suffix = getSuffix();
        return allSuffixVariants(suffix);
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
            else if( isVowel(word.charAt(i)) )
                idx = i;
        return word.substring(idx);
    }

    private String[] allSuffixVariants(String suffix) {
        LinkedHashSet<String> variants = new LinkedHashSet<>(); // для обеспечения уникальности используем LinkedHashSet
        genSimilarWord(suffix.toLowerCase(), variants, 0);
        return hashsetToArray(variants);
    }

    private static String[] hashsetToArray(LinkedHashSet<String> variants) {
        String[] array = new String[variants.size()];
        variants.toArray(array);
        return array;
    }

    private void genSimilarWord(String syllable, LinkedHashSet<String> arr, int firstCharIdx) {
        if (firstCharIdx == syllable.length())
            return;

        char let = syllable.charAt(firstCharIdx);
        char replaces[] = getReplaces(let);

        // получаем все перестановки текущего элемента и вызываем перестановки последующих
        for (int i = 0; i < replaces.length; i++) {
            if(replaces[i] == '\0')
                break;

            String replaceStr = replaceChar(syllable, firstCharIdx, replaces[i]);
            arr.add(replaceStr);
            genSimilarWord(replaceStr, arr, firstCharIdx + 1);
        }
    }

    private static char[] getReplaces(char let) {
        char[] replaces = new char[MAX_SIMILAR_COUNT];
        replaces[0] = let;  // для того, чтобы вызвать перестановки следующих символов, не меняя текущий
        int replacesCnt = 1;
        // ищем в массиве схожих символов наш символ и добавляем в массив замен символ замены
        for (char[] repl: SIMILAR_LETTER)
            if(let == repl[0])
                replaces[replacesCnt++] = repl[1];
        return replaces;
    }

    private static String replaceChar(String string, int index, char charToReplace) {
        String beginStr = index == 0? "": string.substring(0, index);
        String endStr = index == string.length() - 1? "": string.substring(index + 1);
        return beginStr + charToReplace + endStr;
    }

    public static boolean isVowel(char ch) {
        for(char vowel: VOWEL)
            if (vowel == ch)
                return true;

        return false;
    }

    public int syllableCount() {
        int cnt = 0;
        for(char ch: word.toCharArray())
            if (isVowel(ch))
                cnt++;
        return cnt;
    }

    public int emphasisCount() {
        int cnt = 0;
        for(char ch: word.toCharArray())
            if (ch == '`')
                cnt++;
        return cnt;
    }

    @Override
    public String toString() {
        String[] syllables = syllables();
        return String.join(", ", syllables);
    }
}
