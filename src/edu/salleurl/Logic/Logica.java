package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

import java.util.Random;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Logica {
    private static final float MIN_PUNTUACIO = 0;
    private static final float MAX_PUNTUACIO = 50;

    private JsonFileCompeticio jsonFileCompeticio;
    private JsonFileBatalla jsonFileBatalla;
    int numerosFase1[];
    int guanyadorBatallaFase[];
    int contrincant1 = 0;

    public Logica (JsonFileCompeticio jsonFileCompeticio, JsonFileBatalla jsonFileBatalla) {
        this.jsonFileCompeticio = jsonFileCompeticio;
        this.jsonFileBatalla = jsonFileBatalla;
        this.numerosFase1 = RandomizeArray(0, jsonFileCompeticio.getRappers().size()-1);
        this.guanyadorBatallaFase = new int[numerosFase1.length/2];
    }

    public void fesParelles(String usuari) {
        int top[] = new int[3];
        Random r = new Random();

        for (int i = 0; i < numerosFase1.length; i++) {
            float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
            if (jsonFileCompeticio.getRappers().get(numerosFase1[i]).getStageName().equalsIgnoreCase(usuari)) {
                if (i%2 == 0) {
                    jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(0);
                    jsonFileCompeticio.getRappers().get(numerosFase1[i + 1]).setPuntuacio(0);
                    i++;
                }
                else {
                    jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(0);
                    jsonFileCompeticio.getRappers().get(numerosFase1[i - 1]).setPuntuacio(0);
                }
            }
            else {
                jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(random1);
            }
        }
        jugadorGuanyadorBatallaFase1();

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
                    jsonFileCompeticio.getRappers().get(guanyadorBatallaFase[i]).setPuntuacio(random1);
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
        /*
        System.out.println("KO1");
        System.out.println("top1: " + top[1]);
        System.out.println("top1 puntuacio: " + jsonFileCompeticio.getRappers().get(top[1]).getPuntuacio());
        System.out.println("top2: " + top[2]);
        System.out.println("top2 puntuacio: " + jsonFileCompeticio.getRappers().get(top[2]).getPuntuacio());
         */
    }

    // agafem el top1 i top2
    public void getTop1Top2(int top[]) {
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
    }

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
        for (int j = 0; j < numerosFase1.length; j = j+2) {
            if (jsonFileCompeticio.getRappers().get(numerosFase1[j]).getPuntuacio() < jsonFileCompeticio.getRappers().get(numerosFase1[j+1]).getPuntuacio()) {
                guanyadorBatallaFase[k] = numerosFase1[j+1];
            }
            else {
                guanyadorBatallaFase[k] = numerosFase1[j];
            }
            k++;
        }
    }

    //mostrem la info de la competicio
    public void showCompetiStatus() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("Phase: " + " / " + " | Score: " + " | Battle: " + " / 2: " + " | Rival: ");
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
                    System.out.println(jsonFileBatalla.getThemes().get(0).getRhymes().get(0).getLevel1().get(0));
                    Batalla batalla = new Batalla(jsonFileBatalla);

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
