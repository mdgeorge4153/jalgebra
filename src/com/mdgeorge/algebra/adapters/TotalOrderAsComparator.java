package com.mdgeorge.algebra.adapters;

import java.util.Comparator;

import com.mdgeorge.algebra.concept.TotalOrder;

public class TotalOrderAsComparator<E, TO extends TotalOrder<E>>
  implements Comparator<E>
{
	private final TO to;
	
	public TotalOrderAsComparator(TO to) { this.to = to; }
	
	@Override
	public int compare(E a, E b) {
		if (to.eq(a,b))
			return 0;
		
		if (to.leq(a, b))
			return -1;
		
		else
			return 1;
	}
}
