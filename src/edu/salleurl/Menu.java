package edu.salleurl;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;
import edu.salleurl.Logic.*;

import java.time.LocalDate;
import java.util.*;

public class Menu {
    int opcioCompetition = 0;

    public Menu () {}

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

    public void showMenu () {
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
                System.out.println("The winner of the competiton is: ------");
                System.out.println("Press 2 if you want to leave");
            break;
        }

    }

    public boolean getOption(LinkedList<Rapero> raperos, LinkedList<String> paisos, Competicio competicio, String usuari) {
        int opcio = 0;
        boolean start = false;
        System.out.println("\nChoose an option: ");
        Scanner reader = new Scanner(System.in);
        try {
            opcio = reader.nextInt();
            if (opcio > 2|| opcio < 0) {
                System.out.println("Nomes pots insertar numeros del 1 al 2.\n");
            }

        } catch (InputMismatchException ime) {
            System.out.println("Nomes pots insertar numeros.\n");
            reader.next();
        }
        if (opcio == 2) {
            System.out.println("Thanks for your visit!");
        }
        if (opcio == 1 && opcioCompetition == 3) {
            competicio.setRaperos(raperos);
            competicio.setPaisos(paisos);
            competicio.registerRapero();
            System.out.println("\nThanks for your visit!");
        }
        if (opcio == 1 && opcioCompetition == 1) {
            competicio.setRaperos(raperos);
            usuari = competicio.loginRapero();
            start = true;
        }
        return start;
    }

    //mostrem la info de la competicio
    public void showCompetiStatus(LinkedList<Rapero> raperos, Competicio competicio) {
        Random tipusBatallaRandom = new Random();
        int tipus = tipusBatallaRandom.nextInt(3);
        String tipusBatalla = null;
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
        do {
            System.out.println("\n-----------------------------------------------------------");
            System.out.println("Phase: " + numFase + " / " + competicio.getPhases().size() +  " | Score: " + raperos.get(indexUsuari).getPuntuacio() + " | Battle: " + numBatalla + " / 2 " + tipusBatalla + " | Rival: " + contrincant);
            System.out.println("-----------------------------------------------------------\n");
            System.out.println("1. Start the battle");
            System.out.println("2. Show ranking");
            System.out.println("3. Create profile");
            System.out.println("4. Leave competition");
            opcio = controller.getOptionLobby(tipusBatalla);
        } while (opcio != 4);

    }

    public void showCompetiAcabada(LinkedList<Rapero> raperos) {
        int opcio = 0;
        String phrase;
        if (guanyador == indexUsuari) {
            phrase = "Congrat broda! You've WON the competition!";
        } else {
            phrase = "You've lost kid, I'm sure you'll do better next time...";
        }
        // You've lost kid, I'm sure you'll do better next time...
        do {
            System.out.println("\n-----------------------------------------------------------");
            System.out.println("Winner: " + raperos.get(guanyador).getStageName() + " | Score: " + raperos.get(guanyador).getPuntuacio() + " | " + phrase);
            System.out.println("-----------------------------------------------------------\n");
            System.out.println("1. Start the battle (deactivated)");
            System.out.println("2. Show ranking");
            System.out.println("3. Create profile");
            System.out.println("4. Leave competition");
            opcio = getOptionLobby("");
        } while (opcio != 4);
    }

    public int getOptionLobby(String tipusBatalla) {
        int opcio = 0;
        boolean passaFase = false;
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
        switch (opcio) {
            case 1:
                //Batalla batalla = new Batalla(jsonFileBatalla, jsonFileCompeticio);
                Acapella acapella = new Acapella(jsonFileBatalla, jsonFileCompeticio);
                Sangre sangre = new Sangre(jsonFileBatalla, jsonFileCompeticio);
                Escrita escrita = new Escrita(jsonFileBatalla, jsonFileCompeticio);

                switch (numBatalla) {
                    case 1:
                        switch (tipusBatalla) {
                            case "acapella":
                                acapella.startBattle(usuari, contrincant, tipusBatalla);
                                break;
                            case "sangre":
                                sangre.startBattle(usuari, contrincant, tipusBatalla);
                                break;
                            case "escrita":
                                escrita.startBattle(usuari, contrincant, tipusBatalla);
                                break;
                        }
                        numBatalla = 2;
                        fesParelles(usuari);
                        break;

                    case 2:

                        switch (tipusBatalla) {
                            case "acapella":
                                acapella.startBattle(usuari, contrincant, tipusBatalla);
                                break;
                            case "sangre":
                                sangre.startBattle(usuari, contrincant, tipusBatalla);
                                break;
                            case "escrita":
                                escrita.startBattle(usuari, contrincant, tipusBatalla);
                                break;
                        }

                        jugadorGuanyadorBatallaFase1();
                        if (numFase == jsonFileCompeticio.getCompetition().getPhases().size() && acabat) {
                            opcio = 4;
                        }
                        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2) {
                            numFase = 3;
                            if (topBatalla1[0] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio() || topBatalla1[1] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                                numBatalla = 1;
                                numFase = 2;
                                acabat = true;
                            }
                            else {
                                fesParelles(usuari);
                                System.out.println("You're not one of the two best rappers. For you the competition is over!");
                                opcio = 4;
                            }
                        }
                        if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
                            if (numFase == 2) {
                                jugadorGuanyadorBatallaFase2();
                                numFase = 3;
                                if (topBatalla2[0] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio() || topBatalla2[1] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                                    numBatalla = 1;
                                    numFase = 3;
                                    acabat = true;
                                } else {
                                    fesParelles(usuari);
                                    System.out.println("You're not one of the two best rappers. For you the competition is over!");
                                    opcio = 4;
                                }
                            }
                            if (numFase == 1) {
                                numFase = 2;
                                for (int i = 0; i < topBatalla1.length && !passaFase; i++) {
                                    if (topBatalla1[i] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                                        passaFase = true;
                                    }
                                }
                                if (passaFase) {
                                    numBatalla = 1;
                                    fesParelles(usuari);
                                } else {
                                    numFase = 3;
                                    fesParelles(usuari);
                                    System.out.println("You're not one of the half best rappers. For you the competition is over!");
                                }
                            }
                        }
                        break;
                }
                break;

            case 2:
                System.out.println("-----------------------------------------------------------");
                System.out.println("Pos.  |  Name  |  Score ");
                System.out.println("-----------------------------------------------------------\n");
                ranquingCompeticio(numFase);
                break;

            case 3:
                System.out.println("This option is not active yet.");
                break;

            case 4:
                System.out.println("Thanks for your visit!");
                break;
        }
        return opcio;
    }
}
