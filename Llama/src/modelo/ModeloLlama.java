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

    // BASE con puntos calientes y fríos
    private void actualizarBase() {
        int y = alto - 1;

        for (int x = 0; x < ancho; x++) {

            int t;

            double prob = Math.random();

            if (prob < 0.29) {
                // 10% probabilidad de punto frío
                t = (int)(Math.random() * 200); // frío
            } else if (prob < 0.20) {
                // 5% probabilidad de chispa máxima
                t = 1023;
            } else {
                // resto base caliente
                t = 600 + (int)(Math.random() * 200); // base caliente
            }

            // límites
            if (t > 1023) t = 1023;
            if (t < 0) t = 0;

            temperatura[y][x] = t;
        }
    }

    // Propagación determinista
    public void propagar() {
        actualizarBase();

        int fireWidth = ancho;
        int fireHeight = alto;

        for (int actualRow = fireHeight - 2; actualRow > 4; actualRow--) {
            for (int actualCol = 2; actualCol < fireWidth - 2; actualCol++) {

                int y = actualRow;
                int x = actualCol;
                int yB = y + 1;
                int xB = x;

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

                calc -= 3;
                int nuevo = (int) calc;

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
                int temp = temperatura[y][x];

                if (temp <= 0) {
                    buffer[y][x] = 0x00FFFFFF;
                    continue;
                }

                Color c = paleta[temp];

                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                int a = c.getAlpha();

                buffer[y][x] = (a << 24) | (r << 16) | (g << 8) | b;
            }
        }

        return buffer;
    }

}
