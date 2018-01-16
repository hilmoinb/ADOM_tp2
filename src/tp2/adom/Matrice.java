package tp2.adom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Matrice {

	private double[][] matrice;
	private final int NBVILLES;

	public Matrice(Ville[] villes) {
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

	public int calculerCout(int[] chemin) {
		int res = 0;
		int current = 0;
		int next = 0;
		for (int i = 0; i < chemin.length; i++) {
			if (i + 1 > chemin.length - 1) {
				next = chemin[0];
				current = chemin[i];
			} else {
				next = chemin[i + 1];
				current = chemin[i];
			}
			if (next <= current) {
				res += matrice[next][current];
			} else {
				res += matrice[current][next];
			}
		}
		return res;
	}

	public int[] creerCheminAleatoire() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 100; i++) {
			list.add(i);
		}

		int res[] = new int[100];
		int cpt = 0;
		while (!list.isEmpty()) {
			int random = new Random().nextInt(list.size());
			res[cpt] = list.get(random);
			list.remove(random);
			cpt = cpt + 1;
		}
		return res;
	}

	public int fonctionheuristique(double[][] matrice, int debut) {
		String chemin = debut + "";
		List<Integer> utilise = new ArrayList<Integer>();
		int res = 0;
		int current = debut;
		utilise.add(debut);
		int cpt = 0;
		while (cpt < matrice.length - 2) {
			int tmp = findMin(matrice, current, utilise);
			chemin += " - " + tmp;
			if (tmp > current)
				res += matrice[current][tmp];
			else
				res += matrice[tmp][current];
			cpt++;
			current = tmp;
			utilise.add(tmp);
		}
		System.out.println(chemin);
		if (debut > current)
			res += matrice[current][debut];
		else
			res += matrice[debut][current];
		return res;
	}

	public double distance(int v1, int v2) {
		if (v1 > v2)
			return matrice[v2][v1];
		else
			return matrice[v1][v2];
	}

	public int findMin(double[][] matrice, int ville, List<Integer> util) {
		double min = 999999990;
		int sommetpetit = 0;
		for (int i = 1; i < matrice.length; i++) {
			if (i != ville && !util.contains(i)) {
				double tmp = 0.0;
				if (i > ville)
					tmp = matrice[ville][i];
				else
					tmp = matrice[i][ville];
				if (tmp < min && tmp != 0) {
					min = tmp;
					sommetpetit = i;
				}
			}
		}
		return sommetpetit;

	}

}
