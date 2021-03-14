package edu.salleurl.Logic;

import java.lang.reflect.Array;
import java.util.LinkedList;

/**
 * Classe on es defineix una llista on hi seran tots els versos i tota la informacio de batalles.json
 */
public class Tema {
    private String name = null;
    private LinkedList<Rima> rhymes = new LinkedList<>();

    /**
     * Retornar el nom del tema
     * @return String del nom
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna els versos
     * @return LinkedList<Rima> dels versos
     */
    public LinkedList<Rima> getRhymes() {
        return rhymes;
    }
}
