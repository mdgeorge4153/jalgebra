package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.AntiSymmetric;

public interface PartialOrder <E>
         extends Preorder<E>
{
	@Override @AntiSymmetric
	Boolean leq(E a, E b);
}
