package modelo;

import java.awt.*;

public class ModeloLlama {

    private final int ancho;
    private final int alto;
    private final int[][] temperatura;
    private final Color[] paleta;

    public ModeloLlama(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        temperatura = new int[alto][ancho];
        paleta = PaletaFuego.generarPaleta();

    }

    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }

    public int get(int y, int x) { return temperatura[y][x]; }
    public void set(int y, int x, int v) { temperatura[y][x] = v; }

    // CHISPA INICIAL

    public void encenderBase() {
        for (int x = 0; x < ancho; x++)
            temperatura[alto - 1][x] = 1023;
    }

    //BASE

    private void actualizarBase() {

        int y = alto - 1;

        for (int x = 0; x < ancho; x++) {

            // base estable y caliente
            int base = 1023;

            // ruido moderado, más suave
            int ruido = (int)(Math.random() * 50) + 100;  // -160 a +100

            int t = base + ruido;

            // CHISPAS FUERTES EXTREMADAMENTE RARAS
            // antes 0.006 ahora 0.002 CUIDADO
            if (Math.random() < 0.000000001)
                t = 1023;

            // huecos frios para darle forma de flama
            if (Math.random() < 0.49)   // antes 0.008
                t = (int)(Math.random() * 350);

            // límites
            if (t < 0) t = 0;
            else if (t > 1023) t = 1023;

            temperatura[y][x] = t;
        }
    }

    // LÓGICA DE PROPAGACIÓN SE MANTIENE

    public void propagar() {

        actualizarBase();

        int fireWidth = ancho;
        int fireHeight = alto;

        for (int actualRow = fireHeight - 2; actualRow > 4; actualRow--) {

            int iniRow = actualRow * fireWidth;
            int iniBelowRow = iniRow + fireWidth;

            for (int actualCol = 2; actualCol < fireWidth - 2; actualCol++) {

                int pos = iniRow + actualCol;
                int posBelow = iniBelowRow + actualCol;

                int y = pos / fireWidth;
                int x = pos % fireWidth;

                int yB = posBelow / fireWidth;
                int xB = posBelow % fireWidth;

                int tL = temperatura[y][x - 1];
                int tC = temperatura[y][x];
                int tR = temperatura[y][x + 1];

                int bL = temperatura[yB][xB - 1];
                int bC = temperatura[yB][xB];
                int bR = temperatura[yB][xB + 1];

                double calc = (
                        (tL * 1.2) +
                                (tC * 1.5) +
                                (tR * 1.2) +
                                (bL * 0.7) +
                                (bC * 0.7) +
                                (bR * 0.7)
                ) / 5.98569;

                calc -= 0.8;

                int nuevo = (int) calc;

                // ENFRIAMIENTO
                int enf = (int)(Math.random() * 10);
                nuevo -= enf;

                if (nuevo < 0) nuevo = 0;
                else if (nuevo > 1023) nuevo = 1023;

                temperatura[y][x] = nuevo;
            }
        }
    }

    public int[][] getFrame() {
        int[][] buffer = new int[alto][ancho];

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                float v = temperatura[y][x] / 1023f;

                if (v <= 0) {
                    buffer[y][x] = 0x00FFFFFF;
                    continue;
                }

                float idxFloat = v * (paleta.length - 1);
                int idx = (int) Math.floor(idxFloat);
                int next = Math.min(idx + 1, paleta.length - 1);
                float f = idxFloat - idx;

                Color c1 = paleta[idx];
                Color c2 = paleta[next];

                int r = (int)(c1.getRed()   + f*(c2.getRed()   - c1.getRed()));
                int g = (int)(c1.getGreen() + f*(c2.getGreen() - c1.getGreen()));
                int b = (int)(c1.getBlue()  + f*(c2.getBlue()  - c1.getBlue()));
                int a = (int)(c1.getAlpha() + f*(c2.getAlpha() - c1.getAlpha()));

                buffer[y][x] = (a << 24) | (r << 16) | (g << 8) | b;
            }
        }

        return buffer;
    }

}