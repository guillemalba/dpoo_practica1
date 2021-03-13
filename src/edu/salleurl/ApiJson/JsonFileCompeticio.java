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

    /**
     * @Finalitat: Retorna la competicio amb les seves dades del fitxer competicio.json
     * @Paràmetres: no
     * @Retorn: Competicio
     */
    public Competicio getCompetition() {
        return competition;
    }

    /**
     * @Finalitat: Retorna els paisos
     * @Paràmetres: no
     * @Retorn: LinkedList<String>
     */
    public LinkedList<String> getCountries() {
        return countries;
    }

    /**
     * @Finalitat: Retorna els raperos
     * @Paràmetres: no
     * @Retorn: LinkedList<Rapero>
     */
    public LinkedList<Rapero> getRappers() {
        return rappers;
    }
}
