package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Bilinear;

/**
 * An algebra over a ring R is an R-module with an R-bilinear multiplication
 * operation.  In this library, 
 * 
 * 
 * @see http://en.wikipedia.org/wiki/Associative_algebra
 * @author mdgeorge
 */
public interface Algebra<E, S, R extends Ring<S>>
         extends Module<E, S, R>, Ring<E>
{
	@Override @Bilinear
	E times (E a, E b);
}
