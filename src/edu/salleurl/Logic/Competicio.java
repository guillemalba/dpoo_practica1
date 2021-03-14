package edu.salleurl.Logic;

import java.util.LinkedList;


/**
 * @Finalitat: Classe on es defineixen les variables i s'implementen les funcions relacionades amb la informacio de la competicio i els raperos
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
}
