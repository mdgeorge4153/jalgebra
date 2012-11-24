package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.AntiSymmetric;

/**
 * A partial order (or partially ordered set...POSET for short), is a set with
 * a a relation ⊑ that is reflexive, transitive, and anti-symmetric.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Partially_ordered_set">Partially Ordered Set on Wikipedia</a>
 * @author mdgeorge
 */
public interface PartialOrder <E>
         extends Preorder     <E>
{
	@Override
	@AntiSymmetric
	Boolean leq(E a, E b);
}
