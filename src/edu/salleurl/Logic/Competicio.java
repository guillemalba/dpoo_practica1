package edu.salleurl.Logic;

import java.util.LinkedList;


/**
 * Classe on es defineixen les variables i s'implementen les funcions relacionades amb la informacio de la competicio i els raperos
 */
public class Competicio {

    private String name = null;
    private String startDate = null;
    private String endDate =null;
    private LinkedList<Fase> phases;

    public Competicio () {
        this.phases = new LinkedList<>();
    }

    /**
     * Retornar el nom de la competicio
     * @return String amb el nom
     */
    public String getName() {
        return name;
    }

    /**
     * Retornar la data d'inici de la competicio
     * @return String
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Retornar la data final de la competicio
     * @return String
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Retornar la informacio de les fases
     * @return Llista de les fases
     */
    public LinkedList<Fase> getPhases() {
        return phases;
    }
}
