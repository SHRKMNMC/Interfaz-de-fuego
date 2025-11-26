package controlador;

import modelo.ModeloLlama;
import vista.VistaLlama;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ControladorLlama {

    private final ModeloLlama modelo;
    private final VistaLlama vista;

    public ControladorLlama(ModeloLlama modelo, VistaLlama vista) {
        this.modelo = modelo;
        this.vista = vista;

        modelo.encenderBase();

        new Timer(50, (ActionEvent e) -> tick()).start();
    }

    private void tick() {
        modelo.propagar();
        vista.actualizar(modelo.getFrame());
    }
}
