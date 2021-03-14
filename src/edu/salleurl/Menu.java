package edu.salleurl;

import edu.salleurl.ApiJson.JsonFileCompeticio;
import edu.salleurl.ApiJson.JsonFileWinner;
import edu.salleurl.Logic.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Classe trobem totes les funcions relacionades amb els menus i scans per tractar la informacio introduida per l'usuari
 */
public class Menu {
    int opcioCompetition = 0;
    String usuari = null;           // nom del usuari
    String contrincant = null;      // nom del contrincant
    String tipusBatalla = null;     // nom del tipus de batalla

    public Menu () {}

    /**
     * Mostra les dades de la competicio actual
     * @param raperos LinkedList<Rapero> dels raperos
     * @param competicio info de la competicio
     */
    public void showCompeticio (LinkedList<Rapero> raperos, Competicio competicio) {
        LocalDate today = LocalDate.now();

        System.out.println("Welcome to competition: " + competicio.getName());
        System.out.println("Starts on " + competicio.getStartDate());
        System.out.println("Ends on " + competicio.getEndDate());
        System.out.println("Phases: " + competicio.getPhases().size());
        System.out.println("Currently: " + raperos.size() + " participants");

        Date date1 = java.sql.Date.valueOf(competicio.getStartDate());
        Date date2 = java.sql.Date.valueOf(competicio.getEndDate());
        Date todayDate = java.sql.Date.valueOf(today);

        if (date1.equals(todayDate) || date1.before(todayDate)) {
            if (date2.after(todayDate)) {
                System.out.println("\nCompetition started. Do you want to:");
                opcioCompetition = 1;
            }
            else {
                opcioCompetition = 2;
            }
        }
        if (date1.after(todayDate)) {
            System.out.println("\nCompetition hasn't started yet. Do you want to:");
            opcioCompetition = 3;
        }
    }

    /**
     * Mostra el menu inicial d'abans d'iniciar la competicio, en el moment que ja ha comencat i un cop hagi acabat, en aquest ultim cas, es mostrara el  nom  del guanyador de la competicio
     * @param jsonFileWinner classe per fer servir el metode i escriure el nom del guanyador
     * @param winner String amb el nom del guanyador
     */
    public void showMenu (JsonFileWinner jsonFileWinner, String winner) {
        switch (opcioCompetition) {
            case 3:
                System.out.println("\n1. Register");
                System.out.println("2. Leave");
            break;
            case 1:
                System.out.println("\n1. Login");
                System.out.println("2. Leave");
            break;
            case 2:
                if (jsonFileWinner.getName() == null) {
                    System.out.println("\nThe winner of the competiton was: " + winner);
                }
                else {
                    System.out.println("\nThe winner of the competiton was: " + jsonFileWinner.getName());
                }
                System.out.println("\nPress 2 if you want to leave");
            break;
        }

    }

    /**
     * Guardar la opcio del primer menu abans de la lobby, controlar la informacio i retornar el nom del usuari que jugara
     * @param jsonFileCompeticio Classe del json de la competicio per fer login o registrar el rapero
     * @return String del nom del usuari
     */
    public String getOption(JsonFileCompeticio jsonFileCompeticio) {
        int opcio = 0;
        do {
            System.out.println("\nChoose an option: ");
            Scanner reader = new Scanner(System.in);
            try {
                opcio = reader.nextInt();
                if (opcio > 2|| opcio < 0) {
                    System.out.println("ERROR: Nomes pots insertar numeros del 1 al 2.");
                }

            } catch (InputMismatchException ime) {
                System.out.println("ERROR: Nomes pots insertar numeros.");
                reader.next();
            }
            if (opcio == 2) {
                System.out.println("Thanks for your visit!");
            }
            if (opcio == 1 && opcioCompetition == 3) {
                jsonFileCompeticio.registerRapero();
                System.out.println("\nThanks for your visit!");
            }
            if (opcio == 1 && opcioCompetition == 1) {
                usuari = jsonFileCompeticio.loginRapero();
            }
        } while (opcio != 1 && opcio != 2);

        return usuari;
    }

