import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class ModeloCambio {

    @SerializedName("result") // avisa a Gson que este campo JSON corresponde a esta variable java
    private String resultado;

    @SerializedName("base_code")
    private String monedaBase;

    @SerializedName("conversion_rates")
    private Map<String, Double> tasasDeCambio;

    public String getResultado(){
        return resultado;
    }

    public String getMonedaBase(){
        return  monedaBase;
    }

    public Map<String, Double> getTasasDeCambio(){
        return tasasDeCambio;
    }
}
// Map<String>, Double es perfecto para guardar pares como : ARS -> 897.50
// En esta clase se representan las respuestas JSON que nos devuelven la API cuando se consulta.