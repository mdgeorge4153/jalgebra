package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Reflexive;
import com.mdgeorge.algebra.properties.Transitive;

/**
 * A pre-order is a set with a reflexitive transitive relation ⊑.  One can form
 * a partial order by taking equivalence classes of the relation ≈ defined by
 * x ≈ y if x ⊑ y and y ⊑ x
 *
 * @see <a href="http://en.wikipedia.org/wiki/Preorder">Preorder on Wikipedia</a>
 * @author mdgeorge
 */
public interface Preorder <E>
         extends Set      <E>
{
	@Reflexive @Transitive
	Boolean leq(E a, E b);
}
