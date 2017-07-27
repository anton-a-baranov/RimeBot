package RimeBotPack.Rime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *  @author anton.a.baranov
 * created on 10.07.2017.
 *
 * Словарь рифм/слов русского языка.
 * Организован в виде синглтона, при инициализации загружает из файла словарь русских слов, преобразует его в два словаря - с ударениями и без.
 */
final class RussianWordRimesDictionary {
    private final static String DICTIONARY_FILE_PATH = "data\\dictionary.txt";
    private final RimesDictionary dictionaryEmphasis = new RimesDictionary();
    private final RimesDictionary dictionary = new RimesDictionary();

    public static class Singleton {
        private final static RussianWordRimesDictionary wordDictionary = new RussianWordRimesDictionary();

        public static RussianWordRimesDictionary getInstance() {
             return wordDictionary;
        }
    }

    private void load() {
        try {
            Path path = Paths.get(DICTIONARY_FILE_PATH);
            List<String> dictionaryFile = Files.readAllLines(path);
            System.out.println(dictionaryFile.size() + " строк успешно загружены в словарь");
            for(String row: dictionaryFile) {
                ParsedRimeWord parsedWordEmph = extractWordWithEmphasis(row);
                dictionaryEmphasis.addWord(parsedWordEmph);

                ParsedRimeWord parsedWord = extractWordWithoutEmphasis(row);
                dictionary.addWord(parsedWord);
            }
            System.out.println(dictionaryEmphasis.size() + " окончаний с ударениями добавлено в словарь");
            System.out.println(dictionary.size() + " окончаний без ударений добавлено в словарь");
        }
        catch (IOException exc) {
            System.out.println("ERROR: Невозможно загрузить файл " + DICTIONARY_FILE_PATH);
        }
    }
    private ParsedRimeWord extractWordWithEmphasis(String row) {
        String word = extractWordFromRow(row);
        return new ParsedRimeWord(word);
    }

    private ParsedRimeWord extractWordWithoutEmphasis(String row) {
        String word = removeEmphasis(extractWordFromRow(row));
        return new ParsedRimeWord(word);
    }

    private String extractWordFromRow(String row) {
        int right = row.indexOf('%');
        int left = row.indexOf('#');
        return row.substring(left + 1, right);
    }

    private String removeEmphasis(String word) {
        return word.replaceAll("`", "");
    }

    private RussianWordRimesDictionary() {
        super();
        load();
    }

    public ArrayList<String> getRime(String word) {
        if(WordParse.emphasisCount(word) == 0)
            return findWithoutEmphasis(word);
        else
            return findWithEmphasis(word);
    }

    private ArrayList<String> findWithoutEmphasis(String word) {
        return findRimes(dictionary, word);
    }

    private ArrayList<String> findWithEmphasis(String word) {
        return findRimes(dictionaryEmphasis, word);
    }

    private ArrayList<String> findRimes(RimesDictionary dict, String word) {
        ArrayList<String> result = new ArrayList<>();
        ParsedRimeWord parsedWord = new ParsedRimeWord(word);

        for(int i = 0; i < parsedWord.suffixesCount(); i++) {
            LinkedHashSet<String> rimes = dict.getRimes(parsedWord.getSuffix(i));
            if (rimes != null)
                result.addAll(rimes);
        }
        return result;
    }
}
