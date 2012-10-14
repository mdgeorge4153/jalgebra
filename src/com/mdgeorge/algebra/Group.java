package com.mdgeorge.algebra;

import com.mdgeorge.algebra.properties.*;

public interface Group<E> extends Set<E> {
	@Commutative @Associative @Identity("zero") @Inverse("neg")
	E plus(E a, E b);
	
	E neg(E a);
	
	E zero();
}
