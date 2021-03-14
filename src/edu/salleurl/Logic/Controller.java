package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;
import edu.salleurl.ApiJson.JsonFileWinner;
import edu.salleurl.Menu;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/**
 * Aquesta classe es relaciona amb totes les altres classes per tractar de controlar tota la informacio, en aquesta classe tambe s'executa la funcio principal cridada en el Main.
 */
public class Controller {
    private static final float MAX_PUNTUACIO = 25;
    private JsonFileCompeticio jsonFileCompeticio;
    private JsonFileBatalla jsonFileBatalla;
    private JsonFileWinner jsonFileWinner;
    private Menu menu = new Menu();
    private Acapella acapella;
    private Sangre sangre;
    private Escrita escrita;

    // Constants per pintar text
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";

    private int numerosFase1[];                         // array on es guarda aleatoriament l'index de les parelles en la fase 1
    private int numerosFase2[];                         // array on es guarda aleatoriament l'index de les parelles en la fase 2
    private float guanyadorBatallaFase[];               // array on es guarda la puntuacio ordenada descendentment dels raperos
    private float topBatalla1[];                        // array on es guarda els index dels jugadors que passen a la fase 2
    private float topBatalla2[];                        // array on es guarda els index dels jugadors que passen a la fase 3
    private int topPosicio[];                           // array on es guarda el index dels dos millor raperos
    private String nomsFase2[];                         // array dels stageName dels raperos que participen en la fase 2
    private String usuari = null;                       // nom del usuari
    private String contrincant = null;                  // nom del contrincant
    private String tipusBatalla = null;                 // nom del tipus de batalla (acapella, escrita,  sangre)
    private int guanyador = 0;                          // index del guanyador
    private int numBatalla = 1;                         // index de la batalla actual en que es troba
    private int numFase = 1;                            // index de la fase en que es troba
    private int indexUsuari = 0;                        // index del usuari
    private boolean acabat = false;                     // boolean que controla si ha acabat la competicio o no
    private boolean faseAcabada = false;                // boolean que indica si ha acabat la fase
    private boolean juga = true;                        // boolean que controla si l'usuari continua participant en la competicio o no

    public Controller(JsonFileCompeticio jsonFileCompeticio, JsonFileBatalla jsonFileBatalla, JsonFileWinner jsonFileWinner) {
        this.jsonFileCompeticio = jsonFileCompeticio;
        this.jsonFileBatalla = jsonFileBatalla;
        this.jsonFileWinner = jsonFileWinner;
    }

