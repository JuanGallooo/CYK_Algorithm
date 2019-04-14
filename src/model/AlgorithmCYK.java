package model;

import java.util.ArrayList;

public class AlgorithmCYK {

//	4
//	s,a,b,c
//	bbab
//	BA AC
//	CC b
//	AB a
//	BA a
	private String[] variables;
	private ArrayList<ArrayList<String>> productions;
	
	private String w;

	public AlgorithmCYK(String[] variables, ArrayList<ArrayList<String>> productions, String w) {
		super();
		this.variables = variables;
		this.productions = productions;
		this.w = w;
	}
	
	public boolean isProduction() {
		boolean production =false;
		//This indicates the table that we need to determinate if w E l(g) 
		
		String[][] table= new String[w.length()][w.length()];
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table.length; j++) {
				table[i][j]="";
			}
		}
		// This table in every space the production it is separated by spaces
		
		for (int j = 0; j < table.length; j++) {
			if( j== 0 || j==1) {
				if(j==0) {
					// j==0 (j=1 real table)
					for (int i = 0; i < table.length; i++) {
						String result=getWhoProduce(w.substring(i, i+1));
						table[i][j]=result;
					}
					System.out.println(" ---------------Table with j==1 ");
					printTable(table);
				}
				else {
					// j==1 (j=2 real table)
					
					// Xik X i+k j-k
					// 1<=k<=1
					
					int k=1;
					for (int i = 0; i < table.length-1; i++) {
					  String concatenate= concatenate(table[i][0],table[i+k][0]);
					  String result="";
					  String[] allTerms= concatenate.split(" ");
					  
					  for (int l = 0; l < allTerms.length; l++) {
						  result+=getWhoProduce(allTerms[l])+" ";
					  }
					  result= removeDuplicateElements(result.split(" "));
					  table[i][j]= result;
					}
					
					System.out.println(" ---------------Table with j==2 ");
					printTable(table);
					
				}
			}
			else {
				// j=> 2 (j>=3 real table)
				
			}
		}
		return production;
	}
	
	public String removeDuplicateElements(String arr[]){
		String[] result= new String[arr.length];
		int cont=0;
		for (int i = 0; i < arr.length; i++) {
			if(!into(result, arr[i])) {
				result[cont]=arr[i];
				cont++;
			}
		}
		String finalResult="";
		for (int i = 0; i < cont; i++) {
			finalResult += result[i]+" ";
		}
		return finalResult;
    }  
	
	public boolean into(String[] array, String element) {
		for (int i = 0; i < array.length; i++) {
			if( array[i]!=null) {
			if(array[i].equals(element)) return true;
			}
		}
		return false;
	}
	
	public String getWhoProduce(String terminal) {
		String result="";
		for (int i = 0; i < productions.size(); i++) {
			for (int j = 0; j < productions.get(i).size(); j++) {
				if(terminal.equals(productions.get(i).get(j))){
					result+=variables[i]+" ";
				}
			}
		}
		return result;
	}
	
	public String concatenate(String a, String b) {
		System.out.println(a +" "+ b);
		String result= "";
		if(a.equals(b))result=a;
		else {
			String[] rA= a.split(" ");
			String[] rB= b.split(" ");
			
			for (int i = 0; i < rA.length; i++) {
				for (int j = 0; j < rB.length; j++) {
					if(rA[i].equals(rB[j])) result+=rA[i]+" ";
					else {
						result+=rA[i]+rB[j]+" ";
					}
				}
			}
		}
		return result;
	}

	public String[] getVariables() {
		return variables;
	}

	public void setVariables(String[] variables) {
		this.variables = variables;
	}

	public ArrayList<ArrayList<String>> getProductions() {
		return productions;
	}

	public void setProductions(ArrayList<ArrayList<String>> productions) {
		this.productions = productions;
	}

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}
	public void printTable(String[][] table) {
		String top="";
		for (int i = 0; i < table[0].length; i++) {
			top+="j=="+(i+1)+ "  ";
		}
		System.out.println(top);
		for (int i = 0; i < table.length; i++) {
			String result="i="+ (i+1)+" ";
			for (int j = 0; j < table[0].length; j++) {
				result+= "{"+table[i][j]+"} ";
			}
			System.out.println(result);
		}
	}
}
