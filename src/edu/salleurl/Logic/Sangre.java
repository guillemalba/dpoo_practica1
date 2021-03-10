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
        puntuacio += (PI * (numRimas * numRimas))/ 4;
        System.out.println("Sangre " + puntuacio);
        System.out.println("FUNCIONAAAAAAAAAAAA");
        return puntuacio;
    }
}
