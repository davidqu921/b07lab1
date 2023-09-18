public class Polynomial {
	double[] coefficient;
	
	public Polynomial() {
		this.coefficient = new double[1];
		this.coefficient[0]= 0;
	}
	
	public Polynomial(double[] douArray) {
		this.coefficient = new double[douArray.length];
		
		for (int i=0; i < douArray.length; i++) {
			coefficient[i] = douArray[i];
		}
	}
	
	public Polynomial add(Polynomial poly) {
		int this_len = this.coefficient.length;
		int poly_len =  poly.coefficient.length;
		
		double[] newdou = new double[Math.max(this_len, poly_len)];
		
		for (int i =0; i < Math.min(this_len, poly_len); i++) {
			newdou[i] = this.coefficient[i] + poly.coefficient[i];
		}
		
		for (int i = Math.min(this_len, poly_len); i < Math.max(this_len, poly_len); i++) {
			if (this_len > poly_len) {
				newdou[i] = this.coefficient[i]; 
			}
			else {
				newdou[i] = poly.coefficient[i];
			}
		}
		
		Polynomial newpoly = new Polynomial(newdou);
		
		return newpoly;
	}
	
	public double evaluate(double x) {
		double sum = 0;
		
		int len = this.coefficient.length;
		
		for (int coe = 0; coe < len; coe++) {
			sum += this.coefficient[coe] * Math.pow(x, coe);
		}
		
		return sum;
	}
	
	public boolean hasRoot(double r) {
		return this.evaluate(r) == 0;
	}
}