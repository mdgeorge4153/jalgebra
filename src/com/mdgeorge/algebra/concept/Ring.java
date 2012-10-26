package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.*;

public interface Ring<E> extends Group<E> {
	@Associative @Commutative @Identity("one") @DistributesOver("plus")
	E times (E a, E b);
	
	E one();
}
