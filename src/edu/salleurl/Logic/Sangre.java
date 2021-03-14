package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

/**
 * Classe on es defineixen les variables i s'implementen les funcions relacionades amb el tipus de batalla Sangre
 */
public class Sangre extends Batalla{

    private static final double PI = 3.1415926535898;

    private String nomProductor;
    private float puntuacio;

    public Sangre(JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
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
            puntuacio = (float)(PI * (numRimas * numRimas)/4);
        }
        return puntuacio;
    }
}
