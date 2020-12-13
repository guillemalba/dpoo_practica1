package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

import java.lang.reflect.Array;
import java.util.*;

public class Logica {
    private static final float MIN_PUNTUACIO = 0;
    private static final float MAX_PUNTUACIO = 50;

    private JsonFileCompeticio jsonFileCompeticio;
    private JsonFileBatalla jsonFileBatalla;
    int numerosFase1[];
    float guanyadorBatallaFase[];
    String usuari = null;
    String contrincant = null;
    int numBatalla = 1;
    int numFase = 1;

    public Logica (JsonFileCompeticio jsonFileCompeticio, JsonFileBatalla jsonFileBatalla) {
        this.jsonFileCompeticio = jsonFileCompeticio;
        this.jsonFileBatalla = jsonFileBatalla;
    }

    public void fesParelles(String usuari) {
        int top[] = new int[3];
        float aux = 0;
        boolean batallaAcabada = false;
        this.usuari = usuari;
        this.numerosFase1 = RandomizeArray(0, jsonFileCompeticio.getRappers().size()-1);
        this.guanyadorBatallaFase = new float[numerosFase1.length];
        Random r = new Random();

        if ((numBatalla == 1 || numBatalla == 2) && numFase == 1) {
            for (int i = 0; i < numerosFase1.length; i++) {
                float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
                if (jsonFileCompeticio.getRappers().get(numerosFase1[i]).getStageName().equalsIgnoreCase(usuari)) {
                    if (i % 2 == 0) {
                        jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(0);
                        jsonFileCompeticio.getRappers().get(numerosFase1[i + 1]).setPuntuacio(0);
                        contrincant = jsonFileCompeticio.getRappers().get(numerosFase1[i + 1]).getStageName();
                        i++;
                    } else {
                        jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(0);
                        jsonFileCompeticio.getRappers().get(numerosFase1[i - 1]).setPuntuacio(-aux);
                        contrincant = jsonFileCompeticio.getRappers().get(numerosFase1[i - 1]).getStageName();
                    }
                } else {
                    jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(random1);
                }
                aux = random1;
            }
            if (numBatalla == 2) {
                batallaAcabada = true;
            }
        }
        if (numFase == 1 && batallaAcabada == true){
            jugadorGuanyadorBatallaFase1();
        }
        /*else {
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2) {
            // agafem el top1 i top2 per la batalla final
            getTop1Top2(top);

            // top1 vs top2
            for (int i = 0; i < top.length; i++) {
                float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
                jsonFileCompeticio.getRappers().get(top[i]).setPuntuacio(random1);
            }
            getTop1Top2(top);


        } else {
            if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
                int guanyadorBatallaFase2[] = new int[2];

                for (int i = 0; i < guanyadorBatallaFase.length; i++) {
                    float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
                    //jsonFileCompeticio.getRappers().get(guanyadorBatallaFase[i]).setPuntuacio(random1);
                }

                // agafem el top1 i top2 per la batalla final
                getTop1Top2(top);

                // top1 vs top2
                for (int i = 0; i < top.length; i++) {
                    float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
                    jsonFileCompeticio.getRappers().get(top[i]).setPuntuacio(random1);
                }
                getTop1Top2(top);
            }
        }
        }*/
        for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            System.out.println(jsonFileCompeticio.getRappers().get(i).getPuntuacio());
        }
        /*
        System.out.println("KO1");
        System.out.println("top1: " + top[1]);
        System.out.println("top1 puntuacio: " + jsonFileCompeticio.getRappers().get(top[1]).getPuntuacio());
        System.out.println("top2: " + top[2]);
        System.out.println("top2 puntuacio: " + jsonFileCompeticio.getRappers().get(top[2]).getPuntuacio());
         */
    }

    // agafem el top1 i top2
    /*public void getTop1Top2(int top[]) {
        int top1 = 0;
        int top2 = 0;

        float puntuacioJugador;
        for (int i = 0; i < guanyadorBatallaFase.length; i++) {
            puntuacioJugador = jsonFileCompeticio.getRappers().get(guanyadorBatallaFase[i]).getPuntuacio();
            if (jsonFileCompeticio.getRappers().get(top1).getPuntuacio() < puntuacioJugador) {
                top2 = top1;
                top1 = guanyadorBatallaFase[i];
            } else {
                if (jsonFileCompeticio.getRappers().get(top2).getPuntuacio() < puntuacioJugador) {
                    top2 = guanyadorBatallaFase[i];
                }
            }
        }
        top[1] = top1;
        top[2] = top2;
    }*/

    public static int[] RandomizeArray(int a, int b){
        Random rgen = new Random();  // Random number generator
        int size = b-a+1;
        int[] array = new int[size];

        for(int i=0; i< size; i++){
            array[i] = a+i;
        }

        for (int i=0; i<array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
        return array;
    }

    public void jugadorGuanyadorBatallaFase1 () {
        int k = 0;
        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            guanyadorBatallaFase[j] = jsonFileCompeticio.getRappers().get(j).getPuntuacio();
        }
        Arrays.sort(guanyadorBatallaFase, Collections.reverseOrder());
        float[] topBatalla1 = Arrays.copyOfRange(guanyadorBatallaFase, 0, guanyadorBatallaFase.length/2);
        for (int i = 0; i < topBatalla1.length; i++) {
            System.out.println("hola");
            System.out.println(topBatalla1[i]);
        }
        /*for (int j = 0; j < numerosFase1.length; j = j+2) {
            if (jsonFileCompeticio.getRappers().get(numerosFase1[j]).getPuntuacio() < jsonFileCompeticio.getRappers().get(numerosFase1[j+1]).getPuntuacio()) {
                guanyadorBatallaFase[k] = numerosFase1[j+1];
            }
            else {
                guanyadorBatallaFase[k] = numerosFase1[j];
            }
            k++;
        }*/
    }

    //mostrem la info de la competicio
    public void showCompetiStatus() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("Phase: " + " / " + " | Score: " + " | Battle: " + " / 2: " + " | Rival: " + contrincant);
        System.out.println("-----------------------------------------------------------\n");
        System.out.println("1. Start the battle");
        System.out.println("2. Show ranking");
        System.out.println("3. Create profile");
        System.out.println("4. Leave competition");


    }
    public void getOptionLobby() {
        int opcio = 0;
        do {
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
                    Batalla batalla = new Batalla(jsonFileBatalla);
                    System.out.println("Batalla feta");
                    numBatalla = 2;
                    fesParelles(usuari);
                    break;
                case 2:

                    break;
                case 3:
                    System.out.println("This option is not active yet.");
                    break;
                case 4:
                    System.out.println("Thanks for your visit!");
                    break;
            }
        } while (opcio == 2 || opcio == 3);
    }
}
