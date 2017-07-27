package RimeBotPack.Rime;


/**
 * Created by anton.a.baranov on 10.07.2017.
 */
public final class RimeWord {
    private final String word;
    private final String[] lastSyllable;

    public RimeWord(String word) {
        this.word = word;
        this.lastSyllable = WordParse.allSyllableVariants(WordParse.lastSyllable(word));
    }

    public String getWord() {
        return word;
    }

    public String[] getLastSyllable() {

        return lastSyllable;
    }

    @Override
    public String toString() {
        String syllables = "";
        for(String s: lastSyllable)
            syllables += " " + s;
        return "RimeWord{" +
                "word='" + word + '\'' +
                ", lastSyllable=" + syllables +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RimeWord that = (RimeWord) o;

        return getWord().equals(that.getWord());
    }

    @Override
    public int hashCode() {
        return getWord().hashCode();
    }
}
