#Conversor de Monedas

Aplicación de consola desarrollada en Java que permite convertir entre distintas monedas
latinoamericanas utilizando tasas de cambio en tiempo real obtenidas desde una API externa.

Este proyecto fue desarrollado como desafío
del curso de Java Backend de **Alura LATAM**.

---

## Descripción del Proyecto

El Conversor de Monedas permite al usuario:
- Seleccionar entre 8 pares de conversión de monedas
- Ingresar un monto y obtener el valor convertido en tiempo real
- Ver un historial de todas las conversiones realizadas durante la sesión
- Guarda automáticamente cada conversión en un archivo `conversiones.txt` con fecha y hora

### Monedas soportadas
| Código | Moneda |
|--------|--------|
| USD | Dólar estadounidense |
| ARS | Peso argentino |
| BRL | Real brasileño |
| COP | Peso colombiano |
| BOB | Boliviano boliviano |
| CLP | Peso chileno |

---

## Tecnologías utilizadas

- **Java 17** — Lenguaje principal
- **HttpClient** — Para realizar peticiones HTTP a la API (incluido en Java 11+, sin dependencias externas)
- **HttpRequest / HttpResponse** — Para construir y manejar las solicitudes y respuestas HTTP
- **Gson 2.10.1** — Para deserializar la respuesta JSON de la API
- **ExchangeRate-API** — API gratuita de tasas de cambio en tiempo real
- **Postman** — Herramienta utilizada para probar y verificar los endpoints de la API antes de implementarlos en el código,
 permitiendo visualizar la estructura del JSON de respuesta y validar que la URL y los parámetros fueran correctos
- **java.time** — Para registrar la fecha y hora de cada conversión
- **FileWriter** — Para guardar el historial en un archivo `.txt`

---

## Decisiones técnicas

### ¿Por qué `gson.fromJson()` en lugar de `JsonParser` y `JsonObject`?

El Trello del desafío sugería el uso de `JsonParser` y `JsonObject`, 
que son clases de la biblioteca Gson.
Sin embargo, se optó por usar `gson.fromJson()`
junto con un `Map<String, Double>` por las siguientes razones:

1. **Es el enfoque moderno recomendado**: `JsonParser.parseString()`
2. fue introducido como reemplazo del método estático
  `JsonParser.parse()` 

3. **Código más limpio y legible**: En lugar de navegar manualmente por el árbol JSON con `getAsJsonObject()` y `get()`,
el mapeo directo a un objeto Java hace el código más claro.

5. **Mejor separación de responsabilidades**: La clase `ModeloCambio` actúa como un modelo
6. de datos claro, y Gson se encarga del mapeo automáticamente usando `@SerializedName` para conectar
8.   los campos JSON con las variables Java.

```java
// Enfoque sugerido:
JsonObject json = JsonParser.parseString(respuesta).getAsJsonObject();
JsonObject tasas = json.getAsJsonObject("conversion_rates");
double tasa = tasas.get("ARS").getAsDouble();

//Enfoque utilizado:
Gson gson= new Gson();
return gson.fromJson(respuesta.body(),ModeloCambio.class);


##Estructura del Proyecto

```
ConversorDeMonedas/
└── src/
    ├── Principal.java               <- Menú principal y flujo de la aplicación
    ├── ServicioAPI.java             <-Conexión HTTP a ExchangeRate-API
    ├── ModeloCambio.java            <-Modelo que representa la respuesta JSON
    ├── Conversor.java               <-Lógica de cálculo de conversión
    └── RegistroConversiones.java    <- Historial en memoria y guardado en .txt
```

## Mi experiencia desarrollando este proyecto:

Este fue mi primer proyecto consumiendo una API real y
 la verdad es que no fue fácil, pero cada error me enseñó algo nuevo.
Algunos momentos que me marcaron:

- **La URL mal armada**: Estuve un buen rato sin entender por qué la API devolvía HTML en lugar de JSON.
 El problema era que había escrito la URL base con la API key y la moneda hardcodeadas directamente,
 en lugar de construirla dinámicamente.
 Uso de `System.out.println()` para debuggear y ver exactamente qué URL se estaba generando.

- **El punto y coma en el Builder**: Puse un `;` en medio del encadenamiento de métodos de `HttpRequest.newBuilder()` y
 Java lo interpretó como el fin de la línea.
Desde entonces entendí bien cómo funciona el patrón Builder y por qué no se puede cortar la cadena.

- **El type silencioso**: Tenía dos métodos en `RegistroConversiones`, uno vacío llamado `mostrarHistorial()` y
otro con el código real llamado `mostrarHistrorial()` (con una 'r' de más).
 El programa corría sin errores pero el historial nunca aparecía. Me tomó un rato darme cuenta de que estaba llamando al método vacío.

- **El scope de las variables**: Declaré un objeto en el lugar equivocado dentro del código y Java no lo reconocía desde otras partes.
 Reforce que las variables solo existen dentro del bloque `{}` donde fueron declaradas.

Cada uno de estos errores me obligó a salir de mi zona de confort, buscar en la documentación, revisar ejemplos de otros proyectos y releer mis apuntes.
 Soy aprendiz y este proyecto es prueba de que equivocarse es parte del proceso. 

---

## ElizabethHD

Desarrollado como parte del programa **Oracle Next Education (ONE)** en colaboración con **Alura LATAM**.
