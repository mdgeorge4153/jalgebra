package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.properties.meta.MagicCheck;

@MagicCheck
public class Q extends FieldOfFractions<Integer, Z> {
	public final static Q instance = new Q();
	
	private Q() {
		super(Z.instance);
	}
}
