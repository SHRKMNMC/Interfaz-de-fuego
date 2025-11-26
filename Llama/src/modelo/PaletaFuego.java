package modelo;

import java.awt.Color;

public class PaletaFuego {

    public static Color[] generarPaleta() {

        Color[] p = new Color[37];

        final float[] stops = {
                0.00f,
                0.20f,
                0.40f,
                0.50f,
                0.60f,
                0.78f,
                1.00f
        };

        //DOS COLORES EXTRAS QUE SIMULAN EL HUMO, CON TRANSPARENCIA, AHORA MISMO SOLO EMPLEA UNO, PERO PUEDE MODIFICARSE PARA CAMBIAR ASPECTO DEL FUEGO

        final Color[] colors = {
                new Color(0,    0,   0,    0),
                new Color(39, 39, 39, 0),
                new Color(67, 10, 3, 20),
                new Color(255,120, 20,  190),
                new Color(255,200, 40,  210),
                new Color(255,240,180, 235),
                new Color(220,230,255, 140)
        };

        java.util.function.Function<Float, Float> smooth =
                (t) -> t * t * (3 - 2 * t);

        //INTERPOLACIÓN DE COLORES

        for (int i = 0; i < p.length; i++) {

            float t = i / 36f;

            int idx = 0;
            while (idx < stops.length - 1 && t > stops[idx + 1])
                idx++;

            float t0 = stops[idx];
            float t1 = stops[idx + 1];

            float local = (t - t0) / (t1 - t0);
            float s = smooth.apply(local);

            Color c0 = colors[idx];
            Color c1 = colors[idx + 1];

            int r = (int) (c0.getRed()   + (c1.getRed()   - c0.getRed()) * s);
            int g = (int) (c0.getGreen() + (c1.getGreen() - c0.getGreen()) * s);
            int b = (int) (c0.getBlue()  + (c1.getBlue()  - c0.getBlue()) * s);
            int a = (int) (c0.getAlpha() + (c1.getAlpha() - c0.getAlpha()) * s);

            p[i] = new Color(r, g, b, a);
        }

        return p;
    }
}
