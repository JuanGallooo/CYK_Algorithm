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
		
		String[][] table= new String[w.length() + 1][w.length()+ 1];
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table.length; j++) {
				table[i][j]="";
			}
		}
		// This table in every space the production it is separated by spaces
		
		for (int j = 1; j < table.length; j++) {
			if( j== 1 || j==2) {
				if(j==1) {
					// j==0 (j=1 real table)
					for (int i = 0; i < table.length-1; i++) {
						String result=getWhoProduce(w.substring(i, i+1));
						table[i+1][j]=result;
					}
					System.out.println(" ---------------Table with j==1 ");
					printTable(table);
				}
				else {
					int k=1;
					for (int i = 1; i < table.length-1; i++) {
					  String concatenate= concatenate(table[i][1],table[i+k][1]);
					  String result="";
					  String[] allTerms= concatenate.split(" ");
					  
					  for (int l = 0; l < allTerms.length; l++) {
						  result+=getWhoProduce(allTerms[l]);
					  }
					  result= removeDuplicateElements(result.split(" "));
					  table[i][j]= result;
					}
					
					System.out.println(" ---------------Table with j==2 ");
					printTable(table);
					
				}
			}
			else {
		      int rows =  table.length - j;
		      rows++;
		      for (int i = 1; i < rows; i++) {
		    	  ArrayList<String> allTerms = new ArrayList<String>();
				for (int k = 1; k < j; k++) {
					String concatenate = concatenate(table[i][k], table[i+k][j-k]);
					allTerms.add(concatenate);
				}
				String[] allTerms2 = generateUnion(allTerms);
				String result = "";
				  for (int l = 0; l < allTerms2.length; l++) {
					  String produce = getWhoProduce(allTerms2[l]);
					  result+= getWhoProduce(allTerms2[l]);
					
				  }
				  result= removeDuplicateElements(result.split(" "));
				  table[i][j]= result;
			  }
		      
				System.out.println(" ---------------Table with j==" + (j+1));
				printTable(table);
			}
			
		}
		String [][] organized = organizeTable(table);
		production = auxIsProduction(organized[0][organized.length-1],variables[0]);
		System.out.println("END\n");
		printTable(organized);
		
		return production;
	}
	
	public boolean auxIsProduction(String prod, String s) {
		boolean is = false;
		String [] production = prod.split(" ");
		for (int i = 0; i < production.length; i++) {
			if(production[i].equals(s)) {
				is = true;
			}
		}
		return is;
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
			String[] rA= a.split(" ");
			String[] rB= b.split(" ");
			
			if(rA.length == 0) {
				for (int j = 0; j < rB.length; j++) {
					result+= rB[j]+" ";
			    }
			}
			else if(rB.length == 0) {
				for (int j = 0; j < rA.length; j++) {
					result+= rA[j]+" ";
			    }
			}
			else {
				for (int i = 0; i < rA.length; i++) {
					for (int j = 0; j < rB.length; j++) {
							result+=rA[i]+rB[j]+" ";
					}
				}
		   }
		return result;
	}
	
	//Method to generate the union of terms that were concatenated in their respective k term.
	
	public String[] generateUnion(ArrayList<String> allTerms) {
		String[] terms = addTerms(allTerms);
		printUnion(terms);
		String result = "";
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < terms.length; i++) {
			if(!temp.contains(terms[i])) {
				result += terms[i] + " ";
				temp.add(terms[i]);
			}
		}
		String [] end = result.split(" ");
		return end;
	}
	
	//Method to generate a list with all the concatenations created before only in one list.
	public String[] addTerms(ArrayList<String> allTerms) {
		String add = "";
		for (int i = 0; i < allTerms.size(); i++) {
		    add += allTerms.get(i);
		}
		String[] result = add.split(" ");
		return result;
	}
	
	//Method that removes the row i= 0 and j= 0 that has empty strings
	public String[][] organizeTable(String[][] table){
		int ip = 0;
		int jp = 0;
		String[][] organized = new String[table.length-1][table[0].length-1];
		for (int i = 1; i < table.length; i++) {
			for (int j = 1; j < table.length; j++) {
				organized[ip][jp] = table[i][j];
				jp++;
			}
		  jp=0;
		  ip++;
		}
		return organized;
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
			String result="i="+ (i)+" ";
			for (int j = 0; j < table[0].length; j++) {
				result+= "{"+table[i][j]+"} ";
			}
			System.out.println(result);
		}
	}
	public void printUnion(String[]table) {
		String result = "{ ";
		for (int i = 0; i < table.length; i++) {
			result+= "{" + table[i] +"} ";
		}
		result+= "}";
		System.out.println(result);
	}
}
