package RimeBotPack.Rime;


/**
 *  @author anton.a.baranov
 * created on 10.07.2017.
 * разбор слова для поиска рифм
 */
public final class ParsedRimeWord {
    private final String word;
    private final String[] suffixes;

    public ParsedRimeWord(String word) {
        this.word = word;
        SuffixParser suffix = new SuffixParser(word);
        this.suffixes = suffix.suffixes();
    }

    public String getWord() {
        return word;
    }

    public int suffixesCount() {
        return suffixes.length;
    }

    public String getSuffix(int i) {
        return suffixes[i];
    }

    @Override
    public String toString() {
        String syll = "";
        for(String s: suffixes)
            syll += " " + s;
        return "ParsedRimeWord{" +
                "word='" + word + '\'' +
                ", suffixes=" + syll +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsedRimeWord that = (ParsedRimeWord) o;

        return getWord().equals(that.getWord());
    }

    @Override
    public int hashCode() {
        return getWord().hashCode();
    }
}
