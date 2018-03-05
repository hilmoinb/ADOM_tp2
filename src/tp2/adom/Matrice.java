package tp2.adom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Matrice {

	private double[][] matrice;
	private final int NBVILLES;
	private Ville[] villes;

	public Matrice(Ville[] villes) {
		this.villes = villes;
		this.NBVILLES = villes.length;
		this.matrice = new double[NBVILLES + 1][NBVILLES + 1];
		for (int i = 1; i < NBVILLES+1; i++) {
			for (int j = i; j < NBVILLES+1; j++) {
				this.matrice[i][j] = villes[i-1].distance(villes[j-1]);
			}
		}
	}

	@Override
	public String toString() {
		String toReturn = "";
		for (int i = 1; i < matrice.length; i++) {
			for (int j = 1; j < matrice.length; j++)
				toReturn += matrice[i][j] + " ,";
			toReturn += "\n";
		}
		return toReturn;
	}

	public int calculerCout(Ville[] chemin) {
		int res = 0;
		// on pourrait ajouter des if qui check si la taille du chemin est bien = à
		// NBVILLES, si ce sont les bonnes villes etc
		for (int i = 0; i < NBVILLES; i++)
			res += distance(chemin[i], chemin[(i + 1) % NBVILLES]);

		return res;
	}

	public Ville[] creerCheminAleatoire() {
		ArrayList<Ville> list = new ArrayList<>();
		for (Ville v : this.villes)
			list.add(v);

		Ville res[] = new Ville[NBVILLES];
		int cpt = 0;
		while (!list.isEmpty()) {
			int random = new Random().nextInt(list.size());
			res[cpt++] = list.get(random);
			list.remove(random);
		}
		return res;
	}
	
	public Ville[] fonction_solutionAleatoireGlobale() {
		System.out.println("GENERATION DE SOLUTIONS ALEATOIRES :");
		Ville[] bestChemin = this.creerCheminAleatoire();
		int bestCost = this.calculerCout(bestChemin);
		System.out.println("\nChemin : " + TspParser.cheminToString(bestChemin) + "\nCoût : " + bestCost);
		
		Ville[] chemin;
		int cost;
		for(int i=1; i<=100; i++) {
			chemin = this.creerCheminAleatoire();
			cost = this.calculerCout(chemin);
			System.out.println("\nChemin : " + TspParser.cheminToString(chemin) + "\nCoût : " + this.calculerCout(chemin));
			if(cost < bestCost) {
				bestChemin = chemin;
				bestCost = cost;
			}
		}
		System.out.println("MEILLEURE SOLUTION TROUVÉE :\nChemin : " + TspParser.cheminToString(bestChemin) + "\nCoût : " + bestCost);
		return bestChemin;
	}

	public Ville[] fonction_heuristiqueGlobale() {
		System.out.println("HEURISTIQUE CONSTRUCTIVE VOISIN LE PLUS PROCHE :");
		
		Ville[] bestChemin = this.fonction_heuristique(this.villes[0]);
		int bestCost = this.calculerCout(bestChemin);
		System.out.println("\nVille départ : " + this.villes[0] + "\nChemin : " + TspParser.cheminToString(bestChemin) + "\nCoût : " + bestCost);
		
		Ville[] chemin;
		int cost;
		for(int i=1; i<this.NBVILLES; i++) {
			chemin = this.fonction_heuristique(this.villes[i]);
			cost = this.calculerCout(chemin);
			System.out.println("\nVille départ : " + this.villes[i] + "\nChemin : " + TspParser.cheminToString(chemin) + "\nCoût : " + cost);
			if(cost < bestCost) {
				bestChemin = chemin;
				bestCost = cost;
			}
		}
		System.out.println("MEILLEURE SOLUTION TROUVÉE :\nVille départ : " + bestChemin[0] + "\nChemin : " + TspParser.cheminToString(bestChemin) + "\nCoût : " + bestCost);
		return bestChemin;
	}
	
	public Ville[] fonction_heuristique(Ville debut) {
		Ville[] chemin = new Ville[NBVILLES];
		List<Ville> restantes = new ArrayList<Ville>();
		for(Ville v : this.villes)
			restantes.add(v);
		int cpt = 0;
		Ville current = debut;
		chemin[cpt++] = current;
		restantes.remove(debut);
		
		while (!restantes.isEmpty()) {
			Ville tmp = findMin(current, restantes);
			chemin[cpt++] = tmp;
			current = tmp;
			restantes.remove(tmp);
		}
		return chemin;
	}

	public double distance(Ville v1, Ville v2) {
		if (v1.pos > v2.pos)
			return matrice[v2.pos][v1.pos];
		else
			return matrice[v1.pos][v2.pos];
	}

	public Ville findMin(Ville ville, List<Ville> restantes) {
		double min = Double.MAX_VALUE;
		Ville sommetpetit = null;

		double tmp;
		for (Ville v : restantes) {
			tmp = distance(ville, v);
			if (tmp < min && tmp != 0) {
				min = tmp;
				sommetpetit = v;
			}
		}
		return sommetpetit;
	}

	
	public Ville[] fonction_hillClimbing(String voisinage, String initialisation, String mouvement) {
		//System.out.println("RECHERCHE LOCALE HILL-CLIMBING :");
		
		///// INITIALISATION \\\\\
		Ville[] cheminInitial = null;
		if (initialisation.toLowerCase().compareTo("aleatoire") == 0)
			cheminInitial = this.creerCheminAleatoire();
		if (initialisation.toLowerCase().compareTo("heuristique") == 0)
			cheminInitial = this.fonction_heuristique(this.villes[new Random().nextInt(NBVILLES)]);
		if (cheminInitial == null) {
			System.err.println("Fonction fonction_hillClimbing(...) : paramètre initialisation incorrect (\""
					+ initialisation + "\")");
			return null;
		}

		//System.out.println("\nChemin initial : " + TspParser.cheminToString(cheminInitial) + "\nCoût : " + this.calculerCout(cheminInitial));

		///// VOISINAGE & MOUVEMENT \\\\\
		Ville[] cheminUpdated = cheminInitial.clone();
		Ville[][] voisinages = null;
		int cpt = 0;
		do {
			if(cpt != 0)
				//System.out.println("\nChemin intermédiaire("+cpt+") : " + TspParser.cheminToString(cheminInitial) + "\nCoût : " + this.calculerCout(cheminInitial));

			cheminInitial = cheminUpdated.clone();
			
			///// VOISINAGE \\\\\
			if (voisinage.toLowerCase().compareTo("swap") == 0)
				voisinages = this.fonction_swap(cheminInitial);
			if (voisinage.toLowerCase().compareTo("two-opt") == 0)
				voisinages = this.fonction_twoopt(cheminInitial);
			if (voisinages == null) {
				System.err.println(
						"Fonction fonction_hillClimbing(...) : paramètre voisinage incorrect (\"" + voisinage + "\")");
				return null;
			}

			///// MOUVEMENT \\\\\
			if (mouvement.toLowerCase().compareTo("meilleur") == 0)
				cheminUpdated = this.mouvement_meilleurVoisinAmeliorant(voisinages, cheminInitial);
			else if (mouvement.toLowerCase().compareTo("premier") == 0)
				cheminUpdated = this.mouvement_premierVoisinAmeliorant(voisinages, cheminInitial);
			else {
				System.err.println(
						"Fonction fonction_hillClimbing(...) : paramètre mouvement incorrect (\"" + mouvement + "\")");
				return null;
			}
			
		} while (this.calculerCout(cheminInitial) < this.calculerCout(cheminUpdated) && cpt++ < 30);

		//System.out.println("\nChemin final : " + TspParser.cheminToString(cheminUpdated) + "\nCoût : " + this.calculerCout(cheminUpdated));
		System.out.println(this.calculerCout(cheminUpdated));

		return cheminUpdated;
	}
	
	
	/**
	 * Génère tous les voisinages du chemin d'origine :
	 *  chaque voisinage est généré en supprimant 2 arrêtes et en reconnectant les 2 sous-tours obtenus
	 * @param villes Le chemin d'origine sur lequel on va baser nos voisinages
	 * @return la liste des voisinages générés, un tableau de chemins
	 */
	public Ville[][] fonction_twoopt(Ville[] chemin) {
		int nbSolutions = (NBVILLES * (NBVILLES - 3)) / 2 + 1;
		Ville[][] voisinages = new Ville[nbSolutions][NBVILLES];
		int z = -1;
		for (int i = 0; i < NBVILLES; i++) {
			for (int j = i + 2; j < NBVILLES; j++) {
				if (j == i || j == i - 1 || j == i + 1)
					continue;
				voisinages[++z] = chemin.clone();
				int a = i, b = j;
				Ville tmp;
				while (b > a) {
					tmp = voisinages[z][a];
					voisinages[z][a++] = voisinages[z][b];
					voisinages[z][b--] = tmp;
				}
			}
		}
		return voisinages;
	}

	/**
	 * Génère tous les voisinages du chemin d'origine :
	 *  chaque voisinage est généré en échangeant la position de 2 villes
	 * @param chemin Le chemin d'origine sur lequel on va baser nos voisinages
	 * @return la liste des voisinages générés, un tableau de chemins
	 */
	public Ville[][] fonction_swap(Ville[] chemin) {
		int nbSolutions = ((NBVILLES - 1) * (NBVILLES - 2)) / 2;
		Ville[][] voisinages = new Ville[nbSolutions][NBVILLES];
		int z = 0;
		for (int i = 1; i < chemin.length; i++) {
			for (int j = i + 1; j < chemin.length; j++) {
				Ville[] tmp = swap(chemin, i, j);
				for (int k = 0; k < tmp.length; k++) {
					voisinages[z][k] = tmp[k];
				}
				z++;
			}
		}
		return voisinages;
	}
	
	/**
	 * Trouver parmi les voisinages fournis celui qui a le coup le coût le plus bas
	 * @param voisinages - Liste des voisinages sous forme de tableau de tableaux
	 * @return Le meilleur voisinage trouvé, celui avec le coup le plus bas donc.  S
	 */
	public Ville[] mouvement_meilleurVoisinAmeliorant(Ville[][] voisinages, Ville[] cheminABattre) {
		Ville[] meilleurVoisinage = cheminABattre.clone();
		double meilleurCout = this.calculerCout(meilleurVoisinage), cout;
		for(Ville[] voisinage : voisinages)
			if (meilleurCout > (cout = this.calculerCout(voisinage)) ) {
				meilleurVoisinage = voisinage;
				meilleurCout = cout;
			}
		return meilleurVoisinage;
	}
	
	
	/**
	 * Trouver parmi les voisinages fournis le premier à présenter un coup plus bas
	 * @param voisinages - Liste des voisinages sous forme de tableau de tableaux
	 * @return Le meilleur voisinage trouvé, celui avec le coup le plus bas donc
	 */
	public Ville[] mouvement_premierVoisinAmeliorant(Ville[][] voisinages, Ville[] cheminABattre) {
		Ville[] meilleurVoisinage = cheminABattre.clone();
		double meilleurCout = this.calculerCout(meilleurVoisinage);
		for(Ville[] voisinage : voisinages)
			if ( meilleurCout > this.calculerCout(voisinage) )
				return voisinage;
		return meilleurVoisinage;
	}
	
	
	/**
	 * Fonction qui inverse les villes idx1 et idx2 dans le chemin
	 * @param chemin
	 * @param idx1
	 * @param idx2
	 * @return
	 */
	private Ville[] swap(Ville[] chemin, int idx1, int idx2) {
		Ville v1 = chemin[idx1];
		Ville v2 = chemin[idx2];
		chemin[idx1] = v2;
		chemin[idx2] = v1;
		return chemin;

	}
	
	public Ville[] fonction_swap_aleatoire(Ville[] chemin) {
		Random r = new Random();
		int nb1 = r.nextInt(NBVILLES);
		int nb2 = r.nextInt(NBVILLES);
		while (nb1 == nb2)
			nb2= r.nextInt(NBVILLES);
		return swap(chemin, nb1, nb2);
	}
	

}
