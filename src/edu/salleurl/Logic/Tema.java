package edu.salleurl.Logic;

import java.lang.reflect.Array;
import java.util.LinkedList;

/**
 * @Finalitat: Classe on es defineix una llista on hi seran tots els versos i tota la informacio de batalles.json
 */
public class Tema {
    private String name = null;
    private LinkedList<Rima> rhymes = new LinkedList<>();

    /**
     * @Finalitat: Retornar el nom del tema
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getName() {
        return name;
    }

    /**
     * @Finalitat: Retorna els versos
     * @Paràmetres: no
     * @Retorn: LinkedList<Rima>
     */
    public LinkedList<Rima> getRhymes() {
        return rhymes;
    }
}
