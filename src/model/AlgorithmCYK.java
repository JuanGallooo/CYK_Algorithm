package model;

import java.util.ArrayList;

public class AlgorithmCYK {

	/**
	 * Attribute that represents the array of variables en the grammar, in this case it is a static size array
	 */
	private String[] variables;
	/**
	 * Attribute that represents the production of every variable in the grammar, in this case each one has a variable size
	 */
	private ArrayList<ArrayList<String>> productions;
	/**
	 * String to print the final state table
	 */
	private String print;
	
	/**
	 * The String w that we determinate if the grammar produce
	 */
	private String w;
	/**
	 * Constructor of the Arlgorithm CYK
	 * @param variables the variables of the grammar
	 * @param productions the productions of every variable in the grammar
	 * @param w the string w that we determinate if the grammar G produce
	 */
	public AlgorithmCYK(String[] variables, ArrayList<ArrayList<String>> productions, String w) {
		super();
		this.variables = variables;
		this.productions = productions;
		this.w = w;
	}
	/**
	 * Method that indicates if the grammar G produce the string w, this returns a boolean indicated the condition 
	 * @return a boolean true if the grammar produce the chain w, else a false if it is not true
	 */
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
					for (int i = 0; i < table.length-1; i++) {
						String result=getWhoProduce(w.substring(i, i+1));
						table[i+1][j]=result;
					}
					//System.out.println(" ---------------Table with j==1 ");
					//printTable(table);
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
					
					//System.out.println(" ---------------Table with j==2 ");
					//printTable(table);
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
					 // String produce = getWhoProduce(allTerms2[l]);
					  result+= getWhoProduce(allTerms2[l]);
					
				  }
				  result= removeDuplicateElements(result.split(" "));
				  table[i][j]= result;
			  }
		      
				//System.out.println(" ---------------Table with j==" + (j+1));
				//printTable(table);
			}
			
		}
		String [][] organized = organizeTable(table);
		production = auxIsProduction(organized[0][organized.length-1],variables[0]);
		//System.out.println("END\n");
		//printTable(organized);
		
		tableToPrint(organized);
		
		return production;
	}

	/**
	 * This method auxiliary complete the function to indicate if in a production received by parameter the string s it is produce
	 * @param prod the produces of a variable to search the string s 
	 * @param s the chain to search 
	 * @return a boolean if the produces prod include the string s
	 */
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
	/**
	 * This auxiliary method remove duplicated elements in a array
	 * @param arr the array to remove the duplicate elements
	 * @return a array with out duplicate elements 
	 */
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
	/**
	 * This method auxiliary complete the function to indicate if in a production received by parameter the string s it is produce
	 * @param array the produces of a variable to search the string s
	 * @param element the chain to search 
	 * @return  boolean if the produces prod include the string s
	 */
	public boolean into(String[] array, String element) {
		for (int i = 0; i < array.length; i++) {
			if( array[i]!=null) {
			if(array[i].equals(element)) return true;
			}
		}
		return false;
	}
	/**
	 * this method returns in what productions is the concatenation or terminal that you want to search
	 * @param terminal the terminal to search
	 * @return the productions that produces the terminal
	 */
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
	/**
	 * This method concatenate two strings and returns the concatenations in one string separated by spaces
	 * @param a the string a, whit the productions to concatenate to every production of b
	 * @param b the string whit the productions separated by spaces 
	 * @return the concatenations separated by spaces 
	 */
	public String concatenate(String a, String b) {
		//System.out.println(a +" "+ b);
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
	
	/**
	 * Method to generate the union of terms that were concatenated in their respective k term
	 * @param allTerms the terms to generate the unions 
	 * @return a array whit te unions
	 */
	public String[] generateUnion(ArrayList<String> allTerms) {
		String[] terms = addTerms(allTerms);
		//printUnion(terms);
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
	/**
	 * Method to generate a list with all the concatenations created before only in one list.
	 * @param allTerms list with all the concatenations
	 * @return
	 */
	public String[] addTerms(ArrayList<String> allTerms) {
		String add = "";
		for (int i = 0; i < allTerms.size(); i++) {
		    add += allTerms.get(i);
		}
		String[] result = add.split(" ");
		return result;
	}
	/**
	 * Method that removes the row i= 0 and j= 0 that has empty strings
	 * @param table the table that we use to the process
	 * @return the table removed the row i= 0 and j= 0 that has empty strings
	 */
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
	/**
	 * Method get of the variables 
	 * @return variables
	 */
	public String[] getVariables() {
		return variables;
	}
	/**
	 * the set of the variables 
	 * @param variables variables to set 
	 */
	public void setVariables(String[] variables) {
		this.variables = variables;
	}
	/**
	 * the get of the productions 
	 * @return productions 
	 */
	public ArrayList<ArrayList<String>> getProductions() {
		return productions;
	}
	/**
	 * the set of the productions 
	 * @param productions productions to set 
	 */
	public void setProductions(ArrayList<ArrayList<String>> productions) {
		this.productions = productions;
	}
	/**
	 * the get of w 
	 * @return w
	 */
	public String getW() {
		return w;
	}
	/**
	 * the set of w 
	 * @param w w to set
	 */
	public void setW(String w) {
		this.w = w;
	}
	/**
	 * Methot to put in the String print the table received by parameter 
	 * @param table
	 */
	public void tableToPrint(String[][] table) {
		String top="";
		for (int i = 0; i < table[0].length; i++) {
			top+="j=="+(i+1)+ "  ";
		}
		print+=top+"\n";
		for (int i = 0; i < table.length; i++) {
			String result="i="+ (i)+" ";
			for (int j = 0; j < table[0].length; j++) {
				result+= "{"+table[i][j]+"} ";
			}
			print+=result+"\n";
		}
	}
	/**
	 * method to print table in the console 
	 * @param table the table to print 
	 */
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
	/**
	 * Method to print union in the console
	 * @param table the unions to print 
	 */
	public void printUnion(String[]table) {
		String result = "{ ";
		for (int i = 0; i < table.length; i++) {
			result+= "{" + table[i] +"} ";
		}
		result+= "}";
		System.out.println(result);
	}
	/**
	 * get of the table to print
	 * @return print 
	 */
	public String getPrint() {
		return print;
	}
	/**
	 * Set of the print 
	 * @param print set 
	 */
	public void setPrint(String print) {
		this.print = print;
	}
}
