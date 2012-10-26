package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.CommutesWith;

public interface GroupHom < DE, D extends Group<DE>
                          , RE, R extends Group<RE>
                          >
         extends SetHom<DE, D, RE, R>
{
	@CommutesWith("Group.plus")
	RE ap (DE e);
}
