package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.CommutesWith;

public interface SetHom < DE, D extends Set<DE>
                        , RE, R extends Set<RE>
                        >
{
	D domain();
	R range();
	
	@CommutesWith("Set.eq")
	RE ap (DE e);
}
