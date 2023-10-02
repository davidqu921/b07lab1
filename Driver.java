import java.io.File;

public class Driver {
	public static void main(String [] args) throws Exception {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {-15.7, 1.8, -2};
		int [] e1 = {0, 2, 4};
		Polynomial p1 = new Polynomial(c1,e1);
		System.out.println("p1(2) = " + p1.evaluate(2));
		double [] c2 = {3,-1,4};
		int [] e2 = {2,1,3};
		Polynomial p2 = new Polynomial(c2, e2);
		System.out.println("p2(2) = " + p2.evaluate(2));
		double[] c5 = {3.5,-2,1};
		int[] e5 = {2,1,4};
		
		Polynomial p5 = new Polynomial(c5,e5);
		
		Polynomial s1 = p.add(p);
		System.out.println("s1(2) = " + s1.evaluate(2));
		Polynomial s2 = p.add(p5);
		System.out.println("s2(1) = " + s2.evaluate(1));
		
		Polynomial s = p1.add(p2);
		System.out.println("s(2) = " + s.evaluate(2));
		
		Polynomial p6 = p.multiply(p5);
		for(int i = 0; i < p6.coefficient.length;i++  ) {
			System.out.print(p6.coefficient[i]+" ");
		}
		System.out.println("");
		p6.saveToFile("C:/Users/shizh/test3.txt");

		
		if(s.hasRoot(1))
		System.out.println("1 is a root of s");
		else
		System.out.println("1 is not a root of s");
		
		Polynomial p3 = p1.multiply(p2);
		p3.saveToFile("C:/Users/shizh/test4.txt");
		
		System.out.print("the coefficient of p3 are:");
		for(int i = 0; i < p3.coefficient.length;i++  ) {
			System.out.print(p3.coefficient[i]+" ");
		}
		
		System.out.println("");
		System.out.print("the exponent of p3 are:");
		for(int i = 0; i < p3.exponent.length;i++  ) {
			System.out.print(p3.exponent[i]+" ");
		}
		
		System.out.println("");
		System.out.println("The following is a test for constructor that take a file as argument");
		
		File afile = new File("C:/Users/shizh/test.txt");
		Polynomial p4 = new Polynomial(afile);
		System.out.println(p4.coefficient[2]);
		
		
		System.out.print("the coefficient of p4 are:");
		for(int i = 0; i < p4.coefficient.length;i++  ) {
			System.out.print(p4.coefficient[i]+" ");
		}
		System.out.println("");
		
		System.out.print("the exponent of p4 are:");
		for(int i = 0; i < p4.exponent.length;i++  ) {
			System.out.print(p4.exponent[i]+" ");
		}
		
		
		p1.saveToFile("C:/Users/shizh/test2.txt");

	}
}
