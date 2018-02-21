package tp2.adom;
import java.io.File;
import java.io.PrintStream;

public class Main {
	public static final PrintStream SYSTEMOUT = System.out;

	private static final String VOISINAGE = "swap";
	private static final String INITIALISATION = "heuristique";
	private static final String MOUVEMENT = "meilleur";

	public static void main(String[] args) {
		TspParser parser = new TspParser(new File("kroA100.tsp"));
		Ville[] villes = parser.genererVilles();
		Matrice matrice = new Matrice(villes);
		
		TspParser.changeSystemOutToFile("heuristique_globale.txt");
		Ville[] cheminHeuristique = matrice.fonction_heuristiqueGlobale();
		TspParser.changeSystemOutToConsole(SYSTEMOUT);
		
		
//		System.out.println(matrice);
		
//		Ville[] cheminAleatoire = matrice.creerCheminAleatoire();
//		Ville[] cheminHeuristique = matrice.fonction_heuristique(new Ville(1,0,0));
		
		/**
		 * Comparaison de la fonction heuristique constructive en variant le ville de départ
		 */
//		for(int i=1; i<=100; i++)
//			System.out.println(matrice.calculerCout(matrice.fonction_heuristique(new Ville(i,0,0))));
		
		//Hillclimbing
		//Ville[] cheminApresHillclimbing = matrice.fonction_hillClimbing(VOISINAGE, INITIALISATION, MOUVEMENT);
		
		//Coût après le hillclimbing
		//System.out.println(matrice.calculerCout(cheminApresHillclimbing));
	}
	
	
	
	public static void printVoisinages(Ville[][] voisinages) {
		String str = "|\t";

        for(int i=0;i<voisinages.length;i++){
            for(int j=0;j<voisinages[i].length;j++){
                str += voisinages[i][j] + "\t";
            }
            System.out.println(str + "|");
            str = "|\t";
        }

	}
}
