package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

public class Acapella extends Batalla{

    public Acapella(JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
        super(jsonFileBatalla, jsonFileCompeticio);
    }

    @java.lang.Override
    public float calculaPuntuacio(String tipusBatalla, int numRimas) {
        return super.calculaPuntuacio(tipusBatalla, numRimas);
    }
}
