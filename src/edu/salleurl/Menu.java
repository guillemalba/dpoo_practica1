package edu.salleurl;

import edu.salleurl.ApiJson.JsonFileCompeticio;
import edu.salleurl.Logic.Competicio;
import edu.salleurl.Logic.Fase;
import edu.salleurl.Logic.Logica;
import edu.salleurl.Logic.Rapero;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Menu {
    int opcioCompetition = 0;
    private Competicio competicio;
    private LinkedList<Rapero> raperos;
    private LinkedList<String> paisos;
    private Logica logica;

    public Menu (int opcio, JsonFileCompeticio jsonFileCompeticio) {
        this.opcioCompetition = opcio;
        this.competicio = jsonFileCompeticio.getCompetition();
        this.raperos = jsonFileCompeticio.getRappers();
        this.paisos = jsonFileCompeticio.getCountries();
        this.logica = new Logica(jsonFileCompeticio);
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
            competicio.loginRapero();
            logica.fesParelles();
            logica.showCompetiStatus();
            logica.getOptionLobby();
        }
    }
}
