package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Ring;

public class Fraction<N, D, R extends Ring<D>> {
	public  final N num;
	public  final D den;
	private final R r;

	/**
	 * Create a new fraction.
	 *
	 * @param r
	 *        A ring over the denominator type.
	 *       
	 * @throws IllegalArgumentException
	 *        If den is zero in r.
	 */
	public Fraction(N num, D den, R r) throws IllegalArgumentException
	{
		this.num = num;
		this.den = den;
		this.r   = r;
		
		if (r.eq(den, r.zero()))
			throw new IllegalArgumentException("Division by zero");
	}
	
	/**
	 * A string representation of this suitable for display.
	 */
	public String toString() {
		if (r.eq(den, r.one()))
			return num.toString();
		else
			return "(" + num.toString() + " / " + den.toString() + ")";
	}
}
