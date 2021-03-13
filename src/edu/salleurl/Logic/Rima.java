package edu.salleurl.Logic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class Rima {
    @SerializedName("1")
    @Expose
    private LinkedList<String> _1 = new LinkedList<>();

    @SerializedName("2")
    @Expose
    private LinkedList<String> _2 = new LinkedList<>();


    public Rima () {}

    /**
     * @Finalitat: TODO:
     * @Paràmetres: no
     * @Retorn: LinkedList<String>
     */
    public LinkedList<String> get_1() {
        return _1;
    }

    /**
     * @Finalitat: TODO:
     * @Paràmetres: LinkedList<String> _1
     * @Retorn: no
     */
    public void set_1(LinkedList<String> _1) {
        this._1 = _1;
    }

    /**
     * @Finalitat: TODO:
     * @Paràmetres: no
     * @Retorn: LinkedList<String>
     */
    public LinkedList<String> get_2() {
        return _2;
    }

    /**
     * @Finalitat: TODO:
     * @Paràmetres: LinkedList<String> _2
     * @Retorn: no
     */
    public void set_2(LinkedList<String> _2) {
        this._2 = _2;
    }
}
