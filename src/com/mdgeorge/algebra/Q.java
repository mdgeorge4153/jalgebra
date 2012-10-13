package com.mdgeorge.algebra;

public class Q extends FieldOfFractions<Integer, Z> {
	public final static Q instance = new Q();
	
	private Q() {
		super(Z.instance);
	}
}
