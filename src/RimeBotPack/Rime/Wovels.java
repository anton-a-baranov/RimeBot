package RimeBotPack.Rime;

/**
 * @author anton.a.baranov
 * created on 26.07.2017.
 */
final class Wovels {
    private final static char[] VOWEL = {'а', 'е', 'ё', 'и', 'о', 'у', 'ы', 'э', 'ю', 'я'};

    public static boolean isVowel(char ch) {
        for(char vowel: VOWEL)
            if (vowel == ch)
                return true;

        return false;
    }

    private Wovels() {
        super();
    }
}
