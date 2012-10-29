package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Preserves;
import com.mdgeorge.util.OpUnary;

/**
 * A Set Hom is a funny name for a function: a map taking an element of a domain
 * (D) to a codomain (C).
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Function_%28mathematics%29">Function on Wikipedia</a>
 * @author mdgeorge
 */
public interface SetHom < DE, D extends Set<DE>
                        , CE, C extends Set<CE>
                        >
         extends OpUnary<DE, CE>
{
	D domain();
	C codomain();
	
	@Preserves("Set.eq")
	CE ap (DE e);
}
