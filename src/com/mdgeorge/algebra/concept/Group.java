package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.*;

/**
 * A group is a set paired with an associative operation "plus" which has an
 * identity elemnt "zero", and in which every element has an inverse.  In this
 * library, all Groups are Abelian, which means that + is also commutative.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Abelian_group">Abelian Group on Wikipedia</a>
 * @author mdgeorge
 */
public interface Group <E>
         extends Set   <E>
{
	@Commutative @Associative @Identity("zero") @Inverse("neg")
	E plus(E a, E b);
	
	E neg(E a);
	
	E zero();
	
	default E minus(E a, E b) {
		return plus(a, neg(b));
	}
}
