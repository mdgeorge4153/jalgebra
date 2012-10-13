package com.mdgeorge.algebra;

import com.mdgeorge.algebra.properties.*;

public interface Group<E> extends Set<E> {
	@Commutative @Associative @Identity("zero")
	E plus(E a, E b);
	
	@InverseOf("plus")
	E neg(E a);
	
	E zero();
}
