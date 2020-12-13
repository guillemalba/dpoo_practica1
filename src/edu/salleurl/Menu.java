package edu.salleurl;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;
import edu.salleurl.Logic.Competicio;
import edu.salleurl.Logic.Fase;
import edu.salleurl.Logic.Logica;
import edu.salleurl.Logic.Rapero;

import java.time.LocalDate;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Menu {
    int opcioCompetition = 0;
    private Competicio competicio;
    private JsonFileBatalla jsonFileBatalla;
    private LinkedList<Rapero> raperos;
    private LinkedList<String> paisos;
    private Logica logica;

    public Menu (JsonFileCompeticio jsonFileCompeticio, JsonFileBatalla jsonFileBatalla) {
        this.competicio = jsonFileCompeticio.getCompetition();
        this.raperos = jsonFileCompeticio.getRappers();
        this.paisos = jsonFileCompeticio.getCountries();
        this.logica = new Logica(jsonFileCompeticio, jsonFileBatalla);
        this.jsonFileBatalla = jsonFileBatalla;
    }

    public void showCompeticio () {
        LocalDate today = LocalDate.now();

        System.out.println("Welcome to competition: " + competicio.getName());
        System.out.println("Starts on " + competicio.getStartDate());
        System.out.println("Ends on " + competicio.getEndDate());
        System.out.println("Phases: " + competicio.getPhases().size());
        System.out.println("Currently: " + raperos.size() + " participants");

        Date date1 = java.sql.Date.valueOf(competicio.getStartDate());
        Date date2 = java.sql.Date.valueOf(competicio.getEndDate());
        Date todayDate = java.sql.Date.valueOf(today);

        if (date1.equals(todayDate) || date1.before(todayDate)) {
            if (date2.after(todayDate)) {
                System.out.println("\nCompetition started. Do you want to:");
                opcioCompetition = 1;
            }
            else {
                opcioCompetition = 2;
            }
        }
        if (date1.after(todayDate)) {
            System.out.println("\nCompetition hasn't started yet. Do you want to:");
            opcioCompetition = 3;
        }
    }

    public void showMenu () {
        switch (opcioCompetition) {
            case 3:
                System.out.println("\n1. Register");
                System.out.println("2. Leave");
            break;
            case 1:
                System.out.println("\n1. Login");
                System.out.println("2. Leave");
            break;
            case 2:
                System.out.println("The winner of the competiton is: ");
                System.out.println("Press 2 if you want to leave");
            break;
        }

    }

    public void getOption() {
        int opcio = 0;

        System.out.println("\nChoose an option: ");
        Scanner reader = new Scanner(System.in);
        try {
            opcio = reader.nextInt();
            if (opcio > 2|| opcio < 0) {
                System.out.println("Nomes pots insertar numeros del 1 al 2.\n");
            }

        } catch (InputMismatchException ime) {
            System.out.println("Nomes pots insertar numeros.\n");
            reader.next();
        }
        if (opcio == 2) {
            System.out.println("Thanks for your visit!");
        }
        if (opcio == 1 && opcioCompetition == 3) {
            competicio.setRaperos(raperos);
            competicio.setPaisos(paisos);
            competicio.registerRapero();
            System.out.println("\nThanks for your visit!");
        }
        if (opcio == 1 && opcioCompetition == 1) {
            competicio.setRaperos(raperos);
            String usuari = competicio.loginRapero();
            logica.fesParelles(usuari);
            logica.showCompetiStatus();
            logica.getOptionLobby();
        }
    }
}
