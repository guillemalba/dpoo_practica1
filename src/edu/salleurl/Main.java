package edu.salleurl;

import edu.salleurl.ApiJson.ReadJson;
import edu.salleurl.Logic.Controller;

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

        Controller controller = new Controller(readJsonCompeticio.read(), readJsonBatalla.read2());
        controller.startProgram();

        //JsonFileCompeticio jsonCompeticio = null;
        //JsonFileBatalla jsonBatalla = null;

        /*
        jsonCompeticio = readJsonCompeticio.read();
        jsonBatalla = readJsonBatalla.read2();

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
*/

    }
}
