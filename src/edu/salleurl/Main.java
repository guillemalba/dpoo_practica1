package edu.salleurl;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;
import edu.salleurl.ApiJson.ReadJson;
import edu.salleurl.Logic.Competicio;

/**
 * Classe on es fan les crides per a que l'exercici funcioni be
 */
public class Main {

    /**
     * Procediment principal de l'exercici.
     * @param args
     */
    public static void main(String[] args) {
        ReadJson readJsonBatalla = new ReadJson();
        ReadJson readJsonCompeticio = new ReadJson();
        JsonFileCompeticio jsonCompeticio = null;
        JsonFileBatalla jsonBatalla = null;

        jsonCompeticio = readJsonCompeticio.read();
        jsonBatalla = readJsonBatalla.read2();
        //no agafa bé les rimes

        int opcio = 0;
        boolean exit = false;
        if(jsonCompeticio != null && jsonBatalla != null) {
            Menu menu = new Menu(jsonCompeticio, jsonBatalla);
            menu.showCompeticio();
            menu.showMenu();
            menu.getOption();
        } else {
            System.out.println("Error.");
        }


    }
}
