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
		
		/**
		 * Génération de 100 chemins aléatoires, récupération du meilleur
		 */
		TspParser.changeSystemOutToFile("chemins_aleatoires.txt");
		Ville[] cheminAleatoire = matrice.fonction_solutionAleatoireGlobale();
		TspParser.changeSystemOutToConsole(SYSTEMOUT);
		
		/**
		 * Calcul du meilleur chemin possible avec la fonction heuristique constructive
		 */
		TspParser.changeSystemOutToFile("heuristique_globale.txt");
		Ville[] cheminHeuristique = matrice.fonction_heuristiqueGlobale();
		TspParser.changeSystemOutToConsole(SYSTEMOUT);
		
		
		
//		/**
//		 * 100 fois SWAP, ALEATOIRE, PREMIER
//		 */
//		for(int i=0; i<100; i++)
//			matrice.fonction_hillClimbing("SWAP", "ALEATOIRE", "PREMIER");
		
//		/**
//		 * 100 fois SWAP, ALEATOIRE, MEILLEUR
//		 */
//		for(int i=0; i<100; i++)
//			matrice.fonction_hillClimbing("SWAP", "ALEATOIRE", "MEILLEUR");
//		
//		/**
//		 * 100 fois TWO-OPT, ALEATOIRE, PREMIER
//		 */
//		for(int i=0; i<100; i++)
//			matrice.fonction_hillClimbing("TWO-OPT", "ALEATOIRE", "PREMIER");
//		
//		/**
//		 * 100 fois TWO-OPT, ALEATOIRE, MEILLEUR
//		 */
//		for(int i=0; i<100; i++)
//			matrice.fonction_hillClimbing("TWO-OPT", "ALEATOIRE", "MEILLEUR");
//		
//		/**
//		 * 100 fois SWAP, HEURISTIQUE, PREMIER
//		 */
//		for(int i=0; i<100; i++)
//			matrice.fonction_hillClimbing("SWAP", "HEURISTIQUE", "PREMIER");
//		
//		/**
//		 * 100 fois SWAP, HEURISTIQUE, MEILLEUR
//		 */
//		for(int i=0; i<100; i++)
//			matrice.fonction_hillClimbing("SWAP", "HEURISTIQUE", "MEILLEUR");
//		
//		/**
//		 * 100 fois TWO-OPT, HEURISTIQUE, PREMIER
//		 */
//		for(int i=0; i<100; i++)
//			matrice.fonction_hillClimbing("TWO-OPT", "HEURISTIQUE", "PREMIER");
//		
//		/**
//		 * 100 fois TWO-OPT, HEURISTIQUE, MEILLEUR
//		 */
//		for(int i=0; i<100; i++)
//			matrice.fonction_hillClimbing("TWO-OPT", "HEURISTIQUE", "MEILLEUR");
		
		//Hillclimbing
		//Ville[] cheminApresHillclimbing = matrice.fonction_hillClimbing(VOISINAGE, INITIALISATION, MOUVEMENT);
		
		//Coût après le hillclimbing
		//System.out.println(matrice.calculerCout(cheminApresHillclimbing));
	}
	
	
	
	
}
