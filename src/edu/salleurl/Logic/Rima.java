package edu.salleurl.Logic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

/**
 * @Finalitat: Classe on es defineixen les variables i s'implementen les funcions relacionades amb els versos del fitxer batalles.json
 */
public class Rima {
    @SerializedName("1")
    @Expose
    private LinkedList<String> _1 = new LinkedList<>();

    @SerializedName("2")
    @Expose
    private LinkedList<String> _2 = new LinkedList<>();


    public Rima () {}

    /**
     * @Finalitat: Retornar els versos del 1: del json
     * @Paràmetres: no
     * @Retorn: LinkedList<String>
     */
    public LinkedList<String> get_1() {
        return _1;
    }

    /**
     * @Finalitat: Retornar els versos del 2: del json
     * @Paràmetres: no
     * @Retorn: LinkedList<String>
     */
    public LinkedList<String> get_2() {
        return _2;
    }

}
