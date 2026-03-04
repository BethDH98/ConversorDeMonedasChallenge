import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RegistroConversiones {
    private static final String ARCHIVO = "conversiones.txt";
    private List<String> historial = new ArrayList<>();

    public void registrar(double monto, String monedaBase,
                          double resultado, String monedaDestino){

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");
        String fecha = ahora.format(formato);

        String linea = String.format("[%s] %.2f %s => %.2f %s" ,
                fecha, monto, monedaBase, resultado, monedaDestino);

        historial.add(linea); //guarda en memoria

        try(FileWriter escritor = new FileWriter(ARCHIVO, true)){
            escritor.write(linea + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("No se pudo guardar el registro: " + e.getMessage());
        }
    }

    public void mostrarHistorial(){
        if (historial.isEmpty()){
            System.out.println("No hay conversiones registradas todavia. ");
            return;
        }
        System.out.println("\n Historial de conversiones...");
        System.out.println("_________________________________________________");
        historial.forEach(System.out::println);
        System.out.println("_________________________________________________");
    }


}
