package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.WellFormed;
import com.mdgeorge.algebra.properties.meta.OpUnary;

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
