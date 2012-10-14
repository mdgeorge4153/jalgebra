package com.mdgeorge.algebra;

import com.mdgeorge.algebra.properties.Inverse;

public interface Field<E> extends IntegralDomain<E> {
	@Inverse("inv")
	E times (E a, E b);
	
	E inv (E a) throws IllegalArgumentException;
}
