package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.CommutesWith;

public interface RingHom < DE, D extends Ring<DE>
                         , RE, R extends Ring<RE>
                         >
         extends GroupHom<DE, D, RE, R>
{
	@CommutesWith("Ring.times")
	RE ap (DE e);
}
