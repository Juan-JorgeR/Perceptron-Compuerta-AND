import java.util.ArrayList;

import perceptron.Capa;
import perceptron.Neurona;
import perceptron.Red;

public class Entrenador {
    private ArrayList<double[]> sigmas;
    private ArrayList<double[][]> deltas;
    private Red red;
    
    private double Sigmoide(double x) {
        return 1 / (1 + Math.exp(-x));
    }
    private double SigmoideDerivada(double x) {
    	double y = Sigmoide(x);
    	return y*(1 - y);
    }
    private double error(double[] salidaReal, double[] salidaEsperada) { 
        double error = 0;
        for(int i = 0; i < salidaReal.length; i++){
            error += 0.5 * Math.pow(salidaReal[i] - salidaEsperada[i], 2);
        }

        return error;
    }
    private double errorTotal(ArrayList<double[]> entradas, ArrayList<double[]> salidaEsperada) {
        double error = 0;
        for(int i = 0; i < entradas.size(); i++){
            error += error(red.activar(entradas.get(i)), salidaEsperada.get(i));
        }

        return error;
    }
    private void inicializarDeltas() {
        deltas = new ArrayList<double[][]>();
        int cantidadCapas = red.getCapas().size();
        int cantidadNeuronas = 0;
        int cantidadPesos = 0;
        Capa capa = null;
        
        for(int i = 0; i < cantidadCapas; i++) {
        	capa = red.capas.get(i);
        	cantidadNeuronas = capa.getNeuronas().size();
        	cantidadPesos = capa.getNeuronas().get(0).getPesos().length;
        	
            deltas.add(new double[cantidadNeuronas][cantidadPesos]);
            for(int j = 0; j < cantidadNeuronas; j++) {
                for(int k = 0; k < cantidadPesos; k++) {
                    deltas.get(i)[j][k] = 0;
                }
            }
        }
    }
    private void calcularSigmas(double[] salidaEsperada) {
        sigmas = new ArrayList<double[]>();
        int cantidadCapas = red.getCapas().size();
        int cantidadNeuronasCapai = 0;
        int cantidadNeuronasCapaj = 0;
        Capa capai = null;
        Capa capaj = null;
        
        for(int i = 0; i < cantidadCapas; i++) {
        	capai = red.getCapas().get(i);
        	
        	cantidadNeuronasCapai = capai.getNeuronas().size();
        	
        	sigmas.add(new double[cantidadNeuronasCapai]);
        }

        for(int i = cantidadCapas - 1; i >= 0; i--){
            for(int j = 0; j < red.capas.get(i).neuronas.size(); j++) {
            	capai = red.getCapas().get(i);
            	
                if(i == red.capas.size() - 1) {
                    double y = capai.getSalidas()[j];
                    sigmas.get(i)[j] = (y - salidaEsperada[j]) * SigmoideDerivada(y);
                } else {
                    double sum = 0;
                    capaj = red.getCapas().get(i + 1);
                  	cantidadNeuronasCapaj = capaj.getNeuronas().size();
                    for(int k = 0; k < cantidadNeuronasCapaj; k++) {
                        sum += capaj.getNeuronas().get(k).getPesos()[j] * sigmas.get(i + 1)[k];
                    }
                    sigmas.get(i)[j] = SigmoideDerivada(capai.getNeuronas().get(j).sumaPonderada) * sum;
                }
            }
        }
    }
    private void calcularDeltas() { 
        int cantidadCapas = red.getCapas().size();
        int cantidadNeuronasCapai = 0;
        int cantidadNeuronasCapaj = 0;
        int cantidadPesos = 0;
        Capa capai = null;
        Capa capaj = null;
    	
        for(int i = 1; i < cantidadCapas; i++) {
        	capaj =  red.capas.get(i);
        	cantidadNeuronasCapaj = capaj.getNeuronas().size();
        	
            for(int j = 0; j < cantidadNeuronasCapaj; j++) {
            	cantidadPesos = capaj.getNeuronas().get(j).getPesos().length;
                for(int k = 0; k < cantidadPesos; k++) {
                	capai = red.capas.get(i - 1);
                    deltas.get(i)[j][k] += sigmas.get(i)[j] * capai.getSalidas()[k];
                }
            }
        }
    }
    private void actualizarPesos(double alfa) {
    	 int cantidadCapas = red.getCapas().size();
         int cantidadNeuronas = 0;
         int cantidadPesos = 0;
         Capa capai = null;
    	
        for(int i = 0; i < cantidadCapas; i++) {
        	capai = red.capas.get(i);
        	cantidadNeuronas = capai.getNeuronas().size();
            for(int j = 0; j < cantidadNeuronas; j++) {
            	cantidadPesos = capai.getNeuronas().get(j).getPesos().length;
                for(int k = 0; k < cantidadPesos; k++) {
                    capai.getNeuronas().get(j).getPesos()[k] -= alfa * deltas.get(i)[j][k];
                }
            }
        }
    }

    private void actualizarUmbrales(double alfa) {
    	int cantidadCapas = red.getCapas().size();
        int cantidadNeuronas = 0;
        int cantidadPesos = 0;
        Capa capai = null;
    	double umbral;
    	
    	for(int i = 0; i < cantidadCapas; i++) {
    		capai = red.capas.get(i);
    		cantidadNeuronas = capai.getNeuronas().size();
            for(int j = 0; j < cantidadNeuronas; j++) { 
            	umbral =  capai.getNeuronas().get(j).getUmbral();
                umbral -= alfa * sigmas.get(i)[j];
                capai.getNeuronas().get(j).setUmbral(umbral);
            }
        }
    }
    private void backPropagation(ArrayList<double[]> entradas, ArrayList<double[]> salidaEsperada, double alfa){
        inicializarDeltas();
        for(int i = 0; i < entradas.size(); i++) {
            red.activar(entradas.get(i));
            calcularSigmas(salidaEsperada.get(i));
            calcularDeltas();
            actualizarUmbrales(alfa);
        }
        actualizarPesos(alfa);
    }
    public void entrenar(Red red,ArrayList<double[]> entradasPruebas, ArrayList<double[]> salidasPruebas, double alfa, double maximoError,int epoch){
        this.red = red;
    	double error = 9e10;
    	
    	int i = 0;
    	System.out.printf("|%10s |%10s|\n\n","iteracion","error");
        while(error > maximoError && i++ < epoch){
            backPropagation(entradasPruebas, salidasPruebas, alfa);
            error = errorTotal(entradasPruebas, salidasPruebas);
            System.out.printf("|%10d |%10.4f|\n",i++,error);
        }
    }
}