package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Total;

public interface TotalOrder<E>
         extends PartialOrder<E>
{
	@Override @Total
	Boolean leq(E a, E b);
}
