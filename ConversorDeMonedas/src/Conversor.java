public class Conversor {

    private ModeloCambio modelo;

    public Conversor(ModeloCambio modelo){
        this.modelo = modelo;
    }

    public double convertir(String monedaDestino, double monto){
        Double tasa = modelo.getTasasDeCambio().get(monedaDestino);

        if (tasa == null){
            throw new RuntimeException("Moneda no encontrada: " + monedaDestino);
        }

        return monto * tasa;
    }
}
