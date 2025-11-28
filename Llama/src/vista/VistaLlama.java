package vista;

import javax.swing.*;
import java.awt.*;

public class VistaLlama extends JFrame {

    private int[][] buffer;
    private final int ESCALA = 3;
    private final PanelLlama panel;

    public VistaLlama() {
        super("Fuego con humo");

        panel = new PanelLlama(ESCALA);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void actualizar(int[][] frame){
        this.buffer = frame;

        panel.setBuffer(buffer);
        panel.setPreferredSize(new Dimension(frame[0].length * ESCALA,
                frame.length * ESCALA));
        pack();
        panel.repaint();
    }
}
