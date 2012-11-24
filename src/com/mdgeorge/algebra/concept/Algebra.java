package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Bilinear;

/**
 * An algebra over a ring R is an R-module with an R-bilinear multiplication
 * operation.  This operation induces a ring structure on the algebra.
 * In this library, all algebras are associative and unital. 
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Associative_algebra">Associative Algebra on Wikipedia</a>
 * @author mdgeorge
 */
public interface Algebra <E, S>
         extends Module  <E, S>
               , Ring    <E>
{
	@Override @Bilinear
	E times (E a, E b);
}
