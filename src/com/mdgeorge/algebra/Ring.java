package com.mdgeorge.algebra;

import com.mdgeorge.algebra.properties.*;

public interface Ring<E> extends Group<E> {
	@Associative @Commutative @Identity("one") @DistributesOver("plus")
	E times (E a, E b);
	
	E one();
}
