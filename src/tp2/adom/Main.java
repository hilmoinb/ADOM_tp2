package tp2.adom;
import java.io.File;

public class Main {

	public static void main(String[] args) {
		TspParser parser = new TspParser(new File("kroA100.tsp"));
		Ville[] villes = parser.read();
		Matrice matrice = new Matrice(villes);
		
		
		matrice.afficherMatrice();
		
		
		
		
		// int[] tab = /* tsp.solutionpermutationaleatoire(); */new int[3];
		// for (int i = 0; i < tab.length; i++) {
		// tab[i] = i + 1;
		// }
		System.out.println(matrice.fonctionheuristique(20));
	}
}
