package com.mdgeorge.algebra.adapters;

import com.mdgeorge.algebra.concept.OrderedRing;
import com.mdgeorge.algebra.concept.TotalOrder;

public class OrderedRingAsTotalOrder<E>
  implements TotalOrder<E>
{
	private final OrderedRing<E> r;

	public OrderedRingAsTotalOrder(OrderedRing<E> r) {
		this.r = r;
	}
	
	@Override
	public Boolean eq(E a, E b) {
		return r.eq(a,b); 
	}

	@Override
	public Boolean leq(E a, E b) {
		return r.isNonnegative(r.plus(b, r.neg(a)));
	}
}
