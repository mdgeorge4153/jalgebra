package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.Preserves;

public interface PreorderHom < DE, D extends Preorder<DE>
                                 , CE, C extends Preorder<CE>
                                 >
         extends SetHom<DE, D, CE, C>
{
	@Preserves("com.mdgeorge.algebra.concept.Preorder.leq")
	CE ap (DE e);
}
