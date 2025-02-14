package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

/**
 * Classe on es defineixen les variables i s'implementen les funcions relacionades amb el tipus de batalla Escrita
 */
public class Escrita extends Batalla{

    private int numSegonsMax;
    private float puntuacio;

    public Escrita(JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
        super(jsonFileBatalla, jsonFileCompeticio);
    }

    /**
     * Calcular la puntuacio de la funcio definida en el Controller, en cas que numero de rimes = -1, vol dir que el contrincant s'ha quedat en blanc i li assignem 0 punts
     * @param numRimas numero de rimes fetes
     * @return float de la puntuacio
     */
    @java.lang.Override
    public float calculaPuntuacio(int numRimas) {
        if (numRimas == -1) {
            puntuacio = 0;
        } else {
            puntuacio = 1 + 3 * numRimas;
        }
        return puntuacio;
    }
}
