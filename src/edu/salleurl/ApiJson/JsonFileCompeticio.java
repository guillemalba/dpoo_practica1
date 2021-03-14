package edu.salleurl.ApiJson;

import edu.salleurl.Logic.Competicio;
import edu.salleurl.Logic.Rapero;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe on es defineixen les variables on guardarem tota la informacio del fitxer competicio.json
 */
public class JsonFileCompeticio {
    private Competicio competition;
    private LinkedList<String> countries;
    private LinkedList<Rapero> rappers;

    private WriteJson writeJson = new WriteJson();

    public JsonFileCompeticio () {
        this.competition = new Competicio();
        this.countries = new LinkedList<>();
        this.rappers = new LinkedList<>();
    }

    /**
     * Retorna la competicio amb les seves dades del fitxer competicio.json
     * @return competicio
     */
    public Competicio getCompetition() {
        return competition;
    }

    /**
     * Retorna els paisos
     * @return Llista dels paisos
     */
    public LinkedList<String> getCountries() {
        return countries;
    }

    /**
     * Retorna els raperos
     * @return Llista dels raperos
     */
    public LinkedList<Rapero> getRappers() {
        return rappers;
    }

    /**
     * Enregistrar l'usuari amb totes les seves dades i comprovar que sigui correctes
     */
    public void registerRapero () {
        Scanner reader = new Scanner(System.in);
        boolean existeix = false;
        boolean comprovacioData = false;
        String nom;
        String nomArtistic;
        String birthday;
        String pais;

        System.out.println("-----------------------------------------------------------");
        System.out.println("Please, enter your personal information:");

        // demanem totes les dades del usuari i comprovem els errors
        do {
            System.out.println("- Full name: ");
            nom = reader.nextLine();
            for (int i = 0; i < rappers.size() && !existeix; i++) {
                if (nom.equalsIgnoreCase(rappers.get(i).getRealName())) {
                    System.out.println("This rapper already exists");
                    existeix = true;
                }
            }
        } while (existeix);

        existeix = false;
        do {
            System.out.println("- Artistic name: ");
            nomArtistic = reader.nextLine();
            for (int i = 0; i < rappers.size() && !existeix; i++) {
                if (nomArtistic.equalsIgnoreCase(rappers.get(i).getStageName())) {
                    System.out.println("Already exists a rapper with this artistic name");
                    existeix = true;
                }
            }
        } while (existeix);

        do {
            System.out.println("- Birth date (dd/MM/YYYY): ");
            birthday = reader.nextLine();
            comprovacioData = comprovarData(birthday);
            if (!comprovacioData) {
                comprovarErrorData(birthday);
            }
        } while (!comprovacioData);
        birthday = formatData(birthday);

        existeix = false;
        do {
            System.out.println("- Country: ");
            pais = reader.nextLine();
            for (int i = 0; i < countries.size(); i++) {
                if (pais.equalsIgnoreCase(countries.get(i))) {
                    existeix = true;
                }
            }
            if (!existeix) {
                System.out.println("You can't register this competition. You country is not accepted!");
            }
        } while (!existeix);

        System.out.println("- Level: ");
        String nivellString = reader.nextLine();
        int nivell = Integer.parseInt(nivellString);

        System.out.println("- Photo URL: ");
        String foto = reader.nextLine();

        Rapero rapero2 = new Rapero(nom, nomArtistic, birthday, pais, nivell, foto);
        rappers.add(rapero2);

        if(writeJson.write(competition.getName(), competition.getStartDate(), competition.getEndDate(), competition.getPhases(), rappers, countries)) {
            System.out.println("Registration completed!");
            System.out.println("-----------------------------------------------------------");
        }
    }

    /**
     * Iniciar sessio del usuari ja existent, comprovar que existeixi i retornar el nom
     * @return String
     */
    public String loginRapero () {
        boolean trobat = false;
        String nom = null;
        // demanem el login i comprovem que existeixi
        do {
            System.out.println("-----------------------------------------------------------");
            System.out.println("Enter your artistic name: ");
            Scanner reader = new Scanner(System.in);
            nom = reader.nextLine();
            for (int i = 0; i < rappers.size(); i++) {
                if (nom.equalsIgnoreCase(rappers.get(i).getStageName())) {
                    System.out.println("Correct login!");
                    trobat = true;
                }
            }
            if (trobat == false) {
                System.out.println("Yo' bro, there's no " + nom + " in ma' list.\n");
            }
        } while (trobat == false);
        return nom;
    }

    /**
     * Comprova que la data introduida per l'usuari estigui en el format correcte
     * @param data data introduida per l'usuari
     * @return boolean
     */
    public static boolean comprovarData (String data) {
        try {
            SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
            formatData.setLenient(false);
            formatData.parse(data);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Canvia el format de la data del usuari i el retorna
     * @param data data introduida per l'usuari
     * @return String
     */
    public static String formatData (String data) {
        String birthday = null;
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            DateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");
            birthday = formatData.format(date);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return birthday;
    }

    /**
     * Comprovem d'on ve l'error de la data introduida
     * @param dia data introduida per l'usuari
     */
    public void comprovarErrorData (String dia) {
        int dia2 = Integer.parseInt(dia.split("/")[0]);
        int mes = Integer.parseInt(dia.split("/")[1]);
        int any = Integer.parseInt(dia.split("/")[2]);
        if (mes == 2) {
            if ((any % 4 == 0 && any % 100 != 0) || (any % 400 == 0)) {
                if (dia2 > 29) {
                    System.out.println("\nError, aquest any el mes de febrer té 29 dies");
                }
            }
            else {
                if (dia2 > 28) {
                    System.out.println("\nError, aquest any el mes de febrer té 28 dies");
                }
            }
        }
        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
            if (dia2 > 31) {
                System.out.println("\nError, aquest mes té 31 dies");
            }
        } else {
            if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                if (dia2 > 30) {
                    System.out.println("\nError, aquest mes té 30 dies");
                }
            } else {
                if (mes > 12) {
                    System.out.println("\nError, en el paràmetre del mes");
                }
            }
        }
    }

    /**
     * Retorna el WriteJson
     * @return WriteJson
     */
    public WriteJson getWriteJson () {
        return writeJson;
    }
}
