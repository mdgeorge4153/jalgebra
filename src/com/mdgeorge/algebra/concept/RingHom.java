package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.CommutesWith;

public interface RingHom <DE, CE>
         extends GroupHom<DE, CE>
{
	@Override
	@CommutesWith("com.mdgeorge.algebra.concept.Ring.times")
	CE ap (DE e);
	
	@Override Ring<DE> domain();
	@Override Ring<CE> codomain();
}
