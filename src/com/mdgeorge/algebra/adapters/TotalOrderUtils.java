package com.mdgeorge.algebra.adapters;

import java.util.Comparator;

import com.mdgeorge.algebra.concept.TotalOrder;

public class TotalOrderUtils<E>
  implements Comparator<E>
           , TotalOrder<E>
{
	protected final TotalOrder<E> impl;
	
	public TotalOrderUtils(TotalOrder<E> impl)
	{
		this.impl = impl;
	}
	
	@Override
	public int compare(E a, E b) {
		if (eq(a,b))
			return 0;
		
		if (leq(a, b))
			return -1;
		
		else
			return 1;
	}
	
	public boolean lt(E a, E b) {
		return leq(a, b) && !eq(a, b);
	}
	
	public boolean geq(E a, E b) {
		return leq(b, a);
	}
	
	public boolean gt(E a, E b) {
		return lt(b, a);
	}
	
	public E min (E a, E b) {
		return leq (a, b) ? a : b;
	}
	
	public E max (E a, E b) {
		return leq (a, b) ? b : a;
	}

	/*
	** pass through ************************************************************
	*/

	@Override
	public Boolean eq(E a, E b) {
		return impl.eq(a, b);
	}

	@Override
	public Boolean leq(E a, E b) {
		return impl.leq(a, b);
	}
}
