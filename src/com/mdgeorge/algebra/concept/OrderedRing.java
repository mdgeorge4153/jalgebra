package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.ClosedUnder;
import com.mdgeorge.algebra.properties.ContainsSquares;
import com.mdgeorge.algebra.properties.NoNegOne;

/**
 * <p>An Ordered Ring is a ring coupled with a total order structure that is
 * compatible with the ring operations in the following sense:
 * <ol>
 *     <li>If 0 ≤ a and 0 ≤ b then 0 ≤ ab</li>
 *     <li>If a ≤ b then a + c ≤ b + c</li>
 *     </ol>
 * </p>
 * 
 * <p>This interface uses an equivalent definition based on <em>positive
 * cones</em>. A positive cone is a partition of a ring into negative and
 * nonnegative elements, subject to the following constraints:
 * <ol>
 *     <li>-1 ∉ P</li>
 *     <li>a² ∈ P</li>
 *     <li>P is closed under + and *</li>
 *     </ol>
 * Every positive cone induces a total order and vice-versa.
 * </p>
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Ordered_ring">Ordered Ring on Wikipedia</a>
 * @see <a href="http://en.wikipedia.org/wiki/Ordered_field#Positive_cone">Positive Cone on Wikipedia</a>
 * @author mdgeorge
 */
public interface OrderedRing<E>
         extends Ring<E>
{
	@NoNegOne
	@ContainsSquares
	@ClosedUnder({"plus", "times"})
	Boolean isNonnegative(E e);
}
