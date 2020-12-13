package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;
import java.util.*;
import java.lang.Math;

public class Batalla {
    private static final double PI = 3.1415926535898;

    private JsonFileBatalla jsonFileBatalla;
    private JsonFileCompeticio jsonFileCompeticio;

    public Batalla (JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
        this.jsonFileBatalla = jsonFileBatalla;
        this.jsonFileCompeticio = jsonFileCompeticio;
    }



    public void startBattle(String usuari, String contrincant, String tipusBatalla) {
        Random random = new Random();
        int index = random.nextInt(jsonFileBatalla.getThemes().size());
        int randUser = random.nextInt(2);
        String rimas[] = new String[4];
        int numRimasUsuari = 0;
        int numRimasContrincant = 0;

        double puntsUsuari = 0;
        double puntsContrincant = 0;

        jsonFileBatalla.getThemes().get(index);
        System.out.println("-----------------------------------------------------------\n");
        System.out.println("Topic: " + jsonFileBatalla.getThemes().get(index).getName());
        System.out.println("");
        System.out.println("A coin is tossed in the air and...");

        System.out.println("randUser: " + randUser);

        //randUser = 1;   // esborra aquesta linea
        switch (randUser) {
            case 1:
                for (int i = 0; i < 2; i++) {
                    System.out.println(usuari + " your turn! Drop it!\n");
                    System.out.println(usuari + ":\n");
                    Scanner rimaUser = new Scanner(System.in);
                    rimas[0] = rimaUser.nextLine();
                    rimas[1] = rimaUser.nextLine();
                    rimas[2] = rimaUser.nextLine();
                    rimas[3] = rimaUser.nextLine();
                    /*
                    System.out.println("rima1: " + rimas[0]);
                    System.out.println("rima2: " + rimas[1]);
                    System.out.println("rima3: " + rimas[2]);
                    System.out.println("rima4: " + rimas[3]);
                    */

                    // torn del contrincant
                    System.out.println(contrincant + " your turn! Drop it\n");
                    System.out.println(contrincant + ":\n");

                    //System.out.println("lenght: " + jsonFileCompeticio.getRappers().size());

                    int level = 0;

                    for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
                        if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(usuari)) {
                            /*
                            System.out.println("meu: " + usuari);
                            System.out.println("jsonUser: " + jsonFileCompeticio.getRappers().get(j).getStageName());
                            System.out.println("lvl es: " + jsonFileCompeticio.getRappers().get(j).getLevel());
                            System.out.println("index es: " + index);
                            */
                            level = jsonFileCompeticio.getRappers().get(j).getLevel();
                            //level = 1; // delete this line
                            switch (level) {
                                case 1:
                                    if (i < jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_1().size()) {
                                        rimas = jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_1().get(i).split("\n");

                                        System.out.println("" + rimas[0]);
                                        System.out.println("" + rimas[1]);
                                        System.out.println("" + rimas[2]);
                                        System.out.println("" + rimas[3]);
                                        System.out.println("");
                                    } else {
                                        System.out.println("M'he quedat en blanc\n");
                                    }

                                    break;
                                case 2:
                                    if (i < jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_2().size()) {
                                        rimas = jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_2().get(i).split("\n");

                                        System.out.println("" + rimas[0]);
                                        System.out.println("" + rimas[1]);
                                        System.out.println("" + rimas[2]);
                                        System.out.println("" + rimas[3]);
                                        System.out.println("");
                                    } else {
                                        System.out.println("M'he quedat en blanc\n");
                                    }
                                    break;
                            }
                        }
                    }

                    //calcula puntuacio

                    switch (tipusBatalla) {
                        case "acapella":
                            puntsContrincant += (6 * Math.sqrt(numRimasContrincant) + 3)/2;
                            puntsUsuari += (6 * Math.sqrt(numRimasUsuari) + 3)/2;
                            break;
                        case "sangre":
                            puntsContrincant += (PI * (numRimasContrincant * numRimasContrincant))/ 4;
                            puntsUsuari += (PI * (numRimasUsuari * numRimasUsuari))/ 4;
                            break;
                        case "escrita":
                            puntsContrincant += (16 + 2 + 128 + 64 + 256 + 4 + 32 + 512 + 1024 + numRimasContrincant)/(1024 + 128 + 4 + 64 + 16 + 256 + i + 2 + 32 + 512) + 3 * numRimasContrincant;
                            puntsUsuari += (16 + 2 + 128 + 64 + 256 + 4 + 32 + 512 + 1024 + numRimasUsuari)/(1024 + 128 + 4 + 64 + 16 + 256 + i + 2 + 32 + 512) + 3 * numRimasUsuari;
                            break;
                    }
                }
                /*
                LinkedList<String> ter = new LinkedList<>();
                LinkedList<Integer> pun = new LinkedList<>();

                for (String rima: rimas){
                    if(ter.contains(rima.substring(rima.length()-2))){
                        int index = ter.indexOf(rima.substring(rima.length()-2));
                        pun.set(index,pun.get(index)+1);
                    } else {
                        ter.add(rima.substring(rima.length()-2));
                        pun.add(1);
                    }
                }
                */

                break;
            case 0:

                for (int i = 0; i < 2; i++) {
                    // torn del contrincant
                    System.out.println(contrincant + " your turn! Drop it\n");
                    System.out.println(contrincant + ":\n");

                    //System.out.println("lenght: " + jsonFileCompeticio.getRappers().size());

                    int level = 0;

                    for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
                        if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(usuari)) {
                            /*
                            System.out.println("meu: " + usuari);
                            System.out.println("jsonUser: " + jsonFileCompeticio.getRappers().get(j).getStageName());
                            System.out.println("lvl es: " + jsonFileCompeticio.getRappers().get(j).getLevel());
                            System.out.println("index es: " + index);
                            */
                            level = jsonFileCompeticio.getRappers().get(j).getLevel();
                            //level = 1; // delete this line
                            switch (level) {
                                case 1:
                                    if (i < jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_1().size()) {
                                        rimas = jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_1().get(i).split("\n");

                                        System.out.println("" + rimas[0]);
                                        System.out.println("" + rimas[1]);
                                        System.out.println("" + rimas[2]);
                                        System.out.println("" + rimas[3]);
                                        System.out.println("");
                                    } else {
                                        System.out.println("M'he quedat en blanc\n");
                                    }

                                    break;
                                case 2:
                                    if (i < jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_2().size()) {
                                        rimas = jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_2().get(i).split("\n");

                                        System.out.println("" + rimas[0]);
                                        System.out.println("" + rimas[1]);
                                        System.out.println("" + rimas[2]);
                                        System.out.println("" + rimas[3]);
                                        System.out.println("");
                                    } else {
                                        System.out.println("M'he quedat en blanc\n");
                                    }
                                    break;
                            }
                        }
                    }
                    System.out.println(usuari + " your turn! Drop it!\n");
                    System.out.println(usuari + ":\n");
                    Scanner rimaUser = new Scanner(System.in);
                    rimas[0] = rimaUser.nextLine();
                    rimas[1] = rimaUser.nextLine();
                    rimas[2] = rimaUser.nextLine();
                    rimas[3] = rimaUser.nextLine();
                    /*
                    System.out.println("rima1: " + rimas[0]);
                    System.out.println("rima2: " + rimas[1]);
                    System.out.println("rima3: " + rimas[2]);
                    System.out.println("rima4: " + rimas[3]);
                    */

                }
                break;
            default:
                System.out.println("Error on randUser!");
                break;
        }



    }

}
