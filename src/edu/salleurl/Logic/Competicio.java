package edu.salleurl.Logic;

import edu.salleurl.ApiJson.WriteJson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Competicio {

    private WriteJson writeJson;

    private String name = null;
    private String startDate = null;
    private String endDate =null;
    private LinkedList<Fase> phases;
    private LinkedList<Rapero> raperos;
    private LinkedList<String> paisos;

    public Competicio () {
        this.phases = new LinkedList<>();
        this.writeJson = new WriteJson();
    }

    /**
     * @Finalitat: Retornar el nom de la competicio
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getName() {
        return name;
    }

    /**
     * @Finalitat: Retornar la data d'inici de la competicio
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @Finalitat: Retornar la data final de la competicio
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @Finalitat: Retornar la informacio de les fases
     * @Paràmetres: no
     * @Retorn: LinkedList<Fase>
     */
    public LinkedList<Fase> getPhases() {
        return phases;
    }

    /**
     * @Finalitat: TODO:
     * @Paràmetres: LinkedList<Rapero> raperos
     * @Retorn: no
     */
    public void setRaperos(LinkedList<Rapero> raperos) {
        this.raperos = raperos;
    }

    /**
     * @Finalitat: TODO:
     * @Paràmetres: LinkedList<String> paisos
     * @Retorn: no
     */
    public void setPaisos(LinkedList<String> paisos) {
        this.paisos = paisos;
    }

    /**
     * @Finalitat: Enregistrar l'usuari amb totes les seves dades i comprovar que sigui correctes
     * @Paràmetres: no
     * @Retorn: no
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
        do {
            System.out.println("- Full name: ");
            nom = reader.nextLine();
            for (int i = 0; i < raperos.size() && !existeix; i++) {
                if (nom.equalsIgnoreCase(raperos.get(i).getRealName())) {
                    System.out.println("This rapper already exists");
                    existeix = true;
                }
            }
        } while (existeix);

        existeix = false;
        do {
            System.out.println("- Artistic name: ");
            nomArtistic = reader.nextLine();
            for (int i = 0; i < raperos.size() && !existeix; i++) {
                if (nomArtistic.equalsIgnoreCase(raperos.get(i).getStageName())) {
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
            for (int i = 0; i<paisos.size(); i++) {
                if (pais.equalsIgnoreCase(paisos.get(i))) {
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
        raperos.add(rapero2);

        if(writeJson.write(name, startDate, endDate, phases, raperos, paisos)) {
            System.out.println("Registration completed!");
            System.out.println("-----------------------------------------------------------");
        }
    }

    /**
     * @Finalitat: Iniciar sessio del usuari ja existent, comprovar que existeixi i retornar el nom
     * @Paràmetres: LinkedList<Rapero> raperos
     * @Retorn: no
     */
    public String loginRapero () {
        boolean trobat = false;
        String nom = null;
        do {
            System.out.println("-----------------------------------------------------------");
            System.out.println("Enter your artistic name: ");
            Scanner reader = new Scanner(System.in);
            nom = reader.nextLine();
            for (int i = 0; i < raperos.size(); i++) {
                if (nom.equalsIgnoreCase(raperos.get(i).getStageName())) {
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
     * @Finalitat: TODO:
     * @Paràmetres: String dia
     * @Retorn: boolean
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
     * @Finalitat: Comprovem d'on ve l'error de la data introduida
     * @Paràmetres: String dia
     * @Retorn: no
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
}
