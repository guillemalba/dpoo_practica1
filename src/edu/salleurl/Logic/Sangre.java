package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

public class Sangre extends Batalla{

    private static final double PI = 3.1415926535898;

    private String nomProductor;
    private float puntuacio;


    public Sangre(JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
        super(jsonFileBatalla, jsonFileCompeticio);
    }

    @java.lang.Override
    public float calculaPuntuacio(int numRimas) {
        //System.out.println("NUM RIMAS: " + numRimas);
        if (numRimas == -1) {
            puntuacio = 0;
        } else {
            puntuacio = (float)(PI * (numRimas * numRimas)/4);
        }
        //System.out.println("Sangre " + puntuacio);
        return puntuacio;
    }
}
