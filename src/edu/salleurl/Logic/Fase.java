package edu.salleurl.Logic;

/**
 * @Finalitat: Classe on es defineixen les variables i s'implementen les funcions relacionades amb la fase del fitxer competicio.json
 */
public class Fase {
    private float budget;
    private String country = null;

    /**
     * @Finalitat: Retornar el pressupost
     * @Paràmetres: no
     * @Retorn: float
     */
    public float getBudget() {
        return budget;
    }

    /**
     * @Finalitat: Retornar el nom del pais
     * @Paràmetres: no
     * @Retorn: String
     */
    public String getCountry() {
        return country;
    }
}
