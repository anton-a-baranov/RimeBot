package RimeBotPack.Rime;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author anton.a.baranov
 * created on 06.07.2017.
 * получение ругательной рифмы к заданному слову
 */

public final class DumnRime implements Rime {
    private final static char[] VOWEL = {'А', 'Е', 'Ё', 'И', 'О', 'У', 'Ы', 'Э', 'Ю', 'Я', 'а', 'е', 'ё', 'и', 'о', 'у', 'ы', 'э', 'ю', 'я'};
    /**
     * буквы для замены (лАк -> хуЯк)
     */
    private final static char[][] VOWEL_REPLACE = {
            {'а', 'я'},
            {'е', 'е'},
            {'ё', 'ё'},
            {'и', 'и'},
            {'о', 'ё'},
            {'у', 'ю'},
            {'ы', 'и'},
            {'э', 'е'},
            {'ю', 'ю'},
            {'я', 'я'}
    };
    private String errorStr;
    private final String word;

    public DumnRime(String word) {
        super();
        //this.word = word.replaceAll("[^а-яА-Я a-zA-Z]{*}", "");
        this.word = word.toLowerCase();
    }

    @Override
    public ArrayList<String> getRime() throws Exception {
        ArrayList<String> rime = new ArrayList<>();

        if(!checkWord()) {
            throw new Exception(errorStr);
        }
        else
            rime.add(syllableExists()? rimeWithSyllable(): rimeWithoutSyllable());
        return rime;
    }

    public boolean checkWord() {
        errorStr = "";
        Pattern pattern = Pattern.compile("[а-я]*");
        if(!pattern.matcher(word).matches())
            errorStr += "Ошибка в строке твоей, юный падаван. На русском языке слово только требуется. Лишние не нужны символы тебе\n";

        String[] words = word.split("\\s+");
        if (words.length != 1)
            errorStr += "Одно только слово требуется мне, юный падаван\n";
        /*if (!syllableExists())
            errorStr += "Гласная буква присутствовать должна в твоем слове, юный падаван\n";*/

        return errorStr == "";
    }

    private String rimeWithSyllable() {
        int i = dumnSyllableIndex();
        return "Ху" + getDumnVowel(word.charAt(i)) + word.substring(i+1);
    }

    private char getDumnVowel(char ch) {
        for(char[] vowels: VOWEL_REPLACE)
            if(vowels[0] == ch)
                return vowels[1];
        return ch;
    }


    private String rimeWithoutSyllable() {
        return "Хую" + word;
    }

    private int dumnSyllableIndex() {
        int[] indexes = new int[word.length()];
        for (int i = 0; i < word.length(); i++)
            indexes[i] = -1;

        int idx = 0;
        for (int i = 0; i < word.length(); i++)
            if (Wovels.isVowel(word.charAt(i)))
                indexes[idx++] = i;
        // если слово состоит из трех и более слогов, то меняем второй слог с начала, иначе последний слог
        // деменция - хуенция, пиджак - хуиджак, мел - хуел
        return idx > 2? indexes[1]: indexes[0];
    }

    private boolean syllableExists() {
        for (int i = 0; i < word.length(); i++)
            if (Wovels.isVowel(word.charAt(i)))
                return true;

        return false;
    }
}
