package tp2.adom;
import java.io.File;

public class Main {

	public static void main(String[] args) {
		TspReader tsp = new TspReader(new File("kroA100.tsp"));
		Ville[] tmp = tsp.read();
		double[][] d = tsp.matrice(tmp);
		tsp.afficherMatrice(d);
		// int[] tab = /* tsp.solutionpermutationaleatoire(); */new int[3];
		// for (int i = 0; i < tab.length; i++) {
		// tab[i] = i + 1;
		// }
		System.out.println(tsp.fonctionheuristique(d, 1));
	}
}
