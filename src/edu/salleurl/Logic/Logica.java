package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

import java.util.*;

public class Logica {
    private static final float MAX_PUNTUACIO = 30;

    private JsonFileCompeticio jsonFileCompeticio;
    private JsonFileBatalla jsonFileBatalla;
    int numerosFase1[];
    int numerosFase2[];
    int guanyadorBatallaFase[];
    int topBatalla1[];
    int topBatalla2[];
    int topPosicio[];
    String nomsFase2[];
    String usuari = null;
    String contrincant = null;
    int guanyador = 0;
    int numBatalla = 1;
    int numFase = 1;
    int indexUsuari = 0;
    boolean acabat = false;

    public Logica (JsonFileCompeticio jsonFileCompeticio, JsonFileBatalla jsonFileBatalla) {
        this.jsonFileCompeticio = jsonFileCompeticio;
        this.jsonFileBatalla = jsonFileBatalla;
    }

    public void fesParelles(String usuari) {
        this.usuari = usuari;
        this.numerosFase1 = RandomizeArray(0, jsonFileCompeticio.getRappers().size()-1);
        this.guanyadorBatallaFase = new int[numerosFase1.length];

        if ((numBatalla == 1 || numBatalla == 2) && numFase == 1) {
            parellesFase1();
        }

        if (numFase == 3 && !acabat) {
            System.out.println("quan entrem?");
            // agafem el top1 i top2 per la batalla final
            topPosicio = getTop1Top2();

            // top1 vs top2
            for (int i = 0; i < 2; i++) {
                int random1 = (int) (Math.random() * MAX_PUNTUACIO);
                jsonFileCompeticio.getRappers().get(topPosicio[i]).setPuntuacio(random1);
            }

            //comprovem si a la fase final ha canviat el guanyador
            if (jsonFileCompeticio.getRappers().get(topPosicio[0]).getPuntuacio() > jsonFileCompeticio.getRappers().get(topPosicio[1]).getPuntuacio()) {
                guanyador = topPosicio[0];
            }
            else {
                guanyador = topPosicio[1];
            }
        }

        if ((numBatalla == 1 || numBatalla == 2) && numFase == 2 && jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
            this.nomsFase2 = new String[(guanyadorBatallaFase.length/2)];

            jugadorGuanyadorBatallaFase1();
            int j = 0;
            for (int i=0; i < topBatalla1.length-1; i++){
                if (topBatalla1[i] != topBatalla1[i+1]){
                    topBatalla1[j++] = topBatalla1[i];
                }
            }
            topBatalla1[j++] = topBatalla1[topBatalla1.length-1];

            int m = 0;
            for (int i = 0; i < j; i++) {
                for (int k = 0; k < jsonFileCompeticio.getRappers().size(); k++) {
                    if (jsonFileCompeticio.getRappers().get(k).getPuntuacio() == topBatalla1[i]) {
                        nomsFase2[m] = jsonFileCompeticio.getRappers().get(k).getStageName();
                        m++;
                    }
                }
            }
            parellesFase2();

        }
        /*for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            System.out.println(jsonFileCompeticio.getRappers().get(i).getPuntuacio());
        }*/
    }

