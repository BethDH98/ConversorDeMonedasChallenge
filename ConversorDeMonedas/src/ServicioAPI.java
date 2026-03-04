// Esta clase se encarga de hacer la petición HTTP a la API y devolver un objeto ModeloCambio.

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServicioAPI {
    private static final String API_KEY = "TU_APY_KEY";
    private static final String URL_BASE = "https://v6.exchangerate-api.com/v6/";

    public ModeloCambio obtenerTasas(String monedaBase) throws  IOException, InterruptedException{
        String url = URL_BASE + API_KEY + "/latest/" + monedaBase;

        try{
            HttpClient cliente = HttpClient.newHttpClient();

            HttpRequest solicitud = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
                        HttpResponse<String> respuesta = cliente.send(
                                solicitud,
                                HttpResponse.BodyHandlers.ofString()
                        );

                        if (respuesta.statusCode() !=200){ /*se cambio try-catch;agregamos: throws y unos Exception. Ademas de verificación del statusCode.*/
                            throw  new IOException("Error en la API. Código de respuesta: " + respuesta.statusCode());
                        }

                        Gson gson = new  Gson();
                        return gson.fromJson(respuesta.body(), ModeloCambio.class);

        }catch (Exception e){
            throw new RuntimeException("Error al consultar la API: " + e.getMessage());
        }
    }
}
