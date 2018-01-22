package tp2.adom;

import java.util.ArrayList;
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
		for (int i = 1; i < NBVILLES; i++) {
			for (int j = i; j < NBVILLES; j++) {
				this.matrice[i][j] = villes[i].distance(villes[j]);
			}
		}
	}

	public void afficherMatrice() {
		for (int i = 1; i < matrice.length; i++) {
			for (int j = 1; j < matrice.length; j++) {
				System.out.print(matrice[i][j] + " ,");
			}
			System.out.println();
		}
	}

	public int calculerCout(Ville[] chemin) {
		int res = 0;
		Ville current;
		Ville next;
		for (int i = 0; i < chemin.length; i++) {
			if (i + 1 > chemin.length - 1) {
				next = chemin[0];
				current = chemin[i];
			} else {
				next = chemin[i + 1];
				current = chemin[i];
			}
			res += distance(next, current);
		}
		return res;
	}

	public Ville[] creerCheminAleatoire() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= NBVILLES; i++)
			list.add(i);

		Ville res[] = new Ville[NBVILLES];
		int cpt = 0;
		while (!list.isEmpty()) {
			int random = new Random().nextInt(list.size());
			res[cpt] = this.villes[random];
			list.remove(random);
			cpt = cpt + 1;
		}
		return res;
	}

	public int fonctionheuristique(Ville debut) {
		String chemin = debut + "";
		List<Ville> utilise = new ArrayList<Ville>();
		int res = 0;
		Ville current = debut;
		utilise.add(debut);
		int cpt = 0;
		while (cpt < matrice.length - 2) {
			Ville tmp = findMin(current, utilise);
			chemin += " - " + tmp;
			res += distance(tmp, current);
			cpt++;
			current = tmp;
			utilise.add(tmp);
		}
		System.out.println(chemin);
		res += distance(debut, current);
		return res;
	}

	public double distance(Ville v1, Ville v2) {
		if (v1.pos > v2.pos)
			return matrice[v2.pos][v1.pos];
		else
			return matrice[v1.pos][v2.pos];
	}

	public Ville findMin(Ville ville, List<Ville> util) {
		double min = 999999990;
		Ville sommetpetit = null;
		for (int i = 1; i < matrice.length; i++) {
			if (i != ville.pos && !util.contains(i)) {
				double tmp = 0.0;
				tmp = distance(ville, villes[i]);
				if (tmp < min && tmp != 0) {
					min = tmp;
					sommetpetit = villes[i]; //pas sûr, surement un décalage avec les index !
				}
			}
		}
		return sommetpetit;
	}

	
	/**
	 * Génère la liste des voisinages possibles, ne fait rien de plus
	 * @param villes Chemin des villes à parcourir dans l'ordre
	 * @return
	 */
	public Ville[][] fonction_twoopt(Ville[] villes) {
		Ville[][] voisinages = new Ville[NBVILLES][NBVILLES];
		for (int i = 0; i < NBVILLES -1; i++) { // bonne méthode de mettre des -1 ?
			for (int j = i; j < NBVILLES -1; j++) {
				if (j == i || j == i - 1 || j == i + 1)
					continue;
				voisinages[i][i + 1] = villes[j];
				voisinages[i][j] = villes[i + 1];
			}
		}
		return voisinages;
	}

	
	/*public Ville[] fonction_twoopt(Ville[] villes) {
		boolean modifie = true;
		while (modifie) {
			modifie = false;
			for (int i = 1; i < matrice.length - 1; i++) { // bonne méthode de mettre des -1 ?
				for (int j = 1; j < matrice.length - 1; j++) {
					if (j == i || j == i - 1 || j == i + 1)
						continue;
					if (distance(i, i + 1) + distance(j, j + 1) > distance(i, j) + distance(i + 1, j + 1)) {
						Ville tmp = villes[i + 1];
						villes[i + 1] = villes[j];
						villes[j] = tmp;
						modifie = true;
					}
				}
			}
		}
		return villes; // peut être faire une cope du chemin en début de fonction, au cas où ça modifie
						// son chemin d'entrée et que ça lui plaise pas
	}*/
	
	public Ville[] fonction_swap(Ville[] chemin) {
		Ville[] min = chemin;

		return null;
	}

}
