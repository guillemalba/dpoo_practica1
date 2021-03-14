package edu.salleurl.ApiJson;

import edu.salleurl.Logic.Tema;

import java.util.LinkedList;

/**
 * Classe on es defineix la llista de temes on guardarem tots els temes de l fitxer batalles.json
 */
public class JsonFileBatalla {
    private LinkedList<Tema> themes = null;


    /**
     * Retorna els temes del fitxer batalles.json
     * @return llista de temes
     */
    public LinkedList<Tema> getThemes() {
        return themes;
    }
}
