package edu.salleurl.Logic;

import java.util.LinkedList;

public class Tema {
    private String name = null;
    private LinkedList<Rima> rhymes = new LinkedList<>();

    public String getName() {
        return name;
    }

    public LinkedList<Rima> getRhymes() {
        return rhymes;
    }
}
