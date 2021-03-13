package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;
import java.util.*;
import java.lang.Math;

/**
 * @Finalitat: Classe on es defineixen les variables i s'implementen les funcions relacionades amb les batalles
 */
public abstract class Batalla {

    private final JsonFileBatalla jsonFileBatalla;
    private final JsonFileCompeticio jsonFileCompeticio;

    public Batalla (JsonFileBatalla jsonFileBatalla, JsonFileCompeticio jsonFileCompeticio) {
        this.jsonFileBatalla = jsonFileBatalla;
        this.jsonFileCompeticio = jsonFileCompeticio;
    }

    /**
     * @Finalitat: Funcio que executa tot el procediment de les batalles i assignant la puntuacio als raperos despres de la batalla
     * @Paràmetres: String usuari, String contrincant
     * @Retorn: no
     */
    public void startBattle(String usuari, String contrincant) {
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

        // switch per escollir amb un numero random qui comenca la batalla
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
                    puntsUsuari += calculaPuntuacio(numRimasUsuari);
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
                    puntsUsuari += calculaPuntuacio(numRimasUsuari);
                    puntsContrincant += calculaPuntuacio(numRimasContrincant);

                }
                break;
            default:
                System.out.println("Error on randUser!");
                break;
        }
        // afegim la puntuacio dels usuaris
        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(usuari)) {
                jsonFileCompeticio.getRappers().get(j).setPuntuacio(puntsUsuari);
            }
            if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(contrincant)) {
                jsonFileCompeticio.getRappers().get(j).setPuntuacio(puntsContrincant);
            }
        }
    }

    /**
     * @Finalitat: Definir la funcio calculaPuntuacio per implementar-la en les clases (Acapella, Escrita, Sangre) i retornar el valor
     * @Paràmetres: int numRimas
     * @Retorn: float
     */
    protected abstract float calculaPuntuacio (int numRimas);

    /**
     * @Finalitat: Calcula el nombre de rimes que hi ha en els versos i els retorna, si l'usuari abans s'ha quedat en blanc posem el numero de rimes a -1
     * @Paràmetres: String[] rimas
     * @Retorn: int
     */
    public int getNumRimas (String[] rimas) {
        LinkedList<String> lettersRimas = new LinkedList<>();
        LinkedList<Integer> numRimas = new LinkedList<>();
        int numRimasUsuari = 0;
        int indexRima = 0;
        boolean rimaOK;

        if (rimas[0].equalsIgnoreCase("M'he quedat en blanc")) {
            numRimasUsuari = -1;    // assignem -1 perque el contrincant s'ha quedat en blanc, aquest valor es tindrà en compte a l'hora de calcular la puntuacio
        } else {
            for (String rima: rimas) {
                // si els versos acaben amb ',' o '.' els treiem
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
                // si rimaOK = true, vol dir que els versos almenys tenen minim 2 letres i poden ser contabilitzats
                if (rimaOK) {
                    // si conte una rima en les dues ultimes lletres li sumem 1
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
        return numRimasUsuari;
    }

    /**
     * @Finalitat: Agafa la informacio dels raperos i els versos de 'jsonFileCompeticio' i 'jsonFileBatalla' i els mostra per mantalla
     * @Paràmetres: String usuari, int index, int i, String contrincant
     * @Retorn: String[]
     */
    public String[] mostraVersosContrincant (String usuari, int index, int i, String contrincant) {
        boolean end = false;
        int level;
        String[] rimas = new String[4];

        System.out.println(contrincant + " your turn! Drop it\n");
        System.out.println(contrincant + ":\n");

        // bucle per recorrer tots els raperos
        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            // si el rapero del fitxer es igual al del usuari agafem el seu nivell i amb un switch mostrem els versos del nivell
            if (jsonFileCompeticio.getRappers().get(j).getStageName().equalsIgnoreCase(usuari)) {
                level = jsonFileCompeticio.getRappers().get(j).getLevel();
                switch (level) {
                    case 1:
                        if (i < jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_1().size()) {
                            rimas = jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_1().get(i).split("\n");
                        } else {
                            System.out.println("M'he quedat en blanc\n");
                            // assignem "M'he quedat en blanc" a la casella 0 per indicar que s'ha quedat en blanc i se li assignaran 0 punts
                            rimas[0] = "M'he quedat en blanc";
                            end = true;
                        }

                        break;
                    case 2:
                        if (i < jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_2().size()) {
                            rimas = jsonFileBatalla.getThemes().get(index).getRhymes().get(0).get_2().get(i).split("\n");
                        } else {
                            System.out.println("M'he quedat en blanc\n");
                            // assignem "M'he quedat en blanc" a la casella 0 per indicar que s'ha quedat en blanc i se li assignaran 0 punts
                            rimas[0] = "M'he quedat en blanc";
                            end = true;
                        }
                        break;
                }
                // end es un boolean per fer que no mostri les rimes en cas que el contrincant es quedi en blanc, ja que esta buit
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

    /**
     * @Finalitat: Demana a l'usuari que teclegi els versos en el teclat per guardar-los i retornar-los
     * @Paràmetres: String usuari
     * @Retorn: String[]
     */
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
