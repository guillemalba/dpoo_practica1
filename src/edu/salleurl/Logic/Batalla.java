package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;
import java.util.*;
import java.lang.Math;

public abstract class Batalla {

    private final JsonFileBatalla jsonFileBatalla;
    private final JsonFileCompeticio jsonFileCompeticio;

    public Batalla (JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
        this.jsonFileBatalla = jsonFileBatalla;
        this.jsonFileCompeticio = jsonFileCompeticio;
    }

    public void startBattle(String usuari, String contrincant, String tipusBatalla) {
        Random random = new Random();
        int index = random.nextInt(jsonFileBatalla.getThemes().size());
        int randUser = random.nextInt(2);
        String[] rimas;
        int numRimasUsuari;
        int numRimasContrincant;

        float puntsUsuari = 0;
        float puntsContrincant = 0;

        jsonFileBatalla.getThemes().get(index);
        System.out.println("-----------------------------------------------------------\n");
        System.out.println("Topic: " + jsonFileBatalla.getThemes().get(index).getName());
        System.out.println();
        System.out.println("A coin is tossed in the air and...");

        //randUser = 1;   // esborra aquesta linea
        switch (randUser) {
            case 1:
                for (int i = 0; i < 2; i++) {
                    // Comença usuari
                    rimas = demanaVersosUsuari (usuari);
                    numRimasUsuari = getNumRimas (rimas);

                    // torn del contrincant
                    rimas = mostraVersosContrincant (usuari, index, i, contrincant);
                    numRimasContrincant = getNumRimas (rimas);

                    //calcula puntuacio
                    System.out.println();
                    //System.out.println("Usuari: ");
                    puntsUsuari += calculaPuntuacio(numRimasUsuari);
                    //System.out.println("Contrincant: ");
                    puntsContrincant += calculaPuntuacio(numRimasContrincant);
                }
                break;

            case 0:
                for (int i = 0; i < 2; i++) {
                    // Comença el contrincant
                    rimas = mostraVersosContrincant (usuari, index, i, contrincant);
                    numRimasContrincant = getNumRimas (rimas);

                    // torn del usuari
                    rimas = demanaVersosUsuari (usuari);
                    numRimasUsuari = getNumRimas (rimas);

                    //calcula puntuacio
                    System.out.println();
                    //System.out.println("User: ");
                    puntsUsuari += calculaPuntuacio(numRimasUsuari);
                    //System.out.println("Contrincant: ");
                    puntsContrincant += calculaPuntuacio(numRimasContrincant);

                }
                break;
            default:
                System.out.println("Error on randUser!");
                break;
        }
        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(usuari)) {
                jsonFileCompeticio.getRappers().get(j).setPuntuacio(puntsUsuari);
            }
            if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(contrincant)) {
                jsonFileCompeticio.getRappers().get(j).setPuntuacio(puntsContrincant);
            }
        }
    }

    public abstract float calculaPuntuacio (int numRimas);

    public int getNumRimas (String[] rimas) {
        LinkedList<String> lettersRimas = new LinkedList<>();
        LinkedList<Integer> numRimas = new LinkedList<>();
        int numRimasUsuari = 0;
        int indexRima = 0;
        boolean rimaOK;

        /*
        System.out.println("rima0: " + rimas[0]);
        System.out.println("rima1: " + rimas[1]);
        System.out.println("rima2: " + rimas[2]);
        System.out.println("rima3: " + rimas[3]);
        */
        if (rimas[0].equalsIgnoreCase("M'he quedat en blanc")) {
            numRimasUsuari = -1;    // assignem -1 perque el contrincant s'ha quedat en blanc, aquest valor es tindrà en compte a l'hora de calcular la puntuacio
        } else {
            for (String rima: rimas) {
                System.out.println("Lenght is: " + rima.length());
                if (rima.endsWith(",") || rima.endsWith(".")) {
                    if (rima.length() >= 3) {
                        rimaOK = true;
                        rima = rima.substring(0, rima.length() - 1);
                    } else {
                        rimaOK = false;
                    }
                } else {
                    if (rima.length() >= 2) {
                        rimaOK = true;
                    } else {
                        rimaOK = false;
                    }
                }
                if (rimaOK) {
                    if (lettersRimas.contains(rima.substring(rima.length()-2))) {
                        indexRima = lettersRimas.indexOf(rima.substring(rima.length()-2));
                        numRimas.set(indexRima,numRimas.get(indexRima)+1);
                    } else {
                        lettersRimas.add(rima.substring(rima.length()-2));
                        numRimas.add(1);
                    }
                }
            }
            for (int j = 0; j < numRimas.size(); j++) {
                if (numRimas.get(j) > 1) {
                    numRimasUsuari += numRimas.get(j);
                }
            }
        }

        //System.out.println("Num Rimas totals: " + numRimas);
        //System.out.println("NUM RIMAS: " + numRimasUsuari);
        return numRimasUsuari;
    }

    public String[] mostraVersosContrincant (String usuari, int index, int i, String contrincant) {
        boolean end = false;
        int level;
        String[] rimas = new String[4];

        System.out.println(contrincant + " your turn! Drop it\n");
        System.out.println(contrincant + ":\n");

        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(usuari)) {
                level = jsonFileCompeticio.getRappers().get(j).getLevel();
                //level = 1; // delete this line
                switch (level) {
                    case 1:
                        if (i < jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_1().size()) {
                            rimas = jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_1().get(i).split("\n");
                        } else {
                            System.out.println("M'he quedat en blanc\n");
                            rimas[0] = "M'he quedat en blanc";
                            end = true;
                        }

                        break;
                    case 2:
                        if (i < jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_2().size()) {
                            rimas = jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_2().get(i).split("\n");
                        } else {
                            System.out.println("M'he quedat en blanc\n");
                            rimas[0] = "M'he quedat en blanc";
                            end = true;
                        }
                        break;
                }
                if (!end) {
                    System.out.println(rimas[0]);
                    System.out.println(rimas[1]);
                    System.out.println(rimas[2]);
                    System.out.println(rimas[3]);
                    System.out.println();
                }
            }
        }
        return rimas;
    }

    public String[] demanaVersosUsuari (String usuari) {
        String[] rimas = new String[4];

        System.out.println(usuari + " your turn! Drop it!\n");
        System.out.println(usuari + ":\n");
        Scanner rimaUser = new Scanner(System.in);
        rimas[0] = rimaUser.nextLine();
        rimas[1] = rimaUser.nextLine();
        rimas[2] = rimaUser.nextLine();
        rimas[3] = rimaUser.nextLine();

        return rimas;
    }

}
