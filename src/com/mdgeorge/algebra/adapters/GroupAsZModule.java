package com.mdgeorge.algebra.adapters;

import com.mdgeorge.algebra.concept.Group;
import com.mdgeorge.algebra.concept.Module;
import com.mdgeorge.algebra.construction.Z;

public final class GroupAsZModule< E
                                 , G extends Group<E>
                                 >
        implements Module<E, Integer, Z>
{
	private final G g;
	
	public GroupAsZModule(G g) {
		this.g = g;
	}
	
	@Override public E       plus (E a, E b) { return g.plus(a, b); }
	@Override public E       neg  (E a)      { return g.neg(a);     }
	@Override public E       zero ()         { return g.zero();     }
	@Override public Boolean eq   (E a, E b) { return g.eq(a, b);   }

	@Override
	public E smult(Integer n, E a) {
		if (n < 0) {
			n = -n;
			a = neg(a);
		}
		
		E result = g.zero();
		
		for (int i = n; i > 0; i >>= 1, a = g.plus(a, a))
			if ((i & 0x01) == 1)
				result = g.plus(result, a);
		
		return result;
	}

}
