package com.mdgeorge.algebra.adapters;

import com.mdgeorge.algebra.concept.OrderedRing;
import com.mdgeorge.algebra.concept.TotalOrder;

public class OrderedRingAsTotalOrder< E
                                    , R extends OrderedRing<E>>
  implements TotalOrder<E>
{
	private final R r;

	public OrderedRingAsTotalOrder(R r) {
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
