package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Preserves;

public interface PreorderHom <DE, CE>
         extends SetHom      <DE, CE>
{
	@Preserves("com.mdgeorge.algebra.concept.Preorder.leq")
	CE ap (DE e);
	
	@Override
	Preorder<DE> domain   ();
	Preorder<CE> codomain ();
}
