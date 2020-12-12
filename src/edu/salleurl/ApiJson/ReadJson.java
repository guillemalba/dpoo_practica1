package edu.salleurl.ApiJson;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadJson {
    private Gson g;
    private Gson g2;

    public ReadJson () {
        g = new Gson();
        g2 = new Gson();
    }

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
}
