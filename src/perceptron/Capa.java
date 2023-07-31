package perceptron;

import java.util.Random;
import java.util.ArrayList;

public class Capa {
    public ArrayList<Neurona> neuronas;
    public double[] salidas;
    
    Capa(int numEntradas, int numNeuronas, Random r) {
        neuronas = new ArrayList<Neurona>();

        for(int i = 0; i < numNeuronas; i++){
            neuronas.add(new Neurona(numEntradas, r));
        }
    }
    public ArrayList<Neurona> getNeuronas() {
    	return neuronas;
    }
    public double[] getSalidas() {
    	return salidas;
    }
    public double[] activar(double[] entradas) {
        salidas = new double[neuronas.size()];

        for(int i = 0; i < neuronas.size(); i++){
            salidas[i] = neuronas.get(i).activar(entradas);
        }
        return salidas;
    }
}