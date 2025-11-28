import modelo.ModeloLlama;
import vista.VistaLlama;
import controlador.ControladorLlama;

public class Main {
    public static void main(String[] args) {
        ModeloLlama modelo = new ModeloLlama(400, 100);
        VistaLlama vista = new VistaLlama();
        new ControladorLlama(modelo, vista);
    }
}
