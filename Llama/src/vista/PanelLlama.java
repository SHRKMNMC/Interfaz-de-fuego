package vista;

import javax.swing.*;
import java.awt.*;

public class PanelLlama extends JPanel {

    private int[][] buffer;
    private final int escala;

    public PanelLlama(int escala) {
        this.escala = escala;
    }

    public void setBuffer(int[][] buffer) {
        this.buffer = buffer;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (buffer == null) return;

        Graphics2D g2 = (Graphics2D) g;

        for(int y = 0; y < buffer.length; y++){
            for(int x = 0; x < buffer[0].length; x++){
                g2.setColor(new Color(buffer[y][x], true));
                g2.fillRect(x * escala, y * escala, escala, escala);
            }
        }
    }
}
