package edu.salleurl.ApiJson;

import edu.salleurl.Logic.Competicio;
import edu.salleurl.Logic.Rapero;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;

public class JsonFileCompeticio {
    private Competicio competition;
    private LinkedList<String> countries;
    private LinkedList<Rapero> rappers;

    public JsonFileCompeticio () {
        this.competition = new Competicio();
        this.countries = new LinkedList<>();
        this.rappers = new LinkedList<>();
    }

    public Competicio getCompetition() {
        return competition;
    }

    public LinkedList<String> getCountries() {
        return countries;
    }

    public LinkedList<Rapero> getRappers() {
        return rappers;
    }

    public int showCompeticio () {
        int opcio = 0;
        LocalDate today = LocalDate.now();

        System.out.println("Welcome to competition: " + getCompetition().getName());
        System.out.println("Starts on " + getCompetition().getStartDate());
        System.out.println("Ends on " + getCompetition().getEndDate());
        System.out.println("Phases: " + getCompetition().getPhases().size());
        System.out.println("Currently: " + getRappers().size() + " participants");

        Date date1 = java.sql.Date.valueOf(getCompetition().getStartDate());
        Date date2 = java.sql.Date.valueOf(getCompetition().getEndDate());
        Date todayDate = java.sql.Date.valueOf(today);

        if (date1.equals(todayDate) || date1.before(todayDate)) {
            if (date2.after(todayDate)) {
                System.out.println("\nCompetition started. Do you want to:");
                opcio = 1;
            }
            else {
                opcio = 2;
            }
        }
        if (date1.after(todayDate)) {
            System.out.println("\nCompetition hasn't started yet. Do you want to:");
            opcio = 3;
        }

        return opcio;
    }

}
