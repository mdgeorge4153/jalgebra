package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Field;

public final class Zp implements Field<Integer> {

	private int p;
	
	/** p should be prime. */
	public Zp(int p) {
		this.p = p;
	}
	
	@Override
	public Integer one() {
		return 1;
	}
	
	private int reduce(int x) {
		int result = x % p;
		return result < 0 ? p + result : result;
	}

	@Override
	public Integer plus(Integer a, Integer b) {
		return reduce(a+b);
	}

	@Override
	public Integer neg(Integer a) {
		return reduce(-a);
	}

	@Override
	public Integer zero() {
		return 0;
	}

	@Override
	public Boolean eq(Integer a, Integer b) {
		return a == b;
	}

	@Override
	public Integer times(Integer a, Integer b) {
		return reduce(a*b);
	}


	static class Coeffs {
		public int s; public int t;
		public Coeffs(int s, int t) { this.s = s; this.t = t; }
	}
	
	/** return s and t such that gcd(a,b) = sa + tb */
	private static Coeffs bezout(int a, int b) {
		if (b == 0) return new Coeffs(1,0);
		
		int q = a / b; int r = a % b;
		
		Coeffs br = bezout(b,r);
		// a = qb + r,  gcd = sb + tr.  So substituting r = a - qb; gcd = ta + (s-tq)b
		
		int s = br.t;
		int t = br.s - br.t*q;
		br.s = s; br.t = t;
		return br;
	}
	
	@Override
	public Integer inv(Integer a) throws IllegalArgumentException {
		if (a == 0) throw new IllegalArgumentException();
		Coeffs result = bezout(a,p);
		// 1 = sa + tp
		return reduce(result.s);
	}
	
	@Override
	public Integer fromInt(int i) {
		return reduce(i);
	}
}
