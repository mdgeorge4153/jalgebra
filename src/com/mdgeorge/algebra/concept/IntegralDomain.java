package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.NoZeroDividers;

public interface IntegralDomain<E> extends Ring<E> {
	@NoZeroDividers
	E times(E a, E b);
}
