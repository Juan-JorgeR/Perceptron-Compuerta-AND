import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import perceptron.Red;

public class Main {
	public static void main(String[] args) {
        ArrayList<double[]> entradas = new ArrayList<double[]>();
        ArrayList<double[]> salidas = new ArrayList<double[]>();

        for(int i = 0; i < 4; i++){
            entradas.add(new double[2]);
            salidas.add(new double[1]);
        }

        entradas.get(0)[0] = 0; entradas.get(0)[1] = 0; salidas.get(0)[0] = 0;
        entradas.get(1)[0] = 0; entradas.get(1)[1] = 1; salidas.get(1)[0] = 0;
        entradas.get(2)[0] = 1; entradas.get(2)[1] = 0; salidas.get(2)[0] = 0;
        entradas.get(3)[0] = 1; entradas.get(3)[1] = 1; salidas.get(3)[0] = 1;

        Red red = new Red(new int[]{entradas.get(0).length, 3, salidas.get(0).length});
        Entrenador entrenador = new Entrenador();
        entrenador.entrenar(red,entradas, salidas, 0.2, 0.01,100000);
        
        Scanner read = new Scanner(System.in);
        System.out.println("\n\n\tProbar la red\n");
        int i = 0;
        while(i++ < 4){
        	System.out.print("a = ");
            double a = read.nextDouble();
            System.out.print("b = ");
            double b = read.nextDouble();
            System.out.println(
            		"Salida = " +
            		Arrays.toString(red.activar(new double[]{a, b})) +
            		"\n"
            );
        }

    }
}
