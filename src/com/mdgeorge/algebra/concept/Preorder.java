package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Reflexive;
import com.mdgeorge.algebra.properties.Transitive;

public interface Preorder<E>
         extends Set<E>
{
	@Reflexive @Transitive
	Boolean leq(E a, E b);
}
