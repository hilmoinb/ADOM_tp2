package tp2.adom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TspParser {
	
	private File file;
	
	
	public TspParser(File f) {
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

}
