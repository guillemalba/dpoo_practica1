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
        int numerosFase1[] = RandomizeArray(0, jsonFileCompeticio.getRappers().size()-1);
        int guanyadorBatallaFase[] = new int[numerosFase1.length/2];
        Random r = new Random();
        for (int i = 0; i < numerosFase1.length; i++) {
            float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
            jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(random1);
        }
        jugadorGuanyadorBatallaFase1(numerosFase1, guanyadorBatallaFase);
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
        for (int j = 0; j < numerosFase1.length; j= j+2) {
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
