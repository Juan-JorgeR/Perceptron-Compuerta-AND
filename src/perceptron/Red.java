package perceptron;

import java.util.ArrayList;
import java.util.Random;

public class Red {
	 private ArrayList<Capa> capas;

	 public Red(int[] numNeuronasPorCapa) {
		 capas = new ArrayList<Capa>();
		 Random r = new Random();

		 for(int i = 0; i < numNeuronasPorCapa.length; i++) {
			 if(i == 0) {
				 capas.add(new Capa(numNeuronasPorCapa[i], numNeuronasPorCapa[i], r));
	         }
			 else {
	                capas.add(new Capa(numNeuronasPorCapa[i - 1], numNeuronasPorCapa[i], r));
	         }
	      }
	 }
	 public ArrayList<Capa> getCapas() {
		 return capas;
	 }
	 public double[] activar(double[] entradas) {
		 double[] salidas = new double[0];
		 for(int i = 0; i < capas.size(); i++) {
			 salidas = capas.get(i).activar(entradas);
			 entradas = salidas;
		 }
		 return salidas;
	 }
}