    // agafem el top1 i top2
    public int[] getTop1Top2() {
        int top[] = new int[2];
        for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla1[0]) {
                top[0] = i;
            }
            if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla1[1]) {
                top[1]= i;
            }
        }
        return top;
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

    public void parellesFase1 () {
        int aux = 0;
        for (int i = 0; i < numerosFase1.length; i++) {
            int random1 = (int) (Math.random() * MAX_PUNTUACIO);
            if (jsonFileCompeticio.getRappers().get(numerosFase1[i]).getStageName().equalsIgnoreCase(usuari)) {
                indexUsuari = numerosFase1[i];
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
    }

    public void parellesFase2 () {
        int aux = 0;
        int index = 0;
        boolean seguent = false;
        this.numerosFase2 = RandomizeArray(0, nomsFase2.length-1);
        for (int i = 0; i < numerosFase2.length; i++) {
            int random1 = (int) (Math.random() * MAX_PUNTUACIO);
            if (nomsFase2[numerosFase2[i]].equalsIgnoreCase(usuari)) {
                for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
                    if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(usuari)) {
                        if (numerosFase2[i] % 2 == 0) {
                            jsonFileCompeticio.getRappers().get(j).setPuntuacio(0);
                            seguent = true;
                        } else {
                            jsonFileCompeticio.getRappers().get(j).setPuntuacio(0);
                            jsonFileCompeticio.getRappers().get(index).setPuntuacio(-aux);
                            contrincant = jsonFileCompeticio.getRappers().get(index).getStageName();
                        }
                    }
                }
            }
            else {
                for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
                    if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(nomsFase2[numerosFase2[i]])) {
                        jsonFileCompeticio.getRappers().get(j).setPuntuacio(random1);
                        index = j;
                    }
                    if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(nomsFase2[numerosFase2[i]]) && seguent) {
                        jsonFileCompeticio.getRappers().get(j).setPuntuacio(0);
                        contrincant = jsonFileCompeticio.getRappers().get(j).getStageName();
                        seguent = false;
                    }
                }
            }
            aux = random1;
        }
    }

    public void jugadorGuanyadorBatallaFase1 () {
        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            guanyadorBatallaFase[j] = jsonFileCompeticio.getRappers().get(j).getPuntuacio();
        }
        Arrays.sort(guanyadorBatallaFase);
        for( int i = 0; i < guanyadorBatallaFase.length/2; ++i ) {
            int temp = guanyadorBatallaFase[i];
            guanyadorBatallaFase[i] = guanyadorBatallaFase[guanyadorBatallaFase.length - i - 1];
            guanyadorBatallaFase[guanyadorBatallaFase.length - i - 1] = temp;
        }
        topBatalla1 = Arrays.copyOfRange(guanyadorBatallaFase, 0, guanyadorBatallaFase.length/2);
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2) {
            if (topBatalla1[0] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                    if (topBatalla1[1] == jsonFileCompeticio.getRappers().get(i).getPuntuacio()) {
                        contrincant = jsonFileCompeticio.getRappers().get(i).getStageName();
                    }
                }
            }
            if (topBatalla1[1] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                    if (topBatalla1[0] == jsonFileCompeticio.getRappers().get(i).getPuntuacio()) {
                        contrincant = jsonFileCompeticio.getRappers().get(i).getStageName();
                    }
                }
            }
        }
    }

    public void jugadorGuanyadorBatallaFase2 () {
        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            guanyadorBatallaFase[j] = jsonFileCompeticio.getRappers().get(j).getPuntuacio();
        }
        Arrays.sort(guanyadorBatallaFase);
        for( int i = 0; i < guanyadorBatallaFase.length/2; ++i ) {
            int temp = guanyadorBatallaFase[i];
            guanyadorBatallaFase[i] = guanyadorBatallaFase[guanyadorBatallaFase.length - i - 1];
            guanyadorBatallaFase[guanyadorBatallaFase.length - i - 1] = temp;
        }
        topBatalla2 = Arrays.copyOfRange(guanyadorBatallaFase, 0, guanyadorBatallaFase.length/2);
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
            if (topBatalla2[0] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                    if (topBatalla2[1] == jsonFileCompeticio.getRappers().get(i).getPuntuacio()) {
                        contrincant = jsonFileCompeticio.getRappers().get(i).getStageName();
                    }
                }
            }
            if (topBatalla2[1] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                    if (topBatalla2[0] == jsonFileCompeticio.getRappers().get(i).getPuntuacio()) {
                        contrincant = jsonFileCompeticio.getRappers().get(i).getStageName();
                    }
                }
            }
        }
    }

    public void ranquingCompeticio () {
        int puntuacions[] = new int[jsonFileCompeticio.getRappers().size()];

        //guardem les puntuacions en un array
        for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            puntuacions[i] = jsonFileCompeticio.getRappers().get(i).getPuntuacio();
        }

        //ordenem l'array de puntuacions de major a menor
        Arrays.sort(puntuacions);
        for( int i = 0; i < puntuacions.length/2; ++i ) {
            int temp = puntuacions[i];
            puntuacions[i] = puntuacions[puntuacions.length - i - 1];
            puntuacions[puntuacions.length - i - 1] = temp;
        }

        //ens quedem amb les puntuacions uniques
        int j = 0;
        for (int i=0; i < puntuacions.length-1; i++){
            if (puntuacions[i] != puntuacions[i+1]){
                puntuacions[j++] = puntuacions[i];
            }
        }
        puntuacions[j++] = puntuacions[puntuacions.length-1];

        //mostrem el ranquing
        int m = 0;
        for (int i = 0; i < j; i++) {
            for (int k = 0; k < jsonFileCompeticio.getRappers().size(); k++) {
                if (jsonFileCompeticio.getRappers().get(k).getPuntuacio() == puntuacions[i]) {
                    System.out.println(m + 1 + ". " + jsonFileCompeticio.getRappers().get(k).getStageName() + " - " + puntuacions[i]);
                    m++;
                }
            }
        }
    }

    //mostrem la info de la competicio
    public void showCompetiStatus() {
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
            System.out.println("Phase: " + numFase + " / " + jsonFileCompeticio.getCompetition().getPhases().size() +  " | Score: " + " | Battle: " + numBatalla + " / 2 " + tipusBatalla + " | Rival: " + contrincant);
            System.out.println("-----------------------------------------------------------\n");
            System.out.println("1. Start the battle");
            System.out.println("2. Show ranking");
            System.out.println("3. Create profile");
            System.out.println("4. Leave competition");
            opcio = getOptionLobby(tipusBatalla);
        } while (opcio != 4);

    }

    public void showCompetiAcabada() {
        int opcio = 0;
        do {
            System.out.println("\n-----------------------------------------------------------");
            System.out.println("Winner: " + jsonFileCompeticio.getRappers().get(guanyador).getStageName() + " | Score: " + jsonFileCompeticio.getRappers().get(guanyador).getPuntuacio() + " | Battle: ");
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
        String tipusBatalla2 = tipusBatalla;
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
                switch (numBatalla) {
                    case 1:
                        System.out.println("Batalla feta");
                        jsonFileCompeticio.getRappers().get(indexUsuari).setPuntuacio(60);
                        numBatalla = 2;
                        fesParelles(usuari);
                    break;

                    case 2:
                        System.out.println("Batalla feta");
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
                                    jsonFileCompeticio.getRappers().get(indexUsuari).setPuntuacio(40);
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
                    ranquingCompeticio();
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
