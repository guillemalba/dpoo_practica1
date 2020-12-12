package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileCompeticio;

import java.util.Random;

public class Logica {
    private static final float MIN_PUNTUACIO = 0;
    private static final float MAX_PUNTUACIO = 50;

    private JsonFileCompeticio jsonFileCompeticio;

    public Logica (JsonFileCompeticio jsonFileCompeticio) {
        this.jsonFileCompeticio = jsonFileCompeticio;
    }

    public void fesParelles() {
        int top1 = 0;
        int top2 = -1;
        int numerosFase1[] = RandomizeArray(0, jsonFileCompeticio.getRappers().size()-1);
        int guanyadorBatallaFase[] = new int[numerosFase1.length/2];
        Random r = new Random();
        for (int i = 0; i < numerosFase1.length; i++) {
            float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
            jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(random1);
        }
        jugadorGuanyadorBatallaFase1(numerosFase1, guanyadorBatallaFase);

        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2) {
            // agafem el top1 i top2 de la fase1
            getTop1Top2(top1, top2, guanyadorBatallaFase);

            // top1 vs top2

        } else {
            if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
                int guanyadorBatallaFase2[] = new int[2];

                for (int i = 0; i < guanyadorBatallaFase.length; i++) {
                    float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
                    jsonFileCompeticio.getRappers().get(guanyadorBatallaFase[i]).setPuntuacio(random1);
                }

                jugadorGuanyadorBatallaFase1(guanyadorBatallaFase, guanyadorBatallaFase2);

                // agafem el top1 i top2 de la fase1
                getTop1Top2(top1, top2, guanyadorBatallaFase);
                System.out.println("top1: " + top1);
            }
        }
    }

    public void guanyadorFinal (int top1, int top) {

    }

    // agafem el top1 i top2
    public void getTop1Top2(int top1, int top2, int guanyadorBatallaFase[]) {

        for (int i = 0; i < guanyadorBatallaFase.length; i++) {
            if (top1 < guanyadorBatallaFase[i]) {
                top2 = top1;
                top1 = guanyadorBatallaFase[i];
            } else {
                if (top2 < guanyadorBatallaFase[i]) {
                    top2 = guanyadorBatallaFase[i];
                }
            }
        }
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

    public void jugadorGuanyadorBatallaFase1 (int numerosFase1[], int guanyadorBatallaFase[]) {
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

}
