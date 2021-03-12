package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

public class Escrita extends Batalla{

    private int numSegonsMax;
    private float puntuacio;

    public Escrita(JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
        super(jsonFileBatalla, jsonFileCompeticio);
    }

    @java.lang.Override
    public float calculaPuntuacio(int numRimas) {
        //System.out.println("NUM RIMAS: " + numRimas);
        puntuacio = 1 + 3 * numRimas;
        //System.out.println("Escrita " + puntuacio);
        return puntuacio;
    }
}
