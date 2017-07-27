package RimeBotPack.Rime;

/**
 * @author anton.a.baranov
 * created on 26.07.2017.
 */
final class SymbolReplace {
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

    public static char[] getReplaces(char let) {
        char[] replaces = new char[MAX_SIMILAR_COUNT];
        replaces[0] = let;  // для того, чтобы вызвать перестановки следующих символов, не меняя текущий
        int replacesCnt = 1;
        // ищем в массиве схожих символов наш символ и добавляем в массив замен символ замены
        for (char[] repl: SIMILAR_LETTER)
            if(let == repl[0])
                replaces[replacesCnt++] = repl[1];
        return replaces;
    }
}
