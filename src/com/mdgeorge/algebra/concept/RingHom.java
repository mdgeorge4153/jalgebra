package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.CommutesWith;

public interface RingHom < DE, D extends Ring<DE>
                         , CE, C extends Ring<CE>
                         >
         extends GroupHom<DE, D, CE, C>
{
	@CommutesWith("com.mdgeorge.algebra.concept.Ring.times")
	CE ap (DE e);
}
