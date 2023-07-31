package perceptron;

import java.util.Random;

public class Neurona {
	public double umbral; // bias
	public double[] pesos; // w
	public double sumaPonderada; // sumatoria

	public Neurona(int numEntradas, Random r) {
		umbral = r.nextDouble();
	    pesos = new double[numEntradas];

	    for(int i = 0; i < pesos.length; i++) {
	    	pesos[i] = r.nextDouble();
	    }
	}
	public void setUmbral(double umbral) {
		this.umbral = umbral;
	}
	public double getUmbral() {
		return umbral;
	}
	public double[] getPesos() {
		return pesos;
	}
	public double getSumaPonderada() {
		return sumaPonderada;
	}
	public double activar(double[] entradas) {
		sumaPonderada = umbral;
		for(int i = 0; i < entradas.length; i++){
			sumaPonderada += entradas[i] * pesos[i]; 
		}

	    return Sigmoide(sumaPonderada);
	}
	public double Sigmoide(double x) {
		return 1 / (1 + Math.exp(-x));
	}
}
