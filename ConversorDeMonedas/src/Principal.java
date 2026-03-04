import java.io.IOException;
import java.util.Scanner;


public class Principal {
    //metodo que muestra el menu al usuario
    private static void mostrarMenu() {
        System.out.println("\n==== Conversor de Monedas ====");
        System.out.println("1) Dolar => Peso Argentino");
        System.out.println("2) Peso Argentino => Dolar");
        System.out.println("3) Dolar => Real Brasileno");
        System.out.println("4) Real Brasileno => Dolar");
        System.out.println("5) Dolar => Peso Colombiano");
        System.out.println("6) Peso Colombiano => Dolar");
        System.out.println("7) Dolar => Boliviano");
        System.out.println("8) Dolar => Peso Chileno");
        System.out.println("9) Ver historial de conversiones");
        System.out.println("10) Salir");
        System.out.print("Elija una opcion: ");

    }

    private static String[] obtenerMonedas(int opcion){
        return switch (opcion){
            case 1-> new String[]{"USD", "ARS"};
            case 2 -> new String[]{"ARS", "USD"};
            case 3 -> new String[]{"USD", "BRL"};
            case 4 -> new String[]{"BRL", "USD"};
            case 5 -> new String[]{"USD", "COP"};
            case 6 -> new String[]{"COP", "USD"};
            case 7 -> new String[]{"USD", "BOB"};
            case 8 -> new String[]{"USD", "CLP"};
            default -> null;
        };
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ServicioAPI servicioAPI = new ServicioAPI();
        RegistroConversiones registro = new RegistroConversiones();
        int opcion = 0;

        System.out.println("Bienvenido al conversor de Monedas");

        while (opcion !=10){
            mostrarMenu();

            try {
                String entrada = scanner.nextLine();
                opcion = Integer.parseInt(entrada.trim());

                if (opcion< 1 || opcion > 10){
                    System.out.println("Opcion no valida. Por favor elija entre 1 y 10");
                    continue;
                }

                if (opcion ==10){
                    System.out.println("Hasta luego");
                    break;
                }
                if (opcion ==9){
                    registro.mostrarHistorial();
                    continue;
                }

                System.out.println("Ingresa el valor que deseas convertir: ");
                double monto = scanner.nextDouble();
                scanner.nextLine();

                if (monto <= 0){
                    System.out.println("El monto debe ser mayor a cero.");
                    continue;
                }

                String[] monedas = obtenerMonedas(opcion);

                if (monedas == null){
                    System.out.println("Opcion no valida.");
                    continue;
                }

                ModeloCambio modelo = servicioAPI.obtenerTasas(monedas[0]);
                Conversor conversor = new Conversor(modelo);
                double resultado = conversor.convertir(monedas[1], monto);

                System.out.printf("El valor de %.2f %s corresponde a %.2f %s%n",
                        monto, monedas[0], resultado, monedas[1]);
                registro.registrar(monto, monedas[0], resultado, monedas[1] );
                System.out.println("Conversion registrada.");

            }catch (NumberFormatException e){
                System.out.println("Entrada no valida. Por favor ingresa solo números");
            } catch (IOException e){
                System.out.println("Error en conexion: " + e.getMessage());
                System.out.println("Verifica tu conexion a internet e intentalo de nuevo.");
            }catch (InterruptedException e){
                System.out.println("Solicitud interrumpida. Intente de nuevo.");
                Thread.currentThread().interrupt();
            }
        }
        scanner.close();
    }
}












