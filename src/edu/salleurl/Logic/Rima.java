package edu.salleurl.Logic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

/**
 * Classe on es defineixen les variables i s'implementen les funcions relacionades amb els versos del fitxer batalles.json
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
     * Retornar els versos del 1: del json
     * @return LinkedList<String>
     */
    public LinkedList<String> get_1() {
        return _1;
    }

    /**
     * Retornar els versos del 2: del json
     * @return LinkedList<String>
     */
    public LinkedList<String> get_2() {
        return _2;
    }

}
