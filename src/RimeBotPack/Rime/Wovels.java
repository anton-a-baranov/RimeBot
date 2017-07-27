package RimeBotPack.Rime;

/**
 * @author anton.a.baranov
 * created on 26.07.2017.
 */
public class Wovel {
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
}
