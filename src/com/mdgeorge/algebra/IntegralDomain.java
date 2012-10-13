package com.mdgeorge.algebra;

import com.mdgeorge.algebra.properties.NoZeroDividers;

public interface IntegralDomain<E> extends Ring<E> {
	@NoZeroDividers @Override
	E times(E a, E b);
}
