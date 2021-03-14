package edu.salleurl;

import edu.salleurl.ApiJson.ReadJson;
import edu.salleurl.Logic.Controller;


/**
 * Classe on es fan les crides per a que l'exercici funcioni be
 */
public class Main {

    /**
     * Procediment principal de l'exercici.
     * @param args .
     */
    public static void main(String[] args) {
        ReadJson readJson = new ReadJson();

        Controller controller = new Controller(readJson.read(), readJson.read2(), readJson.read3());
        controller.startProgram();
    }
}
