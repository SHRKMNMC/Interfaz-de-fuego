package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PanelLlama extends JPanel {

    private int[][] buffer;
    private final int escala;
    private BufferedImage fondo; // Imagen de fondo

    // Desplazamiento para ubicar el fuego en la imagen
    private int offsetX = 0;
    private int offsetY = 0;

    public PanelLlama(int escala) {
        this.escala = escala;
    }

    public void setBuffer(int[][] buffer) {
        this.buffer = buffer;
    }

    public void setFondo(BufferedImage fondo) {
        this.fondo = fondo;
    }

    // Métodos para ajustar la posición del fuego
    public void setOffset(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, getWidth(), getHeight(), null);
        }

        if (buffer != null) {
            for(int y = 0; y < buffer.length; y++){
                for(int x = 0; x < buffer[0].length; x++){
                    g2.setColor(new Color(buffer[y][x], true));
                    g2.fillRect(x * escala + offsetX, y * escala + offsetY, escala, escala);
                }
            }
        }
    }
}
