package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

/**
 * @Finalitat: Classe on es defineixen les variables i s'implementen les funcions relacionades amb el tipus de batalla Acapella
 */
public class Acapella extends Batalla{

    private float puntuacio;

    public Acapella(JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
        super(jsonFileBatalla, jsonFileCompeticio);
    }

    /**
     * @Finalitat: Calcular la puntuacio de la funcio definida en el Controller, en cas que numero de rimes = -1, vol dir que el contrincant s'ha quedat en blanc i li assignem 0 punts
     * @Paràmetres: int numRimas
     * @Retorn: float
     */
    @java.lang.Override
    public float calculaPuntuacio(int numRimas) {
        if (numRimas == -1) {
            puntuacio = 0;
        } else {
            puntuacio = (float)(6 * Math.sqrt(numRimas) + 3)/2;
        }
        return puntuacio;
    }
}

