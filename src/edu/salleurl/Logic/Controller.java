package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;
import edu.salleurl.ApiJson.JsonFileWinner;
import edu.salleurl.Menu;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Controller {
    private static final float MAX_PUNTUACIO = 30;
    private JsonFileCompeticio jsonFileCompeticio;
    private JsonFileBatalla jsonFileBatalla;
    private JsonFileWinner jsonFileWinner;
    private Menu menu = new Menu();

    // Constants per pintar text
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    int numerosFase1[];
    int numerosFase2[];
    float guanyadorBatallaFase[];
    float topBatalla1[];
    float topBatalla2[];
    int topPosicio[];
    String nomsFase2[];
    String usuari = null;                       // nom del usuari
    String contrincant = null;                  // nom del contrincant
    String tipusBatalla = null;                 // nom del tipus de batalla (acapella, escrita,  sangre)
    int guanyador = 0;                          // index del guanyador
    int numBatalla = 1;                         // index de la batalla actual en que es troba
    int numFase = 1;                            // index de la fase en que es troba
    int indexUsuari = 0;                        // index del usuari
    boolean acabat = false;
    boolean faseAcabada = false;                // boolean que indica si ha acabat la fase
    boolean juga = true;

    public Controller(JsonFileCompeticio jsonFileCompeticio, JsonFileBatalla jsonFileBatalla, JsonFileWinner jsonFileWinner) {
        this.jsonFileCompeticio = jsonFileCompeticio;
        this.jsonFileBatalla = jsonFileBatalla;
        this.jsonFileWinner = jsonFileWinner;
    }

    /**
     * @Finalitat: Funcio principal del programa que s'executa en el main i controla la resta de funcions perque el programa s'executi correctament
     * @Paràmetres: no
     * @Retorn: no
     */
    public void startProgram() {
        int opcio = 0;
        if(jsonFileCompeticio != null && jsonFileBatalla != null) {
            menu.showCompeticio(jsonFileCompeticio.getRappers(), jsonFileCompeticio.getCompetition());
            menu.showMenu(jsonFileWinner);
            usuari = menu.getOption(jsonFileCompeticio.getRappers(), jsonFileCompeticio.getCountries(), jsonFileCompeticio.getCompetition());
            if (usuari != null) {
                batallaRestaRaperos(usuari);
                do {
                    if (opcio == 1) {
                        if (juga) {
                            batallaUsuari();
                        }
                        if (faseAcabada && numFase == 2) {
                            jugadorGuanyadorBatallaFase1();
                            faseAcabada = false;
                        }
                        if (faseAcabada && numFase == 3) {
                            jugadorGuanyadorBatallaFase2();
                            faseAcabada = false;
                        }
                        batallaRestaRaperos(usuari);
                    }
                    if (opcio == 2) {
                        ranquingCompeticio();
                    }
                    if (juga) {
                        opcio = menu.showCompetiStatus(jsonFileCompeticio.getRappers(), jsonFileCompeticio.getCompetition(), contrincant, numFase, numBatalla, indexUsuari);
                    } else {
                        opcio = 1;
                    }
                    if (opcio != 2) {
                        if (numBatalla == 1) {
                            numBatalla = 2;
                        }
                        else {
                            canviarFase();
                        }

                    }
                    /*
                    if (opcio == 4) {
                        acabat = true;
                    }
                    */
                    tipusBatalla = menu.getTipusBatalla();
                } while (!acabat && opcio != 4);
                menu.showCompetiAcabada(jsonFileCompeticio.getRappers(), guanyador, indexUsuari);
            }
        } else {
            System.out.println("Error.");
        }
    }

    /**
     * @Finalitat: //TODO:
     * @Paràmetres: String usuari
     * @Retorn: no
     */
    public void batallaRestaRaperos(String usuari) {
        this.usuari = usuari;
        this.numerosFase1 = RandomizeArray(0, jsonFileCompeticio.getRappers().size()-1);
        this.guanyadorBatallaFase = new float[numerosFase1.length];

        if ((numBatalla == 1 || numBatalla == 2) && numFase == 1) {
            parellesFase1();
        }
        else

        if (numFase == 3 && !acabat) {
            // agafem el top1 i top2 per la batalla final
            topPosicio = getTop1Top2();

            if (topPosicio[0] != indexUsuari && topPosicio[1] != indexUsuari) {
                // top1 vs top2
                for (int i = 0; i < topPosicio.length; i++) {
                    int random1 = (int) (Math.random() * MAX_PUNTUACIO);
                    jsonFileCompeticio.getRappers().get(topPosicio[i]).setPuntuacio(random1);
                }
                //comprovem si a la fase final ha canviat el guanyador
                if (jsonFileCompeticio.getRappers().get(topPosicio[0]).getPuntuacio() > jsonFileCompeticio.getRappers().get(topPosicio[1]).getPuntuacio()) {
                    guanyador = topPosicio[0];
                } else {
                    guanyador = topPosicio[1];
                }
                writeJsonWinner();
            }
        }
        else

        if ((numBatalla == 1 || numBatalla == 2) && numFase == 2 && jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
            if (numBatalla == 1) {
                nomsFase2 = new String[topBatalla1.length];

                int m = 0;
                for (int i = 0; i < topBatalla1.length; i++) {
                    int p = 0;
                    for (int k = 0; k < jsonFileCompeticio.getRappers().size(); k++) {
                        if (jsonFileCompeticio.getRappers().get(k).getPuntuacio() == topBatalla1[i] && m < topBatalla1.length) {
                            nomsFase2[m] = jsonFileCompeticio.getRappers().get(k).getStageName();
                            p++;
                            m++;
                        }
                    }
                    if (p > 1) {
                        i = i + (p - 1);
                    }
                }
            }
            parellesFase2();

        }
    }

    /**
     * @Finalitat: Agafem el index del top 1 i el top 2 i els guardem en un array
     * @Paràmetres: no
     * @Retorn: int[] amb el index del top1 i el top2
     */
    public int[] getTop1Top2() {
        int top[] = new int[2];
        for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla2[0]) {
                top[0] = i;
            }
            if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla2[1]) {
                top[1]= i;
            }
        }
        return top;
    }

    /**
     * @Finalitat: //TODO:
     * @Paràmetres: int a, int b
     * @Retorn: int[] amb numeros random
     */
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

    /**
     * @Finalitat: //TODO:
     * @Paràmetres: no
     * @Retorn: no
     */
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

    /**
     * @Finalitat: //TODO:
     * @Paràmetres: no
     * @Retorn: no
     */
    public void parellesFase2 () {
        int aux = 0;
        int index = 0;
        boolean seguent = false;
        numerosFase2 = RandomizeArray(0, nomsFase2.length-1);
        for (int i = 0; i < numerosFase2.length; i++) {
            int random1 = (int) (Math.random() * MAX_PUNTUACIO);
            if (nomsFase2[numerosFase2[i]].equalsIgnoreCase(usuari)) {
                if (numerosFase2[i] % 2 == 0) {
                    jsonFileCompeticio.getRappers().get(indexUsuari).setPuntuacio(0);
                    seguent = true;
                } else {
                    jsonFileCompeticio.getRappers().get(indexUsuari).setPuntuacio(0);
                    jsonFileCompeticio.getRappers().get(index).setPuntuacio(-aux);
                    contrincant = jsonFileCompeticio.getRappers().get(index).getStageName();
                }
            }
            else {
                for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
                    if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(nomsFase2[numerosFase2[i]]) && seguent) {
                        jsonFileCompeticio.getRappers().get(j).setPuntuacio(0);
                        contrincant = jsonFileCompeticio.getRappers().get(j).getStageName();
                        seguent = false;
                    }
                    else
                    if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(nomsFase2[numerosFase2[i]])) {
                        jsonFileCompeticio.getRappers().get(j).setPuntuacio(random1);
                        index = j;
                    }

                }
            }
            aux = random1;
        }
    }

    /**
     * @Finalitat: //TODO:
     * @Paràmetres: no
     * @Retorn: no
     */
    public void jugadorGuanyadorBatallaFase1 () {
        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            guanyadorBatallaFase[j] = jsonFileCompeticio.getRappers().get(j).getPuntuacio();
        }
        Arrays.sort(guanyadorBatallaFase);
        for (int i = 0; i < guanyadorBatallaFase.length/2; ++i ) {
            float temp = guanyadorBatallaFase[i];
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
        juga = false;
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
            for (int i = 0; i < topBatalla1.length && !juga; i++) {
                if (topBatalla1[i] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                    juga = true;
                }
            }
        }
    }

    /**
     * @Finalitat: //TODO:
     * @Paràmetres: no
     * @Retorn: no
     */
    public void jugadorGuanyadorBatallaFase2 () {
        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            guanyadorBatallaFase[j] = jsonFileCompeticio.getRappers().get(j).getPuntuacio();
        }
        Arrays.sort(guanyadorBatallaFase);
        for( int i = 0; i < guanyadorBatallaFase.length/2; ++i ) {
            float temp = guanyadorBatallaFase[i];
            guanyadorBatallaFase[i] = guanyadorBatallaFase[guanyadorBatallaFase.length - i - 1];
            guanyadorBatallaFase[guanyadorBatallaFase.length - i - 1] = temp;
        }
        topBatalla2 = Arrays.copyOfRange(guanyadorBatallaFase, 0, 2);
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

    /**
     * @Finalitat: Mostra per pantalla el ranking actual de la competicio dels patricipants que van guanyant les fases
     * @Paràmetres: no
     * @Retorn: no
     */
    public void ranquingCompeticio () {
        int numParticipants = 0;
        if (numFase == 1) {
            numParticipants = jsonFileCompeticio.getRappers().size();
        }
        else if (numFase == 2) {
            numParticipants = jsonFileCompeticio.getRappers().size()/2;
        }
        else if (numFase == 3) {
            numParticipants = 2;
        }
        float puntuacions[] = new float[jsonFileCompeticio.getRappers().size()];

        //guardem les puntuacions en un array
        for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            puntuacions[i] = jsonFileCompeticio.getRappers().get(i).getPuntuacio();
        }

        //ordenem l'array de puntuacions de major a menor
        Arrays.sort(puntuacions);
        for( int i = 0; i < puntuacions.length/2; ++i ) {
            float temp = puntuacions[i];
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
        for (int k = 0; k < puntuacions.length; k++) {
            for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                if (puntuacions[k] == jsonFileCompeticio.getRappers().get(i).getPuntuacio() && m < numParticipants) {
                    if (jsonFileCompeticio.getRappers().get(i).getStageName().equalsIgnoreCase(usuari)) {
                        // pintem el text groc
                        System.out.print(ANSI_YELLOW);
                        System.out.println(m + 1 + ". " + jsonFileCompeticio.getRappers().get(i).getStageName() + " - " + puntuacions[k] + " <-- You");
                        // tornem a posar el text blanc
                        System.out.print(ANSI_RESET);
                    } else {
                        System.out.println(m + 1 + ". " + jsonFileCompeticio.getRappers().get(i).getStageName() + " - " + puntuacions[k]);
                    }
                    m++;
                }
            }
        }
    }

    /**
     * @Finalitat: TODO:
     * @Paràmetres: no
     * @Retorn: no
     */
    private void canviarFase () {
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
            if (numBatalla == 2 && numFase == 1) {
                numBatalla = 1;
                numFase = 2;
                faseAcabada = true;
            }
            if (numBatalla == 2 && numFase == 2) {
                numBatalla = 1;
                numFase = 3;
                faseAcabada = true;
            }
            if (numBatalla == 2 && numFase == 3) {
                acabat = true;
            }
        }
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2) {
            if (numBatalla == 2 && numFase == 1) {
                numBatalla = 1;
                numFase = 2;
                jugadorGuanyadorBatallaFase1();
            }
            if (numBatalla == 2 && numFase == 2) {
                acabat = true;
            }
        }
    }

    /**
     * @Finalitat: Executar la funcio de la batalla 'startBattle' implementada en cada clase de l'herencia de Batalla depenent del tipus de batalla (Acapella, Sangre o Escrita)
     * @Paràmetres: no
     * @Retorn: no
     */
    private void batallaUsuari() {
        Acapella acapella = new Acapella(jsonFileBatalla, jsonFileCompeticio);
        Sangre sangre = new Sangre(jsonFileBatalla, jsonFileCompeticio);
        Escrita escrita = new Escrita(jsonFileBatalla, jsonFileCompeticio);
        System.out.println(tipusBatalla);
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
    }

    /**
     * @Finalitat: Escriure en un fitxer anomenat 'winner.json' el guanyador de la competicio per mostrar-lo un cop s'acabi la competicio i l'usuari pari l'execucio del programma
     * @Paràmetres: no
     * @Retorn: no
     */
    public void writeJsonWinner () {
        JSONObject obj = new JSONObject();
        obj.put("name", jsonFileCompeticio.getRappers().get(guanyador).getStageName());
        //obj.put("name", "MariCarmen");

        try {
            FileWriter file = new FileWriter("files/winner.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
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
            System.out.println("Phase: " + numFase + " / " + jsonFileCompeticio.getCompetition().getPhases().size() +  " | Score: " + jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio() + " | Battle: " + numBatalla + " / 2 " + tipusBatalla + " | Rival: " + contrincant);
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
        String phrase;
        if (guanyador == indexUsuari) {
            phrase = "Congrat broda! You've WON the competition!";
        } else {
            phrase = "You've lost kid, I'm sure you'll do better next time...";
        }
        // You've lost kid, I'm sure you'll do better next time...
        do {
            System.out.println("\n-----------------------------------------------------------");
            System.out.println("Winner: " + jsonFileCompeticio.getRappers().get(guanyador).getStageName() + " | Score: " + jsonFileCompeticio.getRappers().get(guanyador).getPuntuacio() + " | " + phrase);
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
    */
}
