package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.WellFormed;
import com.mdgeorge.algebra.properties.meta.OpUnary;

/**
 * A Set Hom is a funny name for a function: a map taking an element of a domain
 * (D) to a codomain (C).
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Function_%28mathematics%29">Function on Wikipedia</a>
 * @author mdgeorge
 */
public interface SetHom < DE, D extends Set<DE>
                        , RE, R extends Set<RE>
                        >
         extends OpUnary<DE, RE>
{
	D domain();
	R codomain();
	
	@WellFormed
	RE ap (DE e);
}
