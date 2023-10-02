import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Polynomial {
	double[] coefficient;
	int[] exponent;
	
	public Polynomial() {
		this.coefficient = new double[1];
		this.coefficient[0]= 0;
		this.exponent = new int[1]; 
		this.exponent[0] = 0;
	}
	
	public Polynomial(double[] douArray, int[] intArray) {
		this.coefficient = new double[douArray.length];
		this.exponent = new int[intArray.length];
		
		for (int i=0; i < douArray.length; i++) {
			coefficient[i] = douArray[i];
			exponent[i] = intArray[i];
		}
		

	}
	
	public Polynomial add(Polynomial poly) { // use set
		
		Set<Integer> exp_set = new HashSet<Integer>();
		
		for (int i = 0; i < this.exponent.length; i++) {
			exp_set.add(this.exponent[i]);
		}
		for (int i = 0; i < poly.exponent.length; i++) {
			exp_set.add(poly.exponent[i]);
		}
		
		int[] new_expArray = new int[exp_set.size()];
		int a = 0;
		Iterator<Integer> iter = exp_set.iterator();
		while(iter.hasNext()) {
			new_expArray[a++] = iter.next();
		}
		
	
		int newArraylen = new_expArray.length;
		double[] new_coeArray = new double[newArraylen];
		
		for (int i = 0; i < newArraylen; i++) {
			double new_coe = 0; 
			for (int j = 0; j < this.exponent.length; j++ ) {
				if (this.exponent[j] == new_expArray[i]) {
					new_coe = new_coe + this.coefficient[j];
				}
			}
			for (int k = 0; k < poly.exponent.length; k++) {
				if(poly.exponent[k]==new_expArray[i]) {
					new_coe = new_coe + poly.coefficient[k];
				}
			}
			
			new_coeArray[i] = new_coe;
		}
		
		
			
		Polynomial newpoly = new Polynomial(new_coeArray,new_expArray);
		
		return newpoly;
	}
	
	public double evaluate(double x) {
		double sum = 0;
		
		int len = this.coefficient.length;
		
		
		for (int i = 0; i < len; i++) {
			sum += this.coefficient[i] * Math.pow(x, this.exponent[i]);
		}
		
		BigDecimal sum1 = new BigDecimal(sum);
		@SuppressWarnings("deprecation")
		double sum2 = sum1.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return sum2;
	}
	
	public boolean hasRoot(double r) {
		return this.evaluate(r) == 0;
	}
	
	public Polynomial multiply(Polynomial p) {
		if (this.coefficient[0]==0 && this.coefficient.length == 1) {
			double[] coe = {0};
			int[] exp = {0};
			Polynomial newPoly = new Polynomial(coe,exp);
			return newPoly;
		}
		
		else if (p.coefficient[0]==0 && p.coefficient.length == 1) {
			double[] coe = {0};
			int[] exp = {0};
			Polynomial newPoly = new Polynomial(coe,exp);
			return newPoly;
		}
		else {
		
			int a_len = this.coefficient.length;
			int b_len = p.coefficient.length;
			int len_of_raw_ExpArray = a_len * b_len;  // Get length of the array with duplicate exponent
		
			double[] rawCoeArray = new double[len_of_raw_ExpArray]; // Get raw coe & exp arrays
			int[] rawExpArray = new int[len_of_raw_ExpArray];
		
			int arrPosition = 0;
		
			for (int i = 0; i < a_len; i++) {
			
				for (int j = 0; j < b_len; j++ ) {
				
					double c = this.coefficient[i]*p.coefficient[j];
					rawCoeArray[arrPosition] = c;
				
					int e = this.exponent[i] + p.exponent[j];
					rawExpArray[arrPosition] = e;
				
					arrPosition++;
				}
			}
		
			Set<Integer> expSet = new HashSet<Integer>();
			for (int k = 0; k < len_of_raw_ExpArray; k++ ) {
				expSet.add(rawExpArray[k]);
			}
		
			// move exponents from set to array, get rid of duplicate , get exp array
			int[] expArray = new int[expSet.size()];
			int n = 0;
			Iterator<Integer> iter2 = expSet.iterator();
			while(iter2.hasNext()) {
				expArray[n++] = iter2.next();
			}
		
			int len_expArray = expArray.length; // Combine coefficients with same exponent, get coe array
			double[] coeArray = new double[len_expArray];
		
			for(int l = 0; l < len_expArray; l++) {;
				double newCoe = 0;
				for (int g = 0; g < len_of_raw_ExpArray; g++) {
					if (rawExpArray[g] == expArray[l]) {
						newCoe = newCoe + rawCoeArray[g];
					}
					coeArray[l]	= newCoe;
				
				}
			}
		
			int len_finalArray = 0;          // Get the length of final array where no coefficient = 0
			for (int e = 0; e < len_expArray; e++) {
				if (coeArray[e] != 0) {
					len_finalArray++;
				}
			}
		
			int[] finalExpArray = new int[len_finalArray];
			double[] finalCoeArray = new double[len_finalArray];
		
			int counter = 0;
			for (int o = 0; o < len_expArray; o++) {
				if (coeArray[o] != 0) {
					finalExpArray[counter] = expArray[o];
					finalCoeArray[counter] = coeArray[o];
					counter++;
				}
			}
		
			
			Polynomial result_poly = new Polynomial(finalCoeArray,finalExpArray);
			return result_poly;
		}
	}
	
	public Polynomial(File f) throws Exception {
		BufferedReader br = new BufferedReader (new FileReader(f));
		String line = br.readLine();
		br.close();
		
		if (line == "") {
			double[] coeArray = {0};
			int[] expArray = {0};
			coefficient = coeArray;
			exponent = expArray;
		}
		
		else if (line.toCharArray()[0]!='-') {
			String[] subpolyArray = line.split("\\+|-");
			String[] rawSignArray = line.split("[123456789x.]");
			int len_subpolyArray = subpolyArray.length;
			
			int numOfSign = len_subpolyArray-1;    // Get the array of signs
			String[] signArray = new String[numOfSign];
			int placeholder = 0;
			for (int i = 0; i<rawSignArray.length; i++) {
				if (rawSignArray[i].equals("+")) {
					signArray[placeholder] = "+";
					placeholder++;
				}
				else if (rawSignArray[i].equals("-")) {
					signArray[placeholder] = "-";
					placeholder++;
				}
			}
			System.out.println("the array of sign has "+placeholder+" of signs");;
			
			double[] coeArray = new double[len_subpolyArray];
			int[] expArray = new int[len_subpolyArray];
			
			
			for (int i = 0 ; i < len_subpolyArray; i++) {
				if (subpolyArray[i].indexOf("x")== -1) {  // subpoly has no "x", a constant num
					expArray[i] = 0;
					if(i==0) {
						coeArray[i]=Double.parseDouble(subpolyArray[i]);
					}
					else if(i!=0) {
						if(signArray[i-1]=="-") {
							coeArray[i]=-Double.parseDouble(subpolyArray[i]);
						}
						else {
							coeArray[i]=Double.parseDouble(subpolyArray[i]);
						}
					}
					
				}
				else if (subpolyArray[i].indexOf("x")!= -1) {
					
					String[] coe_expArray = subpolyArray[i].split("x");
					if (i==0) {
						coeArray[i] = Double.parseDouble(coe_expArray[0]);
						expArray[i] = Integer.parseInt(coe_expArray[1]);
					}
					else if(i!=0) {
						if(signArray[i-1]=="-") {
							coeArray[i]=-Double.parseDouble(coe_expArray[0]);
							expArray[i]=Integer.parseInt(coe_expArray[1]);
						}
						else {
							coeArray[i]=Double.parseDouble(coe_expArray[0]);
							expArray[i]=Integer.parseInt(coe_expArray[1]);
						}
					}
				}
			}
			coefficient = coeArray;
			exponent = expArray;
		}
		
		else if(line.toCharArray()[0]=='-') {
			String[] subpolyArray = line.split("\\+|-");
			String[] rawSignArray = line.split("[123456789x.]");
			int len_subpolyArray = subpolyArray.length - 1;
			
			int numOfSign = len_subpolyArray;    // Get the array of signs
			String[] signArray = new String[numOfSign];
			
			int placeholder = 0;
			for (int i = 0; i<rawSignArray.length; i++) {
				if (rawSignArray[i].equals("+")) {
					signArray[placeholder] = "+";
					placeholder++;
				}
				else if (rawSignArray[i].equals("-")) {
					signArray[placeholder] = "-";
					placeholder++;
				}
			}
			
			double[] coeArray = new double[len_subpolyArray];
			int[] expArray = new int[len_subpolyArray];
			
			for (int i = 1; i < len_subpolyArray+1; i++) {
				if (subpolyArray[i].indexOf("x")== -1) {
					expArray[i-1] = 0;
					if (i==1) {
						coeArray[i-1]=-Double.parseDouble(subpolyArray[i]);
					}
					else if (i!=1) {
						if(signArray[i-1]=="-") {
							coeArray[i-1]=-Double.parseDouble(subpolyArray[i]);
						}
						else {
							coeArray[i-1]=Double.parseDouble(subpolyArray[i]);
						}
					}
				}
				
				else if (subpolyArray[i].indexOf("x")!= -1) {
					String[] coe_expArray = subpolyArray[i].split("x");
					if (i==1) {
						coeArray[i-1] = -Double.parseDouble(coe_expArray[0]);
						expArray[i-1] = Integer.parseInt(coe_expArray[1]);
					}
					else if(i!=1) {
						if(signArray[i-1]=="-") {
							coeArray[i-1] = -Double.parseDouble(coe_expArray[0]);
							expArray[i-1] = Integer.parseInt(coe_expArray[1]);
						}
						else if (signArray[i-1]=="+"){
							coeArray[i-1]=Double.parseDouble(coe_expArray[0]);
							expArray[i-1]=Integer.parseInt(coe_expArray[1]);
						}
					}
				}
				
			}
			coefficient = coeArray;
			exponent = expArray;
		}
	
	}
	
	
	public void saveToFile(String filename) throws Exception {
		
		File newFile = new File(filename);
		newFile.createNewFile();
			
		
		PrintStream ps = new PrintStream(filename);
		int lenArray = coefficient.length;
		
		for (int i = 0; i < lenArray; i++) {
			if(exponent[i]==0) {
				if (i==0) {		
					ps.print(coefficient[i]);					
				}
				else if (i!=0) {
					if(coefficient[i]>0) {
						ps.print("+"+coefficient[i]);
					}
					else {
						ps.print(coefficient[i]);
					}
				}
			}
			
			else if (exponent[i]!=0) {
				if (i==0) {
					ps.print(coefficient[i]+"x"+exponent[i]);
				}
				else if (i!=0) {
					if(coefficient[i]>0) {
						ps.print("+"+coefficient[i]+"x"+exponent[i]);
					}
					else {
						ps.print(coefficient[i]+"x"+exponent[i]);
					}
				}
			}
			
		}
		ps.close();
	}
}
