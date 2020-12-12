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

        float puntsTop[] = new float[3];
        int top[] = new int[3];
        int numerosFase1[] = RandomizeArray(0, jsonFileCompeticio.getRappers().size()-1);
        int guanyadorBatallaFase[] = new int[numerosFase1.length/2];
        Random r = new Random();

        for (int i = 0; i < numerosFase1.length; i++) {
            float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
            jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(random1);
        }
        jugadorGuanyadorBatallaFase1(numerosFase1, guanyadorBatallaFase);

        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2) {
            // agafem el top1 i top2 per la batalla final
            getTop1Top2(top, guanyadorBatallaFase);

            // top1 vs top2
            for (int i = 0; i < top.length; i++) {
                float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
                jsonFileCompeticio.getRappers().get(top[i]).setPuntuacio(random1);
            }
            getTop1Top2(top, guanyadorBatallaFase);


        } else {
            if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
                int guanyadorBatallaFase2[] = new int[2];

                for (int i = 0; i < guanyadorBatallaFase.length; i++) {
                    float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
                    jsonFileCompeticio.getRappers().get(guanyadorBatallaFase[i]).setPuntuacio(random1);
                }

                // agafem el top1 i top2 per la batalla final
                getTop1Top2(top, guanyadorBatallaFase);

                // top1 vs top2
                for (int i = 0; i < top.length; i++) {
                    float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
                    jsonFileCompeticio.getRappers().get(top[i]).setPuntuacio(random1);
                }
                getTop1Top2(top, guanyadorBatallaFase);
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
    public void getTop1Top2(int top[], int guanyadorBatallaFase[]) {
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
