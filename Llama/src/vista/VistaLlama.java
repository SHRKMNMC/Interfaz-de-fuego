package vista;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class VistaLlama extends JFrame {

    private int[][] buffer;
    private final int ESCALA = 3;
    private final PanelLlama panel;

    public VistaLlama() {
        super("Fuego con fondo");

        panel = new PanelLlama(ESCALA);
        add(panel);

        // Cargar imagen de fondo desde recursos
        try {
            BufferedImage img = ImageIO.read(getClass().getResource("/resources/Chimenea.jpg"));
            panel.setFondo(img);
        } catch (IOException | NullPointerException e) {
            System.err.println("No se pudo cargar la imagen de fondo");
            e.printStackTrace();
        }

        // Ajustar posición del fuego sobre la chimenea
        int offsetX = 100; // mueve horizontalmente según tu imagen
        int offsetY = 1; // mueve verticalmente según tu imagen
        panel.setOffset(offsetX, offsetY);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actualizar(int[][] frame){
        this.buffer = frame;

        panel.setBuffer(buffer);
        panel.setPreferredSize(new Dimension(frame[0].length * ESCALA + 200,
                frame.length * ESCALA + 200));
        pack();
        panel.repaint();
    }
}
