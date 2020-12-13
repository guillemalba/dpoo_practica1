package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;

public class Batalla {
    private JsonFileBatalla jsonFileBatalla;

    public Batalla (JsonFileBatalla jsonFileBatalla) {
        this.jsonFileBatalla = jsonFileBatalla;
    }

    public void startBattle() {
        Random random = new Random();
        int index = random.nextInt(jsonFileBatalla.getThemes().size());

        jsonFileBatalla.getThemes().get(index);
        System.out.println("-----------------------------------------------------------\n");
        System.out.println("Topic: " + jsonFileBatalla.getThemes().get(index).getName());
    }

}
