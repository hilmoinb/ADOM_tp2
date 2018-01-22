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
		for (int i = 1; i < NBVILLES; i++) {
			for (int j = i; j < NBVILLES; j++) {
				this.matrice[i][j] = villes[i].distance(villes[j]);
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
		Ville current, next;
		
		//on pourrait ajouter des if qui check si la taille du chemin est bien = à NBVILLES, si ce sont les bonnes villes etc
		for(int i=0; i<NBVILLES; i++)
			res += distance(chemin[i], chemin[(i+1)%NBVILLES]);
		
		return res;
	}

	public Ville[] creerCheminAleatoire() {
		ArrayList<Ville> list = new ArrayList<>();
		for(Ville v : this.villes)
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
		
		double tmp;
		for (Ville v : util) {
			tmp = distance(ville, v);
			if (tmp < min && tmp != 0) {
				min = tmp;
				sommetpetit = v;
			}
		}
		return sommetpetit;
	}

	
	/**
	 * Génère la liste des voisinages possibles, ne fait rien de plus
	 * @param villes Chemin des villes à parcourir dans l'ordre
	 * @return liste des voisinages possibles sous firme de tableau de tableaux
	 */
	public Ville[][] fonction_twoopt(Ville[] chemin) {
		int nbSolutions = (NBVILLES * (NBVILLES-3)) / 2 + 1;
		Ville[][] voisinages = new Ville[nbSolutions][NBVILLES];
		int z=-1;
		for (int i = 0; i < NBVILLES; i++) {
			for(int j=i+2; j<NBVILLES; j++) {
				if (j == i || j == i - 1 || j == i + 1)
					continue;
				voisinages[++z] = chemin.clone();
				int a=i, b=j;
				Ville tmp;
				while(b>a) {
					tmp = voisinages[z][a];
					voisinages[z][a++] = voisinages[z][b];
					voisinages[z][b--] = tmp;
				}
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
	
	public Ville[][] fonction_swap(Ville[] chemin) {
		Ville[] current = chemin;
		for (int i = 1; i< chemin.length;i++) {
			for (int j = i+1; j<chemin.length; j++) {
				
			}
		}
		return null;
	}
	
	public void swap(Ville[] chemin, int idx,int idx2) {
		
	}

}
