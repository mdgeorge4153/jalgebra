package com.mdgeorge.algebra;

import com.mdgeorge.algebra.properties.InverseOf;

public interface Field<E> extends IntegralDomain<E> {
	@InverseOf("times")
	E inv (E a) throws IllegalArgumentException;
}
