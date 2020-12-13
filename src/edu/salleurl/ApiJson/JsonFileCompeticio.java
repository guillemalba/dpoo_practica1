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
}
