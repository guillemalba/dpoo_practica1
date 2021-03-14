package edu.salleurl.Logic;

/**
 * Classe on es defineixen les variables i s'implementen les funcions relacionades amb la fase del fitxer competicio.json
 */
public class Fase {
    private float budget;
    private String country = null;

    /**
     * Retornar el pressupost
     * @return float del pressupost
     */
    public float getBudget() {
        return budget;
    }

    /**
     * Retornar el nom del pais
     * @return String del pais
     */
    public String getCountry() {
        return country;
    }
}
