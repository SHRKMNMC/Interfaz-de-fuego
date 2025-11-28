package modelo;

import java.awt.Color;

public class PaletaFuego {

    public static Color[] generarPaleta() {

        int totalNiveles = 1024;  // Número de temperaturas (0..1023)
        Color[] paleta = new Color[totalNiveles];

        // Puntos de corte dentro del rango 0..1 del gradiente
        final float[] stops = {0.00f, 0.30f, 0.40f, 0.45f, 0.50f, 0.53f, 1.00f};

        // Colores asociados a cada stop
        final Color[] coloresBase = {
                new Color(0, 0, 0, 0),          // total oscuridad
                new Color(39, 39, 39, 0),       // gris oscuro
                new Color(67, 10, 3, 60),       // rojo muy oscuro
                new Color(255, 120, 20, 180),   // naranja intenso
                new Color(255, 200, 40, 210),   // amarillo cálido
                new Color(255, 240, 180, 235),  // amarillo muy claro
                new Color(220, 230, 255, 140)   // luz azulada suave
        };

        for (int nivel = 0; nivel < totalNiveles; nivel++) {

            // Valor normalizado del nivel (0..1)
            float porcentajeGlobal = nivel / (float) (totalNiveles - 1);

            // Determinar entre qué dos stops estamos
            int idx = 0;
            while (idx < stops.length - 1 && porcentajeGlobal > stops[idx + 1]) {
                idx++;
            }

            float stopInicio = stops[idx];
            float stopFin = stops[idx + 1];

            // Valor local entre estos dos stops (0..1)
            float porcentajeLocal = (porcentajeGlobal - stopInicio) / (stopFin - stopInicio);

            // Suavizado tipo smoothstep
            float porcentajeSuavizado = porcentajeLocal * porcentajeLocal * (3 - 2 * porcentajeLocal);

            Color colorInicio = coloresBase[idx];
            Color colorFin = coloresBase[idx + 1];

            // Interpolación de colores
            int r = (int) (colorInicio.getRed()   + (colorFin.getRed()   - colorInicio.getRed())   * porcentajeSuavizado);
            int g = (int) (colorInicio.getGreen() + (colorFin.getGreen() - colorInicio.getGreen()) * porcentajeSuavizado);
            int b = (int) (colorInicio.getBlue()  + (colorFin.getBlue()  - colorInicio.getBlue())  * porcentajeSuavizado);
            int a = (int) (colorInicio.getAlpha() + (colorFin.getAlpha() - colorInicio.getAlpha()) * porcentajeSuavizado);

            paleta[nivel] = new Color(r, g, b, a);
        }

        return paleta;
    }
}
