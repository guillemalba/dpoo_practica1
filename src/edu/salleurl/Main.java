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
        ReadJson readJsonWinner = new ReadJson();

        Controller controller = new Controller(readJsonCompeticio.read(), readJsonBatalla.read2(), readJsonWinner.read3());
        controller.startProgram();
    }
}
