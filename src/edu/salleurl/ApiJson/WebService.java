package edu.salleurl.ApiJson;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe per poder demanar a l'API la informacio del pais
 */
public class WebService {
    private static final String URLWeb = "https://restcountries.eu/rest/v2/name/";

    /**
     * Demana la informacio del pais
     * @param country: pais el qual volem la informacio
     * @return retorna el JSONArray de la API o un null en el cas de que no hi hagi cap
     */
    public JSONArray getCountryInfo (String country) {
        //url que anirem creant
        URL url = null;

        try {
            String[] names = country.split(" ");
            String countryURL = names[0];
            if (names.length > 1) {
                for (int i = 1; i < names.length; i++) {
                    countryURL = countryURL + "%20" + names[i];
                }
            }
            url = new URL(URLWeb + countryURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int apiResposta = connection.getResponseCode();

            //si la resposta és correcte la llegirem
            if(apiResposta == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();

                //la volem en format array
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray)parser.parse(response.toString());

                return jsonArray;

            }
            //en cas contrari voldrà dir que hi ha un error
            else {
                System.out.println("Error, petició a la API incorrecta. Algun paràmetre era erroni.");
                System.out.println();
                return null;
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;

    }
}
