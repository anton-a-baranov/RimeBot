package RimeBotPack.Rime;

import java.util.ArrayList;

/**
 * @author anton.a.baranov
 * created on 06.07.2017.
 *
 * Интерфейс для преставления основного функционала рифм
 */
public interface Rime {
    /**
     * @return строки с возможными рифмами к заданному слову
     */
    public ArrayList<String> getRime () throws Exception;
}
