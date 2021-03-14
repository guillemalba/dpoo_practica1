package edu.salleurl.ApiJson;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Classe on es defineixen les variables i funcions que serviran per llegir els fitxers .json
 */
public class ReadJson {
    private Gson g;
    private Gson g2;
    private Gson g3;

    public ReadJson () {
        g = new Gson();
        g2 = new Gson();
        g3 = new Gson();
    }

    /**
     * Llegeix i retorna tot el fitxer de competicio.json
     * @return JsonFileCompeticio
     */
    public JsonFileCompeticio read() {
        JsonFileCompeticio data = null;

        try {
            FileReader lector = new FileReader("files/competició.json");
            data = g.fromJson(lector, JsonFileCompeticio.class);

        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
        return data;
    }

    /**
     * Llegeix i retorna tot el fitxer de batalles.json
     * @return JsonFileBatalla
     */
    public JsonFileBatalla read2() {
        JsonFileBatalla data2 = new JsonFileBatalla();

        try {
            FileReader lector2 = new FileReader("files/batalles.json");
            data2 = g2.fromJson(lector2, JsonFileBatalla.class);

        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
        return data2;
    }

    /**
     * Llegeix i retorna tot el fitxer de winners.json
     * @return JsonFileWinner
     */
    public JsonFileWinner read3() {
        JsonFileWinner data3 = new JsonFileWinner();

        try {
            FileReader lector3 = new FileReader("files/winner.json");
            data3 = g3.fromJson(lector3, JsonFileWinner.class);

        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
        return data3;
    }
}
