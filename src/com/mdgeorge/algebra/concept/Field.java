package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Inverse;

/**
 * A field is a (commutative) ring where every non-zero element has a
 * multiplicative inverse.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Field_%28mathematics%29">Field on Wikipedia</a>
 * @author mdgeorge
 */
public interface Field          <E>
         extends IntegralDomain <E>
{
	@Override
	@Inverse("inv")
	E times (E a, E b);

	/** @throws IllegalArgumentException if a is zero. */
	E inv (E a) throws IllegalArgumentException;
}
