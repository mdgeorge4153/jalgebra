package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Total;

/**
 * A totally ordered set is partially ordered set with the property that every
 * two elements are related (i.e. either a ⊑ b or b ⊑ a).
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Total_order">Total order on Wikipedia</a>
 * @see java.util.Comparator
 * @author mdgeorge
 */
public interface TotalOrder<E>
         extends PartialOrder<E>
{
	@Override @Total
	Boolean leq(E a, E b);
}
