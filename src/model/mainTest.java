package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class mainTest {

	public static void main(String[] args) {
		try {
			File file = new File ("./docs/Problem2.txt");
	        FileReader fr = new FileReader (file);
	        BufferedReader sr = new BufferedReader(fr);
			String message = sr.readLine();
			int var= Integer.parseInt(message);
			message= sr.readLine();
			String[] variables= message.split(",");
			message= sr.readLine();
			String w= message;
			message= sr.readLine();
			ArrayList<ArrayList<String>> productions= new ArrayList<ArrayList<String>>();
			while (message!=null && !message.equals("")) {
					String[] production= message.split(" ");
					ArrayList<String> produ= new ArrayList<String>();
					for (int i = 0; i < production.length; i++) {
						produ.add(production[i]);
					}
					productions.add(produ);
					message= sr.readLine();
			}
			
			AlgorithmCYK model= new AlgorithmCYK(variables, productions, w);
			model.isProduction();
//			4
//			s,a,b,c
//			bbab
//			BA AC
//			CC b
//			AB a
//			BA a
			sr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