    /**
     * Mostrar la info de la competicio amb la batalla i la fase actual, la puntuacio i el rival
     * @param raperos LLista de raperos
     * @param competicio info de la competicio
     * @param contrincant nom del contrincant
     * @param numFase index de la fase
     * @param numBatalla index de la batalla
     * @param indexUsuari index del usuari
     * @return int de la opcio
     */
    public int showCompetiStatus(LinkedList<Rapero> raperos, Competicio competicio, String contrincant, int numFase, int numBatalla, int indexUsuari) {
        this.contrincant = contrincant;
        Random tipusBatallaRandom = new Random();
        int tipus = tipusBatallaRandom.nextInt(3);
        switch (tipus) {
            case 0:
                tipusBatalla = "acapella";
                break;
            case 1:
                tipusBatalla = "sangre";
                break;
            case 2:
                tipusBatalla = "escrita";
                break;
        }
        int opcio = 0;
        System.out.println("\n-----------------------------------------------------------");
        System.out.println("Phase: " + numFase + " / " + competicio.getPhases().size() +  " | Score: " + raperos.get(indexUsuari).getPuntuacio() + " | Battle: " + numBatalla + " / 2 " + tipusBatalla + " | Rival: " + contrincant);
        System.out.println("-----------------------------------------------------------\n");
        System.out.println("1. Start the battle");
        System.out.println("2. Show ranking");
        System.out.println("3. Create profile");
        System.out.println("4. Leave competition");
        opcio = getOptionLobby();
        return opcio;
    }

    /**
     * Mostrar el guanyador i el menu de la lobby un cop hagi acabat la competicio
     * @param raperos Llista de raperos
     * @param guanyador index del guanyador
     * @param indexUsuari index del usuari
     * @return int de la opcio
     */
    public int showCompetiAcabada(LinkedList<Rapero> raperos, int guanyador, int indexUsuari) {
        int opcio = 0;
        String phrase;

        if (guanyador == indexUsuari) {
            phrase = "Congrats bro! You've WON the competition!";
        } else {
            phrase = "You've lost kid, I'm sure you'll do better next time...";
        }
        System.out.println("\n-----------------------------------------------------------");
        System.out.println("Winner: " + raperos.get(guanyador).getStageName() + " | Score: " + raperos.get(guanyador).getPuntuacio() + " | " + phrase);
        System.out.println("-----------------------------------------------------------\n");
        System.out.println("1. Start the battle (deactivated)");
        System.out.println("2. Show ranking");
        System.out.println("3. Create profile");
        System.out.println("4. Leave competition");
        opcio = getOptionLobby();
        return opcio;
    }

    /**
     * Llegir el valor de la opcio introduida per l'usuari, comprovar errors i retornar el valor
     * @return int de la opcio
     */
    public int getOptionLobby() {
        int opcio = 0;
        System.out.println("\nChoose an option: ");
        Scanner reader = new Scanner(System.in);
        try {
            opcio = reader.nextInt();
            if (opcio > 4 || opcio < 1) {
                System.out.println("Nomes pots insertar numeros del 1 al 4.\n");
            }

        } catch (InputMismatchException ime) {
            System.out.println("Nomes pots insertar numeros.\n");
            reader.next();
        }
        return opcio;
    }

    /**
     * Retornar el tipus de batalla (acapella, escrita o sangre)
     * @return String del tipus de batalla (acapella, escrita, sangre)
     */
    public String getTipusBatalla() {
        return tipusBatalla;
    }

    /**
     * Retornar si la competicio ha de començar, ha començat o ja ha acabat
     * @return int de la opcio
     */
    public int getOpcioCompetition() {
        return opcioCompetition;
    }
}
