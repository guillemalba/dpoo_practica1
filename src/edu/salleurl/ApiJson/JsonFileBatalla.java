package edu.salleurl.ApiJson;

import edu.salleurl.Logic.Tema;

import java.util.LinkedList;

/**
 * @Finalitat: Classe on es defineix la llista de temes on guardarem tots els temes de l fitxer batalles.json
 */
public class JsonFileBatalla {
    private LinkedList<Tema> themes = null;

    /**
     * @Finalitat: Retorna els temes del fitxer batalles.json
     * @Paràmetres: no
     * @Retorn: LinkedList<Tema>
     */
    public LinkedList<Tema> getThemes() {
        return themes;
    }
}