    /**
     * Funcio principal del programa que s'executa en el main i controla la resta de funcions perque el programa s'executi correctament
     */
    public void startProgram() {
        int opcio = 0;
        String winner = null;
        if(jsonFileCompeticio != null && jsonFileBatalla != null) {
            menu.showCompeticio(jsonFileCompeticio.getRappers(), jsonFileCompeticio.getCompetition());

            // per simular la competicio de la resta de raperos en el cas de que aquesta hagi passat i l'usuari no hagi participat
            if (menu.getOpcioCompetition() == 2 && jsonFileWinner.getName() == null) {
                     batallaNoUsuari();
                     winner = jsonFileCompeticio.getRappers().get(guanyador).getStageName();
            }
            menu.showMenu(jsonFileWinner, winner);
            usuari = menu.getOption(jsonFileCompeticio);

            // comença la competicio
            if (usuari != null) {
                batallaRestaRaperos(usuari);

                // fer la competicio fins que acabi o abandoni
                do {
                    if (opcio == 1) {
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
                    tipusBatalla = menu.getTipusBatalla();
                    if (opcio == 1) {
                        if (juga) {
                            batallaUsuari();
                        }
                    }
                    if (opcio != 2) {
                        if (numBatalla == 1) {
                            numBatalla = 2;
                        }
                        else {
                            canviarFase();
                        }
                    }
                    tipusBatalla = menu.getTipusBatalla();
                } while (!acabat && opcio != 4);

                // mostrar el menu quan la competicio ja hagi acabat
                do {
                    if (!acabat) {
                        batallaNoUsuari();
                    }
                    opcio = menu.showCompetiAcabada(jsonFileCompeticio.getRappers(), guanyador, indexUsuari);
                    if (opcio == 2) {
                        ranquingCompeticio();
                    }
                    if (opcio == 1) {
                        System.out.println("Competition ended. You cannot battle anyone else!");
                    }
                } while (opcio != 4);
            }
        } else {
            System.out.println("Error.");
        }
        System.out.println("Thanks for your visit!");
    }

    /**
     * Funcio per simular les batalles de la resta de raperos en el cas de que l'usuari no hi participi
     */
    public void batallaNoUsuari() {
        do {
            jsonFileCompeticio.getRappers().get(indexUsuari).setPuntuacio(0);
            if (faseAcabada && numFase == 2) {
                jugadorGuanyadorBatallaFase1();
                faseAcabada = false;
            }
            if (faseAcabada && numFase == 3) {
                jugadorGuanyadorBatallaFase2();
                faseAcabada = false;
            }
            batallaRestaRaperos(usuari);
            if (numBatalla == 1) {
                numBatalla = 2;
            } else {
                canviarFase();
            }
        } while (!acabat);
    }

    /**
     *
     * @param usuari String del usuari
     */
    public void batallaRestaRaperos(String usuari) {
        this.usuari = usuari;
        this.numerosFase1 = RandomizeArray(0, jsonFileCompeticio.getRappers().size()-1);
        this.guanyadorBatallaFase = new float[numerosFase1.length];

        // quan es trobi en la fase inicial
        if ((numBatalla == 1 || numBatalla == 2) && numFase == 1) {
            parellesFase1();
        }

        // quan es trobi en la fase final
        else
        if ((numFase == 3 && !acabat) || (jsonFileCompeticio.getCompetition().getPhases().size() == 2 && numFase == 2 && !acabat)) {

            // agafem el top1 i top2 per la batalla final
            if (numBatalla == 1) {
                topPosicio = getTop1Top2();
            }

            if (topPosicio[0] != indexUsuari && topPosicio[1] != indexUsuari) {
                juga = false;

                // top1 vs top2 en el cas de que l'usuari no sigui cap d'aquests
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
            }
        }

        // quan es trobi en la fase intermitja
        else
        if ((numBatalla == 1 || numBatalla == 2) && numFase == 2 && jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
            if (numBatalla == 1) {
                nomsFase2 = new String[topBatalla1.length];

                // guardem els noms dels jugadors que participen en la fase 2
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
     * Agafem el index del top 1 i el top 2 i els guardem en un array
     * @return int[] amb el index del top1 i el top2
     */
    public int[] getTop1Top2() {
        int top[] = new int[2];

        // guardem l'index dels dos millors jugadors de la fase 2 en el cas de que hi hagin tres fases
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
            for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla2[0]) {
                    top[0] = i;
                }
                if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla2[1]) {
                    top[1] = i;
                }
            }
        }

        // guardem l'index dels dos millors jugadors de la fase 1 en el cas de que hi hagin dues fases
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2) {
            for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla1[0]) {
                    top[0] = i;
                }
                if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla1[1]) {
                    top[1] = i;
                }
            }
        }
        return top;
    }

    /**
     * Funcio que crea un array amb el index de 2 usuaris i els retorna ordenats aleatoriament
     * @param a index rapero a
     * @param b index rapero b
     * @return int[] amb els index ordenats aleatoriament
     */
    public static int[] RandomizeArray(int a, int b){
        Random rgen = new Random();
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
     * Funcio a on es dona puntuacio a la resta de parelles que es troben en la fase 1 per tal de simular la batalla
     */
    public void parellesFase1 () {
        int aux = 0;
        for (int i = 0; i < numerosFase1.length; i++) {
            int random1 = (int) (Math.random() * MAX_PUNTUACIO);

            // en el cas de que sigui la posicio de l'usuari posem puntuacio 0 a ell i al rapero amb el qual li toca fer parella i ens guardem el nom
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
     * Funcio a on es dona puntuacio a la resta de parelles que es troben en la fase 2 per tal de simular la batalla
     */
    public void parellesFase2 () {
        int aux = 0;
        int index = 0;
        boolean seguent = false;
        numerosFase2 = RandomizeArray(0, nomsFase2.length-1);

        // donem puntuacio a la resta de parelles de la fase 2
        // donem puntuacio 0 a ell i al rapero amb el qual li toca fer parella i ens guradem el nom del contrincant
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
     * Funcio a on es guarda els raperos amb la millor puntuacio de la fase 1 per passar en el seguent fase i mirem si l'usuari passa
     */
    public void jugadorGuanyadorBatallaFase1 () {

        //guardem les puntuacions de tots els raperos, les ordenem i ens quedem amb la meitat mes alta que son els que passaran a la seguent fase
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

        // en el cas de que hi hagi 2 fases mirem si l'usuari es troba en els dos millors
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2) {
            juga = false;
            if (topBatalla1[0] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                juga = true;
                for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                    if (topBatalla1[1] == jsonFileCompeticio.getRappers().get(i).getPuntuacio()) {
                        contrincant = jsonFileCompeticio.getRappers().get(i).getStageName();
                    }
                }
            }
            if (topBatalla1[1] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                juga = true;
                for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                    if (topBatalla1[0] == jsonFileCompeticio.getRappers().get(i).getPuntuacio()) {
                        contrincant = jsonFileCompeticio.getRappers().get(i).getStageName();
                    }
                }
            }
        }
        juga = false;

        // en el cas de que hi hagi 3 fases mirem si l'usuari es troba en l'array dels que passen a la fase 2
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
            for (int i = 0; i < topBatalla1.length && !juga; i++) {
                if (topBatalla1[i] == jsonFileCompeticio.getRappers().get(indexUsuari).getPuntuacio()) {
                    juga = true;
                }
            }
        }
    }

    /**
     * Funcio a on es guarda els dos millors raperos de la fase 2 per passar a la fase final
     */
    public void jugadorGuanyadorBatallaFase2 () {

        //guardem les puntuacions de tots els raperos, les ordenem i ens quedem amb els dos millors
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

        // mirem si l'usuari es troba en els dos millors
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
     * Mostra per pantalla el ranking actual de la competicio dels patricipants que van guanyant les fases
     */
    public void ranquingCompeticio () {

        // guardem quants raperos ha de mostrar en el ranquing
        int numParticipants = 0;
        if (numFase == 1) {
            numParticipants = jsonFileCompeticio.getRappers().size();
        }
        else if (numFase == 2 && jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
            numParticipants = jsonFileCompeticio.getRappers().size()/2;
        }
        else if ((numFase == 3) || (jsonFileCompeticio.getCompetition().getPhases().size() == 2 && numFase == 2)) {
            numParticipants = 2;
        }
        float puntuacions[] = new float[jsonFileCompeticio.getRappers().size()];

        // guardem les puntuacions en un array
        for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            puntuacions[i] = jsonFileCompeticio.getRappers().get(i).getPuntuacio();
        }

        // ordenem l'array de puntuacions de major a menor
        Arrays.sort(puntuacions);
        for( int i = 0; i < puntuacions.length/2; ++i ) {
            float temp = puntuacions[i];
            puntuacions[i] = puntuacions[puntuacions.length - i - 1];
            puntuacions[puntuacions.length - i - 1] = temp;
        }

        // ens quedem amb les puntuacions uniques
        int j = 0;
        for (int i=0; i < puntuacions.length-1; i++){
            if (puntuacions[i] != puntuacions[i+1]){
                puntuacions[j++] = puntuacions[i];
            }
        }
        puntuacions[j++] = puntuacions[puntuacions.length-1];

        // mostrem el ranquing
        int m = 0;
        for (int k = 0; k < puntuacions.length; k++) {
            for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
                if (puntuacions[k] == jsonFileCompeticio.getRappers().get(i).getPuntuacio() && m < numParticipants) {
                    if (jsonFileCompeticio.getRappers().get(i).getStageName().equalsIgnoreCase(usuari)) {
                        // pintem el text groc
                        System.out.print(YELLOW_BOLD_BRIGHT);
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
     * Controla i modifica els index de les batalles i les fases i escrivim en un fitxer winner.json el guanyador
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
                if (juga) {
                    guanyador = topPosicio[0];
                }
                jsonFileCompeticio.getWriteJson().writeJsonWinner(jsonFileCompeticio, guanyador);
            }
        }
        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2) {
            if (numBatalla == 2 && numFase == 1) {
                numBatalla = 1;
                numFase = 2;
                faseAcabada = true;
            }
            if (numBatalla == 2 && numFase == 2) {
                acabat = true;
                if (juga) {
                    guanyador = topPosicio[0];
                }
                jsonFileCompeticio.getWriteJson().writeJsonWinner(jsonFileCompeticio, guanyador);
            }
        }
    }

    /**
     * Executar la funcio de la batalla 'startBattle' implementada en cada clase de l'herencia de Batalla depenent del tipus de batalla (Acapella, Sangre o Escrita)
     */
    private void batallaUsuari() {
        acapella = new Acapella(jsonFileBatalla, jsonFileCompeticio);
        sangre = new Sangre(jsonFileBatalla, jsonFileCompeticio);
        escrita = new Escrita(jsonFileBatalla, jsonFileCompeticio);
        System.out.println(tipusBatalla);
        switch (tipusBatalla) {
            case "acapella":
                acapella.startBattle(usuari, contrincant);
                break;
            case "sangre":
                sangre.startBattle(usuari, contrincant);
                break;
            case "escrita":
                escrita.startBattle(usuari, contrincant);
                break;
        }
    }
}
