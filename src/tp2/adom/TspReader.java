package tp2.adom;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TspReader {

	private File file;

	public TspReader(File f) {
		this.file = f;
	}

	public Ville[] read() {
		BufferedReader br = null;
		FileReader fr = null;
		Ville[] tab = new Ville[101];
		try {
			fr = new FileReader(this.file);
			br = new BufferedReader(fr);
			String line = br.readLine();
			int cpt = 1;
			while (line != null) {
				if (!line.equals("EOF")) {
					String tmp[] = line.split(" ");
					tab[cpt++] = new Ville(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]),
							Integer.parseInt(tmp[2]));
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return tab;
	}

	public double[][] matrice(Ville[] list) {
		double[][] res = new double[101][101];
		for (int i = 1; i < list.length; i++) {
			for (int j = i; j < list.length; j++) {
				res[i][j] = list[i].distance(list[j]);
			}
		}
		return res;
	}

	public void afficherMatrice(double[][] matrice) {
		for (int i = 1; i < matrice.length; i++) {
			for (int j = 1; j < matrice.length; j++) {
				System.out.print(matrice[i][j] + " ,");
			}
			System.out.println();
		}
	}

	public int fonctionEvaluation(int[][] matrice, int[] solutionpermutation) {
		int res = 0;
		int current = 0;
		int next = 0;
		for (int i = 0; i < solutionpermutation.length; i++) {
			if (i + 1 > solutionpermutation.length - 1) {
				next = solutionpermutation[0];
				current = solutionpermutation[i];
			} else {
				next = solutionpermutation[i + 1];
				current = solutionpermutation[i];
			}
			if (next <= current) {
				res += matrice[next][current];
			} else {
				res += matrice[current][next];
			}
		}
		return res;
	}

	public int[] solutionpermutationaleatoire() {
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
		String chemin = debut +"";
		List<Integer> utilise = new ArrayList<Integer>();
		int res = 0;
		int current = debut;
		utilise.add(debut);
		int cpt = 0;
		while (cpt < matrice.length -2) {
			int tmp = findMin(matrice, current, utilise);
			chemin +=" - " +tmp ;
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

	public int findMin(double[][] matrice, int ville, List<Integer> util) {
		double min = 99999999999.0;
		int sommetpetit = 0;
		for (int i = 1; i < matrice.length; i++) {
			if (i != ville && !util.contains(i)) {
				double tmp = 0.0;
				if (i > ville)
					tmp = matrice[ville][i];
				else
					tmp = matrice[i][ville];
				if (tmp < min && tmp != 0 ) {
					min = tmp;
					sommetpetit = i;
				}
			}
		}
		return sommetpetit;

	}

	
}
