package RimeBotPack.Rime;

/**
 * @author anton.a.baranov
 * created on 26.07.2017.
 */
final class WordParse {
    public static int syllableCount(String word) {
        int cnt = 0;
        for(char ch: word.toCharArray())
            if (Wovels.isVowel(ch))
                cnt++;
        return cnt;
    }

    public static int emphasisCount(String word) {
        int cnt = 0;
        for(char ch: word.toCharArray())
            if (ch == '`')
                cnt++;
        return cnt;
    }

    private WordParse(){
        super();
    };
}
