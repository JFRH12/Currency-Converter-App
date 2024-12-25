# Currency Converter App

Esta es una aplicación de consola desarrollada en Java que permite realizar conversiones de divisas utilizando la API de ExchangeRate-API. La aplicación incluye características como historial de conversiones con marcas de tiempo, limpieza de pantalla para mejorar la experiencia del usuario, y un menú interactivo para realizar diferentes acciones.

## Características

1. **Conversión de divisas:** El usuario puede seleccionar las divisas de origen y destino desde una lista numerada y convertir cualquier cantidad.
2. **Historial de conversiones:** Se almacena un historial detallado de las conversiones realizadas, incluyendo la cantidad, las divisas y la marca de tiempo.
3. **Menú interactivo:** El usuario puede elegir entre realizar una conversión, ver el historial o salir del programa.
4. **Pantalla limpia:** Después de cada acción, la pantalla se limpia para mantener una interfaz ordenada.

## Requisitos

- **Java JDK 8 o superior**
- Conexión a Internet
- Clave de API válida para [ExchangeRate-API](https://app.exchangerate-api.com/)

## Instalación

1. **Clonar el repositorio:**
   ```bash
   git clone <URL del repositorio>
   ```
2. **Navegar al directorio del proyecto:**
   ```bash
   cd currency-converter-app
   ```
3. **Compilar el código:**
   ```bash
   javac CurrencyConverterApp.java
   ```
4. **Ejecutar la aplicación:**
   ```bash
   java CurrencyConverterApp
   ```

## Uso

1. **Inicio del programa:** Al ejecutar el programa, se mostrará un menú principal con tres opciones:
   - Convertir divisas
   - Ver historial de conversiones
   - Salir

2. **Conversión de divisas:**
   - Seleccione la divisa de origen y destino de una lista numerada.
   - Ingrese la cantidad que desea convertir.
   - El resultado se mostrará en la pantalla junto con la cantidad convertida.

3. **Historial de conversiones:**
   - Seleccione la opción para ver el historial.
   - Verá una lista de todas las conversiones realizadas con detalles y marcas de tiempo.

4. **Salir:** Seleccione esta opción para cerrar la aplicación.

## Ejemplo de Ejecución

### Menú Principal
```
--- Conversor de Divisas ---
1. Convertir Divisas
2. Ver Historial de Conversiones
3. Salir
Seleccione una opción:
```

### Conversión de Divisas
```
--- Lista de Divisas ---
1. USD
2. EUR
3. GBP
4. JPY
5. AUD
6. CAD
7. CLP
8. BRL
9. ARS
10. MXN

Seleccione la divisa de origen (número): 1
Seleccione la divisa de destino (número): 2
Ingrese la cantidad a convertir: 100
100.00 USD son 90.00 EUR
```

### Historial
```
--- Historial de Conversiones ---
[2024-12-25T15:30:00] 100.00 USD -> 90.00 EUR
[2024-12-25T15:45:00] 50.00 GBP -> 65.00 USD
```

## Configuración de la API

La aplicación utiliza la clave de API proporcionada para acceder a las tasas de cambio. En el archivo `CurrencyConverterApp.java`, asegúrese de que la clave de API sea válida:
```java
private static final String API_KEY = "031505551e6c410197a24f04";
```

Si desea cambiar la clave de API, simplemente reemplace el valor de `API_KEY` por su propia clave.

## Notas

- La lista de divisas se puede personalizar editando el método `loadCurrencies` en el archivo `CurrencyConverterApp.java`.
- Si experimenta errores al conectarse a la API, verifique su conexión a Internet y la validez de la clave de API.

## Contribuciones

¡Las contribuciones son bienvenidas! Siéntase libre de realizar un fork del proyecto y enviar un pull request con mejoras o nuevas características.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulte el archivo `LICENSE` para obtener más detalles.

---

**Autor:**
Juan F Rivera

**Contacto:**
jfrivera.premier@gmail.com

