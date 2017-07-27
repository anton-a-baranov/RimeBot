package RimeBotPack.Rime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *  @author anton.a.baranov
 * created on 10.07.2017.
 *
 * Словарь рифм/слов русского языка.
 * Организован в виде синглтона, при инициализации загружает из файла словарь русских слов, преобразует его в два словаря - с ударениями и без.
 */
final class WordDictionary {
    private final static String DICTIONARY_FILE_PATH = "d:\\Проекты\\RimeBot\\out\\production\\RimeBot\\RimeBotPack\\Rime\\data\\rimesDictionary.txt";
    private final RimesDictionary dictionaryEmphasis = new RimesDictionary();
    private final RimesDictionary dictionary = new RimesDictionary();

    public static class Singleton {
        private final static WordDictionary wordDictionary = new WordDictionary();

        public static WordDictionary getInstance() {
             return wordDictionary;
        }
    }

    private void load() {
        try {
            List<String> dictionaryFile = Files.readAllLines(Paths.get(DICTIONARY_FILE_PATH));
            System.out.println(dictionaryFile.size() + " строк успешно загружены в словарь");
            for(String fileLine: dictionaryFile) {
                String wordEmph = extractWordEmphasis(fileLine); // слово с проставленным знаком ударения
                ParsedRimeWord parsedWordEmph = new ParsedRimeWord(wordEmph);
                insertIntoDictionary(parsedWordEmph, dictionaryEmphasis);

                String word = wordEmph.replaceAll("`", ""); // слово без знака ударения
                ParsedRimeWord parsedWord = new ParsedRimeWord(word);
                insertIntoDictionary(parsedWord, dictionary);
            }
            System.out.println(dictionaryEmphasis.size() + " окончаний с ударениями добавлено в словарь");
            System.out.println(dictionary.size() + " окончаний без ударений добавлено в словарь");
        }
        catch (IOException exc) {
            System.out.println(exc.toString());
        }
    }

    private void insertIntoDictionary(ParsedRimeWord word, Hashtable<String, LinkedHashSet<String>> dictionary) {

    }

    private String extractWordEmphasis(String fileLine) {
        int right = fileLine.indexOf('%');
        int left = fileLine.indexOf('#');
        return fileLine.substring(left + 1, right);
    }

    private WordDictionary() {
        super();
        load();
    }

    public ArrayList<String> getRime(String word) {
        ParsedRimeWord parsedWord = new ParsedRimeWord(word);
        ArrayList<String> result = new ArrayList<>();
        Hashtable<String, LinkedHashSet<String>> dict = WordParse.emphasisCount(word) == 0? dictionary : dictionaryEmphasis;

        for(int i = 0; i < parsedWord.syllablesCount(); i++) {
            LinkedHashSet<String> rimes = dict.get(parsedWord.getSyllable(i));
            if (rimes != null)
                result.addAll(rimes);
        }
        return result;
    }
}
