package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

public class Acapella extends Batalla{

    private float puntuacio;

    public Acapella(JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
        super(jsonFileBatalla, jsonFileCompeticio);
    }

    /*
     * @Finalitat: Calcular la puntuacio de la funcio definida en el Controller, en cas que numero de rimes = -1, vol dir que el contrincant s'ha quedat en blanc i li assignem 0 punts
     * @Paràmetres: int numRimas
     * @Retorn: float
     */
    @java.lang.Override
    public float calculaPuntuacio(int numRimas) {
        //System.out.println("NUM RIMAS: " + numRimas);
        if (numRimas == -1) {
            puntuacio = 0;
        } else {
            puntuacio = (float)(6 * Math.sqrt(numRimas) + 3)/2;
        }
        //System.out.println("Acapella " + puntuacio);
        return puntuacio;
    }
}

