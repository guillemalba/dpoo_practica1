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
        puntuacio += (16 + 2 + 128 + 64 + 256 + 4 + 32 + 512 + 1024 + numRimas)/(1024 + 128 + 4 + 64 + 16 + 256 + numRimas + 2 + 32 + 512) + 3 * numRimas;
        System.out.println("Escrita " + puntuacio);
        System.out.println("FUNCIONAAAAAAAAAAAA");
        return puntuacio;
    }
}
